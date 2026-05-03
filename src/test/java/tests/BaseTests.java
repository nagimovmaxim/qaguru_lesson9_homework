package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTests {

    @BeforeAll
    static void setup() {

        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        Configuration.savePageSource = true;
        Configuration.screenshots = true;

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=*");

        Configuration.browserCapabilities = options;

        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            String[] possiblePaths = {
                    "/usr/bin/chromedriver",
                    "/usr/local/bin/chromedriver",
                    "/snap/bin/chromedriver"
            };

            for (String path : possiblePaths) {
                if (new java.io.File(path).exists()) {
                    System.setProperty("webdriver.chrome.driver", path);
                    break;
                }
            }
        }

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );
    }
}