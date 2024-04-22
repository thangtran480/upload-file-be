package com.example.uploadfilebe.controller;

import com.example.uploadfilebe.Response;
import com.example.uploadfilebe.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping("/local/api/v1")
public class UploadFileController {

    @Autowired
    private UploadFileService service;

    @PostMapping(value = "/upload")
    public ResponseEntity uploadFileV2(@RequestPart("file") byte[] file,
                                       @RequestParam("chunkNumber") Integer chunkNumber,
                                       @RequestParam("totalChunks") Integer totalChunks,
                                       @RequestParam("originalName") String originalName) {
        log.info(String.format("chunkNumber: %d, totalChunks: %d, originalName: %s", chunkNumber, totalChunks, originalName));
        try {
            service.fileResumeUpload(file, originalName, chunkNumber, totalChunks);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("success");
    }

    @PostMapping(value = "/merge")
    public ResponseEntity mergeFile(@RequestParam("originalName") String originalName,
                                    @RequestParam("totalChunks") Integer totalChunks){
        service.mergeFile(originalName, totalChunks);
        return ResponseEntity.ok("success");
    }
}
