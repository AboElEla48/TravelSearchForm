package foureg.eg.travelsearchform.data.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aboelela on 7/10/15.
 * Define one place as name, location and distance from current location
 */
public class Place implements Parcelable{

    public Place()
    {

    }

    public Place(Parcel in)
    {
        mName = in.readString();
        mLat = in.readDouble();
        mLong = in.readDouble();
        mDistance = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeDouble(mLat);
        dest.writeDouble(mLong);
        dest.writeDouble(mDistance);
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public double getLat() {
        return mLat;
    }

    public double getLong() {
        return mLong;
    }

    public String getName() {
        return mName;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setLat(double lat) {
        this.mLat = lat;
    }

    public void setLong(double mLong) {
        this.mLong = mLong;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setDistance(double distance) {
        this.mDistance = distance;
    }

    private String mName;
    private double mLong;
    private double mLat;
    private double mDistance = 0.0;
}
