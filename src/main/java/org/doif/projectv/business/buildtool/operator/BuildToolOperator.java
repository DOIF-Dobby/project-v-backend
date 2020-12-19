package org.doif.projectv.business.buildtool.operator;

import java.io.File;

public interface BuildToolOperator {

    void updateVersion(File buildToolFile, String versionName);
}
