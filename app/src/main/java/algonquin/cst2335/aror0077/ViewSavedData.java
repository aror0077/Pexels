package algonquin.cst2335.aror0077;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.aror0077.sqliteDatabase.StorageDb;
import algonquin.cst2335.aror0077.sqliteDatabase.TableClass;
import aror0077.R;

public class ViewSavedData extends AppCompatActivity {

    private ViewSavedAdapter adapter;
    private ArrayList<ModelResponse.Photo> getPhotosfromdb;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SQLiteDatabase storageDb;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar2);
        constraintLayout = findViewById(R.id.constraint);

        getPhotosfromdb = new ArrayList<>();
        adapter = new ViewSavedAdapter(getPhotosfromdb, this);
        recyclerView.setAdapter(adapter);
        StorageDb dbHelper = new StorageDb(getApplicationContext());


        getphotosfromLocalDB(dbHelper);

    }


    //method to get data from Sqlite
    private void getphotosfromLocalDB(StorageDb dbHelper) {

        progressBar.setVisibility(View.VISIBLE);

        storageDb = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                TableClass.PexelDataBase.COLUMN_NAME_Image,
                TableClass.PexelDataBase.COLUMN_NAME_LargeImage,
                TableClass.PexelDataBase.COLUMN_NAME_Url};


        Cursor cursor = storageDb.query(TableClass.PexelDataBase.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            ModelResponse.Photo photo = new ModelResponse.Photo();
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(TableClass.PexelDataBase._ID));
            photo.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TableClass.PexelDataBase._ID)));
            photo.setSmall(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TableClass.PexelDataBase.COLUMN_NAME_Url))));
            photo.setUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableClass.PexelDataBase.COLUMN_NAME_LargeImage)));
            photo.setPhotographerUrl(cursor.getString(cursor.getColumnIndexOrThrow(TableClass.PexelDataBase.COLUMN_NAME_Image)));
            getPhotosfromdb.add(photo);
        }

        if (getPhotosfromdb.size() > 0) {
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();


    }


}