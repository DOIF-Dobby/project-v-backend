package org.doif.projectv.common.external.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface S3Service {
    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String bucket, String fileName);

    void deleteFile(String bucket, String fileName);

    String getFileUrl(String bucket, String fileName);
}
