package herrick.fr.advisor.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import herrick.fr.advisor.R;

/**
 * Fragment used to upload file and get label array
 */
public class LabelUploadFragment extends Fragment {


    public LabelUploadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label_upload, container, false);

        return view;
    }

}
