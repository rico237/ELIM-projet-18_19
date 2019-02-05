package herrick.fr.advisor.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import herrick.fr.advisor.Base.BaseActivity;
import herrick.fr.advisor.Fragments.HistoriqueFragment;
import herrick.fr.advisor.R;

public class MainActivity extends BaseActivity {

/*    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_historique);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_prise_photo);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_profil);
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HistoriqueFragment())
                .commit();

        /*mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation;
    }

}
