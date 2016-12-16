/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lu.btsi.simulationapp.objects;


import com.google.gson.*;
import java.lang.reflect.Type;
/**
 *
 * @author cardosoken
 */

public class EntityAdapter implements JsonSerializer<Entity>, JsonDeserializer<Entity> {

    /**
     *
     * @param src Entity
     * @param typeOfSrc The Type of Entity
     * @param context
     * @return
     */

    @Override
    public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }

    /**
     *
     * @param json The element to deserialize from
     * @param typeOfT The Type of (extracted from the jsonObject)
     * @param context
     * @return
     * @throws JsonParseException Catch unrecognized JSON elements
     */
    @Override
    public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

    try {
            return context.deserialize(element, Class.forName("lu.btsi.simulationapp.objects." + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}

