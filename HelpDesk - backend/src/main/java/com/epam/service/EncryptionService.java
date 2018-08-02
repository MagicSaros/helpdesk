package com.epam.service;

public interface EncryptionService {

    String encode(String value);

    String decode(String value);
}
