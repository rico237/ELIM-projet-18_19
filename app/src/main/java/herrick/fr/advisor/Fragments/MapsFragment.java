package herrick.fr.advisor.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.json.Json;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import herrick.fr.advisor.Adapter.ArticleAdapter;
import herrick.fr.advisor.Adapter.HistoriqueAdapter;
import herrick.fr.advisor.R;

public class MapsFragment extends Fragment
{
    private ArrayList<ParseObject> myDataset = new ArrayList<>();
    private ArrayList<ParseObject> myDatasetForAdapter = new ArrayList<>();
private RecyclerView.Adapter mAdapter;
 public static ArrayList<ParseObject> getAllArticlesWithLabel= new ArrayList<>();

   public static String description="";
    public MapsFragment()
    {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

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


 return inflate;
    }

}
