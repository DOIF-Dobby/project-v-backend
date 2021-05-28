package org.doif.projectv.business.test;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.entity.*;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("none")
@Component
@RequiredArgsConstructor
public class DevInitData2 {

    private final DevInitDataService devInitDataService;

    @PostConstruct
    public void init() {
        devInitDataService.init();
    }

    @Component
    static class DevInitDataService {
        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        BytesEncryptor bytesEncryptor;

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Page page = em.find(Page.class, 5L);
            Role role = em.find(Role.class, 1L);


//            Button button = new Button("조회", "페이지 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_PAGE_FIND_BY_LABEL", "/api/resources/pages", HttpMethod.GET, page, "");

//            em.remove(em.find(Resource.class, 29L));
//            em.remove(em.find(RoleResource.class, 29L));
//            em.remove(em.find(Button.class, 29L));
//            em.persist(button);
//            em.persist(new RoleResource(role, button));
        }
    }
}
