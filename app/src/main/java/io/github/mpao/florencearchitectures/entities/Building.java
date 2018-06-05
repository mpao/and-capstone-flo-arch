package io.github.mpao.florencearchitectures.entities;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.github.mpao.florencearchitectures.models.databases.AppContract;

@SuppressWarnings("unused")
public class Building implements Parcelable{

    //region Fields
    private final static String URL_SEPARATOR = "#";
    private class Attributes implements Parcelable{

        @SerializedName("Costruzione")
        @Expose
        private String year;
        @SerializedName("Tipologia")
        @Expose
        private String typology;
        @SerializedName("Codicearchitetture900")
        @Expose
        private String id;
        @SerializedName("Periodo")
        @Expose
        private String period;
        @SerializedName("Autore")
        @Expose
        private String author;
        @SerializedName("Descrizione")
        @Expose
        private String description;
        @SerializedName("Progetto")
        @Expose
        private String project;
        @SerializedName("Comune")
        @Expose
        private String municipality;
        @SerializedName("Indirizzo")
        @Expose
        private String address;
        @SerializedName("Denominazione")
        @Expose
        private String name;
        @SerializedName("Provincia")
        @Expose
        private String province;

        //region Attributes Parcel Methods
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.year);
            dest.writeString(this.typology);
            dest.writeString(this.id);
            dest.writeString(this.period);
            dest.writeString(this.author);
            dest.writeString(this.description);
            dest.writeString(this.project);
            dest.writeString(this.municipality);
            dest.writeString(this.address);
            dest.writeString(this.name);
            dest.writeString(this.province);
        }

        public Attributes() {
        }

        protected Attributes(Parcel in) {
            this.year = in.readString();
            this.typology = in.readString();
            this.id = in.readString();
            this.period = in.readString();
            this.author = in.readString();
            this.description = in.readString();
            this.project = in.readString();
            this.municipality = in.readString();
            this.address = in.readString();
            this.name = in.readString();
            this.province = in.readString();
        }

        public final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(Parcel source) {
                return new Attributes(source);
            }

            @Override
            public Attributes[] newArray(int size) {
                return new Attributes[size];
            }
        };
        //endregion
    }

    @SerializedName("centroid")
    @Expose
    private List<Double> centroid = null;
    @SerializedName("attributes")
    @Expose
    private Attributes attributes;
    @SerializedName("mainImageResized")
    @Expose
    private List<String> mainImageResized = null;
    @SerializedName("imagesResized")
    @Expose
    private List<String> imagesResized = null;
    private boolean favorite;
    //endregion

    public Building(Cursor cursor){

        this.attributes = new Attributes();
        this.attributes.id = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.ID));
        this.attributes.name = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.NAME));
        this.attributes.period = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.CATEGORY));
        this.attributes.year = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.YEAR));
        this.attributes.typology = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.TIPOLOGY));
        this.attributes.author = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.AUTHOR));
        this.attributes.description = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.DESCRIPTION));
        this.attributes.project = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.PROJECT));
        this.attributes.municipality = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.MUNICIPALITY));
        this.attributes.address = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.ADDRESS));
        this.attributes.province = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.PROVINCE));
        this.centroid = new ArrayList<>();
        this.centroid.add( cursor.getDouble(cursor.getColumnIndex(AppContract.AppContractElement.LONGITUDE)));
        this.centroid.add( cursor.getDouble(cursor.getColumnIndex(AppContract.AppContractElement.LATITUDE)));
        this.mainImageResized = new ArrayList<>();
        this.mainImageResized.add( cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.MAIN_IMAGE)) );
        String s = cursor.getString(cursor.getColumnIndex(AppContract.AppContractElement.OTHER_IMAGES));
        this.imagesResized = createImagesList(s);
        this.favorite = cursor.getInt(cursor.getColumnIndex(AppContract.AppContractElement.ID))==1;

    }

    //region Getters
    public double getLatitude(){
        return this.centroid.get(1);
    }

    public double getLongitude(){
        return this.centroid.get(0);
    }

    public String getMainImage(){
        return mainImageResized.get(0);
    }

    public List<String> getAllMainImages(){
        return mainImageResized;
    }

    public List<String> getImages(){
        return imagesResized;
    }

    public String getYear(){
        return attributes.year;
    }

    public String getTypology(){
        return  attributes.typology;
    }

    public String getPeriod(){
        return attributes.period;
    }

    public String getId(){
        return attributes.id;
    }

    public String getAuthor(){
        return attributes.author;
    }

    public String getDescription(){
        return attributes.description;
    }

    public String getProject(){
        return attributes.project;
    }

    public String getMunicipality(){
        return attributes.municipality;
    }

    public String getAddress(){
        return attributes.address;
    }

    public String getName(){
        return attributes.name;
    }

    public String getProvince(){
        return attributes.province;
    }

    public boolean isFavorite(){
        return favorite;
    }

    public String getImagesAsString(){
        StringBuilder urls = new StringBuilder();
        for (String url : this.getAllMainImages()) {
            urls.append(url).append(URL_SEPARATOR);
        }
        // add all the images
        if( imagesResized != null) {
            for (String url : this.getImages()) {
                urls.append(url).append(URL_SEPARATOR);
            }
        }
        String s = urls.toString();
        return s.endsWith(URL_SEPARATOR) ? s.substring(0, s.length() - 1) : s;

    }

    public List<String> createImagesList(String urls){
        String[] array = urls.split(URL_SEPARATOR);
        return Arrays.asList(array);
    }
    //endregion

    //region Parcelable implementation method, via plugin
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.centroid);
        dest.writeParcelable(this.attributes, flags);
        dest.writeStringList(this.mainImageResized);
        dest.writeStringList(this.imagesResized);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    protected Building(Parcel in) {
        this.centroid = new ArrayList<Double>();
        in.readList(this.centroid, Double.class.getClassLoader());
        this.attributes = in.readParcelable(Attributes.class.getClassLoader());
        this.mainImageResized = in.createStringArrayList();
        this.imagesResized = in.createStringArrayList();
        this.favorite = in.readByte() != 0;
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
