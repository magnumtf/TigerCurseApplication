package com.buber.tigercurseapplication;

import android.animation.Animator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TigerCurseActivity extends FragmentActivity implements OnMapReadyCallback, LatLngInterpolator {
    private static final String TAG = "TigerCurseActivity";

    private GoogleMap mMap;
    private Marker mKreuznach;
    private Marker mLapland;
    private MarkerAnimation mMarkerAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiger_curse);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMarkerAnimation = new MarkerAnimation();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng kreuznach = new LatLng(49.844, 7.872);
        LatLng lapland = new LatLng(69.1044, 27.1946);
        mKreuznach = mMap.addMarker(new MarkerOptions().position(kreuznach).title("Marker in Kreuznach"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kreuznach));
        Log.i(TAG, "onMapReady: Before Animation");
        mMarkerAnimation.animateMarkerToICS(mKreuznach, lapland, this);
        if(mMarkerAnimation.mAnimator!=null) {
            mMarkerAnimation.mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.i(TAG, "Listener: Start Animation");
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    Log.i(TAG, "Listener: End Animation");
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    Log.i(TAG, "Listener: Cancel Animation");
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    Log.i(TAG, "Listener: Repeat Animation");
                }
            });
        }
//        mMarkerAnimation.animateMarkerToICS(mKreuznach, kreuznach, this);
    }

    @Override
    public LatLng interpolate(float fraction, LatLng a, LatLng b) {
        double lat = (b.latitude - a.latitude) * fraction + a.latitude;
        double lngDelta = b.longitude - a.longitude;

        // Take the shortest path across the 180th meridian.
        if (Math.abs(lngDelta) > 180) {
            lngDelta -= Math.signum(lngDelta) * 360;
        }
        double lng = lngDelta * fraction + a.longitude;
        return new LatLng(lat, lng);
    }
}
