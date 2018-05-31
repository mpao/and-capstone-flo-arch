package io.github.mpao.florencearchitectures.models.databases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import io.github.mpao.florencearchitectures.R;
import static io.github.mpao.florencearchitectures.models.databases.AppContract.AppContractElement.CATEGORY;
import static io.github.mpao.florencearchitectures.models.databases.AppContract.AppContractElement.CONTENT_URI;
import static io.github.mpao.florencearchitectures.models.databases.AppContract.AppContractElement.BUILDINGS_TABLE;
import static io.github.mpao.florencearchitectures.models.databases.AppContract.AppContractElement.MAIN_IMAGE;

public class AppContentProvider extends ContentProvider {

    DbHelper dbHelper;
    Context context;
    // use always the conventional mode: 100,200... for directories, and 101,102... for items in that direcotry
    public static final int BUILDINGS  = 100;
    public static final int ELEMENT    = 101;
    public static final int CATEGORIES = 200;
    public static final UriMatcher URI_MATCHER = buildUriMatcher();
    public static UriMatcher buildUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AppContract.AUTHORITY, AppContract.ALL_BUILDINGS, BUILDINGS);
        uriMatcher.addURI(AppContract.AUTHORITY, AppContract.CATEGORIES, CATEGORIES);
        uriMatcher.addURI(AppContract.AUTHORITY, AppContract.ALL_BUILDINGS + "/#", ELEMENT);
        return uriMatcher;

    }

    /*
     * Initialize the Content Provider
     */
    @Override
    public boolean onCreate() {

        this.context = getContext();
        dbHelper = new DbHelper(getContext());
        return true;

    }

    //region CRUD methods
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match               = URI_MATCHER.match(uri);
        Uri returnUri;

        switch (match){
            case BUILDINGS:
                long id = db.insert(BUILDINGS_TABLE, null, values);
                if( id > 0 ){
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                }else {
                    // -1 returned
                    throw new SQLException(context.getString(R.string.network_error));
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI");
        }

        context.getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        int match               = URI_MATCHER.match(uri);
        Cursor cursor;

        switch (match){
            case BUILDINGS:
                cursor = db.query(BUILDINGS_TABLE, projection, selection,selectionArgs,null,null,sortOrder);
                break;
            case ELEMENT:
                cursor = db.query(
                        BUILDINGS_TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        "id="+uri.getPathSegments().get(1),
                        sortOrder);
                break;
            case CATEGORIES:
                cursor = getCategories(db);
                break;
            default:
                throw new UnsupportedOperationException(context.getString(R.string.network_error));
        }

        cursor.setNotificationUri( context.getContentResolver(), uri);
        return cursor;

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // (not required now) Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = URI_MATCHER.match(uri);
        int removed;

        switch (match) {
            case BUILDINGS:
                removed = db.delete(BUILDINGS_TABLE, null,null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (removed != 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return removed;

    }
    //endregion

    @Override
    public String getType(@NonNull Uri uri) {
        // (not required now) Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //region Helper Methods
    private Cursor getCategories(SQLiteDatabase db){

        //sqlite, as mysql, enables multi-selection with a single group by <3
        return db.query(
                BUILDINGS_TABLE,
                new String[] {CATEGORY, MAIN_IMAGE, "count(*) AS count"},
                null,
                null,
                CATEGORY,
                null,
                CATEGORY
        );

    }
    //endregion
}
