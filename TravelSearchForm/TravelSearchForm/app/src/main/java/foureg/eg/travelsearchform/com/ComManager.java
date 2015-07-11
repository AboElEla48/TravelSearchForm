package foureg.eg.travelsearchform.com;

import android.content.Context;
import java.util.List;
import foureg.eg.travelsearchform.data.models.Place;

/**
 * Created by aboelela on 7/10/15.
 *
 * This class acting as Facade to send request to appropriate com manager
 */
public class ComManager {

    /**
     * get supported locations
     *
     * @param context
     * @return
     */
    public static List<Place> getLocations(Context context)
    {
        return LocationsComManager.getLocations(context);
    }
}
