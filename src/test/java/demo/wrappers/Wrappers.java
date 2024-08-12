package demo.wrappers;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    ChromeDriver driver;
    WebDriverWait wait;
    /*
     * Write your selenium wrappers here
     */

    public Wrappers(ChromeDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToUrl(String url){
        driver.get(url);
    }

    public void enterText(By locator, String text) throws InterruptedException{
        WebElement textBox = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        textBox.clear();
        textBox.sendKeys(text);
        textBox.sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }

    public void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public List<WebElement> findElements(By locator){
        return driver.findElements(locator);
    }

    public void waitForVisibility(By locator) {
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
