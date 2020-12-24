package org.doif.projectv.common.system.service;

import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.system.constant.PropertyGroupType;
import org.doif.projectv.common.system.dto.SystemPropertyDto;
import org.doif.projectv.common.system.entity.SystemProperty;
import org.doif.projectv.common.system.repository.SystemPropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class SystemProperytServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    SystemPropertyService systemPropertyService;

    @Autowired
    SystemPropertyRepository systemPropertyRepository;

    @BeforeEach
    public void init() {
        SystemProperty systemProperty = new SystemProperty(PropertyGroupType.COMMON, "TEST", "테스트입니다.", "테스트이지요", true);
        em.persist(systemProperty);
    }

    @Test
    public void SystemProperty_조회_서비스_테스트() throws Exception {
        // given
        SystemPropertyDto.Search search = new SystemPropertyDto.Search();

        // when
        List<SystemPropertyDto.Result> results = systemPropertyService.searchByCondition(search);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getValue()).isEqualTo("테스트입니다.");
    }

    @Test
    public void SystemProperty_추가_서비스_테스트() throws Exception {
        // given
        SystemPropertyDto.Insert insert = new SystemPropertyDto.Insert();
        insert.setPropertyGroup(PropertyGroupType.COMMON);
        insert.setProperty("TEST2");
        insert.setValue("테스트2");
        insert.setDescription("테스트입니다.");
        insert.setUpdatable(false);

        SystemPropertyDto.Search search = new SystemPropertyDto.Search();
        search.setPropertyGroup(PropertyGroupType.COMMON);

        // when
        CommonResponse response = systemPropertyService.insert(insert);
        List<SystemPropertyDto.Result> results = systemPropertyService.searchByCondition(search);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(1).getProperty()).isEqualTo("TEST2");
    }

    @Test
    public void SystemProperty_수정_서비스_테스트() throws Exception {
        // given
        Long systemPropertyId = systemPropertyRepository.findAll().get(0).getId();

        SystemPropertyDto.Update update = new SystemPropertyDto.Update();
        update.setValue("테스트1");
        update.setDescription("테스트1 입니다.");
        update.setUpdatable(true);

        SystemPropertyDto.Search search = new SystemPropertyDto.Search();
        search.setPropertyGroup(PropertyGroupType.COMMON);

        // when
        CommonResponse response = systemPropertyService.update(systemPropertyId, update);
        List<SystemPropertyDto.Result> results = systemPropertyService.searchByCondition(search);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getDescription()).isEqualTo("테스트1 입니다.");
    }

    @Test
    public void SystemProperty_삭제_서비스_테스트() throws Exception {
        // given
        Long systemPropertyId = systemPropertyRepository.findAll().get(0).getId();
        SystemPropertyDto.Search search = new SystemPropertyDto.Search();

        // when
        CommonResponse response = systemPropertyService.delete(systemPropertyId);
        List<SystemPropertyDto.Result> results = systemPropertyService.searchByCondition(search);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

    @Test
    public void SystemProperty_업데이트_안되야하는_서비스_테스트() throws Exception {
        // given
        Long systemPropertyId = systemPropertyRepository.findAll().get(0).getId();

        SystemPropertyDto.Update update = new SystemPropertyDto.Update();
        update.setValue("테스트1");
        update.setDescription("테스트1 입니다.");
        update.setUpdatable(false);

        SystemPropertyDto.Search search = new SystemPropertyDto.Search();
        search.setPropertyGroup(PropertyGroupType.COMMON);

        // when
        systemPropertyService.update(systemPropertyId, update);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> systemPropertyService.update(systemPropertyId, update));

        // then
        assertThat(e.getMessage()).isEqualTo("수정이 불가능한 SystemProperty");
    }
}