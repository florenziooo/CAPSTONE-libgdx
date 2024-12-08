package io.mygame.datahandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * Provides methods for serializing and deserializing objects to and from JSON using Gson.
 * Uses a Gson instance configured with pretty printing and null serialization.
 */
public class JSONService {
    /************ GSON INSTANCE ************/
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    /**
     * Serializes a given object and writes the JSON representation to the specified FileWriter.
     *
     * @param obj the object to be serialized
     * @param fw  the FileWriter to write the serialized JSON data to
     */
    public void serializedObject(Serializable obj, FileWriter fw) {
        gson.toJson(obj, fw);
    }

    /**
     * Deserializes the JSON data from the specified FileReader into an object of the given class type.
     *
     * @param fr    the FileReader from which to read the JSON data
     * @param clazz the class type to deserialize into
     * @param <T>   the type of object to return
     * @return the deserialized object
     */
    public <T extends Serializable> T deserialize(FileReader fr, Class<T> clazz) {
        return gson.fromJson(fr, clazz);
    }
}
