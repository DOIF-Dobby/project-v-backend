package org.doif.projectv.common.resource.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

//@SpringBootTest
//@Transactional
class ResourceRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


}