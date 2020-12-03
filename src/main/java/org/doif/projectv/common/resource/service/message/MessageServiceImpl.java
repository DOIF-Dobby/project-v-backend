package org.doif.projectv.common.resource.service.message;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MessageDto;
import org.doif.projectv.common.resource.entity.Message;
import org.doif.projectv.common.resource.repository.message.MessageRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MessageDto.Result> select() {
        return messageRepository.findAll()
                .stream()
                .map(message -> new MessageDto.Result(
                        message.getId(),
                        message.getName(),
                        message.getDescription(),
                        message.getStatus(),
                        message.getMessage(),
                        message.getType()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(MessageDto.Insert dto) {
        Message message = new Message(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getMessage(), dto.getType());
        messageRepository.save(message);
        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, MessageDto.Update dto) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        Message message = optionalMessage.orElseThrow(() -> new IllegalArgumentException("메세지를 찾을 수 없음"));
        message.changeMessage(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getType());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        Message message = optionalMessage.orElseThrow(() -> new IllegalArgumentException("메세지를 찾을 수 없음"));
        messageRepository.delete(message);

        return ResponseUtil.ok();
    }
}
