package org.doif.projectv.common.resource.service.tab;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
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
public class TabServiceImpl implements TabService {

    private final TabRepository tabRepository;
    private final PageRepository pageRepository;

    @Transactional(readOnly = true)
    @Override
    public List<TabDto.Result> selectByPage(TabDto.Search search) {
        Optional<Page> optionalPage = pageRepository.findById(search.getPageId());
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
        return tabRepository.findAllByPage(page)
                .stream()
                .map(tab -> new TabDto.Result(
                        tab.getId(),
                        tab.getName(),
                        tab.getDescription(),
                        tab.getStatus(),
                        tab.getUrl(),
                        tab.getHttpMethod(),
                        tab.getTabGroup(),
                        tab.getSort()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(TabDto.Insert dto) {
        Optional<Page> optionalPage = pageRepository.findById(dto.getPageId());
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));

        Tab tab = new Tab(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getUrl(), dto.getHttpMethod(), page, dto.getTabGroup(), dto.getSort());
        tabRepository.save(tab);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, TabDto.Update dto) {
        Optional<Tab> optionalTab = tabRepository.findById(id);
        Tab tab = optionalTab.orElseThrow(() -> new IllegalArgumentException("탭을 찾을 수 없음"));
        tab.changeTab(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getUrl(), dto.getHttpMethod(), dto.getTabGroup(), dto.getSort());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Tab> optionalTab = tabRepository.findById(id);
        Tab tab = optionalTab.orElseThrow(() -> new IllegalArgumentException("탭을 찾을 수 없음"));
        tabRepository.delete(tab);

        return ResponseUtil.ok();
    }
}
