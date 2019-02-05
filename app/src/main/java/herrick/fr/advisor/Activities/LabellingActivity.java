package herrick.fr.advisor.Activities;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import herrick.fr.advisor.Fragments.LabelUploadFragment;
import herrick.fr.advisor.R;

public class LabellingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labelling);

        getActionBar().setTitle("Recherche du produit");

        switchFragment(new LabelUploadFragment());
    }

    private void switchFragment(Fragment fragment){

        try {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            if (getSupportFragmentManager().findFragmentById(R.id.fragment_container_label) == null) {
                ft.add(R.id.fragment_container_label, fragment);
            } else {
                ft.replace(R.id.fragment_container_label, fragment);
            }
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
