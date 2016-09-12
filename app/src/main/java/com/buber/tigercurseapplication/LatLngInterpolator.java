package com.buber.tigercurseapplication;

/**
 * Created by o on 9/11/2016.
 */

import com.google.android.gms.maps.model.LatLng;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

public interface LatLngInterpolator {
    public LatLng interpolate(float fraction, LatLng a, LatLng b);

}
