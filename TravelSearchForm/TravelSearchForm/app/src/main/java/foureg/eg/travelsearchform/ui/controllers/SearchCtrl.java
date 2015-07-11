package foureg.eg.travelsearchform.ui.controllers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import foureg.eg.travelsearchform.com.ComManager;
import foureg.eg.travelsearchform.common.Constants;
import foureg.eg.travelsearchform.data.models.Place;
import foureg.eg.travelsearchform.utils.GPSUtil;

/**
 * Created by aboelela on 7/10/15.
 * This is the controller that holds the logic behind search activity. If provides all logic needed to communicate from UI to data or com layers
 */
public class SearchCtrl {

    /**
     * Load locations from com layer
     * @param context
     * @return
     */
    public void getLocations(Context context, final Handler handler)
    {
        mLocations = ComManager.getLocations(context);

        if(mLocations == null)
        {
            handler.sendEmptyMessage(Constants.Error_Invalid_JSON);
            return;
        }

        // order locations according to distance from current location
        boolean serviceEnabled = GPSUtil.getGPSLocation(context, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Get current long and lat
                double currentLong = location.getLongitude();
                double currentLat = location.getLatitude();

                // calculate the distance from all locations
                for(int i = 0; i < mLocations.size(); i++)
                {
                    Place place = mLocations.get(i);
                    place.setDistance(GPSUtil.getDistanceFromLatLonInKm(currentLat,currentLong, place.getLat(), place.getLong()));
                }

                // Sort locations
                Place tempPLace;
                if (mLocations.size() > 1) // check if the number of orders is larger than 1
                {
                    for (int x = 0; x< mLocations.size(); x++) // bubble sort outer loop
                    {
                        for (int i = 0; i < mLocations.size() - i; i++) {
                            if (mLocations.get(i).getDistance() > mLocations.get(i + 1).getDistance())
                            {
                                tempPLace = mLocations.get(i);
                                mLocations.set(i,mLocations.get(i + 1) );
                                mLocations.set(i + 1, tempPLace);
                            }
                        }
                    }
                }

                // Notify handler of change in location and set locations sorted
                Message msg = new Message();
                msg.what = Constants.Service_Enabled;
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants.Bundle_Locations_Key, (ArrayList<Place>)mLocations);
                msg.setData(bundle);
                handler.sendMessage(msg);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });

        if(!serviceEnabled)
        {
            //Notify handler the GPS service isn't enabled
            handler.sendEmptyMessage(Constants.Error_Service_Disabled);
        }
    }

    private List<Place> mLocations;

}
