package herrick.fr.advisor.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import herrick.fr.advisor.R;

public class HistoriqueAdapter extends RecyclerView.Adapter<HistoriqueAdapter.HistoriqueCell> {

    private ArrayList<ParseObject> mDatasetParse;
    private Context context;

    public HistoriqueAdapter(ArrayList<ParseObject> data) {
        mDatasetParse = data;
    }

    @NonNull
    @Override
    public HistoriqueAdapter.HistoriqueCell onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View cell = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historique_row_item, viewGroup, false);
        return new HistoriqueCell(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriqueAdapter.HistoriqueCell myViewHolder, int i) {
        ParseObject object = mDatasetParse.get(i);
        ParseFile file = object.getParseFile("photoIn");

        Picasso.get()
                .load(file.getUrl())
                .into(myViewHolder.takenPicture);
        DateFormat format = android.text.format.DateFormat.getDateFormat(context);
        myViewHolder.dateCreation.setText(format.format(object.getCreatedAt()));

        String label = object.getString("label");
        if (label == null || label.equalsIgnoreCase("")){
            // Nom catégorisé
            label = "Aucun label défini pour le moment";
        }
        myViewHolder.label.setText(label);
    }

    @Override
    public int getItemCount() {
        if (mDatasetParse == null) return 0;
        return mDatasetParse.size();
    }



    public static class HistoriqueCell extends RecyclerView.ViewHolder{

        public TextView label;
        public TextView dateCreation;
        public ImageView takenPicture;

        public HistoriqueCell(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.parse_label);
            dateCreation = itemView.findViewById(R.id.date_creation);
            takenPicture = itemView.findViewById(R.id.parse_taken_picture);
        }
    }
}
