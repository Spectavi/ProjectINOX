package com.spectavi.projectinox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

// Page Object for WebstaurantStore Search Results Pages.
public class SearchResultsPage {

    protected static By lastPageBtnSelector = By.cssSelector("a[aria-label*='last']");
    protected static By productResultsContainerSelector = By.cssSelector("div[data-testid=productBoxContainer]");
    protected static By productDescSelector = By.cssSelector("span[data-testid='itemDescription']");
    protected static By productAddToCartSelector = By.className("add-to-cart");
    @FindBy(css = "a[aria-label*='current']")
    protected WebElement currentPageBtn;
    @FindBy(css = "a[data-testid='cart-button']")
    protected WebElement cartBtn;
    protected WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getCurrentPageNumber() {
        return Integer.parseInt(currentPageBtn.getText());
    }

    public int getNumberOfPages() {
        // If there's no last page button it's because we're on the last page, get current page instead.
        List<WebElement> lastPageBtn = driver.findElements(lastPageBtnSelector);
        if (!lastPageBtn.isEmpty()) {
            return Integer.parseInt(lastPageBtn.getFirst().getText());
        }
        return Integer.parseInt(currentPageBtn.getText());
    }

    public SearchResultsPage navigateToPage(int pageNumber) {
        String selector = String.format("a[aria-label*='page %s']", pageNumber);
        driver.findElement(By.cssSelector(selector)).click();
        return this;
    }

    public int getNumberOfResultsOnPage() {
        List<WebElement> results = driver.findElements(productResultsContainerSelector);
        return results.size();
    }

    public WebElement getResultNumber(int resultNumber) {
        List<WebElement> results = driver.findElements(productResultsContainerSelector);
        if (results.size() >= resultNumber) {
            return results.get(resultNumber - 1);
        }
        return null;
    }

    public String getResultNumberDescription(int resultNumber) {
        return getResultNumber(resultNumber).findElement(productDescSelector).getText();
    }

    public SearchResultsPage addItemToCart(int resultNumber) {
        getResultNumber(resultNumber).findElement(productAddToCartSelector).click();
        return this;
    }

    public Boolean verifyProductTitlesContain(String text) {
        List<WebElement> results = driver.findElements(productResultsContainerSelector);
        for (WebElement result : results) {
            WebElement productTitle = result.findElement(productDescSelector);
            if (!productTitle.getText().toLowerCase().contains(text.toLowerCase())) {
                System.out.println("Product title does not contain: " + text);
                System.out.println("Product title: " + productTitle.getText());
                return false;
            }
        }
        return true;
    }

    public CartPage navigateToCart() {
        cartBtn.click();
        return new CartPage(this.driver);
    }
}
