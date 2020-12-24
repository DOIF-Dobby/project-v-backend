package org.doif.projectv.business.vcs.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.business.vcs.entity.VcsAuthInfo;
import org.doif.projectv.business.vcs.repository.VcsAuthInfoRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VcsAuthInfoServiceImpl implements VcsAuthInfoService {

    private final VcsAuthInfoRepository vcsAuthInfoRepository;
    private final BytesEncryptor bytesEncryptor;

    @Transactional(readOnly = true)
    @Override
    public List<VcsAuthInfoDto.Result> searchByCondition(VcsAuthInfoDto.Search search) {
        return vcsAuthInfoRepository.findByUserId(search.getUserId())
                .stream()
                .map(vcsAuthInfo -> {
                    String decryptAuthId = new String(bytesEncryptor.decrypt(vcsAuthInfo.getVcsAuthId().getBytes()));
                    String decryptAuthPassword = new String(bytesEncryptor.decrypt(vcsAuthInfo.getVcsAuthPassword().getBytes()));

                    return new VcsAuthInfoDto.Result(
                            vcsAuthInfo.getId(),
                            vcsAuthInfo.getUserId(),
                            vcsAuthInfo.getVcsType(),
                            decryptAuthId,
                            decryptAuthPassword,
                            vcsAuthInfo.getStatus()
                            );
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(VcsAuthInfoDto.Insert dto) {
        String encryptAuthId = new String(bytesEncryptor.encrypt(dto.getVcsAuthId().getBytes()));
        String encryptAuthPassword = new String(bytesEncryptor.encrypt(dto.getVcsAuthPassword().getBytes()));

        VcsAuthInfo vcsAuthInfo = new VcsAuthInfo(dto.getUserId(), dto.getVcsType(), encryptAuthId, encryptAuthPassword, dto.getStatus());
        vcsAuthInfoRepository.save(vcsAuthInfo);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, VcsAuthInfoDto.Update dto) {
        Optional<VcsAuthInfo> optionalVcsAuthInfo = vcsAuthInfoRepository.findById(id);
        VcsAuthInfo vcsAuthInfo = optionalVcsAuthInfo.orElseThrow(() -> new IllegalArgumentException("버전관리 인증정보를 찾을 수 없음"));

        String encryptAuthId = new String(bytesEncryptor.encrypt(dto.getVcsAuthId().getBytes()));
        String encryptAuthPassword = new String(bytesEncryptor.encrypt(dto.getVcsAuthPassword().getBytes()));

        vcsAuthInfo.changeVcsAuthInfo(encryptAuthId, encryptAuthPassword, dto.getStatus());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<VcsAuthInfo> optionalVcsAuthInfo = vcsAuthInfoRepository.findById(id);
        VcsAuthInfo vcsAuthInfo = optionalVcsAuthInfo.orElseThrow(() -> new IllegalArgumentException("버전관리 인증정보를 찾을 수 없음"));
        vcsAuthInfoRepository.delete(vcsAuthInfo);

        return ResponseUtil.ok();
    }

    @Override
    @Transactional(readOnly = true)
    public VcsAuthInfoDto.Result searchByUserIdAndVcsType(String userId, VcsType vcsType) {
        Optional<VcsAuthInfo> optionalVcsAuthInfo = vcsAuthInfoRepository.findByUserIdAndVcsType(userId, vcsType);
        VcsAuthInfo vcsAuthInfo = optionalVcsAuthInfo.orElseThrow(() -> new IllegalArgumentException("버전관리 인증정보를 찾을 수 없음"));

        String decryptAuthId = new String(bytesEncryptor.decrypt(vcsAuthInfo.getVcsAuthId().getBytes()));
        String decryptAuthPassword = new String(bytesEncryptor.decrypt(vcsAuthInfo.getVcsAuthPassword().getBytes()));

        return new VcsAuthInfoDto.Result(
                vcsAuthInfo.getId(),
                vcsAuthInfo.getUserId(),
                vcsAuthInfo.getVcsType(),
                decryptAuthId,
                decryptAuthPassword,
                vcsAuthInfo.getStatus()
        );
    }
}
