package io.github.mpao.florencearchitectures.models.databases;

import android.arch.persistence.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Convert complex data type to sqlite compatible ones
 * see https://developer.android.com/training/data-storage/room/referencing-data.html
 * see https://goo.gl/U8PzVM
 */
public class DataConverter {

    /*
     * transform a List in a json string for saving it in the db
     */
    @TypeConverter
    public String fromList(List<String> images){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return images != null ? gson.toJson(images, type) : null;
    }

    /*
     * retrive a json string and transform it in a list
     */
    @TypeConverter
    public List<String> toList(String string){
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return string != null ? gson.fromJson(string, type) : null;
    }

}
