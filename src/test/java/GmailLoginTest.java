import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.io.IOException;

/**
 * Project FOC Solution
 * Created by Chamila Ambahera on 12/2/2017.
 * chamila.ambahera@gmail.com
 */

public class GmailLoginTest extends BaseTest {

    @Before
    public void setUp() throws Exception {
        setUpTest();
    }

    @After
    public void tearDown() throws IOException {
        quitAppiumSession();
    }


    @Test
    public void enterInvalidUsernameTest() {
        handleWelcomeMessages();

        appiumDriver.findElement(By.id("identifierId")).sendKeys("foc.solutions.demo123");
        appiumDriver.findElement(By.id("identifierNext")).click();

        //validate error message
        Assert.assertTrue("Unsuccessful message should be displayed", ((AndroidDriver) appiumDriver).findElementByAndroidUIAutomator("UiSelector().description(\"Couldn't find your Google Account\")").isDisplayed());
        takeScreenshot("Invalid Username");
    }

    @Test
    public void enterInvalidPasswordTest() {
        handleWelcomeMessages();

        appiumDriver.findElement(By.id("identifierId")).sendKeys("foc.solutions.demo");
        appiumDriver.findElement(By.id("identifierNext")).click();

        appiumDriver.findElement(By.xpath("//android.widget.EditText[@text='Enter your password']")).click();
        appiumDriver.findElement(By.xpath("//android.widget.EditText[@text='Enter your password']")).sendKeys("foc.solution");
        appiumDriver.findElement(By.id("passwordNext")).click();

//        Validate the message
        Assert.assertTrue("Unsuccessful message should be displayed", ((AndroidDriver) appiumDriver).findElementByAndroidUIAutomator("UiSelector().description(\"Wrong password. Try again or click Forgot password to reset it.\")").isDisplayed());

//        Assert.assertTrue("Unsuccessful message should be displayed", appiumDriver.findElement(By.xpath("//android.view.View[contains(@content-desc='Wrong password. Try again or click Forgot password to reset it.']")).isDisplayed());
        takeScreenshot("Invalid Password");
    }

    @Test
    public void addValidCredentialsTest() throws IOException, InterruptedException {
        addGoogleAccount();
    }

    public void addGoogleAccount() {

        handleWelcomeMessages();

        appiumDriver.findElement(By.id("identifierId")).sendKeys("foc.solutions.demo");
        appiumDriver.findElement(By.id("identifierNext")).click();

        appiumDriver.findElement(By.xpath("//android.widget.EditText[@text='Enter your password']")).click();
        appiumDriver.findElement(By.xpath("//android.widget.EditText[@text='Enter your password']")).sendKeys("foc.solutions");

        appiumDriver.findElement(By.id("passwordNext")).click();
        appiumDriver.findElement(By.id("signinconsentNext")).click();

        Assert.assertTrue("Invalid email address displayed", appiumDriver.findElement(By.id("com.google.android.gms:id/suw_items_title")).getText().contains("foc.solutions.demo@gmail.com"));

        appiumDriver.findElement(By.id("com.google.android.gms:id/next_button")).click();

        //Validate account Info
        Assert.assertTrue("Invalid user name displayed", appiumDriver.findElement(By.id("com.google.android.gm:id/account_display_name")).getText().equals("FOC Solutions Demo"));
        Assert.assertTrue("Invalid email address displayed", appiumDriver.findElement(By.id("com.google.android.gm:id/account_address")).getText().equals("foc.solutions.demo@gmail.com"));

        appiumDriver.findElement(By.id("com.google.android.gm:id/action_done")).click();

    }

    private void handleWelcomeMessages() {
        appiumDriver.findElement(By.id("com.google.android.gm:id/welcome_tour_got_it")).click();
        appiumDriver.findElement(By.id("com.google.android.gm:id/setup_addresses_add_another")).click();
        appiumDriver.findElement(By.id("com.google.android.gm:id/account_setup_item")).click();
        appiumDriver.findElement(By.id("identifierId")).click();
    }

}
