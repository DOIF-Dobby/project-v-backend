package org.doif.projectv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ProjectvApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectvApplication.class, args);
    }

}
