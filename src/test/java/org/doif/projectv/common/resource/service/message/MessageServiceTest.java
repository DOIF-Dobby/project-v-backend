package org.doif.projectv.common.resource.service.message;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.resource.dto.MessageDto;
import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Message;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.message.MessageRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;

    @BeforeEach
    public void init() {
        Message message = new Message("임진성 맨날 늦게 옴", "내 가슴 속엔 먼지만 쌓이죠 say goodbye", EnableStatus.ENABLE, "MESSAGE_JS_LATE", MessageType.WARN);

        em.persist(message);
    }

    @Test
    public void Message_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<MessageDto.Result> results = messageService.select();

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("임진성 맨날 늦게 옴");
    }

    @Test
    public void Message_추가_서비스_테스트() throws Exception {
        // given
        MessageDto.Insert insert = new MessageDto.Insert();
        insert.setName("I cry cry");
        insert.setDescription("don`t lie lie");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setType(MessageType.INFO);
        insert.setMessage("MESSAGE_HARU_HARU");

        // when
        CommonResponse response = messageService.insert(insert);
        List<MessageDto.Result> results = messageService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("임진성 맨날 늦게 옴", "I cry cry");
    }

    @Test
    public void Message_수정_서비스_테스트() throws Exception {
        // given
        Long messageId = messageRepository.findAll().get(0).getId();

        MessageDto.Update update = new MessageDto.Update();
        update.setName("how gee");
        update.setDescription("2020");
        update.setStatus(EnableStatus.DISABLE);
        update.setType(MessageType.ERROR);

        // when
        CommonResponse response = messageService.update(messageId, update);
        List<MessageDto.Result> results = messageService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("how gee");
    }

    @Test
    public void Message_삭제_서비스_테스트() throws Exception {
        // given
        Long messageId = messageRepository.findAll().get(0).getId();

        // when
        CommonResponse response = messageService.delete(messageId);
        List<MessageDto.Result> results = messageService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

}