package herrick.fr.advisor.Fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import herrick.fr.advisor.R;

public class HWMapsFragment extends Fragment {
    public HWMapsFragment() {}

    private final String TAG = "MapsFragment";
    private ArrayList<ElimPins> elimPins = new ArrayList<>();
    Double centerLat = 43.614922; Double centerLng = 7.071741;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hwmaps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap mMap) {

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers

                ParseUser.becomeInBackground("r:963a9325f0de97c4fc1a29524053a2c7", new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProducts").addDescendingOrder("updatedAt");
                            query.whereEqualTo("user", user);

                            query.findInBackground(new FindCallback<ParseObject>() {

                                public void done(List<ParseObject> objects, ParseException ex) {
                                    if (ex == null) {
                                        Log.i(TAG, objects.size()+"");
                                        for (ParseObject obj : objects) {
                                            // Google VISION results
                                            JSONArray label = obj.getJSONArray("labelResults");
                                            ParseFile file = obj.getParseFile("photoIn");
                                            ParseGeoPoint geo = obj.getParseGeoPoint("positionAssocie");

                                            // Afficher seulement ceux avec une position
                                            if (geo != null) {
                                                try {
                                                    if ( label.length() > 0) {
                                                        String description = label.getJSONObject(0).getString("description");
                                                        elimPins.add(new ElimPins(description, geo, file));
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        fetchPinsData(mMap);

                                    } else {
                                        Log.d(TAG, "Error ("+ex.getCode()+") : " + ex.getMessage());
                                        Toast.makeText(getContext(), "Error ("+ex.getCode()+") : " + ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });



                        }
                        else
                            Log.d("ParseException", "Error: " + e.getMessage() + "Code : " + e.getCode());

                    }
                });

            }
        });

        return rootView;
    }

    private void fetchPinsData(final GoogleMap mMap){

        final LatLngBounds.Builder builder = LatLngBounds.builder();
        //builder.include(new LatLng(centerLat, centerLng));

        for (final ElimPins pin: elimPins) {
            builder.include(new LatLng(pin.getLatitude(), pin.getLongitude()));
            Picasso.get().load(pin.getPictureURL()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    int height = 100; int width = 100;
                    Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(pin.getLatitude(), pin.getLongitude()))
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                            .title(pin.description));
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(pin.getLatitude(), pin.getLongitude()))
                            .title(pin.description));
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }

        LatLngBounds bounds = builder.build();

        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, 10);
        mMap.animateCamera(update);

/*
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4629101,-122.2449094))
                        .title("Iron Man")
                        .snippet("His Talent : Plenty of money"));
*/
    }

}

class ElimPins {

    public String description;
    public ParseGeoPoint geoPoint;
    public ParseFile file;

    ElimPins(){ description = "Description"; geoPoint = new ParseGeoPoint(0,0); file = null; }
    ElimPins(String desc, ParseGeoPoint geo, ParseFile f){ description = desc; geoPoint = geo; file = f; }

    public String getPictureURL() { return file.getUrl(); }
    public Double getLatitude()   { return geoPoint.getLatitude(); }
    public Double getLongitude()  { return geoPoint.getLongitude(); }
}

