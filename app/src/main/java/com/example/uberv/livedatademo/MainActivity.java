package com.example.uberv.livedatademo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationLiveData locationLiveData = LocationLiveData.getInstance(this);
        locationLiveData.observe(this, location -> {
            Log.d("MyActivity", "Location: " + location.toString());
        });
        LiveData<String> latitude = Transformations.map(locationLiveData, location -> "Latitude: " + location.getLatitude());
        latitude.observe(this, lat -> {
            Log.d("MyActivity", lat);
        });

        Transformations.switchMap(locationLiveData, location -> {
            Log.d("MyActivity", "Backing livedata");
            return new LiveData<String>() {
                @Override
                protected void onActive() {
                    super.onActive();
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                                Log.d("TAG", "tick");
                                handler.postDelayed(this, 1000);
                            }
                            , 1000);
                }

                @Override
                protected void onInactive() {
                    super.onInactive();
                }
            };
        }).observe(this, loc -> {
            Log.d("MyActivity", "Backed:" + loc);
        });
    }
}
