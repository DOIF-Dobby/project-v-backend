package org.doif.projectv.business.maven.service;

import java.io.File;

public interface MavenService {

    void updateVersion(File pomFile, String version);
}
