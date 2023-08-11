package com.example.sysmap.parrot.Config.Aws;

import org.springframework.web.multipart.MultipartFile;

public interface IAwsFileService {
    public String upload(MultipartFile file, String fileName);
}
