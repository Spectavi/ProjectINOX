package com.spectavi.projectinox.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// Page Object for WebstaurantStore Home Page
public class HomePage {

    protected WebDriver driver;
    @FindBy(css = "input[data-testid=searchval]")
    private WebElement searchBox;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get("https://www.webstaurantstore.com");
        PageFactory.initElements(driver, this);
    }

    public SearchResultsPage searchForText(String searchText) {
        searchBox.sendKeys(searchText);
        searchBox.submit();
        return new SearchResultsPage(this.driver);
    }
}
