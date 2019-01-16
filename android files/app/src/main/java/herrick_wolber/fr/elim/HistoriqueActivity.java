package herrick_wolber.fr.elim;

import android.os.Bundle;
import android.widget.TextView;

import herrick_wolber.fr.elim.Helpers.BaseActivity;

public class HistoriqueActivity extends BaseActivity {

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTextMessage = findViewById(R.id.message);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_historique;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation;
    }

}
