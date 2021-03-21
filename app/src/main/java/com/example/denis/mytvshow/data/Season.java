
package com.example.denis.mytvshow.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Season implements Parcelable
{

    @SerializedName("air_date")
    @Expose
    private String airDate;
    @SerializedName("episode_count")
    @Expose
    private int episodeCount;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("season_number")
    @Expose
    private int seasonNumber;
    public final static Creator<Season> CREATOR = new Creator<Season>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        public Season[] newArray(int size) {
            return (new Season[size]);
        }

    }
    ;

    protected Season(Parcel in) {
        this.airDate = ((String) in.readValue((String.class.getClassLoader())));
        this.episodeCount = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.seasonNumber = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Season() {
    }

    /**
     * 
     * @param id
     * @param airDate
     * @param posterPath
     * @param episodeCount
     * @param seasonNumber
     */
    public Season(String airDate, int episodeCount, int id, String posterPath, int seasonNumber) {
        super();
        this.airDate = airDate;
        this.episodeCount = episodeCount;
        this.id = id;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Season withAirDate(String airDate) {
        this.airDate = airDate;
        return this;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public Season withEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Season withId(int id) {
        this.id = id;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Season withPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Season withSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airDate);
        dest.writeValue(episodeCount);
        dest.writeValue(id);
        dest.writeValue(posterPath);
        dest.writeValue(seasonNumber);
    }

    public int describeContents() {
        return  0;
    }

}
