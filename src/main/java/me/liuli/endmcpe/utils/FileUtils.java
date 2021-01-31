package me.liuli.endmcpe.utils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FileUtils {
    public static String readFile(File file) {
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return new String(filecontent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String base64Encode(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }
    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input.getBytes()));
    }
}
