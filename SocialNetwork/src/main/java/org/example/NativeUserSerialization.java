package org.example;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class NativeUserSerialization {
    public void serializeUsers(List<User> users, String fileName) throws IOException {
        // Clear messages before serialization
        List<User> usersWithoutMessages = users.stream()
                .map(user -> new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()))
                .collect(Collectors.toList());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(usersWithoutMessages);
        }
    }

    public List<User> deserializeUsers(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<User>) ois.readObject();
        }
    }
}
