package com.example.denis.mytvshow.data;

/**
 * Created by lsitec205.ferreira on 01/02/18.
 */

public class SerieDatabaseInfo {
    private int id;
    private String name;
    private String posterPath;
    private boolean favorite;
    private String firstAirDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public SerieDatabaseInfo() {
    }

    public SerieDatabaseInfo(int id, String name, String posterPath, boolean favorite, String firstAirDate) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
        this.favorite = favorite;
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }
}
