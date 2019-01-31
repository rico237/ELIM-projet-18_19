package herrick_wolber.fr.elim;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import herrick_wolber.fr.elim.Helpers.BaseActivity;

public class HistoriqueActivity extends BaseActivity {

    private List<ParseObject> myDataset;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = findViewById(R.id.historiqueRecycler);
        mRecyclerView.setHasFixedSize(true);

        myDataset = new ArrayList<>();

        fetchDataSet();

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HistoriqueAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fetchDataSet(){
        ParseUser.becomeInBackground("r:963a9325f0de97c4fc1a29524053a2c7", new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // The current user is now set to user.
                    final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProducts");
                    query.whereEqualTo("user", user);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                // Success
                                myDataset.clear();
                                for (ParseObject obj : objects) {
                                    myDataset.add(obj);
                                }
                                mAdapter.notifyDataSetChanged();
                            } else {
                                // Error
                                Toast.makeText(HistoriqueActivity.this, "Erreur dans la récupération des données", Toast.LENGTH_SHORT);
                            }
                        }
                    });
                } else {
                    // The token could not be validated.
                    Toast.makeText(HistoriqueActivity.this, "Erreur token", Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_historique;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_histo;
    }

    @Override
    public int getClickedIndex(){ return 0; }

}
