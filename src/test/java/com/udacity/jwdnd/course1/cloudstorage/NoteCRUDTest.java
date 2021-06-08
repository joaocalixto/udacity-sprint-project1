package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteCRUDTest {

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
    public void testCreateANewNote(){
        loginPage = new LoginPage(driver);
        loginPage.login("teste", "teste");

        homePage = new HomePage(driver);

        Notes note = new Notes();
        note.setNotedescription("Description");
        note.setNotetitle("Note Titile");

        homePage.addNewNote(note);

        assertEquals("Aww yeah, you successfully created a Note", homePage.getSuccessMessage());

        Notes expectedNote = homePage.getLastCreatedNote();

        assertEquals(note.getNotedescription(), expectedNote.getNotedescription());
        assertEquals(note.getNotetitle(), expectedNote.getNotetitle());

        homePage.deleteLastNoteCreated();

    }

    @Test
    public void testEditANote(){
        loginPage = new LoginPage(driver);
        loginPage.login("teste", "teste");

        homePage = new HomePage(driver);

        Notes note = new Notes();
        note.setNotedescription("Description");
        note.setNotetitle("Note Title");

        homePage.addNewNote(note);

        note.setNotedescription("New Desc");
        note.setNotetitle("New Title");

        homePage.editLasNoteCreated(note);

        Notes expectedNote = homePage.getLastCreatedNote();

        assertEquals(note.getNotedescription(), expectedNote.getNotedescription());
        assertEquals(note.getNotetitle(), expectedNote.getNotetitle());

        homePage.deleteLastNoteCreated();

    }

    @Test
    public void testDeleteANote(){
        loginPage = new LoginPage(driver);
        loginPage.login("teste", "teste");

        homePage = new HomePage(driver);

        Notes note = new Notes();
        note.setNotedescription("Description");
        note.setNotetitle("Note Title");

        homePage.addNewNote(note);
        homePage.deleteLastNoteCreated();

        assertEquals("Aww yeah, you successfully deleted a Note", homePage.getSuccessMessage());

        Notes expectedNote = homePage.getLastCreatedNote();

        assertNull(expectedNote.getNotedescription());
        assertNull(expectedNote.getNotetitle());

    }



}
