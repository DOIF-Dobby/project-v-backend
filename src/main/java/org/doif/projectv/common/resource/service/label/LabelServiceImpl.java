package org.doif.projectv.common.resource.service.label;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.label.LabelRepository;
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
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final PageRepository pageRepository;

    @Transactional(readOnly = true)
    @Override
    public List<LabelDto.Result> selectByPage(Long pageId) {
        Optional<Page> optionalPage = pageRepository.findById(pageId);
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
        return labelRepository.findAllByPage(page)
                .stream()
                .map(label -> new LabelDto.Result(
                        label.getId(),
                        label.getName(),
                        label.getDescription(),
                        label.getStatus(),
                        label.getCode(),
                        label.getPage().getId()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(LabelDto.Insert dto) {
        Optional<Page> optionalPage = pageRepository.findById(dto.getPageId());
        Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));

        Label label = new Label(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getCode(), page);
        labelRepository.save(label);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, LabelDto.Update dto) {
        Optional<Label> optionalLabel = labelRepository.findById(id);
        Label label = optionalLabel.orElseThrow(() -> new IllegalArgumentException("라벨을 찾을 수 없음"));
        label.changeLabel(dto.getName(), dto.getDescription(), dto.getStatus());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Label> optionalLabel = labelRepository.findById(id);
        Label label = optionalLabel.orElseThrow(() -> new IllegalArgumentException("라벨을 찾을 수 없음"));
        labelRepository.delete(label);

        return ResponseUtil.ok();
    }
}
