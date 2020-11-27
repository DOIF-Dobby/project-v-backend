package org.doif.projectv.common.role.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.role.dto.RoleDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    RoleService roleService;

    @BeforeEach
    public void init() {
        Role role = new Role("개발자 Role", "개발자 롤입니다.", EnableStatus.ENABLE);

        em.persist(role);
    }

    @Test
    void ROLE_조회_서비스_테스트() {
        // given

        // when
        List<RoleDto.Result> results = roleService.select();

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("개발자 Role");
    }

    @Test
    void ROLE_추가_서비스_테스트() {
        // given
        RoleDto.Insert insert = new RoleDto.Insert("관리자 Role", "관리자 롤입니다.");

        // when
        CommonResponse response = roleService.insert(insert);
        List<RoleDto.Result> results = roleService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("개발자 Role", "관리자 Role");
    }

    @Test
    void ROLE_수정_서비스_테스트() {
        // given
        RoleDto.Update update = new RoleDto.Update("개발자 Role", "개발자 롤입니다.", EnableStatus.DISABLE);
        Long roleId = roleService.select().get(0).getRoleId();

        // when
        CommonResponse response = roleService.update(roleId, update);
        List<RoleDto.Result> results = roleService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getStatusName()).isEqualTo(EnableStatus.DISABLE.getMessage());
    }

    @Test
    void ROLE_삭제_서비스_테스트() {
        // given
        Long roleId = roleService.select().get(0).getRoleId();

        // when
        CommonResponse response = roleService.delete(roleId);
        List<RoleDto.Result> results = roleService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }
}