package com.epam.service.implementation;

import com.epam.service.EncryptionService;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    @Override
    public String decode(String value) {
        return new String(Base64.getDecoder().decode(value));
    }
}
