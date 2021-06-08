package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.data.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsCRUDTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private LoginPage loginPage;
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
    public void testCreateANewCredential(){
        loginPage = new LoginPage(driver);
        loginPage.login("teste", "teste");

        homePage = new HomePage(driver);

        Credentials cred = new Credentials();
        cred.setPassword("teste");
        cred.setUrl("URL_TEST");
        cred.setUsername("TESTE-USER");

        homePage.addNewCredential(cred);

        assertEquals("Aww yeah, you successfully created a Credentials", homePage.getSuccessMessage());

        Credentials actualCredentials = homePage.getLastCredentialsCreated();

        assertEquals(cred.getUrl(), actualCredentials.getUrl());
        assertEquals(cred.getUsername(), actualCredentials.getUsername());

        assertThat(actualCredentials.getPassword()).isNotEmpty();

        homePage.deleteLastCredentialCreated();

    }

    @Test
    public void testEditACredential(){
        loginPage = new LoginPage(driver);
        loginPage.login("teste", "teste");

        homePage = new HomePage(driver);

        Credentials cred = new Credentials();
        cred.setPassword("teste");
        cred.setUrl("URL_TEST");
        cred.setUsername("TESTE-USER");

        homePage.addNewCredential(cred);

        cred.setUrl("URL_NEW");

        homePage.editLasCredentialCreated(cred);

        Credentials actualCredentials = homePage.getLastCredentialsCreated();

        assertEquals(cred.getUrl(), actualCredentials.getUrl());
        assertEquals(cred.getUsername(), actualCredentials.getUsername());

        assertThat(actualCredentials.getPassword()).isNotEmpty();

        homePage.deleteLastCredentialCreated();

    }

    @Test
    public void testDeleteACredential(){
        loginPage = new LoginPage(driver);
        loginPage.login("teste", "teste");

        homePage = new HomePage(driver);

        Credentials cred = new Credentials();
        cred.setPassword("teste");
        cred.setUrl("URL_TEST");
        cred.setUsername("TESTE-USER");

        homePage.addNewCredential(cred);
        homePage.deleteLastCredentialCreated();

        assertEquals("Aww yeah, you successfully deleted a Credentials", homePage.getSuccessMessage());

        Credentials expectedNote = homePage.getLastCredentialsCreated();

        assertNull(expectedNote.getPassword());
        assertNull(expectedNote.getUsername());
        assertNull(expectedNote.getPassword());

    }



}
