package org.doif.projectv.common.resource.web.message;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MessageDto;
import org.doif.projectv.common.resource.service.message.MessageService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/resources/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<MessageDto.Response> select() {
        List<MessageDto.Result> result = messageService.select();
        MessageDto.Response response = new MessageDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody @Valid MessageDto.Insert dto) {
        CommonResponse response = messageService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody @Valid MessageDto.Update dto) {
        CommonResponse response = messageService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = messageService.delete(id);
        return ResponseEntity.ok(response);
    }

}
