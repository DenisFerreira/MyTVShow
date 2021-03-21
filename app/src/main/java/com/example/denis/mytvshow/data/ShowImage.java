
package com.example.denis.mytvshow.data;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowImage implements Parcelable
{

    @SerializedName("backdrops")
    @Expose
    private List<Backdrop> backdrops = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("posters")
    @Expose
    private List<Poster> posters = null;
    public final static Creator<ShowImage> CREATOR = new Creator<ShowImage>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ShowImage createFromParcel(Parcel in) {
            return new ShowImage(in);
        }

        public ShowImage[] newArray(int size) {
            return (new ShowImage[size]);
        }

    }
    ;

    protected ShowImage(Parcel in) {
        in.readList(this.backdrops, (Backdrop.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.posters, (Poster.class.getClassLoader()));
    }

    public ShowImage() {
    }

    public List<Backdrop> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrop> backdrops) {
        this.backdrops = backdrops;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Poster> getPosters() {
        return posters;
    }

    public void setPosters(List<Poster> posters) {
        this.posters = posters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(backdrops);
        dest.writeValue(id);
        dest.writeList(posters);
    }

    public int describeContents() {
        return  0;
    }

}
