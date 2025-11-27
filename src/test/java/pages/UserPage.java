package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class UserPage extends BasePage {
    @CacheLookup
    @FindBy(xpath = "//android.widget.TextView[@text='Вход в Alfa-Test выполнен']")
    private WebElement title;

    public UserPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isUserPageOpened() {
        return isElementDisplayed(title);
    }
}
