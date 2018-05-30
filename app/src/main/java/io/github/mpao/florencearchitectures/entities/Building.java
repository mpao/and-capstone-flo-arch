package io.github.mpao.florencearchitectures.entities;

import android.database.Cursor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.github.mpao.florencearchitectures.models.databases.AppContract;

@SuppressWarnings("unused")
public class Building {

    //region Fields
    private final static String URL_SEPARATOR = "#";
    private class Attributes {

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
}
