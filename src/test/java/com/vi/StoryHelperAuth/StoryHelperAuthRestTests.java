package com.vi.StoryHelperAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vi.StoryHelperAuth.config.CassandraConfig;
import com.vi.StoryHelperAuth.config.SecurityConfig;
import com.vi.StoryHelperAuth.model.User;
import com.vi.StoryHelperAuth.rest.UserController;
import com.vi.StoryHelperAuth.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Import({CassandraConfig.class, SecurityConfig.class})
public class StoryHelperAuthRestTests {

    @Autowired
    private WebApplicationContext context;

    @Mock
    private UserService userService;

    private MockMvc mvc;

    private JacksonTester<User> jsonUser;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }


    @Test
    @WithMockUser("test")
    public void canRetrieveByUsernameWhenExists() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("test", new BCryptPasswordEncoder().encode("test"));

        // given
        given(userService.loadUserByUsername("test"))
                .willReturn(new User("test", new BCryptPasswordEncoder().encode("test"), "user"));

        // when
        MockHttpServletResponse response = mvc.perform(
                        post("/api/v1/login")
                                .headers(headers)
//                                .with(csrf())
//                                .with(SecurityMockMvcRequestPostProcessors.user("test").password(new BCryptPasswordEncoder().encode("test")))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getContentAsString()).isEqualTo(
                jsonUser.write(new User("test", new BCryptPasswordEncoder().encode("test"), "user")).getJson()
        );
    }
}
