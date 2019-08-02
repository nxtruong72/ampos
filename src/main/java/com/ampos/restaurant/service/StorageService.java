package com.ampos.restaurant.service;

import com.ampos.restaurant.exception.StorageException;
import com.ampos.restaurant.service.impl.MenuData;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private static final String DATA_FOLDER = "data";
    private final Path rootLocation = Paths.get(DATA_FOLDER);

    /**
     * Initialize the storage service, check and create if the folder isn't exist.
     */
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    /**
     * Store the file to server folder - data folder
     * @param file
     */
    public void store(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if (fileName.contains("..")) {
                // This is a security check
                throw new StorageException("Cannot store file with relative path outside current directory " + fileName);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
        } catch (Exception e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }
    }

    /**
     * Load file from data folder based on file name
     * @param filename
     * @return
     */
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    /**
     * Load data for menu items
     * @return
     * @throws FileNotFoundException
     */
    public MenuData loadMenuData() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA_FOLDER + "/menuItem.json"));
        MenuData menuData = gson.fromJson(bufferedReader, MenuData.class);
        return menuData;
    }
}
