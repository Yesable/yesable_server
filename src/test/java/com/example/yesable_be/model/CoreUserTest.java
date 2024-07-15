package com.example.yesable_be.model;

import com.example.yesable_be.model.entity.mariadb.CoreUser;
import com.example.yesable_be.repository.mariadb.UserRepository;
import com.example.yesable_be.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //jpa 관련 설정만 로드하며 자동으로 인메모리 환경에서 동작, 끝나면 drop
//@SpringBootTest //main과 같은 사양의 테스트 환경을 제공
class CoreUserTest {

    @Autowired
    private UserRepository userRepository;

    private CoreUser coreUser;

    @BeforeEach
    void setUp() {
        coreUser = new CoreUser();
        coreUser.setPassword("password123");
       // coreUser.setUsertype("Individual"); DiscriminatorColumn으로 대체
        coreUser.setEmail("test@example.com");
        coreUser.setPhoneNumber("1234567890");
        coreUser.setName("Test User");
        coreUser.setGender(Gender.MALE);
        coreUser.setDateOfBirth(LocalDate.of(1990, 1, 1));
    }

    @Test
    @DisplayName("Should correctly save and retrieve CoreUser")
    void saveAndRetrieveCoreUser() {
        userRepository.save(coreUser);
        Optional<CoreUser> retrievedUser = userRepository.findById(coreUser.getId());
        assertTrue(retrievedUser.isPresent());
        assertEquals(coreUser, retrievedUser.get());
    }

    @Test
    @DisplayName("Should return empty Optional when no CoreUser with given id exists")
    void returnEmptyOptionalForNonExistentId() {
        Optional<CoreUser> retrievedUser = userRepository.findById(999L);
        assertFalse(retrievedUser.isPresent());
    }
}