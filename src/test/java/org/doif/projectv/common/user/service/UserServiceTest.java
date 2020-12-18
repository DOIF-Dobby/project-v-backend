package org.doif.projectv.common.user.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        String limPassword = passwordEncoder.encode("limPassword");

        User user = new User("lim", limPassword, "임진성", UserStatus.VALID);
        em.persist(user);
    }

    @Test
    public void 유저_조회_서비스_테스트() throws Exception {
        // given
        UserDto.Search search = new UserDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<UserDto.Result> results = userService.selectByCondition(search, pageRequest);
        List<UserDto.Result> content = results.getContent();

        // then
        for (UserDto.Result result : content) {
            System.out.println("result = " + result);
        }
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getName()).isEqualTo("임진성");
    }

    @Test
    public void 유저_추가_서비스_테스트() throws Exception {
        // given
        UserDto.Insert insert = new UserDto.Insert();
        insert.setId("typ");
        insert.setName("태용팍");
        insert.setStatus(UserStatus.VALID);
        insert.setPassword("1234");

        UserDto.Search search = new UserDto.Search();
        search.setName("태용");
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        CommonResponse response = userService.insert(insert);
        Page<UserDto.Result> results = userService.selectByCondition(search, pageRequest);
        List<UserDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
    }

    @Test
    public void 유저_수정_서비스_테스트() throws Exception {
        // given
        UserDto.Update update = new UserDto.Update();
        update.setName("진성");
        update.setStatus(UserStatus.VALID);

        UserDto.Search search = new UserDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        CommonResponse response = userService.update("lim", update);
        Page<UserDto.Result> results = userService.selectByCondition(search, pageRequest);
        List<UserDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.get(0).getName()).isEqualTo("진성");
    }

    @Test
    public void 유저_삭제_서비스_테스트() throws Exception {
        // given
        UserDto.Search search = new UserDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        CommonResponse response = userService.delete("lim");
        Page<UserDto.Result> results = userService.selectByCondition(search, pageRequest);
        List<UserDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content).isEmpty();
    }

    @Test
    public void 유저_추가시_아이디_중복_체크_테스트() throws Exception {
        // given
        UserDto.Insert insert = new UserDto.Insert();
        insert.setId("lim");
        insert.setName("임");
        insert.setStatus(UserStatus.VALID);
        insert.setPassword("1234");

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userService.insert(insert));

        // then
        assertThat(e.getMessage()).isEqualTo("이미 사용중인 ID 입니다.");
    }
}