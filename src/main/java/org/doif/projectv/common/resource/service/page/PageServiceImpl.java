package org.doif.projectv.common.resource.service.page;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.page.PageRepository;
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
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    @Override
    public List<PageDto.Result> select() {
        return pageRepository.findAll()
                .stream()
                .map(page -> new PageDto.Result(page.getId(), page.getName(), page.getDescription(), page.getStatus(), page.getUrl(), page.getHttpMethod()))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(PageDto.Insert dto) {
        Page page = new Page(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getUrl(), dto.getHttpMethod());
        pageRepository.save(page);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, PageDto.Update dto) {
        Optional<Page> optionalPage = pageRepository.findById(id);
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
        page.changePage(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getUrl(), dto.getHttpMethod());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Page> optionalPage = pageRepository.findById(id);
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
        pageRepository.delete(page);

        return ResponseUtil.ok();
    }
}
