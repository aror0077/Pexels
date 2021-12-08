package algonquin.cst2335.aror0077;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import aror0077.R;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolderVh> {

    private ArrayList<ModelResponse.Photo> getPhotos;
    private Activity activity;

    public PhotosAdapter(ArrayList<ModelResponse.Photo> getPhotos, Activity activity) {
        this.getPhotos = getPhotos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolderVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_item_view, parent, false);
        return new PhotosAdapter.ViewHolderVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderVh holder, int position) {
        ModelResponse.Photo photo = getPhotos.get(position);

        holder.definition.setText(photo.getPhotographer());
        Glide.with(activity).load(photo.getSrc().getSmall())
                .apply(new RequestOptions())
                .into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToLocalDatabse(photo);
            }
        });

    }

    private void saveToLocalDatabse(ModelResponse.Photo photo) {
        Intent intent = new Intent(activity, DetailsFragment.class);
        intent.putExtra("ImageDetails", photo.getSrc().getPortrait());
        intent.putExtra("smallImage", photo.getSrc().getSmall());
        intent.putExtra("PhotoGrapher", photo.getPhotographer());
        intent.putExtra("url", photo.getUrl());
        activity.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return getPhotos.size();
    }

    public class ViewHolderVh extends RecyclerView.ViewHolder {
        public ImageView imageView;
        TextView definition;
        ConstraintLayout constraintLayout;

        public ViewHolderVh(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            definition = itemView.findViewById(R.id.textView);
            constraintLayout = itemView.findViewById(R.id.constraint);
        }
    }
}
