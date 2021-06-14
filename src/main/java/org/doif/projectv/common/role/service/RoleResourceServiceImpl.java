package org.doif.projectv.common.role.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.role.repository.RoleResourceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleResourceServiceImpl implements RoleResourceService {

    private final RoleResourceRepository roleResourceRepository;
    private final RoleRepository roleRepository;
    private final PageRepository pageRepository;
    private final ButtonRepository buttonRepository;
    private final TabRepository tabRepository;

    @Override
    public List<RoleResourceDto.ResultPage> selectPage(RoleResourceDto.SearchPage search) {
        List<RoleResourceDto.ResultPage> resultPages = roleResourceRepository.selectPage(search);
        for (RoleResourceDto.ResultPage resultPage : resultPages) {
            List<RoleResourceDto.ResultButton> resultButtons = roleResourceRepository.selectButton(new RoleResourceDto.Search(search.getRoleId(), resultPage.getPageId()));
            List<RoleResourceDto.ResultTab> resultTabs = roleResourceRepository.selectTab(new RoleResourceDto.Search(search.getRoleId(), resultPage.getPageId()));
            resultPage.setButtons(resultButtons);
            resultPage.setTabs(resultTabs);
        }

        return resultPages;
    }

    @Transactional
    @Override
    public CommonResponse allocate(RoleResourceDto.Allocate dto) {
        Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
        Role role = optionalRole.orElseThrow(() -> new IllegalArgumentException("Role를 찾을 수 없음"));

        // TODO: deleteBy ... 했는데 select문이 됨 좀 더 찾아볼까?
        roleResourceRepository.deleteByRoleId(role.getId());

        // TODO: roleResource를 한 방에 지우고 다시 Insert 하는 것이 맞는가? 맞다면 아래처럼 findById로 찾는 것이 아니라, insert into select로 찾아야 하는 것이 아닐까?
        // 일단 쓰다가 성능상 이슈가 발생하면 한 번 고민해보자

        dto.getPages()
                .forEach(pageId -> {
                    Optional<Page> optionalPage = pageRepository.findById(pageId);
                    Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
                    RoleResource roleResource = new RoleResource(role, page);
                    roleResourceRepository.save(roleResource);
                });

        dto.getButtons()
                .forEach(buttonId -> {
                    Optional<Button> optionalButton = buttonRepository.findById(buttonId);
                    Button button = optionalButton.orElseThrow(() -> new IllegalArgumentException("버튼을 찾을 수 없음"));
                    RoleResource roleResource = new RoleResource(role, button);
                    roleResourceRepository.save(roleResource);
                });

        dto.getTabs()
                .forEach(tabId -> {
                    Optional<Tab> optionalTab = tabRepository.findById(tabId);
                    Tab tab = optionalTab.orElseThrow(() -> new IllegalArgumentException("탭을 찾을 수 없음"));
                    RoleResource roleResource = new RoleResource(role, tab);
                    roleResourceRepository.save(roleResource);
                });

        return ResponseUtil.ok();
    }
}
