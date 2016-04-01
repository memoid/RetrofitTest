package com.example.gmoro.retrofittest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gmoro.retrofittest.entities.RelatedTopic;
import com.example.gmoro.retrofittest.entities.Result;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetData getData = new GetData();
        getData.execute();

        listView = (ListView) findViewById(R.id.listview_forecast);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class GetData extends AsyncTask<Void, Void, Result> {

        private static final String DATA_API = "http://api.duckduckgo.com/";

        protected Result doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(DATA_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            DuckService duckService = retrofit.create(DuckService.class);

            Call<Result> listCall = duckService.listCharacters("game of thrones characters");

            Result results = null;

            try {
                results = listCall.execute().body();
            } catch (Exception e) {
                Log.e("Error", "Error: " + e.toString());
            }

            return results;
        }

        @Override
        protected void onPostExecute(Result s) {

            ArrayList<String> data = new ArrayList<>();
            for (RelatedTopic relatedTopic : s.getRelatedTopics()) {
                Log.v("xxx", relatedTopic.getText());
                data.add(relatedTopic.getText());
            }

            arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, data);
            listView.setAdapter(arrayAdapter);

        }
    }

}
