package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public abstract class BasePage {
    protected AppiumDriver driver;
    protected FluentWait<WebDriver> wait;
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(30);
    private static final Duration TIMEOUT_POOLING = Duration.ofMillis(300);

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new FluentWait(driver)
                .pollingEvery(TIMEOUT_POOLING)
                .withTimeout(TIMEOUT_DURATION)
                .ignoring(NoSuchElementException.class,
                        StaleElementReferenceException.class);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void clickElement(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }

    protected void sendKeysToElement(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getElementText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }

    protected String getAttribute(WebElement element, String attribute) {
        waitForElementToBeVisible(element);
        return element.getAttribute(attribute);
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            waitForElementToBeVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
