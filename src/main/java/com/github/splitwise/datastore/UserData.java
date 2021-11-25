package com.github.splitwise.datastore;

import com.github.splitwise.model.User;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class UserData {
    private Map<String, User> getUserById = new HashMap<>();
}
