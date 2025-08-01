package com.ll.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    static Path DB_BASE_PATH = Paths.get("db");
    static Path WISE_SAYING_PATH = DB_BASE_PATH.resolve("wiseSaying");
    static String LAST_ID_FILENAME = "lastId.txt";

    public static void createDirectory() {
        try {
            Files.createDirectories(WISE_SAYING_PATH);

        } catch(IOException e) {
            System.err.println("Directory 생성 실패: " + e.getMessage());
        }
    }


}
