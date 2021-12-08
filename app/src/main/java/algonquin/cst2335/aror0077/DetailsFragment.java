package algonquin.cst2335.aror0077;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import algonquin.cst2335.aror0077.sqliteDatabase.StorageDb;
import algonquin.cst2335.aror0077.sqliteDatabase.TableClass;
import aror0077.R;


public class DetailsFragment extends AppCompatActivity {

    //declared all the variables
    String image, photographer, url, smallImage;
    TextView urlTv, PhotographerTv;
    ImageView imageView;
    Button button2;
    StorageDb storageDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details);

        //initialized all the variables

        urlTv = findViewById(R.id.textView3);
        PhotographerTv = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView2);
        button2 = findViewById(R.id.button2);
        storageDb = new StorageDb(getApplicationContext()); //initialized the database
        Intent intent = getIntent();

        image = intent.getStringExtra("ImageDetails");
        photographer = intent.getStringExtra("PhotoGrapher");
        url = intent.getStringExtra("url");
        smallImage = intent.getStringExtra("smallImage");

        Log.d("TAG", "onCreate: " + photographer);

        urlTv.setText(url);
        PhotographerTv.setText(photographer);
        Glide.with(getApplicationContext()).load(image)
                .apply(new RequestOptions())
                .into(imageView);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageInDataBase();
            }
        });


    }

    //method for save data in sqlite
    private void saveImageInDataBase() {
        SQLiteDatabase db = storageDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableClass.PexelDataBase.COLUMN_NAME_Image, image);
        values.put(TableClass.PexelDataBase.COLUMN_NAME_LargeImage, url);
        values.put(TableClass.PexelDataBase.COLUMN_NAME_Url, smallImage);

        long row = db.insert(TableClass.PexelDataBase.TABLE_NAME, null, values);

        if (row > 0) {
            Toast.makeText(getApplicationContext(), "Saved Locally", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Something wrong! try again", Toast.LENGTH_SHORT).show();
        }


    }
}