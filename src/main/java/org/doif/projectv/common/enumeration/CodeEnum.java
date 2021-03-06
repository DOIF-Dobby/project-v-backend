package org.doif.projectv.common.enumeration;

import lombok.Getter;
import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.version.constant.VersionStatus;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.system.constant.PropertyGroupType;
import org.doif.projectv.common.user.constant.UserStatus;

@Getter
public enum CodeEnum {
    // common 패키지 하위
    RESPONSE_CODE("responseCode", "응답 코드", ResponseCode.class),
    PROPERTY_GROUP_TYPE("propertyGroupType", "시스템 속성 그룹 유형", PropertyGroupType.class),
    MESSAGE_TYPE("messageType", "메세지 유형", MessageType.class),
    MENU_TYPE("menuType", "메뉴 유형", MenuType.class),
    USER_STATUS("userStatus", "유저 상태", UserStatus.class),
    ENABLE_STATUS("enableStatus", "사용가능 상태", EnableStatus.class),

    // business 패키지 하위
    TASK_TYPE("taskType", "작업 유형", TaskType.class),
    ISSUE_CATEGORY("issueCategory", "이슈 유형", IssueCategory.class),
    ISSUE_STATUS("issueStatus", "이슈 상태", IssueStatus.class),
    VERSION_ISSUE_PROGRESS("versionIssueProgress", "버전별 이슈 진행상황", VersionIssueProgress.class),
    PATCH_STATUS("patchStatus", "패치 상태", PatchStatus.class),
    PATCH_TARGET("patchTarget", "패치 대상", PatchTarget.class),
    VERSION_STATUS("versionStatus", "버전 상태", VersionStatus.class),
    VCS_TYPE("vcsType", "버전관리 유형", VcsType.class),
    BUILD_TOOL("buildTool", "빌드 도구", BuildTool.class);

    private final String key;
    private final String value;
    private final Class<? extends EnumModel> type;

    CodeEnum(String key, String value, Class<? extends EnumModel> type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }
}
