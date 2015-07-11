package foureg.eg.travelsearchform.com;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import foureg.eg.travelsearchform.R;
import foureg.eg.travelsearchform.common.Constants;
import foureg.eg.travelsearchform.data.models.Place;

/**
 * Created by aboelela on 7/10/15.
 * This class is responsible to retrieve locations from webservice
 */
class LocationsComManager {

    /**
     * Parse locations from json
     * @param context
     * @return
     */
    public static List<Place> getLocations(Context context)
    {
        String jsonStr = context.getString(R.string.txt_json);

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray locationsArr = jsonObject.getJSONArray(Constants.Locations_JSON_Array_Key);

            ArrayList<Place> places = new ArrayList<>();

            for (int i = 0; i < locationsArr.length(); i++)
            {
                JSONObject oneJsonLocation = locationsArr.getJSONObject(i);

                Place place = new Place();

                // Get location name
                place.setName(oneJsonLocation.getString(Constants.Locations_JSON_Place_Name_Key));
                place.setLat(oneJsonLocation.getDouble(Constants.Locations_JSON_Place_Lat_Key));
                place.setLong(oneJsonLocation.getDouble(Constants.Locations_JSON_Place_Long_Key));

                places.add(place);
            }

            return places;

        }catch (JSONException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }



}
