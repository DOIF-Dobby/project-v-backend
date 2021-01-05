-- create user 'vuser'@'%' identified by 'vuser11201!!';
-- GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER
--    ON projectv.* to 'vuser'@'%';
--
--  create user 'vuser_crud'@'%' identified by 'vuser_crud11201!!';
--  GRANT SELECT, INSERT, UPDATE, DELETE
--      ON projectv.* to 'vuser_crud'@'%';

-- ------------------------
-- ------------------------
-- ------------------------

-- project
create or replace table project
(
    project_id         bigint auto_increment
        primary key comment '프로젝트 ID',
    project_name       varchar(50)  not null comment '프로젝트명',
    description        varchar(255) null comment '프로젝트 설명',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = '프로젝트', charset = utf8;

-- module
create or replace table module
(
    module_id          bigint auto_increment
        primary key comment '모듈 ID',
    module_name        varchar(50)  not null comment '모듈명',
    description        varchar(255) null comment '모듈 설명',
    project_id         bigint       not null comment '프로젝트 ID',
    vcs_type           varchar(20)  not null comment '버전관리시스템 유형',
    vcs_repository     varchar(255) null comment '버전관리시스템 저장소',
    build_tool         varchar(20)  null comment '빌드 도구',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시',
    constraint FK_module__project
        foreign key (project_id) references project (project_id)
) comment = '모듈', charset = utf8;

-- version
create or replace table version
(
    version_id         bigint auto_increment
        primary key comment '버전 ID',
    name               varchar(50)  not null comment '버전명',
    description        longtext     not null comment '버전 설명',
    module_id          bigint       not null comment '모듈 ID',
    status             varchar(20)  not null comment '버전 상태',
    revision           varchar(255) null comment '커밋 번호',
    tag                varchar(255) null comment '태그',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시',
    constraint FK_version__module
        foreign key (module_id) references module (module_id)
) comment = '버전', charset = utf8;

-- issue
create or replace table issue
(
    issue_id           bigint auto_increment
        primary key comment '이슈 ID',
    issue_name         varchar(255) not null comment '이슈명',
    contents           longtext     not null comment '이슈 내용',
    status             varchar(20)  not null comment '이슈 상태',
    category           varchar(20)  not null comment '이슈 유형',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = '이슈', charset = utf8;

-- version_issue
create or replace table version_issue
(
    version_issue_id   bigint auto_increment
        primary key comment '버전-이슈 ID',
    version_id         bigint       not null comment '버전 ID',
    issue_id           bigint       not null comment '이슈 ID',
    issue_ym           varchar(6)   not null comment '작업 년월',
    progress           varchar(20)  not null comment '진행 상황',
    assignee           varchar(50)  null comment '작업 예정자',
    remark             varchar(255) null comment '비고',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시',
    constraint FK_version_issue__issue
        foreign key (issue_id) references issue (issue_id),
    constraint FK_version_issue__version
        foreign key (version_id) references version (version_id)
) comment = '버전별 이슈', charset = utf8;

create unique index UK_version_id__issue_id on version_issue(version_id, issue_id);

-- task
create or replace table task
(
    task_id            bigint auto_increment
        primary key comment '작업 ID',
    version_issue_id   bigint       not null comment '버전-이슈 ID',
    start_date         date         not null comment '작업 시작일',
    end_date           date         not null comment '작업 종료일',
    contents           longtext     not null comment '작업 내용',
    type               varchar(20)  not null comment '작업 유형',
    man_day            double       not null comment '공수(M/D)',
    worker             varchar(50)  not null comment '작업자',
    remark             varchar(255) null comment '비고',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시',
    constraint FK_task__version_issue
        foreign key (version_issue_id) references version_issue (version_issue_id)
) comment = '작업', charset = utf8;

-- patch_log
create or replace table patch_log
(
    patch_log_id        bigint auto_increment
        primary key comment '패치 이력 ID',
    version_id          bigint       not null comment '버전 ID',
    target              varchar(10)  not null comment '패치 적용 대상',
    status              varchar(20)  not null comment '패치 상태',
    patch_schedule_date date         not null comment '패치 예정 일자',
    patch_date          date         null comment '패치 일자',
    worker              varchar(50)  null comment '패치 작업자',
    remark              varchar(255) null comment '특이사항',
    created_by          varchar(255) null comment '등록자',
    created_date        datetime(6)  null comment '등록일시',
    last_modified_by    varchar(255) null comment '수정자',
    last_modified_date  datetime(6)  null comment '수정일시',
    constraint FK_patch_log__version
        foreign key (version_id) references version (version_id)
) comment = '패치 이력', charset = utf8;

-- vcs_auth_info
create or replace table vcs_auth_info
(
    vcs_auth_info_id   bigint auto_increment
        primary key comment '버전관리시스템 인증정보 ID',
    user_id            varchar(50)  not null comment '사용자 ID',
    vcs_type           varchar(20)  not null comment '버전관리시스템 유형',
    vcs_auth_id        varchar(255) not null comment '버전관리시스템 인증 ID',
    vcs_auth_password  varchar(255) not null comment '버전관리시스템 인증 패스워드',
    status             varchar(255) not null comment '버전관리시스템 인증정보 상태',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = '버전관리시스템 인증정보', charset = utf8;

create unique index UK_user_id__vcs_type on vcs_auth_info(user_id, vcs_type);

-- ------------------------
-- ------------------------
-- ------------------------

-- user
create or replace table user
(
    user_id            varchar(50)  not null
        primary key comment '사용자 ID',
    password           varchar(255) not null comment '사용자 PW',
    name               varchar(50)  not null comment '사용자 이름',
    status             varchar(20)  not null comment '사용자 상태',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = '사용자', charset = utf8;

-- role
create or replace table role
(
    role_id            bigint auto_increment
        primary key comment 'Role ID',
    name               varchar(50)  not null comment 'Role명',
    description        varchar(255) null comment 'Role 설명',
    status             varchar(20)  not null comment 'Role 상태',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = 'Role', charset = utf8;

-- user_role
create or replace table user_role
(
    user_role_id       bigint auto_increment
        primary key comment '사용자 Role Id',
    user_id            varchar(50)  null comment '사용자 ID',
    role_id            bigint       null comment 'Role ID',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시',
    constraint FK_user_role__user
        foreign key (user_id) references user (user_id),
    constraint FK_user_role__role
        foreign key (role_id) references role (role_id)
) comment = '사용자별 Role', charset = utf8;

create unique index UK_user_id__role_id on user_role(user_id, role_id);

-- resource
create or replace table resource
(
    resource_id        bigint auto_increment
        primary key comment 'Resource ID',
    dtype              varchar(31)  not null comment 'D type',
    name               varchar(50)  not null comment 'Resource명',
    description        varchar(255) null comment 'Resource 설명',
    status             varchar(20)  not null comment 'Resource 상태',
    code               varchar(255) not null comment 'Resource Code',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = 'Resource', charset = utf8;

create unique index UK_code on resource(code);

-- resource_page
create or replace table resource_page
(
    resource_id bigint not null
        primary key comment 'Resource ID',
    url         varchar(255) not null comment 'Resource URL',
    constraint FK_resource_page__resource
        foreign key (resource_id) references resource (resource_id)
) comment = '페이지', charset = utf8;

create unique index UK_url on resource_page(url);

-- resource_authority
create or replace table resource_authority
(
    resource_id bigint       not null
        primary key comment 'Resource ID',
    url         varchar(255) not null comment 'Resource URL',
    http_method varchar(10)  not null comment 'HTTP 메서드',
    page_id     bigint       not null comment '페이지 ID',
    constraint FK_resource_authority__resource_page
        foreign key (page_id) references resource_page (resource_id),
    constraint FK_resource_authority__resource
        foreign key (resource_id) references resource (resource_id)
) comment = '권한 Resource', charset = utf8;

create unique index UK_url__http_method__page_id on resource_authority(url, http_method, page_id);

-- role_resource
create or replace table role_resource
(
    role_resource_id   bigint auto_increment
        primary key comment 'Role Resource ID',
    role_id            bigint       not null comment 'Role ID',
    resource_id        bigint       not null comment 'Resource ID',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시',
    constraint FK_role_resource__resource
        foreign key (resource_id) references resource (resource_id),
    constraint FK_role_resource__role
        foreign key (role_id) references role (role_id)
) comment = 'Role별 Resource', charset = utf8;

create unique index UK_role_id__resource_id on role_resource(role_id, resource_id);

-- resource_menu_category
create or replace table resource_menu_category
(
    resource_id bigint      not null
        primary key comment 'Resource ID',
    parent_id   bigint      null comment '부모 Resource ID',
    sort        int         not null comment '순서',
    icon        varchar(50) null comment '아이콘',
    constraint FK_resource_menu_category__resource_menu_category
        foreign key (parent_id) references resource_menu_category (resource_id),
    constraint FK_resource_menu_category__resource
        foreign key (resource_id) references resource (resource_id)
) comment = '메뉴 카테고리', charset = utf8;

-- resource_menu
create or replace table resource_menu
(
    resource_id      bigint       not null
        primary key comment 'Resource ID',
    menu_category_id bigint       null comment '메뉴 카테고리 ID',
    sort             int          not null comment '순서',
    url              varchar(255) not null comment 'URL',
    icon             varchar(50)  null comment '아이콘',
    constraint FK_resource_menu__resource_menu_category
        foreign key (menu_category_id) references resource_menu_category (resource_id),
    constraint FK_resource_menu__resource
        foreign key (resource_id) references resource (resource_id)
) comment = '메뉴', charset = utf8;

-- resource_button
create or replace table resource_button
(
    resource_id bigint      not null
        primary key comment 'Resource ID',
    icon        varchar(50) null comment '아이콘',
    constraint FK_resource_button__resource_authority
        foreign key (resource_id) references resource_authority (resource_id)
) comment = '버튼', charset = utf8;

-- resource_tab
create or replace table resource_tab
(
    resource_id bigint      not null
        primary key comment 'Resource ID',
    tab_group   varchar(50) not null comment '탭 그룹',
    sort        int         not null comment '순서',
    constraint FK_resource_tab__resource_authority
        foreign key (resource_id) references resource_authority (resource_id)
) comment = '탭', charset = utf8;

-- resource_message
create or replace table resource_message
(
    resource_id bigint      not null
        primary key comment 'Resource ID',
    type        varchar(20) not null comment '메시지 유형',
    constraint FK_resource_message__resource
        foreign key (resource_id) references resource (resource_id)
) comment = '메시지', charset = utf8;

-- resource_label
create or replace table resource_label
(
    resource_id bigint      not null
        primary key comment 'Resource ID',
    page_id     bigint      null comment '페이지 ID',
    constraint FK_resource_label__resource
        foreign key (resource_id) references resource (resource_id),
    constraint FK_resource_label_resource_page
        foreign key (page_id) references resource_page (resource_id)
) comment = '라벨', charset = utf8;

-- system_property
create or replace table system_property
(
    system_property_id bigint auto_increment
        primary key comment '시스템 속성 ID',
    property_group     varchar(50)  not null comment '속성 그룹',
    property           varchar(50)  not null comment '속성',
    value              varchar(255) not null comment '속성 값',
    description        varchar(255) not null comment '속성 설명',
    updatable          char(1)      not null comment '변경 가능 여부',
    created_by         varchar(255) null comment '등록자',
    created_date       datetime(6)  null comment '등록일시',
    last_modified_by   varchar(255) null comment '수정자',
    last_modified_date datetime(6)  null comment '수정일시'
) comment = '시스템 속성', charset = utf8;

create unique index UK_property_group__property on system_property(property_group, property);






