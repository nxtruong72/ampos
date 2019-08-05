package com.ampos.restaurant.service;

import com.ampos.restaurant.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    private static final String DATA_FOLDER = "data";
    private final Path rootLocation = Paths.get(DATA_FOLDER);
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Initialize the storage service, check and create if the folder isn't exist.
     */
    public void init() {
        try {
        	if (Files.notExists(rootLocation)) {
        		 Files.createDirectory(rootLocation);
			}
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    /**
     * Store the file to server folder - data folder
     *
     * @param file
     */
    public void store(MultipartFile file) throws ApplicationException {
        String fileName = file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                throw new ApplicationException("Failed to store empty file " + fileName);
            }
            if (fileName.contains("..")) {
                // This is a security check
                throw new ApplicationException("Cannot store file with relative path outside current directory " + fileName);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
        } catch (Exception e) {
            throw new ApplicationException("Failed to store file " + fileName, e);
        }
    }

    /**
     * Load file from data folder based on file name
     *
     * @param filename
     * @return
     */
    public Resource loadFile(String filename) throws ApplicationException {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ApplicationException("Cannot load file " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ApplicationException("Load file " + filename + " error", e);
        }
    }
}
