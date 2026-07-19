package com.mehnaz.testautomation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.mehnaz.testautomation.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
    private WebDriver driver;

    @BeforeMethod
     public void setup(){
     WebDriverManager.chromedriver().setup();
     ChromeOptions options = new ChromeOptions();
     if(System.getenv("CI") != null){
        options.addArguments("--headless");
     }
     
     options.addArguments("--no-sandbox");
     options.addArguments("--disable-dev-shm-usage");
     driver = new ChromeDriver(options);
     driver.manage().window().maximize();
   }
   @Test
    public void testVSuccessfulLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("standard_user", "secret_sauce");

        // Verify successful login by checking the presence of an element on the landing page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));

       WebElement title  = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
       String pageTile = title.getText().trim();
       Assert.assertTrue(pageTile.equalsIgnoreCase("Products")); 
    }
    @AfterMethod
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}

