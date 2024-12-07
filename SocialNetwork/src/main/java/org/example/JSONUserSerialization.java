package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONUserSerialization {
    private final Gson gson;

    public JSONUserSerialization() {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .create();
    }

    public void serializeUsersToJson(List<User> users, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(users, writer);
        }
    }

    public List<User> deserializeUsersFromJson(String fileName) throws IOException {
        try (Reader reader = new FileReader(fileName)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> users = gson.fromJson(reader, userListType);
            return users != null ? users : new ArrayList<>();
        }
    }
}