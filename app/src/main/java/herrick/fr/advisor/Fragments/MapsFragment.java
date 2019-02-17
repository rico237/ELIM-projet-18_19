package herrick.fr.advisor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import herrick.fr.advisor.R;

public class MapsFragment extends Fragment {
    private ArrayList<ParseObject> myDataset = new ArrayList<>();

    public MapsFragment() {

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

                                String description="";
                                try {
                              description=label.getJSONObject(0).getString("description");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                Toast.makeText(getActivity(),"maps"+description,

                                        Toast.LENGTH_LONG).show();
                                Log.d("maps", "Retrieved " + objects.size() + " objects");
                                myDataset.addAll(objects);

                             //   mAdapter.notifyDataSetChanged();
                            } else {
                                Log.d("maps", "Error: " + ex.getMessage());
                            }
                        }
                    });


                    final ParseQuery<ParseObject> query1 = ParseQuery.getQuery("UserProducts").addDescendingOrder("updatedAt");


                    query1.setLimit(15);

                    query1.findInBackground(new FindCallback<ParseObject>() {

                        public void done(List<ParseObject> objects, ParseException ex) {
                            if (ex == null) {

                                //   String label = myDataset.get(0).getString("createdAt");
                                JSONArray label = objects.get(0).getJSONArray("labelResults");

                                String description="";
                                try {
                                    description=label.getJSONObject(0).getString("description");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                Toast.makeText(getActivity(),"maps"+description,

                                        Toast.LENGTH_LONG).show();
                                Log.d("maps", "Retrieved " + objects.size() + " objects");
                                myDataset.addAll(objects);

                                //   mAdapter.notifyDataSetChanged();
                            } else {
                                Log.d("maps", "Error: " + ex.getMessage());
                            }
                        }
                    });









                } else {
                    Log.d("ParseException", "Error: " + e.getMessage() + "Code : " + e.getCode());
                }
            }
        });






        return inflate;
    }

}
