package io.github.mpao.florencearchitectures.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@SuppressWarnings("unused")
public class Building {

    //region Fields
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
    //endregion

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
    //endregion
}
