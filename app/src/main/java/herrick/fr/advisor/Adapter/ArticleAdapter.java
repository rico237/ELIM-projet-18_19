package herrick.fr.advisor.Adapter;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import herrick.fr.advisor.R;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.ArrayList;

import herrick.fr.advisor.Fragments.MapsFragment;

/**
 * Created by Inessa on 18/02/2019.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ProductCell> {

    private ArrayList<ParseObject> mDatasetParse;
    private Context context;





    public ArticleAdapter(ArrayList<ParseObject> data) {

        mDatasetParse = data;

    }




    @NonNull
    @Override
    public ArticleAdapter.ProductCell onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();
        View cell = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.corresponding_articles_row_item, viewGroup, false);
        return new ProductCell(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ProductCell myViewHolder, int i) {

        ParseObject object = mDatasetParse.get(i);
        ParseFile file = object.getParseFile("photoIn");


        Picasso.get()
                .load(file.getUrl())
                .into(myViewHolder.takenPicture);



        String label = MapsFragment.description;
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



    public static class ProductCell extends RecyclerView.ViewHolder{

        public TextView label;

        public ImageView takenPicture;

        public ProductCell(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.productProp);

            takenPicture = itemView.findViewById(R.id.article);

        }
    }
}
