package org.doif.projectv.common.user.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.dto.UserRoleDto;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRoleServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void init() {
        User user1 = new User("kjpmj", "1234", "김명진", UserStatus.VALID, "", "");
        User user2 = new User("kim", "1234", "김명진", UserStatus.VALID, "", "");
        Role role1 = new Role("개발자 Role", "개발자 롤입니다.", EnableStatus.ENABLE);
        Role role2 = new Role("관리자 Role", "관리자 롤입니다.", EnableStatus.ENABLE);
        Role role3 = new Role("사용자 Role", "사용자 롤입니다.", EnableStatus.ENABLE);

        UserRole userRole1 = new UserRole(user1, role1);
        UserRole userRole2 = new UserRole(user2, role2);

        em.persist(user1);
        em.persist(user2);
        em.persist(role1);
        em.persist(role2);
        em.persist(role3);

        em.persist(userRole1);
        em.persist(userRole2);
    }

    @Test
    public void USER_ROLE_조회_서비스_테스트() throws Exception {
        // given
        String userId = "kjpmj";

        // when
        List<UserRoleDto.ResultRole> results = userRoleService.selectRole(userId);

        // then
        assertThat(results.size()).isEqualTo(3);
        assertThat(results.get(0).isChecked()).isEqualTo(true);
    }

    @Test
    public void USER_ROLE_할당_서비스_테스트() throws Exception {
        // given
        List<Role> roles = roleRepository.findAll();

        List<Long> roleIds = roles.stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        roleIds.remove(roleIds.size() - 1);

        UserRoleDto.Allocate allocate = new UserRoleDto.Allocate();
        allocate.setRoleIds(roleIds);
        allocate.setUserId("kjpmj");

        // when
        CommonResponse response = userRoleService.allocate(allocate);
        List<UserRoleDto.ResultRole> results = userRoleService.selectRole("kjpmj");

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(3);
        assertThat(results).extracting("checked").containsExactly(true, true, false);
    }
}
