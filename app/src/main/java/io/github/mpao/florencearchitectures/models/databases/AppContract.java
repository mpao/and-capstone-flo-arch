package io.github.mpao.florencearchitectures.models.databases;

import android.net.Uri;

public class AppContract {

    /*
     * Content provider constant and URIs definition
     */
    public static final String AUTHORITY     = "io.github.mpao.florencearchitectures";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH          = "buildings";

    private AppContract(){}

    /**
     * Contract for db
     */
    public static class AppContractElement {

        // define the content uri for the table
        // for a single element, append che # to identify the building's ID
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();

        public static final String BUILDINGS_TABLE = "buildings";
        public static final String ID              = "id";
        public static final String NAME            = "name";
        public static final String CATEGORY        = "period";
        public static final String YEAR            = "year";
        public static final String TIPOLOGY        = "typology";
        public static final String AUTHOR          = "author";
        public static final String DESCRIPTION     = "description";
        public static final String PROJECT         = "project";
        public static final String MUNICIPALITY    = "municipality";
        public static final String ADDRESS         = "address";
        public static final String PROVINCE        = "province";
        public static final String LATITUDE        = "latitude";
        public static final String LONGITUDE       = "longitude";
        public static final String MAIN_IMAGE      = "mainImageResized";
        public static final String OTHER_IMAGES    = "imagesResized";
        public static final String FAVORITE        = "favorite";

    }

    /*
     * Bug in endpoint, there is a duplicate in the ID.
     * Use name for primary key
     */
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppContractElement.BUILDINGS_TABLE + " (" +
                    AppContractElement.ID           + " TEXT, " +
                    AppContractElement.NAME         + " TEXT PRIMARY KEY, " +
                    AppContractElement.CATEGORY     + " TEXT, " +
                    AppContractElement.YEAR         + " TEXT, " +
                    AppContractElement.TIPOLOGY     + " TEXT, " +
                    AppContractElement.AUTHOR       + " TEXT, " +
                    AppContractElement.DESCRIPTION  + " TEXT, " +
                    AppContractElement.PROJECT      + " TEXT, " +
                    AppContractElement.MUNICIPALITY + " TEXT, " +
                    AppContractElement.ADDRESS      + " TEXT, " +
                    AppContractElement.PROVINCE     + " TEXT, " +
                    AppContractElement.LATITUDE     + " REAL, " +
                    AppContractElement.LONGITUDE    + " REAL, " +
                    AppContractElement.MAIN_IMAGE   + " TEXT, " +
                    AppContractElement.OTHER_IMAGES + " TEXT, " +
                    AppContractElement.FAVORITE     + " INTEGER DEFAULT 0 " +
                    ")";

    protected static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AppContractElement.BUILDINGS_TABLE;

}
