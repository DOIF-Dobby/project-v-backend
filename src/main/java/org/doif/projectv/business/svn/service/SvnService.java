package org.doif.projectv.business.svn.service;

import org.doif.projectv.business.svn.dto.SvnDto;
import org.doif.projectv.business.svn.dto.SvnSearchCondition;
import org.doif.projectv.business.svn.dto.SvnTagDto;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface SvnService {

    List<SvnDto> search(SvnSearchCondition condition);

    Optional<SvnTagDto> tag(String svnUrl, String versionName);

    File checkout(String svnUrl);

    void commit(File pomFile, String commitMessage);
}
