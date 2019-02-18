package herrick.fr.advisor.Base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import herrick.fr.advisor.Activities.LabellingActivity;
import herrick.fr.advisor.Fragments.HWMapsFragment;
import herrick.fr.advisor.Fragments.HistoriqueFragment;
import herrick.fr.advisor.Fragments.MapsFragment;
import herrick.fr.advisor.Fragments.SimpleScannerFragment;
import herrick.fr.advisor.R;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    protected ActionBar toolbar;

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                toolbar.setTitle(R.string.toolbar_historique);
                startFragment(new HistoriqueFragment());
                return true;
            case R.id.navigation_dashboard:
                toolbar.setTitle(R.string.toolbar_photo);
                startFragment(new SimpleScannerFragment());
                return true;
            case R.id.navigation_notifications:
                toolbar.setTitle(R.string.toolbar_profil);
                //startFragment(new MapsFragment());
                startFragment(new HWMapsFragment());
                return true;
        }
        return false;
    }

    boolean startFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        toolbar = getSupportActionBar();
        navigationView = findViewById(getNavigationMenuItemId());
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigationView.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

    @Override
    protected void onStart() {
        super.onStart();
        selectBottomNavigationBarItem(getNavigationMenuItemId());
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
}
