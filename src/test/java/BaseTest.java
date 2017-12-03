import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *
 * Project FOC Solution
 * Created by Chamila Ambahera on 12/2/2017.
 * chamila.ambahera@gmail.com
 */

public abstract class BaseTest {
    private static final String LOCAL_APPIUM_ADDRESS = "http://0.0.0.0:4723";
    protected AppiumDriver<MobileElement> appiumDriver;
    protected DesiredCapabilities capabilities;
    protected static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    AppiumDriverLocalService service;

    public void setUpTest() throws Exception {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        setUpAppiumDriver();
    }


    public void setUpAppiumDriver() throws IOException {
        DesiredCapabilities desiredCapabilities = getDesiredCapabilities();
        this.capabilities = desiredCapabilities;
        logger.debug("Creating Appium session ....");
        setAppiumDriver();
    }


    private DesiredCapabilities getDesiredCapabilities() {
        logger.debug("Setting desiredCapabilities defined in property file");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        Properties desiredCapabilitiesProperties = fetchProperties("desiredCapabilities.properties");
        Set<String> keys = desiredCapabilitiesProperties.stringPropertyNames();
        for (String key : keys) {
            String value = desiredCapabilitiesProperties.getProperty(key);
            desiredCapabilities.setCapability(key, value);
        }
        return desiredCapabilities;
    }

    protected void setAppiumDriver() throws IOException{
        logger.debug("Setting up AndroidDriver");
            this.appiumDriver = new AndroidDriver<>(new URL(getAppiumServerAddress() + "/wd/hub"),
                    capabilities);
            appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    private Properties fetchProperties(String filename) {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                logger.error("Unable to find " + filename);
                throw new FileNotFoundException("Unable to find/open file: " + filename);
            }
            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }


    protected String getAppiumServerAddress() {
        return LOCAL_APPIUM_ADDRESS;
    }


    protected void quitAppiumSession() throws IOException {
        if (service.isRunning())
            service.stop();
    }

    protected File takeScreenshot(String screenshotName) {
        String fullFileName = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
        logger.debug("Taking screenshot...");
        File scrFile = appiumDriver.getScreenshotAs(OutputType.FILE);

        try {
            File testScreenshot = new File(fullFileName);
            FileUtils.copyFile(scrFile, testScreenshot);
            logger.debug("Screenshot stored to " + testScreenshot.getAbsolutePath());

            return testScreenshot;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
