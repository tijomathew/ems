package org.rms.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.rms.helpers.GridRow;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tijo on 7/12/14.
 */
public class ResultSerializer implements JsonSerializer<GridRow> {

    private static final Type LIST = new TypeToken<List<String>>() {
    }.getType();


    @Override
    public JsonElement serialize(GridRow gridRow, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        final JsonElement jsonResultList = jsonSerializationContext.serialize(gridRow.getGridRow(0), LIST);
        result.addProperty("id", gridRow.getId().toString());
        result.add("cell", jsonResultList);

        return result;
    }
}
