package com.epam.component.implementation;

import com.epam.component.RandomHashGenerator;
import java.security.SecureRandom;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

@Component
public class RandomHashGeneratorImpl implements RandomHashGenerator {

    @Override
    public String generate(String key) {
        SecureRandom secureRandom = new SecureRandom();
        String number = Long.toString(Math.abs(secureRandom.nextLong()), 16);
        if (key == null) {
            key = "";
        }
        return new HashCodeBuilder().append(key).build().toString() + number;
    }
}
