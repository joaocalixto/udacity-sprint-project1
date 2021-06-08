package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement erroMessage;

    @FindBy(id = "signup-link")
    private WebElement singupLink;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public void typeUserName(String username){
        usernameField.sendKeys(username);
    }

    public void typePassword(String password){
        passwordField.sendKeys(password);
    }

    public void clickLogin(){
        submitButton.click();
    }

    public String getMessageError(){
        return erroMessage.getText();
    }

    public void clickSingUpLink(){
        singupLink.click();
    }

    public void login(String username, String password){
        typeUserName(username);
        typePassword(password);
        clickLogin();
    }
}
