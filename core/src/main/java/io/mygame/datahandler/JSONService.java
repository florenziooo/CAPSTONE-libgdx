package io.mygame.datahandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;

public class JSONService {
    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();

    public void serializedObject(Serializable obj, FileWriter fw) {
        gson.toJson(obj, fw);
    }

    public <T extends Serializable> T deserialize(FileReader fr, Class<T> clazz) {
        return gson.fromJson(fr, clazz);
    }
}
