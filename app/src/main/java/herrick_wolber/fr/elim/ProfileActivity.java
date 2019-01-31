package herrick_wolber.fr.elim;

import android.os.Bundle;


import herrick_wolber.fr.elim.Helpers.BaseActivity;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_profile;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_profile;
    }

    @Override
    public int getClickedIndex(){ return 2; }
}
