package com.vi.StoryHelperAuth;

import com.vi.StoryHelperAuth.repository.UserRepository;
import com.vi.StoryHelperAuth.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StoryHelperAuthApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		Assertions.assertThat(userRepository).isNotNull();
		Assertions.assertThat(userService).isNotNull();
	}

}
