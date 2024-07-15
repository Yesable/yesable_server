package com.example.yesable_be;

import com.example.yesable_be.repository.mariadb.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//@SpringBootTest UserRepository를 찾을 수 없다는 에러 발생
@DataJpaTest
class YesableBeApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    void contextLoads() {
    }

}
