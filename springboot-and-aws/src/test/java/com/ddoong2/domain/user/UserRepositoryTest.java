package com.ddoong2.domain.user;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void 이메일정보로_유저를가져온다() {

        // given
        String name = "user";
        String email = "test@test.com";
        Role role = Role.USER;
        String picture = "avatar.png";

        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build()
        );

        // when
        Optional<User> userOptional = userRepository.findByEmail(email);

        // then
        User user = userOptional.get();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getPicture()).isEqualTo(picture);
        assertThat(user.getRole()).isEqualTo(role);
    }
}