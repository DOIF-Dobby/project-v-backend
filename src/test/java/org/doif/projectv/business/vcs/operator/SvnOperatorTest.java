package org.doif.projectv.business.vcs.operator;

import org.junit.jupiter.api.Test;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

import java.time.ZoneId;
import java.util.Collection;

public class SvnOperatorTest {

//    @Test
    public void logTest() throws Exception {
        SVNURL url = SVNURL.parseURIEncoded("");
        SVNRepository repository = SVNRepositoryFactory.create(url);
        BasicAuthenticationManager authManager = BasicAuthenticationManager.newInstance("", "".toCharArray());
        repository.setAuthenticationManager(authManager);

        Collection<SVNLogEntry> logEntries = repository.log(new String[]{""}, null, 0, repository.getLatestRevision(), false, false);

        System.out.println("logEntries.size() = " + logEntries.size());

        logEntries.stream()
                .forEach(svnLogEntry -> System.out.println(svnLogEntry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() + "   " + svnLogEntry.getMessage()));
    }
}
