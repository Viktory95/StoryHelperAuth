package com.vi.StoryHelperAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vi.StoryHelperAuth.model.User;
import com.vi.StoryHelperAuth.rest.UserResource;
import com.vi.StoryHelperAuth.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

@ExtendWith(MockitoExtension.class)
public class StoryHelperAuthRestTests {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    private JacksonTester<User> jsonUser;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(userResource)
                .build();
    }

    @Test
    public void canRetrieveByUsernameWhenExists() throws Exception {
        // given
        given(userService.loadUserByUsername("test"))
                .willReturn(new User("test", new BCryptPasswordEncoder().encode("test"), "user"));

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/v1/login")
                                .with(SecurityMockMvcRequestPostProcessors.user("test").password("test")) //TODO: fix Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString()).isEqualTo(
                jsonUser.write(new User("test", new BCryptPasswordEncoder().encode("test"), "user")).getJson()
        );
    }
}
