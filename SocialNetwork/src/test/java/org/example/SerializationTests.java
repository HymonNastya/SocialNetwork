package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerializationTests {

    @Test
    void testJSONSerialization() throws IOException {
        JSONUserSerialization jsonSerializer = new JSONUserSerialization();
        String fileName = "test_users.json";

        List<User> users = new ArrayList<>();
        users.add(new User(1, "John", "Doe", "john.doe@example.com"));
        users.add(new User(2, "Jane", "Smith", "jane.smith@example.com"));

        jsonSerializer.serializeUsersToJson(users, fileName);
        File file = new File(fileName);
        assertTrue(file.exists());

        List<User> deserializedUsers = jsonSerializer.deserializeUsersFromJson(fileName);
        assertEquals(users.size(), deserializedUsers.size());
        assertEquals(users.get(0).getFirstName(), deserializedUsers.get(0).getFirstName());

        assertTrue(file.delete());
    }

    @Test
    void testYAMLSerialization() throws IOException {
        YAMLUserSerialization yamlSerializer = new YAMLUserSerialization();
        String fileName = "test_users.yaml";

        List<User> users = new ArrayList<>();
        users.add(new User(1, "John", "Doe", "john.doe@example.com"));
        users.add(new User(2, "Jane", "Smith", "jane.smith@example.com"));

        yamlSerializer.serializeUsersToYaml(users, fileName);
        File file = new File(fileName);
        assertTrue(file.exists());

        List<User> deserializedUsers = yamlSerializer.deserializeUsersFromYaml(fileName);
        assertEquals(users.size(), deserializedUsers.size());
        assertEquals(users.get(0).getFirstName(), deserializedUsers.get(0).getFirstName());

        assertTrue(file.delete());
    }

    @Test
    void testNativeSerialization() throws IOException, ClassNotFoundException {
        NativeUserSerialization nativeSerializer = new NativeUserSerialization();
        String fileName = "test_users.ser";

        List<User> users = new ArrayList<>();
        users.add(new User(1, "John", "Doe", "john.doe@example.com"));
        users.add(new User(2, "Jane", "Smith", "jane.smith@example.com"));

        nativeSerializer.serializeUsers(users, fileName);
        File file = new File(fileName);
        assertTrue(file.exists());

        List<User> deserializedUsers = nativeSerializer.deserializeUsers(fileName);
        assertEquals(users.size(), deserializedUsers.size());
        assertEquals(users.get(0).getFirstName(), deserializedUsers.get(0).getFirstName());

        assertTrue(file.delete());
    }

    @Test
    void testUserAttributes() {
        User user = new User(1, "John", "Doe", "john.doe@example.com");
        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());

        Message message = new Message("Subject", "Text", new Date());
        user.addMessage(message);
        assertEquals(1, user.getMessages().size());
        assertEquals("Subject", user.getMessages().get(0).getSubject());
    }

    @Test
    void testMessageAttributes() {
        Date date = new Date();
        Message message = new Message("Test Subject", "Test Text", date);
        assertEquals("Test Subject", message.getSubject());
        assertEquals("Test Text", message.getText());
        assertEquals(date, message.getDate());
    }

    @Test
    void testUserIO() throws IOException {
        UserIO userIO = new UserIO();
        String baseFileName = "test_users";

        List<User> users = new ArrayList<>();
        User user = new User(1, "John", "Doe", "john.doe@example.com");
        user.addMessage(new Message("Subject", "Text", new Date()));
        users.add(user);

        userIO.writeUsers(users, baseFileName);

        File profileFile = new File(baseFileName + "_profiles.txt");
        File messageFile = new File(baseFileName + "_messages_1.txt");
        assertTrue(profileFile.exists());
        assertTrue(messageFile.exists());

        List<User> readUsers = userIO.readUsers(baseFileName);
        assertEquals(1, readUsers.size());
        assertEquals("John", readUsers.get(0).getFirstName());

        assertTrue(profileFile.delete());
        assertTrue(messageFile.delete());
    }
}
