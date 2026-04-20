package com.featureflag.model;

import java.util.HashMap;
import java.util.Map;

public class UserContext {
    private final String userId;
    private final Map<String, String> attributes;

    public UserContext(String userId) {
        this.userId = userId;
        this.attributes = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public String toString() {
        return "UserContext{userId='" + userId + "', attributes=" + attributes + "}";
    }
}
