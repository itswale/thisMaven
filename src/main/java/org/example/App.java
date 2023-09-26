package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

public class App {
    public static void main(String[] args) {
        Duration waitTime = Duration.ofSeconds(10);

        System.setProperty("webdriver.gecko.driver", "downloads/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(options);

        driver.get("https://www.kurtosys.com/");

        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            WebElement cookiesBanner = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cookies-eu-banner")));
            cookiesBanner.findElement(By.linkText("Accept")).click();
        } catch (Exception e) {
            // Banner may not be present, continue without dismissing it
        }

        WebElement resourcesLink = driver.findElement(By.linkText("INSIGHTS"));
        resourcesLink.click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            WebElement cookiesBanner = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cookies-eu-banner")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", cookiesBanner);
        } catch (Exception e) {
            // Banner may not be present, continue without dismissing it
        }

        WebElement whitePapersLink = driver.findElement(By.linkText("White Papers & eBooks"));
        whitePapersLink.click();

        String expectedTitle = "White Papers";
        String actualTitle = driver.getTitle();
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Title verification passed!");
        } else {
            System.out.println("Title verification failed!");
        }

        WebElement whitePaperTile = driver.findElement(By.partialLinkText("UCITS White Paper"));
        whitePaperTile.click();

        // Now you can locate and interact with elements inside the iframe
        WebElement firstNameField = driver.findElement(By.cssSelector("input[name='First Name']"));
        firstNameField.sendKeys("John");

        WebElement lastNameField = driver.findElement(By.cssSelector("input[name='Last Name']"));
        lastNameField.sendKeys("Doe");

        WebElement companyField = driver.findElement(By.cssSelector("input[name='Company']"));
        companyField.sendKeys("XYZ Corp");

        WebElement industryField = driver.findElement(By.cssSelector("input[name='Industry']"));
        industryField.sendKeys("IT");

        // DO NOT populate the "Email” field

        WebElement sendButton = driver.findElement(By.cssSelector("input[value='Send Me a Copy']"));
        sendButton.click();

        // Capture a screenshot and save it to a file
        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotFilePath = "screenshot.png"; // Change the file path as needed
            FileHandler.copy(screenshotFile, new File(screenshotFilePath));
            System.out.println("Screenshot saved to: " + screenshotFilePath);
        } catch (IOException e) {
            System.err.println("Error while saving the screenshot: " + e.getMessage());
        }

        driver.quit();
    }
}
