package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.data.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import org.apache.logging.log4j.util.Strings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-files-tab")
    private WebElement navFileTab;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialTab;

    // note tab elements
    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "note-description")
    private WebElement inputNoteDescription;

    @FindBy(id = "success-msg")
    private WebElement successMessage;

    @FindBy(css = "#userTable > tbody > tr:last-child > th")
    private WebElement lastNoteTitle;

    @FindBy(css = "#userTable > tbody > tr:last-child > td:nth-child(3)")
    private WebElement lastNoteDes;

    @FindBy(css = "#userTable > tbody > tr:last-child > td:nth-child(1) > button")
    private WebElement lastNoteEditButton;

    @FindBy(css = "#userTable > tbody > tr:last-child > td:nth-child(1) > a")
    private WebElement lastNoteDeleteButton;

    @FindBy(css = "#userTable > tbody")
    private WebElement noteTable;

    // credentials tab elements
    @FindBy(id = "add-credentials-button")
    private WebElement addCredentialsButton;

    @FindBy(id = "credential-url")
    private WebElement inputCredentialsUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialsUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialsPassword;

    @FindBy(css = "#credentialTable > tbody")
    private WebElement credentialsTable;

    @FindBy(css = "#credentialTable > tbody > tr:last-child > td:nth-child(3)")
    private WebElement lastCredentialUsername;

    @FindBy(css = "#credentialTable > tbody > tr:last-child > td:nth-child(4)")
    private WebElement lastCredentialPassword;

    @FindBy(css = "#credentialTable > tbody > tr:last-child > th")
    private WebElement  lastCredentialUrl;

    @FindBy(css = "#credentialTable > tbody > tr:last-child > td:nth-child(1) > button")
    private WebElement lastCredentialsEditButton;

    @FindBy(css = "#credentialTable > tbody > tr:last-child > td:nth-child(1) > a")
    private WebElement lastCredentialDeleteButton;


    private void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public HomePage(WebDriver driver){

        sleep(2000l);

        PageFactory.initElements(driver, this);
    }

    private void clickLastCredentialEditButton(){
        lastCredentialsEditButton.click();
        sleep(500);
    }

    private void clickLastNoteEditButton(){
        lastNoteEditButton.click();
        sleep(500);
    }

    private String getLastCredentialUsername(){
        return lastCredentialUsername.getText();
    }

    private String getLastCredentialPassword(){
        return lastCredentialPassword.getText();
    }

    private String getlastcredentialurl(){
        return lastCredentialUrl.getText();
    }


    private String getLastNoteTitle(){
        return lastNoteTitle.getText();
    }

    private String getLastNoteDescription(){
        return lastNoteDes.getText();
    }
    private void clickLastNoteDeleteButton(){
        lastNoteDeleteButton.click();
    }

    private void clickLastCredentialDeleteButton(){
        lastCredentialDeleteButton.click();
    }
    private boolean isNotesLisEmpty(){
        return Strings.isEmpty(noteTable.getText().trim());
    }

    private boolean isCredentialsLisEmpty(){
        return Strings.isEmpty(credentialsTable.getText().trim());
    }

    public Notes getLastCreatedNote(){
        clickNoteTab();

        Notes note = new Notes();
        if(!isNotesLisEmpty()){
            note.setNotetitle( getLastNoteTitle());
            note.setNotedescription(getLastNoteDescription());
        }

        return note;
    }

    public Credentials getLastCredentialsCreated(){
        clickCredentialsTab();

        Credentials cred = new Credentials();
        if(!isCredentialsLisEmpty()){

            cred.setPassword(getLastCredentialPassword());
            cred.setUsername(getLastCredentialUsername());
            cred.setUrl(getlastcredentialurl());
        }

        return cred;
    }


    public void deleteLastNoteCreated(){
        clickNoteTab();
        clickLastNoteDeleteButton();
        sleep(1000);
    }

    public void deleteLastCredentialCreated(){
        clickCredentialsTab();
        clickLastCredentialDeleteButton();
        sleep(1000);
    }

    public void editLasNoteCreated(Notes newNote){
        clickNoteTab();
        clickLastNoteEditButton();
        typeDescription(newNote.getNotedescription());
        typeTitle(newNote.getNotetitle());
        submitNote();
        sleep(500);
    }

    public void editLasCredentialCreated(Credentials credential){
        clickCredentialsTab();
        clickLastCredentialEditButton();

        typeCredentialsURL(credential.getUrl());
        typeCredentialsUsername(credential.getUsername());
        typeCredentialsPassword(credential.getPassword());

        submitCredentials();
        sleep(500);
    }

    public String getSuccessMessage(){
        return successMessage.getText();
    }

    public void clickNoteTab(){
        this.navNoteTab.click();
        sleep(1000l);
    }
    public void clickCredentialsTab(){
        this.navCredentialTab.click();
        sleep(1000l);
    }

    public void clickAddNewNoteButton(){
        sleep(1000l);
        addNoteButton.click();
        sleep(1000l);
    }

    public void clickAddNewCredentialsButton(){
        sleep(1000l);
        addCredentialsButton.click();
        sleep(1000l);
    }

    public void typeCredentialsPassword(String password){
        inputCredentialsPassword.clear();
        inputCredentialsPassword.sendKeys(password);
    }

    public void typeCredentialsUsername(String username){
        inputCredentialsUsername.clear();
        inputCredentialsUsername.sendKeys(username);
    }

    public void typeCredentialsURL(String url){
        inputCredentialsUrl.clear();
        inputCredentialsUrl.sendKeys(url);
    }

    public void typeTitle(String title){
        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(title);
    }

    public void submitNote(){
        inputNoteTitle.submit();
    }

    public void submitCredentials(){
        inputCredentialsPassword.submit();
    }

    public void typeDescription(String description){
        inputNoteDescription.clear();
        inputNoteDescription.sendKeys(description);
    }

    public void addNewNote(Notes note){
        clickNoteTab();
        clickAddNewNoteButton();

        typeTitle(note.getNotetitle());
        typeDescription(note.getNotedescription());

        submitNote();
        sleep(1000l);
    }

    public void addNewCredential(Credentials credential){
        clickCredentialsTab();
        clickAddNewCredentialsButton();

       typeCredentialsURL(credential.getUrl());
       typeCredentialsUsername(credential.getUsername());
       typeCredentialsPassword(credential.getPassword());

        submitCredentials();
        sleep(1000l);
    }

}
