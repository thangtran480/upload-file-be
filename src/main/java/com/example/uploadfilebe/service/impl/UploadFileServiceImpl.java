package com.example.uploadfilebe.service.impl;

import com.example.uploadfilebe.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {

    private long count = 0;

    @Override
    public synchronized void  fileResumeUpload(byte[] chunk, String fileName, int chunkNumber, int totalChunk) throws IOException, ParseException {
        count += 1;
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("src/main/resources/" + fileName + ".part-"+ chunkNumber, true));

        boolean uploaded = true;

        try {
            out.write(chunk);
        } catch (IOException e) {
            uploaded = false;
            System.err.println("io exception");
        } finally {
            if (uploaded) {
                out.close();
            } else {
                out.close();
            }
        }
    }

    @Override
    public void mergeFile(String originalName, Integer totalChunks) {
        List<File> files = new ArrayList<>();
        for(int i = 0; i< totalChunks; i++) {
            File file = new File("src/main/resources/" + originalName + ".part-"+ i);
            files.add(file);
        }
        File output = new File("src/main/resources/" + originalName);
        BufferedOutputStream boss = null;
        try
        {
            boss = new BufferedOutputStream(new FileOutputStream(output));
            for (File file : files)
            {
                log.info("merge part: " + file.getName());
                BufferedInputStream bis = null;
                try
                {
                    bis = new BufferedInputStream(new FileInputStream(file));
                    boolean done = false;
                    while (!done)
                    {
                        int data = bis.read();
                        boss.write(data);
                        done = data < 0;
                    }
                }
                catch (Exception e)
                {
                    //do error handling stuff, log it maybe?
                }
                finally
                {
                    try
                    {
                        bis.close();//do this in a try catch just in case
                    }
                    catch (Exception e)
                    {
                        //handle this
                    }
                }
            }
        } catch (Exception e)
        {
            //handle this
        }
        finally
        {
            try
            {
                boss.close();
            }
            catch (Exception e) {
                //handle this
            }
        }
    }
}
