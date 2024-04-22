package com.example.uploadfilebe.service;



import java.io.IOException;
import java.text.ParseException;

public interface UploadFileService {
    void fileResumeUpload(byte[] chunk, String fileName, int chunkNumber, int totalChunk) throws IOException, ParseException;

    void mergeFile(String originalName, Integer totalChunks);
}
