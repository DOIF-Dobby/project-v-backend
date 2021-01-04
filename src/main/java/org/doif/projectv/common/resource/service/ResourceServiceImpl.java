package org.doif.projectv.common.resource.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.*;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.ResourceRepository;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.label.LabelRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final PageRepository pageRepository;
    private final ButtonRepository buttonRepository;
    private final TabRepository tabRepository;
    private final LabelRepository labelRepository;

    /**
     * ResourceAuthority 하위의 Resource들을 userId 별로 캐싱해두고 다음 권한 검사할 때는 캐싱된 것으로 비교한다.
     * @param userId
     * @return
     */
    @Cacheable(value = "authorityResourceCache", key = "#userId")
    @Override
    public List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId) {
        return resourceRepository.searchAuthorityResource(userId);
    }

    /**
     * ResourcePage들을 userId 별로 캐싱해두고 다음 권한 검사할 때는 캐싱된 것으로 비교한다.
     * @param userId
     * @return
     */
    @Cacheable(value = "pageResourceCache", key = "#userId")
    @Override
    public List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId) {
        return resourceRepository.searchPageResource(userId);
    }

    /**
     * 특정 페이지의 자식들을 캐싱해두고 같은 페이지 요청 시 캐싱된 것을 반환 한다.
     * @param url
     * @return
     */
    @Cacheable(value = "pageChildResourceCache", key = "#url")
    @Override
    public PageDto.Child searchPageChildResource(String url) {
        PageDto.Child child = new PageDto.Child();

        Optional<Page> optionalPage = pageRepository.findByUrl(url);
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));

        Map<String, ButtonDto.Result> buttonMap = buttonRepository.findAllByPageAndStatus(page, EnableStatus.ENABLE)
                .stream()
                .map(button -> new ButtonDto.Result(
                        button.getId(),
                        button.getName(),
                        button.getDescription(),
                        button.getStatus(),
                        button.getCode(),
                        button.getUrl(),
                        button.getHttpMethod(),
                        button.getPage().getId(),
                        button.getIcon()
                ))
                .collect(Collectors.toMap(ResourceDto.Result::getCode, result -> result));

        Map<String, TabDto.Result> tabMap = tabRepository.findAllByPageAndStatus(page, EnableStatus.ENABLE)
                .stream()
                .map(tab -> new TabDto.Result(
                        tab.getId(),
                        tab.getName(),
                        tab.getDescription(),
                        tab.getStatus(),
                        tab.getCode(),
                        tab.getUrl(),
                        tab.getHttpMethod(),
                        tab.getPage().getId(),
                        tab.getTabGroup(),
                        tab.getSort()
                ))
                .collect(Collectors.toMap(ResourceDto.Result::getCode, result -> result));

        Map<String, LabelDto.Result> labelMap = labelRepository.findAllByPageAndStatus(page, EnableStatus.ENABLE)
                .stream()
                .map(label -> new LabelDto.Result(
                        label.getId(),
                        label.getName(),
                        label.getDescription(),
                        label.getStatus(),
                        label.getCode(),
                        label.getPage().getId()
                ))
                .collect(Collectors.toMap(ResourceDto.Result::getCode, result -> result));

        child.setButtonMap(buttonMap);
        child.setTabMap(tabMap);
        child.setLabelMap(labelMap);

        return child;
    }


}
