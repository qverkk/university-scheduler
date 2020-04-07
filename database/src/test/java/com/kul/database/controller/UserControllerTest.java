package com.kul.database.controller;

import com.google.gson.Gson;
import com.kul.database.constants.AuthorityEnum;
import com.kul.database.model.User;
import com.kul.database.model.UserLogin;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static String regularUserToken;
    private static String adminToken;
    private static Gson gson;
    private static User user;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeAll
    static void set_data() {
        regularUserToken = "";
        adminToken = "";
        gson = new Gson();
        user = new User(
                null,
                "some@newuser.com",
                "someuserpassword",
                "tomasz",
                "kowalski",
                false,
                AuthorityEnum.DZIEKANAT
        );
    }

    @Test
    @Order(1)
    public void register_regular_user() throws Exception {
        String json = gson.toJson(user);
        this.mockMvc.perform(
                post("/auth/register")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(json)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @Order(2)
    public void get_regular_user_token_disabled_exception() throws Exception {
        UserLogin userLogin = new UserLogin(user.getUsername(), user.getPassword());
        String json = gson.toJson(userLogin);
        MvcResult result = this.mockMvc.perform(
                post("/auth/auth")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(json)
        ).andDo(print())
                .andReturn();
        Optional<Exception> resolvedException = Optional.ofNullable((Exception) result.getResolvedException());
        resolvedException.ifPresent(Assert::assertNotNull);
        resolvedException.ifPresent(e -> Assert.assertTrue(e instanceof DisabledException));
//        regularUserToken = result.getResponse().getContentAsString();
//        Assert.assertFalse(regularUserToken.isEmpty());
    }

    @Test
    @Order(3)
    public void get_admin_token() throws Exception {
        UserLogin userLogin = new UserLogin("admin@admin.com", "admin");
        String json = gson.toJson(userLogin);
        MvcResult result = this.mockMvc.perform(
                post("/auth/auth")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(json)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        adminToken = result.getResponse().getContentAsString();
        Assert.assertFalse(adminToken.isEmpty());
    }

    @Test
    @Order(4)
    public void test_regular_user_not_enabled() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/user/enabled/2")
                        .header("Authorization", "Bearer " + adminToken)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String enabledResponse = result.getResponse().getContentAsString();
        Assert.assertFalse(Boolean.parseBoolean(enabledResponse));
    }

    @Test
    @Order(5)
    public void test_enable_regular_user() throws Exception {
        this.mockMvc.perform(
                post("/user/enable/2")
                        .header("Authorization", "Bearer " + adminToken)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Order(6)
    public void test_regular_user_enabled() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/user/enabled/2")
                        .header("Authorization", "Bearer " + adminToken)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String enabledResponse = result.getResponse().getContentAsString();
        Assert.assertTrue(Boolean.parseBoolean(enabledResponse));
    }

    @Test
    @Order(6)
    public void test_user_exists() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/user/user/2")
                        .header("Authorization", "Bearer " + adminToken)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        System.out.println(response);
        Assert.assertFalse(response.isEmpty());
    }

    @Test
    @Order(7)
    public void test_delete_user() throws Exception {
        this.mockMvc.perform(
                delete("/user/delete/2")
                        .header("Authorization", "Bearer " + adminToken)
        ).andDo(print())
                .andReturn();
    }

    @Test
    @Order(8)
    public void test_user_does_not_exists() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/user/user/2")
                        .header("Authorization", "Bearer " + adminToken)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        Assert.assertTrue(response.isEmpty());
    }
}