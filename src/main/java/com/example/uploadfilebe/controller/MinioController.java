package com.example.uploadfilebe.controller;


import com.example.uploadfilebe.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "minio/api/v1")
public class MinioController {

    @Autowired
    private MinioService service;

    @PostMapping(value = "/cerateUrl")
    public String createUrlUpload(@RequestParam(value = "mtId") String mtId,
                                  @RequestParam(value = "type") String type) {
        return service.createUrlUpload(mtId, type);
    }
}
