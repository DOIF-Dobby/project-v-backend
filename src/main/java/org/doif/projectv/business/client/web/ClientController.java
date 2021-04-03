package org.doif.projectv.business.client.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.client.dto.ClientDto;
import org.doif.projectv.business.client.service.ClientService;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.business.patchlog.service.PatchLogService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final PatchLogService patchLogService;

    @GetMapping
    public ResponseEntity<ClientDto.Response> select() {
        List<ClientDto.Result> result = clientService.select();
        ClientDto.Response response = new ClientDto.Response(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody ClientDto.Insert dto) {
        CommonResponse response = clientService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody ClientDto.Update dto) {
        CommonResponse response = clientService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = clientService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/patch-logs")
    public ResponseEntity<PatchLogDto.Response> searchByCondition(@PathVariable Long id, PatchLogDto.Search search, Pageable pageable) {
        Page<PatchLogDto.Result> result = patchLogService.searchByCondition(id, search, pageable);
        PatchLogDto.Response response = new PatchLogDto.Response(result);
        return ResponseEntity.ok(response);
    }
}
