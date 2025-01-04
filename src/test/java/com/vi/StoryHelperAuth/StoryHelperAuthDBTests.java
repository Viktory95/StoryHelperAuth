package com.vi.StoryHelperAuth;

import com.vi.StoryHelperAuth.config.CassandraConfig;
import com.vi.StoryHelperAuth.model.User;
import com.vi.StoryHelperAuth.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataCassandraTest
@Import(CassandraConfig.class)
public class StoryHelperAuthDBTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        Assertions.assertThat(userRepository.save(User.builder()
                .username("test")
                .password(new BCryptPasswordEncoder().encode("test"))
                .authorities("user")
                .build())).isNotNull();
    }

    @Test
    void findUserByUsername() {
        Assertions.assertThat(userRepository.findByUsername("admin")).isNotNull();
    }

//    @Test
//    void removeUser() {
//        userRepository.delete("test");
//    }
}
