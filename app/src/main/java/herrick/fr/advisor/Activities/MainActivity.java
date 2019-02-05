package herrick.fr.advisor.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import herrick.fr.advisor.Base.BaseActivity;
import herrick.fr.advisor.Fragments.HistoriqueFragment;
import herrick.fr.advisor.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HistoriqueFragment())
                .commit();
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
