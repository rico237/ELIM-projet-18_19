package herrick.fr.advisor.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import herrick.fr.advisor.Activities.PrisePhotoActivity;
import herrick.fr.advisor.R;

public class SimpleScannerFragment extends Fragment {

    public SimpleScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_simple_scanner, container, false);

        Button scannerButton = inflate.findViewById(R.id.button_simple_scanner);
        scannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(inflate.getContext(), PrisePhotoActivity.class));
            }
        });

        return inflate;
    }

}
