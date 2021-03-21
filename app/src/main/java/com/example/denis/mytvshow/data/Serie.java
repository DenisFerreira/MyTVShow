
package com.example.denis.mytvshow.data;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Serie implements Parcelable
{

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("created_by")
    @Expose
    private List<CreatedBy> createdBy = null;
    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> episodeRunTime = null;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("in_production")
    @Expose
    private boolean inProduction;
    @SerializedName("languages")
    @Expose
    private List<String> languages = null;
    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("networks")
    @Expose
    private List<Network> networks = null;
    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private int numberOfSeasons;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = null;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = null;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("vote_average")
    @Expose
    private Float voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;
    public final static Creator<Serie> CREATOR = new Creator<Serie>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Serie createFromParcel(Parcel in) {
            return new Serie(in);
        }

        public Serie[] newArray(int size) {
            return (new Serie[size]);
        }

    }
    ;

    protected Serie(Parcel in) {
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        //in.readList(this.createdBy, (CreatedBy.class.getClassLoader()));
        this.createdBy = in.createTypedArrayList(CreatedBy.CREATOR);
        in.readList(this.episodeRunTime, (Integer.class.getClassLoader()));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        //in.readList(this.genres, (Genre.class.getClassLoader()));
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.homepage = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.inProduction = ((boolean) in.readValue((boolean.class.getClassLoader())));
        in.readList(this.languages, (String.class.getClassLoader()));
        this.lastAirDate = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        //in.readList(this.networks, (Network.class.getClassLoader()));
        this.networks = in.createTypedArrayList(Network.CREATOR);
        this.numberOfEpisodes = ((int) in.readValue((int.class.getClassLoader())));
        this.numberOfSeasons = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.originCountry, (String.class.getClassLoader()));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((double) in.readValue((double.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        //in.readList(this.productionCompanies, (ProductionCompany.class.getClassLoader()));
        this.productionCompanies = in.createTypedArrayList(ProductionCompany.CREATOR);
        //in.readList(this.seasons, (Season.class.getClassLoader()));
        this.seasons = in.createTypedArrayList(Season.CREATOR);
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((Float) in.readValue((Float.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public Serie() {
    }

    /**
     *  @param backdropPath
     * @param createdBy
     * @param episodeRunTime
     * @param firstAirDate
     * @param genres
     * @param homepage
     * @param id
     * @param inProduction
     * @param languages
     * @param lastAirDate
     * @param name
     * @param networks
     * @param numberOfEpisodes
     * @param numberOfSeasons
     * @param originCountry
     * @param originalLanguage
     * @param originalName
     * @param overview
     * @param popularity
     * @param posterPath
     * @param productionCompanies
     * @param seasons
     * @param status
     * @param type
     * @param voteAverage
     * @param voteCount
     */
    public Serie(String backdropPath, List<CreatedBy> createdBy, List<Integer> episodeRunTime, String firstAirDate, List<Genre> genres, String homepage, int id, boolean inProduction, List<String> languages, String lastAirDate, String name, List<Network> networks, int numberOfEpisodes, int numberOfSeasons, List<String> originCountry, String originalLanguage, String originalName, String overview, double popularity, String posterPath, List<ProductionCompany> productionCompanies, List<Season> seasons, String status, String type, Float voteAverage, int voteCount) {
        super();
        this.backdropPath = backdropPath;
        this.createdBy = createdBy;
        this.episodeRunTime = episodeRunTime;
        this.firstAirDate = firstAirDate;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.inProduction = inProduction;
        this.languages = languages;
        this.lastAirDate = lastAirDate;
        this.name = name;
        this.networks = networks;
        this.numberOfEpisodes = numberOfEpisodes;
        this.numberOfSeasons = numberOfSeasons;
        this.originCountry = originCountry;
        this.originalLanguage = originalLanguage;
        this.originalName = originalName;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.seasons = seasons;
        this.status = status;
        this.type = type;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Serie withBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public List<CreatedBy> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<CreatedBy> createdBy) {
        this.createdBy = createdBy;
    }

    public Serie withCreatedBy(List<CreatedBy> createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public List<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public Serie withEpisodeRunTime(List<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
        return this;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public Serie withFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Serie withGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Serie withHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Serie withId(int id) {
        this.id = id;
        return this;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public Serie withInProduction(boolean inProduction) {
        this.inProduction = inProduction;
        return this;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Serie withLanguages(List<String> languages) {
        this.languages = languages;
        return this;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public Serie withLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Serie withName(String name) {
        this.name = name;
        return this;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public Serie withNetworks(List<Network> networks) {
        this.networks = networks;
        return this;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Serie withNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
        return this;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public Serie withNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
        return this;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public Serie withOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
        return this;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Serie withOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Serie withOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Serie withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public Serie withPopularity(double popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Serie withPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public Serie withProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
        return this;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public Serie withSeasons(List<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Serie withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Serie withType(String type) {
        this.type = type;
        return this;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Serie withVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Serie withVoteCount(int voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(backdropPath);
        //dest.writeList(createdBy);
        dest.writeTypedList(createdBy);
        dest.writeList(episodeRunTime);
        dest.writeValue(firstAirDate);
        //dest.writeList(genres);
        dest.writeTypedList(genres);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(inProduction);
        dest.writeList(languages);
        dest.writeValue(lastAirDate);
        dest.writeValue(name);
        //dest.writeList(networks);
        dest.writeTypedList(networks);
        dest.writeValue(numberOfEpisodes);
        dest.writeValue(numberOfSeasons);
        dest.writeList(originCountry);
        dest.writeValue(originalLanguage);
        dest.writeValue(originalName);
        dest.writeValue(overview);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);
        //dest.writeList(productionCompanies);
        dest.writeTypedList(productionCompanies);
        //dest.writeList(seasons);
        dest.writeTypedList(seasons);
        dest.writeValue(status);
        dest.writeValue(type);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
    }

    public int describeContents() {
        return  0;
    }

}
