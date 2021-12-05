package com.example.app_nativa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_nativa.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class LocalNewsActivity extends BaseActivity implements LocationListener {

    protected RecyclerView mRecyclerView;
    protected AdapterMasterDetail mAdapter;
    protected RequestQueue requestQueue;
    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.name_title_localsnews);
        listaNoticias = new ArrayList<>();
        requestQueue= Volley.newRequestQueue(this);

        mRecyclerView = findViewById(R.id.recyclerViewLocals);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getLocationCountry();
        db= new DBHelper(this);
    }

    public void getLocationCountry() {
        Double latitude;
        Double longitude;
        String coords = null;


        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        //check the network permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0, this);

        if (locationManager != null) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                coords = latitude + "+" + longitude;
            }

        }

        String url_geocode = "https://api.opencagedata.com/geocode/v1/json?q=" + coords + "&key=3813ad361a624db9acbf1ce7e921c3f8";

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url_geocode, null,
                response -> {

                    try {
                        JSONArray mJsonArray = response.getJSONArray("results");
                        JSONObject objectJson = mJsonArray.getJSONObject(0);

                        JSONObject itemJson=objectJson.getJSONObject("components");

                        String country_code = itemJson.getString("country_code");

                        getLocalNews(country_code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                },
                error -> {
                    Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                });
        requestQueue.add(objectRequest);

    }

    public void getLocalNews(String country_code) throws JSONException {

        /*
        String url_api = "http://api.mediastack.com/v1/news?access_key=d67e5f39b3825efab82f83e260ae52ca"+"&limit=50"+"&countries="+country_code;

        JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.GET, url_api,null,
                response -> {
                    try {
                        JSONArray mJsonArray = response.getJSONArray("data");

                        for (int i=0; i<=mJsonArray.length();i++)
                        {
                            JSONObject itemJson = mJsonArray.getJSONObject(i);
                            String image = itemJson.getString("image");
                            if (image != "null") {
                                String title = itemJson.getString("title");
                                String description = itemJson.getString("description");
                                if (title != description) {
                                    String author = itemJson.getString("author");
                                    String country = itemJson.getString("country");
                                    String category = itemJson.getString("category");
                                    String lang = itemJson.getString("language");
                                    String published_at = itemJson.getString("published_at");
                                    String source = itemJson.getString("source");
                                    String url = itemJson.getString("url");

                                    PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title, author, description, image, country, url, lang, source, category, published_at);

                                    listaNoticias.add(item);

                                    mAdapter=new AdapterMasterDetail(listaNoticias);

                                    mAdapter.setOnClickListener(v -> {
                                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                                        intent.putExtra("title", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getTitle());
                                        intent.putExtra("description", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getDescription());
                                        intent.putExtra("image", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getImage());
                                        intent.putExtra("author", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getAuthor());
                                        intent.putExtra("source", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getSource());
                                        intent.putExtra("country", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getCountry());
                                        intent.putExtra("category", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getCategory());
                                        intent.putExtra("published_at", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getPublished_at());
                                        intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
                                        startActivity(intent);
                                    });

                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
            Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
        });


        requestQueue.add(objectRequest);
*/


        JSONArray arrayJson= new JSONArray();

        JSONObject objetoJson1 = new JSONObject();
        objetoJson1.put("title", "Ingresó a una casa y manoseó a una joven");
        objetoJson1.put("author", "Redacción LA");
        objetoJson1.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
        objetoJson1.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
        objetoJson1.put("country", "ar");
        objetoJson1.put("category", "general");
        objetoJson1.put("language", "es");
        objetoJson1.put("published_at", "2021-08-01T01:16:09+00:00");
        objetoJson1.put("source", "losandes");
        objetoJson1.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        JSONObject objetoJson2= new JSONObject();
        objetoJson2.put("title", "Lo que dejó la natación: las consagraciones de Dressel y McKeon");
        objetoJson2.put("author", "Redacción LA");
        objetoJson2.put("description", "EE.UU. dominó entre los hombres y Australia entre las mujeres");
        objetoJson2.put("image", "https://images.pagina12.com.ar/styles/width960/public/2021-08/182414-000-9hd8y4.jpg?itok=jbcngzNx");
        objetoJson2.put("country", "ar");
        objetoJson2.put("category", "general");
        objetoJson2.put("language", "es");
        objetoJson2.put("published_at", "2021-09-01T01:16:09+00:00");
        objetoJson2.put("source", "cronica");
        objetoJson2.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        JSONObject objetoJson3 = new JSONObject();
        objetoJson3.put("title", "Ingresó a una casa y manoseó a una joven");
        objetoJson3.put("author", "Redacción LA");
        objetoJson3.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
        objetoJson3.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
        objetoJson3.put("country", "ar");
        objetoJson3.put("category", "general");
        objetoJson3.put("language", "es");
        objetoJson3.put("published_at", "2021-08-01T01:16:09+00:00");
        objetoJson3.put("source", "losandes");
        objetoJson3.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        //guardo en arreglo los elementos
        arrayJson.put(objetoJson1);
        arrayJson.put(objetoJson2);
        arrayJson.put(objetoJson3);

        for (int i=0; i<=arrayJson.length();i++)
        {
            JSONObject mJsonObject =  arrayJson.getJSONObject(i);
            String title = mJsonObject.getString("title");
            String author = mJsonObject.getString("author");
            String description = mJsonObject.getString("description");
            String image = mJsonObject.getString("image");
            String country = mJsonObject.getString("country");
            String category = mJsonObject.getString("category");
            String language = mJsonObject.getString("language");
            String published_at = mJsonObject.getString("published_at");
            String source = mJsonObject.getString("source");
            String url = mJsonObject.getString("url");

            PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);

            listaNoticias.add(item);
            mAdapter=new AdapterMasterDetail(listaNoticias);

            mAdapter.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                intent.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
                startActivity(intent);
            });

            mRecyclerView.setAdapter(mAdapter);
        }

    }

    @Override
    int getContentViewId() {
        return R.layout.activity_local_news;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.LocalNewsActivity;
    }

    @Override
    AdapterMasterDetail getAdapter() {
        return mAdapter;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case 101:
                PlaceholderContent.PlaceholderItem favourite= mAdapter.getItemByPosition(item.getGroupId());
                db.insertFavourite(favourite);
                Toast.makeText(getApplicationContext(), R.string.add_favourites, Toast.LENGTH_SHORT).show();
                return true;

            case 102:
                shareNoticia(mAdapter.getItemByPosition(item.getGroupId()));
                return true;
        }
        return false;
    }


}