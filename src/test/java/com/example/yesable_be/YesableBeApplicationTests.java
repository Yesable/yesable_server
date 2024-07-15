package com.example.yesable_be;

import com.example.yesable_be.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest UserRepository를 찾을 수 없다는 에러 발생
@DataJpaTest
class YesableBeApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    void contextLoads() {
    }

}
