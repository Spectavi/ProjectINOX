package com.spectavi.projectinox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

// Page Object for WebstaurantStore Shopping Cart
public class CartPage {

    // Selector for confirming the "Empty Cart" dialog.
    protected static By modalAgreeBtnSelector =
            By.cssSelector("footer[data-testid='modal-footer'] > button[class*='mr-2']");
    @FindBy(className = "emptyCartButton")
    protected WebElement emptyCartBtn;
    @FindBy(css = "span[id='cartItemCountSpan']")
    protected WebElement cartItemCountSpan;
    protected WebDriver driver;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getCartSize() {
        return Integer.parseInt(cartItemCountSpan.getText());
    }

    public Boolean cartContainsItem(String expectedDesc) {
        List<WebElement> items = driver.findElements(By.className("cartItem"));
        for (WebElement itemElement : items) {
            String desc = itemElement.findElement(By.className("itemDescription")).getText();
            if (desc.equals(expectedDesc)) {
                return true;
            }
        }
        return false;
    }

    public CartPage emptyCart() {
        emptyCartBtn.click();
        // TODO: Find a better selector for the "Empty Cart" button in the confirm dialog.
        driver.findElements(modalAgreeBtnSelector).getFirst().click();
        // Cart item count only seems to update after a refresh. Bug, maybe?
        driver.navigate().refresh();
        return this;
    }
}