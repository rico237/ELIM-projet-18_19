package herrick.fr.advisor.Fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.cloud.ByteArray;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import herrick.fr.advisor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HWMapsFragment extends Fragment {
    private ArrayList<ParseObject> myDataset = new ArrayList<>();
    private ArrayList<ParseObject> myDatasetForAdapter = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    public static ArrayList<ParseObject> getAllArticlesWithLabel= new ArrayList<>();

    public static String description="";
    public HWMapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hwmaps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers




                ParseUser.becomeInBackground("r:963a9325f0de97c4fc1a29524053a2c7", new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null) {
                            final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProducts").addDescendingOrder("updatedAt");
                            query.whereEqualTo("user", user);

                            query.setLimit(1);

                            query.findInBackground(new FindCallback<ParseObject>() {

                                public void done(List<ParseObject> objects, ParseException ex) {
                                    if (ex == null) {

                                        //   String label = myDataset.get(0).getString("createdAt");
                                        JSONArray label = objects.get(0).getJSONArray("labelResults");


                                        try {
                                            description=label.getJSONObject(0).getString("description");
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                        }
                                        //  Toast.makeText(getActivity(),"maps"+description,Toast.LENGTH_LONG).show();
                                        Log.d("maps", "Retrieved " + objects.size() + " objects");
                                        //   myDataset.addAll(objects);

                                        //   mAdapter.notifyDataSetChanged();
                                    } else {
                                        Log.d("maps", "Error: " + ex.getMessage());
                                    }
                                }
                            });


                            final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UserProducts").addDescendingOrder("updatedAt");





                            query1.findInBackground(new FindCallback<ParseObject>() {

                                public void done(List<ParseObject> objects, ParseException ex) {
                                    if (ex == null) {


                                        int a = objects.size();
                                        for (int i=0; i<a; i++)
                                        {
                                            String er=""; String za="";
                                            ParseGeoPoint e= null;

                                            try
                                            {
                                                za= Double.toString(objects.get(i).getParseGeoPoint("positionAssocie").getLatitude());
                                                er=za;
                                                if(er!="")
                                                {
                                                    //Toast.makeText(getActivity(), "i"+i+"er" + er,Toast.LENGTH_LONG).show();
                                                    myDataset.add(objects.get(i)) ;
                                                }




                                            }

                                            catch(Exception ez)
                                            {
                                                ez.printStackTrace();
                                            }
                                            //er=Double.toString(e.getLatitude()) ;



                                        }

                                        for(int z=0; z<myDataset.size();z++)

                                        {
                                            JSONArray label = myDataset.get(z).getJSONArray("labelResults");

                                            try {
                                                String  descri = label.getJSONObject(0).getString("description");

                                                if(descri.equals(description))
                                                {
                                                    //Toast.makeText(getActivity(), "meme description",Toast.LENGTH_LONG).show();
                                                    getAllArticlesWithLabel.add(myDataset.get(z));
                                                }

                                            }catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }



                                        }

/*try {
                                    myDatasetForAdapter.addAll(getAllArticlesWithLabel);
                                    mAdapter.notifyDataSetChanged();
                                }
                                catch (Exception ea)
                                {
                                    ea.printStackTrace();
                                }
                                RecyclerView mRecyclerView1 = getView().findViewById(R.id.products_recycler);
                                mRecyclerView1.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                                mRecyclerView1.setLayoutManager(mLayoutManager);
                                mAdapter = new ArticleAdapter(myDatasetForAdapter);
                                mRecyclerView1.setAdapter(mAdapter);

*/

                                    } else {
                                        Log.d("maps", "Error: " + ex.getMessage());
                                    }
                                }
                            });

                            query1.setLimit(15);
                        } else {
                            Log.d("ParseException", "Error: " + e.getMessage() + "Code : " + e.getCode());
                        }
                    }
                });

                /*----------------- FAIT TES MODIFICATIONS ICI -------------------*/




try {

    CameraPosition googlePlex = CameraPosition.builder()
            .target(new LatLng(getAllArticlesWithLabel.get(0).getParseGeoPoint("positionAssocie").getLatitude(), getAllArticlesWithLabel.get(0).getParseGeoPoint("positionAssocie").getLongitude()))
            .zoom(10)
            .bearing(0)
            .tilt(45)
            .build();

    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);
    for (int zz = 0; zz < getAllArticlesWithLabel.size(); zz++) {
        ParseFile file = getAllArticlesWithLabel.get(zz).getParseFile("photoIn");
       // Bitmap bit;
        ImageView e=new ImageView(getActivity());

    Picasso.get()
            .load(file.getUrl())
            .into(e);
      //  BitmapDrawable drawable = (BitmapDrawable) e.getDrawable();
     byte[] bit=file.getData();
Bitmap bitmap=BitmapFactory.decodeByteArray(bit, 0, bit.length);
        int height = 100;
        int width = 100;
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(getAllArticlesWithLabel.get(zz).getParseGeoPoint("positionAssocie").getLatitude(), getAllArticlesWithLabel.get(zz).getParseGeoPoint("positionAssocie").getLongitude()))
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                .title(description)
        );

    }
}
catch (Exception ez)
{
    ez.printStackTrace();
}

/*
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.4629101,-122.2449094))
                        .title("Iron Man")
                        .snippet("His Talent : Plenty of money"));

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.3092293,-122.1136845))
                        .title("Captain America"));





*/





                /*--------------------------------------------------------------------------*/

            }
        });


        return rootView;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
