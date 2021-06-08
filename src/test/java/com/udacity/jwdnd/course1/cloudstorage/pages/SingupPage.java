package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.data.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SingupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id = "submit-button")
    private WebElement signupButton;

    @FindBy(id = "error-msg")
    private WebElement erroMessage;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    public SingupPage(WebDriver driver){

        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PageFactory.initElements(driver, this);
    }

    public void typeFirstname(String firstname){
        firstNameField.sendKeys(firstname);
    }

    public void typeLastname(String lastname){
        lastNameField.sendKeys(lastname);
    }

    public void typeUsername(String username){
        usernameField.sendKeys(username);
    }

    public void typePassword(String password){
        inputPasswordField.sendKeys(password);
    }

    public void clickSingup(){
        signupButton.click();
    }

    public void clickLoginLink(){
        loginLink.click();
    }

    public String getErroMessage(){
        return erroMessage.getText();
    }

    public String getSuccessMessage(){
        return successMessage.getText();
    }

    public void singup(User userDTO){

        typeFirstname(userDTO.getFirstName());
        typeLastname(userDTO.getLastName());
        typeUsername(userDTO.getUsername());
        typePassword(userDTO.getPassword());

        clickSingup();
    }
}
