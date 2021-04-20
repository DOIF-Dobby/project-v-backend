package org.doif.projectv.business.test;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.entity.Page;
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
//            Page commonPage = new Page("메인 페이지", "메인 페이지", EnableStatus.ENABLE, "PAGE_MAIN", "/api/pages/main");

            Role devRole = em.find(Role.class, 1L);

            MenuCategory menuCategory = em.find(MenuCategory.class, 60L);
            Menu menu1 = em.find(Menu.class, 61L);
            Menu menu2 = em.find(Menu.class, 62L);
            Menu menu3 = em.find(Menu.class, 63L);
            Menu menu4 = em.find(Menu.class, 64L);
            Menu menu5 = em.find(Menu.class, 65L);
            Menu menu6 = em.find(Menu.class, 66L);


            // 페이지 등록
//            em.persist(commonPage);

            // 페이지 RoleResource 등록
//            em.persist(new RoleResource(devRole, commonPage));
            em.persist(new RoleResource(devRole, menuCategory));
            em.persist(new RoleResource(devRole, menu1));
            em.persist(new RoleResource(devRole, menu2));
            em.persist(new RoleResource(devRole, menu3));
            em.persist(new RoleResource(devRole, menu4));
            em.persist(new RoleResource(devRole, menu5));
            em.persist(new RoleResource(devRole, menu6));

            // 버튼 등록 및 RoleResource 등록
        }
    }
}
