package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    public static final String TITLE_TEXT = "Вход в Alfa-Test";
    public static final String LOGIN_BTN_TEXT = "Войти";
    public static final int MAX_LOGIN_INPUT_LENGTH = 50;
    public static final int MAX_PASSWORD_INPUT_LENGTH = 50;

    @CacheLookup
    @FindBy(id = "tvTitle")
    private WebElement loginTitle;

    @CacheLookup
    @FindBy(id = "etUsername")
    private WebElement loginInput;

    @CacheLookup
    @FindBy(id = "etPassword")
    private WebElement passwordInput;

    @CacheLookup
    @FindBy(id = "btnConfirm")
    private WebElement loginButton;

    @CacheLookup
    @FindBy(id = "tvError")
    private WebElement loginErrorMessage;

    @CacheLookup
    @FindBy(xpath = "//android.widget.ImageButton[@content-desc='Show password']")
    private WebElement passwordVisibilityToggle;

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public void enterLogin(String login) {
        sendKeysToElement(loginInput, login);
    }

    public void enterPassword(String password) {
        sendKeysToElement(passwordInput, password);
    }

    public String getLoginBtnTxt() {
        return getElementText(loginButton);
    }

    public String getLoginText() {
        return getElementText(loginInput);
    }

    public String getPasswordText() {
        return getElementText(passwordInput);
    }

    public void togglePasswordVisibility() {
        clickElement(passwordVisibilityToggle);
    }

    public boolean isPasswordMasked() {
        return getAttribute(passwordInput, "password").equals("true");
    }

    public String getLoginTitleText() {
        return getElementText(loginTitle).trim();
    }

    public String getLoginErrorMessage() {
        return getElementText(loginErrorMessage).trim();
    }

    public void login(String username, String password) {
        enterLogin(username);
        enterPassword(password);
    }

    public UserPage clickLoginButton() {
        clickElement(loginButton);
        return new UserPage(driver);
    }

    public boolean isLoginButtonEnabled() {
        try {
            return loginButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}
