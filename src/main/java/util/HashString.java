package util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashString {
    public String hashString(String originalString){
        return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
    }
}
