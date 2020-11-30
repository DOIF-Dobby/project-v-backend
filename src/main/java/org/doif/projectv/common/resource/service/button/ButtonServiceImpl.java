package org.doif.projectv.common.resource.service.button;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.ButtonDto;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
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
public class ButtonServiceImpl implements ButtonService {

    private final ButtonRepository buttonRepository;
    private final PageRepository pageRepository;

    @Override
    public List<ButtonDto.Result> selectByPage(Long pageId) {
        Optional<Page> optionalPage = pageRepository.findById(pageId);
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
        return buttonRepository.findAllByPage(page)
                .stream()
                .map(button -> new ButtonDto.Result(
                        button.getId(),
                        button.getName(), button.getDescription(),
                        button.getStatus(), button.getUrl(),
                        button.getHttpMethod(),
                        button.getPage().getId(),
                        button.getIcon()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(ButtonDto.Insert dto) {
        Optional<Page> optionalPage = pageRepository.findById(dto.getPageId());
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));

        Button button = new Button(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getUrl(), dto.getHttpMethod(), page, dto.getIcon());
        buttonRepository.save(button);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, ButtonDto.Update dto) {
        Optional<Button> optionalButton = buttonRepository.findById(id);
        Button button = optionalButton.orElseThrow(() -> new IllegalArgumentException("버튼을 찾을 수 없음"));
        button.changeButton(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getUrl(), dto.getHttpMethod(), dto.getIcon());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Button> optionalButton = buttonRepository.findById(id);
        Button button = optionalButton.orElseThrow(() -> new IllegalArgumentException("버튼을 찾을 수 없음"));
        buttonRepository.delete(button);

        return ResponseUtil.ok();
    }
}
