package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create test data
        List<User> users = new ArrayList<>();
        User user1 = new User(1, "John", "Doe", "john@example.com");
        Date now = new Date();
        user1.addMessage(new Message("Hello", "First message", now));
        user1.addMessage(new Message("Test", "Second message", now));

        User user2 = new User(2, "Jane", "Smith", "jane@example.com");
        user2.addMessage(new Message("Welcome", "Hello there", now));

        users.add(user1);
        users.add(user2);

        try {
            // Test IO operations
            UserIO io = new UserIO();
            io.writeUsers(users, "users");
            List<User> readUsers = io.readUsers("users");
            System.out.println("IO Read users: " + readUsers);

            // Test native serialization
            NativeUserSerialization nativeSerialization = new NativeUserSerialization();
            nativeSerialization.serializeUsers(users, "users.ser");
            List<User> deserializedUsers = nativeSerialization.deserializeUsers("users.ser");
            System.out.println("Native deserialized users: " + deserializedUsers);

            // Test JSON serialization
            JSONUserSerialization jsonSerialization = new JSONUserSerialization();
            jsonSerialization.serializeUsersToJson(users, "users.json");
            List<User> jsonUsers = jsonSerialization.deserializeUsersFromJson("users.json");
            System.out.println("JSON deserialized users: " + jsonUsers);

            // Test YAML serialization
            YAMLUserSerialization yamlSerialization = new YAMLUserSerialization();
            yamlSerialization.serializeUsersToYaml(users, "users.yaml");
            List<User> yamlUsers = yamlSerialization.deserializeUsersFromYaml("users.yaml");
            System.out.println("YAML deserialized users: " + yamlUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}