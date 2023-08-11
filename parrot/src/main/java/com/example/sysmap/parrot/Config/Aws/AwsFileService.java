package com.example.sysmap.parrot.Config.Aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsFileService implements IAwsFileService {
    
    @Autowired
    private AwsService awsService;

    @Override
    public String upload(MultipartFile file, String fileName) {
        var fileUri = "";

        try {
            fileUri = awsService.upload(file, fileName);
        } catch (Exception e){

        }
        return fileUri;
    }
}
