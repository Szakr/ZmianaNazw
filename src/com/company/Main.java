package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {

    final private static String DIRECTORY_PATH = "C://test/";

    public static void main(String[] args) {
        File directory = new File(DIRECTORY_PATH);
        File[] fileList = directory.listFiles();

        for (File file : fileList) {
            if (file.isFile()) {
                System.out.print(file.getName() + " renamed to ");
                Path path = FileSystems.getDefault().getPath(file.getPath());
                BasicFileAttributes attributes = null;
                try {
                    attributes = Files.readAttributes(path, BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileTime date = attributes.creationTime();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd,kk:mm:ss");
                String newName = df.format(date.toMillis())+ "," +file.getName();
                newName = newName.replace(":", "-");
                System.out.println(newName);
                File newFile = new File(DIRECTORY_PATH + newName);
                Path newPath = FileSystems.getDefault().getPath(newFile.getPath());
                try {
                    Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}