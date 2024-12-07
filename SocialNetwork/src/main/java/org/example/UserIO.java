package org.example;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserIO {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void writeUsers(List<User> users, String fileName) throws IOException {
        // Buffered stream for user profiles
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "_profiles.txt"))) {
            for (User user : users) {
                String userProfile = String.format("%d,%s,%s\n",
                        user.getId(), user.getFirstName(), user.getLastName());
                writer.write(userProfile);
            }
        }

        // Non-buffered stream for messages
        for (User user : users) {
            try (FileOutputStream messageStream = new FileOutputStream(fileName + "_messages_" + user.getId() + ".txt")) {
                for (Message message : user.getMessages()) {
                    String messageStr = String.format("%s|%s|%s\n",
                            message.getSubject(),
                            message.getText(),
                            DATE_FORMAT.format(message.getDate()));
                    messageStream.write(messageStr.getBytes());
                }
            }
        }
    }

    public List<User> readUsers(String fileName) throws IOException {
        List<User> users = new ArrayList<>();

        // Read profiles
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName + "_profiles.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                User user = new User(
                        Integer.parseInt(parts[0]), parts[1], parts[2], null);
                users.add(user);
            }
        }

        // Read messages
        for (User user : users) {
            File messageFile = new File(fileName + "_messages_" + user.getId() + ".txt");
            if (messageFile.exists()) {
                try (FileInputStream messageStream = new FileInputStream(messageFile)) {
                    byte[] data = messageStream.readAllBytes();
                    String content = new String(data);
                    for (String messageLine : content.split("\n")) {
                        if (!messageLine.isEmpty()) {
                            String[] parts = messageLine.split("\\|");
                            try {
                                Date messageDate = DATE_FORMAT.parse(parts[2]);
                                user.addMessage(new Message(parts[0], parts[1], messageDate));
                            } catch (ParseException e) {
                                // Якщо дата некоректна, використовуємо поточну дату
                                user.addMessage(new Message(parts[0], parts[1], new Date()));
                            }
                        }
                    }
                }
            }
        }

        return users;
    }
}
