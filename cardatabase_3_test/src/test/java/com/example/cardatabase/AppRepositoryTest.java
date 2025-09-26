package com.example.cardatabase;

import com.example.cardatabase.domain.AppUser;
import com.example.cardatabase.domain.AppUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AppRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    @DisplayName("사용자 이름으로 사용자 조회")
    void findByUsernameSouldReturnAppUser() {
        // given - 사용자 데이터 저장
        AppUser user = new AppUser("user1", "user1", "USER");
        appUserRepository.save(user);

        // when - 사용자 이름으로 조회
        Optional<AppUser> foundUser = appUserRepository.findByUsername("user1");

        // then - 결과가 존재하는지, role이 USER인지 검증
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getRole()).isEqualTo("USER");
    }
}
