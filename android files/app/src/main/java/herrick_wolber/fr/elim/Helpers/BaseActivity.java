package herrick_wolber.fr.elim.Helpers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;

import herrick_wolber.fr.elim.HistoriqueActivity;
import herrick_wolber.fr.elim.ProfileActivity;
import herrick_wolber.fr.elim.R;
import herrick_wolber.fr.elim.ScannerActivity;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;
    protected ActionBar toolbar;

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        if (item.getItemId() != getNavigationMenuItemId()) {
            Intent intentToShow;
            switch (item.getItemId()) {
                case R.id.navigation_historique:
                    intentToShow = new Intent(this, HistoriqueActivity.class);
                    startActivity(intentToShow);
                    toolbar.setTitle(R.string.title_historique);
                    break;
                case R.id.navigation_photo:
                    intentToShow = new Intent(this, ScannerActivity.class);
                    startActivity(intentToShow);
                    toolbar.setTitle(R.string.title_prise_photo);
                    break;
                case R.id.navigation_profil:
                    intentToShow = new Intent(this, ProfileActivity.class);
                    startActivity(intentToShow);
                    toolbar.setTitle(R.string.title_profil);
                    break;
            }
            finish();
        }
        return true;
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
