package org.doif.projectv.common.external.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.external.s3.dto.S3Component;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class S3ServiceImpl implements S3Service{

    private final AmazonS3Client amazonS3Client;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String bucket, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public void deleteFile(String bucket, String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }

    @Override
    public String getFileUrl(String bucket, String fileName) {
        return String.valueOf(amazonS3Client.getUrl(bucket, fileName));
    }
}
