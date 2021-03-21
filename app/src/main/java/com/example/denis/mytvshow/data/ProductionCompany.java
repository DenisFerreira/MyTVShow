
package com.example.denis.mytvshow.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompany implements Parcelable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;
    public final static Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProductionCompany createFromParcel(Parcel in) {
            return new ProductionCompany(in);
        }

        public ProductionCompany[] newArray(int size) {
            return (new ProductionCompany[size]);
        }

    }
    ;

    protected ProductionCompany(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductionCompany() {
    }

    /**
     * 
     * @param id
     * @param name
     */
    public ProductionCompany(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductionCompany withName(String name) {
        this.name = name;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductionCompany withId(int id) {
        this.id = id;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(id);
    }

    public int describeContents() {
        return  0;
    }

}
