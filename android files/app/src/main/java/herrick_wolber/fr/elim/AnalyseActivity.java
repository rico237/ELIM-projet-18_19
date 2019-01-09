package herrick_wolber.fr.elim;

import java.util.concurrent.Future;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class AnalyseActivity extends AppCompatActivity {
    private Button takePictureButton;
    private ImageView imageView;
private Button uploadImg;
    private Uri file;
    private TextView mTextMessage;

    static int TAKE_PICTURE = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        takePictureButton = (Button) findViewById(R.id.button_image);
        imageView = (ImageView) findViewById(R.id.imageview);

      /*  takePictureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File f = new File(path);

                Future uploading = Ion.with(MainActivity.this)
                        .load("http://192.168.150.1:7800/upload")
                        .setMultipartFile("image", f)
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                try {
                                    JSONObject jobj = new JSONObject(result.getResult());
                                    Toast.makeText(getApplicationContext(), jobj.getString("response"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });
            }

        });
*/
        uploadImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }




        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap)intent.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setVisibility(View.VISIBLE);



        }
    }
}