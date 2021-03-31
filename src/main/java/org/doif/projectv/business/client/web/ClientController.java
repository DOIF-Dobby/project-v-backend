package org.doif.projectv.business.client.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.client.service.ClientService;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.business.patchlog.service.PatchLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final PatchLogService patchLogService;

    @GetMapping("/{id}/patch-logs")
    public ResponseEntity<PatchLogDto.Response> searchByCondition(@PathVariable Long id, PatchLogDto.Search search, Pageable pageable) {
        Page<PatchLogDto.Result> result = patchLogService.searchByCondition(id, search, pageable);
        PatchLogDto.Response response = new PatchLogDto.Response(result);
        return ResponseEntity.ok(response);
    }
}
