package com.spectavi.projectinox.pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

// Base test class for handling common test setup.
public class BaseTestClass {

    protected HomePage homePage;
    protected WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        homePage = new HomePage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.manage().deleteAllCookies();
        driver.close();
    }
}