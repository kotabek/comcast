package com.comcast.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kotabek on 3/22/17.
 */
public class AdValidateResult {
    private List<String> messages = new ArrayList<>();

    public boolean hasError() {
        return !messages.isEmpty();
    }

    public String generateMessage() {
        if (this.hasError()) {
            return String.join(", ", messages);
        }
        return "";
    }

    public void regError(String message) {
        messages.add(message);
    }
}
