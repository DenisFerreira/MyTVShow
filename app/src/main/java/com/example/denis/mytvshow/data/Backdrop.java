
package com.example.denis.mytvshow.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Backdrop implements Parcelable
{

    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("width")
    @Expose
    private Integer width;
    public final static Creator<Backdrop> CREATOR = new Creator<Backdrop>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Backdrop createFromParcel(Parcel in) {
            return new Backdrop(in);
        }

        public Backdrop[] newArray(int size) {
            return (new Backdrop[size]);
        }

    }
    ;

    protected Backdrop(Parcel in) {
        this.aspectRatio = ((Double) in.readValue((Double.class.getClassLoader())));
        this.filePath = ((String) in.readValue((String.class.getClassLoader())));
        this.height = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((Float) in.readValue((Float.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.width = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public Backdrop() {
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(aspectRatio);
        dest.writeValue(filePath);
        dest.writeValue(height);
        dest.writeValue(iso6391);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(width);
    }

    public int describeContents() {
        return  0;
    }

}
