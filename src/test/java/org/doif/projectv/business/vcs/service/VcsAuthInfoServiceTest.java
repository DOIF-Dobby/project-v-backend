package org.doif.projectv.business.vcs.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.business.vcs.entity.VcsAuthInfo;
import org.doif.projectv.business.vcs.repository.VcsAuthInfoRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VcsAuthInfoServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    VcsAuthInfoService vcsAuthInfoService;

    @Autowired
    VcsAuthInfoRepository vcsAuthInfoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BytesEncryptor bytesEncryptor;

    @BeforeEach
    public void init() {
        String limPassword = passwordEncoder.encode("limPassword");
        String encAuthId = new String(bytesEncryptor.encrypt("svnId".getBytes()));
        String encAuthPassword = new String(bytesEncryptor.encrypt("svnPassword".getBytes()));

        User user = new User("lim", limPassword, "임진성", UserStatus.VALID);
        VcsAuthInfo vcsAuthInfo = new VcsAuthInfo("lim", VcsType.SVN, encAuthId, encAuthPassword, EnableStatus.ENABLE);

        em.persist(user);
        em.persist(vcsAuthInfo);
    }

    @Test
    public void 버전관리_인증정보_조회_서비스_테스트() throws Exception {
        // given
        String userId = "lim";
        VcsAuthInfoDto.Search search = new VcsAuthInfoDto.Search();
        search.setUserId(userId);

        // when
        List<VcsAuthInfoDto.Result> results = vcsAuthInfoService.searchByCondition(search);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getVcsAuthPassword()).isEqualTo("svnPassword");
    }

    @Test
    public void 버전관리_인증정보_추가_서비스_테스트() throws Exception {
        // given
        VcsAuthInfoDto.Insert insert = new VcsAuthInfoDto.Insert();
        insert.setUserId("lim");
        insert.setVcsType(VcsType.GIT);
        insert.setVcsAuthId("gitId");
        insert.setVcsAuthPassword("gitPassword");
        insert.setStatus(EnableStatus.ENABLE);

        String userId = "lim";
        VcsAuthInfoDto.Search search = new VcsAuthInfoDto.Search();
        search.setUserId(userId);

        // when
        CommonResponse response = vcsAuthInfoService.insert(insert);
        List<VcsAuthInfoDto.Result> results = vcsAuthInfoService.searchByCondition(search);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(1).getVcsAuthPassword()).isEqualTo("gitPassword");
    }

    @Test
    public void 버전관리_인증정보_수정_서비스_테스트() throws Exception {
        // given
        Long vcsAuthInfoId = vcsAuthInfoRepository.findAll().get(0).getId();

        VcsAuthInfoDto.Update update = new VcsAuthInfoDto.Update();
        update.setVcsAuthId("gitId");
        update.setVcsAuthPassword("gitPassword2");
        update.setStatus(EnableStatus.ENABLE);

        String userId = "lim";
        VcsAuthInfoDto.Search search = new VcsAuthInfoDto.Search();
        search.setUserId(userId);

        // when
        CommonResponse response = vcsAuthInfoService.update(vcsAuthInfoId, update);
        List<VcsAuthInfoDto.Result> results = vcsAuthInfoService.searchByCondition(search);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getVcsAuthPassword()).isEqualTo("gitPassword2");
    }

    @Test
    public void 버전관리_인증정보_삭제_서비스_테스트() throws Exception {
        // given
        Long vcsAuthInfoId = vcsAuthInfoRepository.findAll().get(0).getId();

        String userId = "lim";
        VcsAuthInfoDto.Search search = new VcsAuthInfoDto.Search();
        search.setUserId(userId);

        // when
        CommonResponse response = vcsAuthInfoService.delete(vcsAuthInfoId);
        List<VcsAuthInfoDto.Result> results = vcsAuthInfoService.searchByCondition(search);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

}