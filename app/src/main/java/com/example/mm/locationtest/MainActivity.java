package com.example.mm.locationtest;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private boolean isNetworkLocation, isGPSLocation;

    // SPLASH FRAGMENT TIME
    private final long SPLASH_DISPLAY_LENGTH = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartAnimations();

        LocationManager mListener = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(mListener != null){
            isGPSLocation = mListener.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkLocation = mListener.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                if(isGPSLocation){
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.putExtra("provider", LocationManager.GPS_PROVIDER);
                    startActivity(intent);
                    finish();

                }else if(isNetworkLocation){
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.putExtra("provider", LocationManager.NETWORK_PROVIDER);
                    startActivity(intent);
                    finish();

                }else{
                    //Device location is not set
                    PermissionUtils.LocationSettingDialog.newInstance().show(getSupportFragmentManager(), "Setting");
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l= findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        TextView tv = findViewById(R.id.logo);
        tv.clearAnimation();
        tv.startAnimation(anim);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
