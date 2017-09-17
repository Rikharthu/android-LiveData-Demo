package com.example.uberv.livedatademo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationLiveData extends LiveData<Location> {

    private static LocationLiveData sInstance;
    private LocationManager mLocationManager;

    public static LocationLiveData getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocationLiveData(context.getApplicationContext());
        }
        return sInstance;
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setValue(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            // Do nothing
            Log.d("LocationListener", s);
        }

        @Override
        public void onProviderEnabled(String s) {
            // Do nothing
            Log.d("LocationListener", s);
        }

        @Override
        public void onProviderDisabled(String s) {
            // Do nothing
            Log.d("LocationListener", s);
        }
    };

    private LocationLiveData(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, mLocationListener);
    }


    @Override
    protected void onInactive() {
        mLocationManager.removeUpdates(mLocationListener);
    }
}
