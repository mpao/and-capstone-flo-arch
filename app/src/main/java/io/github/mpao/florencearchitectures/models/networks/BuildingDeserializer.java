package io.github.mpao.florencearchitectures.models.networks;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

import io.github.mpao.florencearchitectures.entities.Building;

public class BuildingDeserializer implements JsonDeserializer<Building[]> {

    private JsonElement output = new JsonArray();

    @Override
    public Building[] deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        JsonElement array = je.getAsJsonObject().getAsJsonArray("digitalLocations");
        // flattening the endpoint's weird structure
        for (JsonElement element: array.getAsJsonArray()){
            JsonObject flattenObj = new JsonObject();
            JsonObject originalObj = element.getAsJsonObject();
            // coords
            flattenObj.add("latitude", originalObj.getAsJsonArray("centroid").get(1));
            flattenObj.add("longitude", originalObj.getAsJsonArray("centroid").get(0));
            // create images array
            flattenObj.add("main_image", originalObj.getAsJsonArray("mainImageResized").get(0));
            JsonArray images = new JsonArray();
            images.addAll( originalObj.getAsJsonArray("mainImageResized") );
            if( originalObj.has("imagesResized") ) {
                images.addAll(originalObj.getAsJsonArray("imagesResized"));
            }
            flattenObj.add("images", images);
            // all the attributes
            JsonObject attributes = originalObj.getAsJsonObject("attributes");
            flattenObj.add("build_year", attributes.get("Costruzione"));
            flattenObj.add("typology", attributes.get("Tipologia"));
            flattenObj.add("category", attributes.get("Periodo"));
            flattenObj.add("id", attributes.get("Codicearchitetture900")); // NB: id has some duplicates -.-'
            flattenObj.add("author", attributes.get("Autore"));
            flattenObj.add("description", attributes.get("Descrizione"));
            flattenObj.add("project_year", attributes.get("Progetto"));
            flattenObj.add("municipality", attributes.get("Comune"));
            flattenObj.add("address", attributes.get("Indirizzo"));
            flattenObj.add("name", attributes.get("Denominazione"));
            flattenObj.add("city", attributes.get("Provincia"));

            output.getAsJsonArray().add(flattenObj);
            
        }
        return new Gson().fromJson(output, type);

    }

}
