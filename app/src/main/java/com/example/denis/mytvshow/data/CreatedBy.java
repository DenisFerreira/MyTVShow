
package com.example.denis.mytvshow.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedBy implements Parcelable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private int gender;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    public final static Creator<CreatedBy> CREATOR = new Creator<CreatedBy>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CreatedBy createFromParcel(Parcel in) {
            return new CreatedBy(in);
        }

        public CreatedBy[] newArray(int size) {
            return (new CreatedBy[size]);
        }

    }
    ;

    protected CreatedBy(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public CreatedBy() {
    }

    /**
     * 
     * @param id
     * @param profilePath
     * @param name
     * @param gender
     */
    public CreatedBy(int id, String name, int gender, String profilePath) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.profilePath = profilePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CreatedBy withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CreatedBy withName(String name) {
        this.name = name;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public CreatedBy withGender(int gender) {
        this.gender = gender;
        return this;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public CreatedBy withProfilePath(String profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(gender);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return  0;
    }

}
