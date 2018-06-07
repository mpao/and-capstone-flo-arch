package io.github.mpao.florencearchitectures.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import io.github.mpao.florencearchitectures.models.databases.DataConverter;

@SuppressWarnings("unused")
@Entity
public class Building implements Parcelable{

    //region Fields
    @SerializedName("name")
    @Expose
    @PrimaryKey
    @NonNull
    public String name = "";
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("longitude")
    @Expose
    public Double longitude;
    @SerializedName("main_image")
    @Expose
    public String mainImage;
    @SerializedName("images")
    @Expose
    @TypeConverters(DataConverter.class)
    public List<String> images = null;
    @SerializedName("build_year")
    @Expose
    public String buildYear;
    @SerializedName("typology")
    @Expose
    public String typology;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("project_year")
    @Expose
    public String projectYear;
    @SerializedName("municipality")
    @Expose
    public String municipality;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("city")
    @Expose
    public String city;
    //endregion

    //region Getters
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getImages() {
        return images;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public String getTypology() {
        return typology;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getProjectYear() {
        return projectYear;
    }

    public String getMunicipality() {
        return municipality;
    }

    public String getAddress() {
        return address +" "+ municipality + " (" + city.substring(0,2).toUpperCase() + ")";
    }

    @NonNull public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }
    //endregion

    //region Setters
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public void setTypology(String typology) {
        this.typology = typology;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectYear(String projectYear) {
        this.projectYear = projectYear;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }
    //endregion

    public String getImageDescription(String string){
        return string + " " + this.name;
    }

    //region Parcelable implementation method, via plugin
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeString(this.mainImage);
        dest.writeStringList(this.images);
        dest.writeString(this.buildYear);
        dest.writeString(this.typology);
        dest.writeString(this.category);
        dest.writeString(this.id);
        dest.writeString(this.author);
        dest.writeString(this.description);
        dest.writeString(this.projectYear);
        dest.writeString(this.municipality);
        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeString(this.city);
    }

    public Building() {
    }

    protected Building(Parcel in) {
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.mainImage = in.readString();
        this.images = in.createStringArrayList();
        this.buildYear = in.readString();
        this.typology = in.readString();
        this.category = in.readString();
        this.id = in.readString();
        this.author = in.readString();
        this.description = in.readString();
        this.projectYear = in.readString();
        this.municipality = in.readString();
        this.address = in.readString();
        this.name = in.readString();
        this.city = in.readString();
    }

    public static final Creator<Building> CREATOR = new Creator<Building>() {
        @Override
        public Building createFromParcel(Parcel source) {
            return new Building(source);
        }

        @Override
        public Building[] newArray(int size) {
            return new Building[size];
        }
    };
    //endregion

}
