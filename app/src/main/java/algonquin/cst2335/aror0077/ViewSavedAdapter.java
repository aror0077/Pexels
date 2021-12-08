package algonquin.cst2335.aror0077;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import algonquin.cst2335.aror0077.sqliteDatabase.StorageDb;
import algonquin.cst2335.aror0077.sqliteDatabase.TableClass;
import aror0077.R;

/**
 * @author Jaskaran Singh Arora
 * @version 1.0
 */
public class ViewSavedAdapter extends RecyclerView.Adapter<ViewSavedAdapter.viewHoldervh> {

    private ArrayList<ModelResponse.Photo> getPhotos;
    private Activity activity;
    SQLiteDatabase storageDb;

    public ViewSavedAdapter(ArrayList<ModelResponse.Photo> getPhotos, Activity activity) {
        this.getPhotos = getPhotos;
        this.activity = activity;

    }


    @Override
    public ViewSavedAdapter.viewHoldervh onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.saved_view, parent, false);
        return new ViewSavedAdapter.viewHoldervh(view);
    }

    @Override
    public void onBindViewHolder( ViewSavedAdapter.viewHoldervh holder, @SuppressLint("RecyclerView") int position) {
        ModelResponse.Photo photo = getPhotos.get(position);
        StorageDb dbHelper = new StorageDb(activity);

        Glide.with(activity).load(photo.getSmall())
                .apply(new RequestOptions())
                .into(holder.imageView);

        holder.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(activity)
                        .setTitle("Deletion Confirmation")
                        .setMessage("Do you really want to Delete?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                DeleteDataFromdb(photo, holder.constraintLayout,dbHelper);
                                getPhotos.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

            }
        });
    }

    private void DeleteDataFromdb(ModelResponse.Photo photo, ConstraintLayout constraintLayout, StorageDb dbHelper) {

        storageDb = dbHelper.getWritableDatabase();
        String selection = TableClass.PexelDataBase._ID;
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(photo.getId())};
        // Issue SQL statement.
        int deletedRows = storageDb.delete(TableClass.PexelDataBase.TABLE_NAME, "_id= " + photo.getId() + "", null);
        Snackbar snackbar = Snackbar
                .make(constraintLayout, "Deleted", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public int getItemCount() {
        return getPhotos.size();
    }

    public class viewHoldervh extends RecyclerView.ViewHolder {

        public ImageView imageView, imageView4;
        TextView tvAtuthorName;
        ConstraintLayout constraintLayout;

        public viewHoldervh(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvAtuthorName = itemView.findViewById(R.id.textView);
            constraintLayout = itemView.findViewById(R.id.constraint);
            imageView4 = itemView.findViewById(R.id.imageView4);
        }
    }
}
