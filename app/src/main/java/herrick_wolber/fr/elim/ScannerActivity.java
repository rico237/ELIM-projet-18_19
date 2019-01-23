package herrick_wolber.fr.elim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import herrick_wolber.fr.elim.Helpers.BaseActivity;

public class ScannerActivity extends BaseActivity {

    private Button scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanButton = findViewById(R.id.button_scan);
        scanButton.setOnClickListener(mButtonListener);
    }

    private Button.OnClickListener mButtonListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ScannerActivity.this, PrisePhotoActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_scanner;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation;
    }
}
