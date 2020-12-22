package org.doif.projectv.business.vcs.operator;

import org.doif.projectv.business.vcs.dto.VcsDto;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VcsOperator {

    List<VcsDto.Log> getLogs(String repositoryInfo, LocalDate startDate, LocalDate endDate);

    Optional<VcsDto.Tag> tag(String repositoryInfo, String versionName);

    File checkout(String repositoryInfo);

    void commit(File vcsFile, String commitMessage);
}
