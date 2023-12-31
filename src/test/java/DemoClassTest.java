import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class DemoClassTest {
    private WebDriver driver;
    private String url = "https://www.nordea.fi/en/personal/get-help/";

    @BeforeClass
    public void setUp() {
        String driverPath = System.getProperty("user.dir") + "/src/test/java/driver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testDemo() {
        // 1. Wejście na stronę
        driver.get(url);

        // 2. Zaakceptuj cookies z popupu (ewentualnie spraw aby html nie wyswietlał cookies np. javascriptem)
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Accept all']"))).click();

        // 3. Na stronie FAQ obsłuż obszar „Latest news” o selektorze css „article .content-card”
        WebElement faqCard = driver.findElement(By.cssSelector("article .content-card"));
        List<WebElement> linksInFaqCard = faqCard.findElements(By.tagName("li"));

        //W teście wykonaj:
        //- wyprintuj header karty
        System.out.println(faqCard.findElement(By.tagName("h2")).getText());

        //- wyprintuj ilość linków które istnieją na owej karcie
        System.out.println(linksInFaqCard.size());

        //- wyprintuj adres href linku nr 2
        System.out.println(linksInFaqCard.get(1).findElement(By.tagName("a")).getAttribute("href"));

        //- zrób jedną/dwie asercję na danej karcie
        Assert.assertTrue(faqCard.isDisplayed());
        Assert.assertEquals(linksInFaqCard.size(), 5);

        // - kliknij w link nr 2
        linksInFaqCard.get(1).click();

        // - zrób asercje, że zostałeś przekierowany na inną stronę
        Assert.assertNotEquals(driver.getTitle(), "Frequently asked questions | Nordea");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
