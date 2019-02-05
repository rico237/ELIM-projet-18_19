package herrick.fr.advisor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import herrick.fr.advisor.Adapter.HistoriqueAdapter;
import herrick.fr.advisor.R;

public class HistoriqueFragment extends Fragment {

    private ArrayList<ParseObject> myDataset = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_historique, container, false);
        fetchDataFromParse();

        RecyclerView mRecyclerView = v.findViewById(R.id.historique_recycler);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HistoriqueAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    private void fetchDataFromParse(){
        ParseUser.becomeInBackground("r:963a9325f0de97c4fc1a29524053a2c7", new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProducts");
                    query.whereEqualTo("user", user);
                    query.setLimit(30);

                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> objects, ParseException ex) {
                            if (ex == null) {
                                Log.d("Historique", "Retrieved " + objects.size() + " objects");
                                myDataset.addAll(objects);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                Log.d("Historique", "Error: " + ex.getMessage());
                            }
                        }
                    });
                } else {
                    Log.d("ParseException", "Error: " + e.getMessage() + "Code : " + e.getCode());
                }
            }
        });
    }

}
