package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class YAMLUserSerialization {
    private final ObjectMapper mapper;

    public YAMLUserSerialization() {
        this.mapper = new ObjectMapper(new YAMLFactory());
        // Налаштування для обробки дат
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(df);
        // Ігнорування невідомих властивостей
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void serializeUsersToYaml(List<User> users, String fileName) throws IOException {
        mapper.writeValue(new File(fileName), users);
    }


    public List<User> deserializeUsersFromYaml(String fileName) throws IOException {
        List<User> users = mapper.readValue(new File(fileName),
                new TypeReference<List<User>>() {});

        // Видалення дат з повідомлень після десеріалізації
        if (users != null) {
            for (User user : users) {
                if (user.getMessages() != null) {
                    for (Message message : user.getMessages()) {
                        message.setDate(null);
                    }
                }
            }
        }
        return users != null ? users : new ArrayList<>();
    }
}
