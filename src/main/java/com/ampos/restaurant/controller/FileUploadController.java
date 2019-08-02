package com.ampos.restaurant.controller;

import com.ampos.restaurant.exception.ApplicationException;
import com.ampos.restaurant.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/files")
public class FileUploadController {
    @Autowired
    private StorageService storageService;

    List<String> files = new ArrayList<String>();

    /**
     * Upload a file and store in the data folder
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    /**
     * Load a file with filename from data folder
     * @param filename
     * @return
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws ApplicationException {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
