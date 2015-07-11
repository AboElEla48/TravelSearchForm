package foureg.eg.travelsearchform.utils;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by aboelela on 7/10/15.
 */
public class GPSUtil {

    /**
     * Calculate the distance between two locations
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        double  R = 6371; // Radius of the earth in km
        double  dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return Math.abs(d);
    }

    static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    /**
     * Get the current location of device
     * @param context : application context
     * @param locationListener: the listener need to receive events in location change
     * @return boolean: true in case the GPS location service is enabled, otherwise false
     *
     */
    public static boolean getGPSLocation(Context context, LocationListener locationListener)
    {
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

            return true;
        }
        else
        {
            return false;
        }

    }
}
