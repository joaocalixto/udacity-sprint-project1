package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SingupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SingupAndLoginFlowTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private LoginPage loginPage;
    private SingupPage singupPage;
    private HomePage homePage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/login");
    }

    @Test
    public void testLoginInvalido(){

        loginPage = new LoginPage(driver);
        String usernameInvalido = "usernameInvalido";
        String passwordInvalida = "passwordinvalida";

        loginPage.login(usernameInvalido, passwordInvalida);

        String messageErroAtual = loginPage.getMessageError();
        String messageErrorExpected = "Invalid username or password";

        assertEquals(messageErrorExpected, messageErroAtual);
    }

    @Test
    public void testSignUpAlreadyExistantUser(){

        loginPage = new LoginPage(driver);
        loginPage.clickSingUpLink();

        singupPage = new SingupPage(driver);
        User userDTO = new User();
        userDTO.setFirstName("teste");
        userDTO.setLastName("teste");
        userDTO.setPassword("teste");
        userDTO.setUsername("teste");
        singupPage.singup(userDTO);

        String errorMessageActual = singupPage.getErroMessage();
        String errorMessageExpected = "The username already exists.";

        assertEquals(errorMessageExpected, errorMessageActual);
    }

    @Test
    public void testSignUpNewUserAndLogin(){

        loginPage = new LoginPage(driver);
        loginPage.clickSingUpLink();

        String randomUser = RandomString.make();
        String username = "teste"+ randomUser;
        String password = RandomString.make(6);

        singupPage = new SingupPage(driver);
        User userDTO = new User();
        userDTO.setFirstName("teste");
        userDTO.setLastName("teste");
        userDTO.setPassword(password);
        userDTO.setUsername(username);
        singupPage.singup(userDTO);

        String successMessageActual = singupPage.getSuccessMessage();
        String successMessageExpected = "You successfully signed up! Please continue to the login page.";

        assertEquals(successMessageExpected, successMessageActual);

        singupPage.clickLoginLink();

        loginPage.login(username, password);

        Assertions.assertThat(driver.getCurrentUrl()).contains("/home");
    }

}
