package algonquin.cst2335.aror0077;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import aror0077.R;

/**
 * @author Jaskaran Singh Arora
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //all the variables are declared
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    Button button;
    ProgressBar progressBar;
    EditText etpexel;
    private ArrayList<ModelResponse.Photo> getPhotos;
    private PhotosAdapter photosAdapter;
    RecyclerView recyclerView;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //method for click on navigation drawer
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //all the variables are initialized

        drawerLayout = findViewById(R.id.my_drawer_layout);
        button = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);
        etpexel = findViewById(R.id.etpexel);
        recyclerView = findViewById(R.id.recyclerView);

        //it will add horizontal line on recyclerview list item
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // it will pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        NavigationView navigationView = findViewById(R.id.navigationVIew);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getPhotos = new ArrayList<>();
        photosAdapter = new PhotosAdapter(getPhotos, this);
        recyclerView.setAdapter(photosAdapter);


        button.setOnClickListener(v -> hitPixelApi());
        getSearchTermFromSharedPreferences(etpexel);
    }


    //it will get search term from sharedPreference
    private void getSearchTermFromSharedPreferences(EditText etpexel) {

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("PexelPref", MODE_APPEND);
        String s1 = sh.getString("searchTerm", "");
        etpexel.setText(s1);
    }
 //method for call api
    private void hitPixelApi() {
        progressBar.setVisibility(View.VISIBLE);
        saveSearchTermInSharedPreference(etpexel.getText().toString().trim());
        String url = "https://api.pexels.com/v1/search?query=" + etpexel.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onResponse: " + response.toString());
                try {
                    JSONArray businessesJson = response.getJSONArray("photos");
                    ArrayList<ModelResponse.Photo> photos = ModelResponse.Photo.fromJson(businessesJson);
                    getPhotos.clear();
                    getPhotos.addAll(photos);
                    photosAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> error.printStackTrace()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "563492ad6f9170000100000147c00744022c4d02bb34b51d7e8c7a69");
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    //it will save search term in sharedPreferences
    private void saveSearchTermInSharedPreference(String searchTerm) {
        SharedPreferences sharedPreferences = getSharedPreferences("PexelPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("searchTerm", searchTerm);
        editor.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  //method for click on navigation drawer
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(MainActivity.this, ViewSavedData.class));
            return true;
        }
        return true;
    }


}
