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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private static String token;
    private static Gson gson;
    private static User user;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @BeforeAll
    static void set_data() {
        token = " ";
        gson = new Gson();
        user = new User(
                null,
                "qverkk",
                "qverkk",
                "marek",
                "filipowicz",
                false,
                AuthorityEnum.ADMIN
        );
    }

    @Test
    @Order(1)
    public void test_user_login_failure() throws Exception {
        String json = gson.toJson(user);
        this.mockMvc.perform(
                post("/auth/auth")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(json)
        ).andDo(print())
                .andExpect(status().isOk())
        .andExpect(content().string(""));
    }

    @Test
    @Order(2)
    public void test_user_register_ok() throws Exception {
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
    @Order(3)
    public void test_get_auth_token() throws Exception {
        UserLogin userLogin = new UserLogin("qverkk", "qverkk");
        String json = gson.toJson(userLogin);
        MvcResult result = this.mockMvc.perform(
                post("/auth/auth")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(json)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String resultToken = result.getResponse().getContentAsString();
        token = resultToken;
        Assert.assertNotEquals(token, "");

    }

    @Test
    @Order(4)
    public void test_user_login_success() throws Exception {
        MvcResult result = this.mockMvc.perform(
                get("/auth/login")
                        .param("token", UserControllerTest.token)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        User responseUser = gson.fromJson(jsonResponse, User.class);
        Assert.assertEquals(user.getUsername(), responseUser.getUsername());
        Assert.assertTrue(passwordEncoder.matches(user.getPassword(), responseUser.getPassword()));
        Assert.assertEquals(user.getFirstName(), responseUser.getFirstName());
        Assert.assertEquals(user.getLastName(), responseUser.getLastName());
    }

    @AfterAll
    static void clean_after_tests() {
        token = null;
        gson = null;
        user = null;
    }
}