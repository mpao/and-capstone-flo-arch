package io.github.mpao.florencearchitectures.entities;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class BuildingDeserializer implements JsonDeserializer<Building[]> {

    @Override
    public Building[] deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        JsonElement array = je.getAsJsonObject().getAsJsonArray("digitalLocations");
        return new Gson().fromJson(array, type);

    }

}
