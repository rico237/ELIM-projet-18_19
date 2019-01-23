package herrick_wolber.fr.elim;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.HistoriqueViewHolder> {
    private static final String TAG = "HistoriqueAdapter";
    private List<ParseObject> mDataset;

    @NonNull
    @Override
    public HistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.historique_single_item, parent, false);
        return new HistoriqueViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriqueViewHolder holder, int position) {
        if (mDataset.isEmpty()){
            holder.mTextView.setText("No data");
        } else {
            holder.mTextView.setText(mDataset.get(position).getObjectId());
        }
    }

    @Override
    public int getItemCount() {
        if (mDataset == null)
            return 0;
        else
            return mDataset.size();
    }

    public static class HistoriqueViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public Button button;
        public HistoriqueViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textview);
            button = v.findViewById(R.id.button2);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
        }
    }

    public HistoriqueAdapter(List<ParseObject> p0) {
        mDataset = p0;
    }
}
