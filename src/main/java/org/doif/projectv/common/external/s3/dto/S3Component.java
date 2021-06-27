package org.doif.projectv.common.external.s3.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
//@ConfigurationProperties(prefix = "cloud.aws.s3")
@Component
public class S3Component {

    private String bucket;
    private String profilePictureDirectory;

    public S3Component() {
        this.bucket = "projectv-deploy";
        this.profilePictureDirectory = this.bucket + "/" + "profile-picture";
    }
}
