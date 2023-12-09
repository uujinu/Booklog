package com.booklog.booklog.common.util;

public interface EncryptHelper {

    String encrypt(String password);
    boolean isMatch(String password, String hashedPassword);
}
