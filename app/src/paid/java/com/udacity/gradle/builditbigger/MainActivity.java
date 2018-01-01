package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jokesandroidlibrary.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static SimpleIdlingResource idlingResource;
    /** Variable used to save the joke received after the async task for the IdlingResource test */
    public String jokeReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public SimpleIdlingResource getIdlingResource() {
        if (idlingResource == null)
            idlingResource = new SimpleIdlingResource();
        return idlingResource;
    }

    @Override
    protected void onStart() {
        super.onStart();
        idlingResource = getIdlingResource();
    }

    public void tellJoke(View view) {
        new JokesAsyncTask().execute();
    }


    public class JokesAsyncTask extends AsyncTask<Void, Void, String> {

        private MyApi myApiService = null;

        public JokesAsyncTask() {
        }

        @Override
            protected void onPreExecute() {
            super.onPreExecute();
            idlingResource.setIdleState(false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://192.168.43.181:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                return myApiService.getJoke().execute().getJoke();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String joke) {
            idlingResource.setIdleState(true);
            jokeReceived = joke;
            Intent intent = new Intent(MainActivity.this, JokeActivity.class);
            intent.putExtra("joke", joke);
            startActivity(intent);
        }
    }
}
