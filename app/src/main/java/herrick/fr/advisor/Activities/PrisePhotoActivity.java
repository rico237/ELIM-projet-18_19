package herrick.fr.advisor.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.camerakit.CameraKitView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.jetbrains.annotations.NotNull;

import herrick.fr.advisor.R;

public class PrisePhotoActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private CameraKitView cameraKitView;
    private Button takePhotoButton;

    private static final int MY_GPS_REQUEST_CODE = 100;
    private boolean isInStore = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location lastLocation;
    private LocationManager manager;
    private GoogleApiClient googleApiClient;

    private final LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Position", location.toString());
            lastLocation = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_prise_photo);
        cameraKitView = findViewById(R.id.camera);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        takePhotoButton = findViewById(R.id.button_capture);
        takePhotoButton.setOnClickListener(mCaptureListener);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
    }

    private void capturePhoto(){
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {

                ParseUser.becomeInBackground("r:963a9325f0de97c4fc1a29524053a2c7", new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        ParseFile photo = new ParseFile("photo.jpg", capturedImage);
                        ParseObject object = new ParseObject("UserProducts");
                        object.put("photoIn", photo);
                        object.put("user", user);

                        if (lastLocation != null && isInStore) {
                            object.put("positionAssocie", new ParseGeoPoint(lastLocation.getLatitude(), lastLocation.getLongitude()));
                        }

                        Log.d("Capture image", "Is in store value : "+ isInStore);

                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null){
                                    // Success
                                    Toast.makeText(PrisePhotoActivity.this, "Save with success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                    /*photo.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            // Handle success or error

                        }
                    }, new ProgressCallback() {
                        @Override
                        public void done(Integer percentDone) {
                            // Update your progress spinner here. percentDone will be between 0 and 100.
                        }
                    });*/
            }
        });
    }

    private Button.OnClickListener mCaptureListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            AlertDialog.Builder builder = new AlertDialog.Builder(PrisePhotoActivity.this);
            builder.setTitle("Analyse de l'image").setMessage("Etes vous dans un magasin ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    isInStore = true;
                    askForPermission();
                    capturePhoto();
                    dialogInterface.dismiss();
                }
            });

            builder.setNegativeButton("Non", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    capturePhoto();
                    dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

    private void askForPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PrisePhotoActivity.this);
            builder.setTitle("Demande de position").setMessage("Nous avons besoins de votre position pour savoir où votre photo à été prise afin d'aider les autres utilisateurs");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCompat.requestPermissions(PrisePhotoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_GPS_REQUEST_CODE);
                    updateLocation();
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            updateLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void updateLocation(){
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(PrisePhotoActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lastLocation = location;
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            updateLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
