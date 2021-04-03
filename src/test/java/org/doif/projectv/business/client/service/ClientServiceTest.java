package org.doif.projectv.business.client.service;

import org.doif.projectv.business.client.dto.ClientDto;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.client.repository.ClientRepository;
import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClientServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;


    @BeforeEach
    public void init() {
        Client client = new Client("금융결제원", "VVVVVIP", "01012345678", "1112233333", "12345", "서울", "강남");
        em.persist(client);
    }

    @Test
    public void 고객사_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<ClientDto.Result> results = clientService.select();

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("금융결제원");
    }

    @Test
    public void 고객사_추가_서비스_테스트() throws Exception {
        // given
        ClientDto.Insert insert = new ClientDto.Insert("견우와직녀", "77", "01012345678", "1112233333", "12345", "서울", "강남");

        // when
        CommonResponse response = clientService.insert(insert);
        List<ClientDto.Result> results = clientService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("금융결제원", "견우와직녀");
    }

    @Test
    public void 고객사_수정_서비스_테스트() throws Exception {
        // given
        Long clientId = clientRepository.findAll().get(0).getId();
        ClientDto.Update update = new ClientDto.Update("금융결제원", "VIP", "01012345678", "1112233333", "12345", "서울", "강남");

        // when
        CommonResponse response = clientService.update(clientId, update);
        List<ClientDto.Result> results = clientService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getDescription()).isEqualTo("VIP");
    }

    @Test
    public void 고객사_삭제_서비스_테스트() throws Exception {
        // given
        Long clientId = clientRepository.findAll().get(0).getId();

        // when
        CommonResponse response = clientService.delete(clientId);
        List<ClientDto.Result> results = clientService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }
}