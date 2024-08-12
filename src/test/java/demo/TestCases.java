package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers actions;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start Testcase01");
        try {
            // navigate to the
            actions.navigateToUrl("https://www.flipkart.com/");
            Thread.sleep(3000);

            // Search "Washing Machine".
            actions.enterText(By.xpath("//input[@placeholder = 'Search for Products, Brands and More']"),
                    "Washing Machine");
            Thread.sleep(3000);

            // Sort by popularity
            actions.click(By.xpath("//div[contains(text(), 'Popularity')]"));
            Thread.sleep(3000);

            // and print the count of items with rating less than or equal to 4 stars.
            By stars = By.xpath("//div[@class = 'XQDdHH']");
            List<WebElement> starRatings = actions.findElements(stars);
            int count = 0;
            for (WebElement starRating : starRatings) {
                double ratings = Double.parseDouble(starRating.getText());
                if (ratings <= 4.0) {
                    count = count + 1;
                }
            }
            System.out.println("Count of items with rating less than or equals to 4 stars are: " + count);
        } catch (Exception e) {
            System.out.println("Exception in Testcase01: " + e.getMessage());
        }
        System.out.println("End Testcase01");
    }

    @Test
    public void testCase02() {
        System.out.println("Start Testcase02");
        try {
            // Navigate to the website
            actions.navigateToUrl("https://www.flipkart.com/");
            Thread.sleep(3000);
            //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            // Search for "iPhone"
            actions.enterText(By.xpath("//input[@placeholder='Search for Products, Brands and More']"), "iPhone");
            Thread.sleep(3000);

            // Find discount elements
            List<WebElement> discounts = actions.findElements(By.xpath("//div[@class = 'UkUFwK']/span"));

            // Iterate through each discount element
            for (WebElement discount : discounts) {
                String outputResult = discount.getText().replaceAll("[^0-9]", "");
                if(!outputResult.isEmpty() && Integer.parseInt(outputResult) > 17){
                    // Find the parent container of the current discount
                    WebElement parentContainer = discount.findElement(By.xpath("./ancestor::div[contains(@class, 'tUxRFH')]"));
                    Thread.sleep(3000);
                    // Find the title within the same parent container   .//div[contains(@class, 'yKfJKb')]//div[contains(@class, 'KzDlHZ')]//text()
                    WebElement titles = parentContainer.findElement(By.xpath(".//div[@class='KzDlHZ']"));
                    //Thread.sleep(3000);
                    String title = titles.getText();
                    Thread.sleep(3000);
                    System.out.println("Title is: " + title + " | Discount is: " + outputResult + "%");
                    //Thread.sleep(3000);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in Testcase02: " + e.getMessage());
        }
        System.out.println("End Testcase02");
    }

    @Test
    public void testCase03(){
        System.out.println("Start Testcase03");
        try {
            // Navigate to the website
            actions.navigateToUrl("https://www.flipkart.com/");
            //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            //Search "Coffee Mug",
            actions.enterText(By.xpath("//input[@placeholder='Search for Products, Brands and More']"), "Coffee Mug");
            //Thread.sleep(3000);

            //select 4 stars and above,
            actions.click(By.xpath("//div[contains(text(),'4★ & above')]")); //(//div[@class = 'XqNaEv'])[1]
            Thread.sleep(3000);

            //image URL of the 5 items with highest number of reviews
            List<WebElement> titleText = actions.findElements(By.xpath("//a[@class='wjcEIp']"));
            List<WebElement> imageURL = actions.findElements(By.xpath("//a[@class='VJA3rP']"));
            List<WebElement> reviewCounts = actions.findElements(By.xpath("//span[@class = 'Wphh3N']"));
            //List<WebElement> itemContainers = actions.findElements(By.xpath("//div[@class = 'slAVV4']"));


            List<Item> items = new ArrayList<>();

            for (int i = 0; i < titleText.size(); i++) {
                String title = titleText.get(i).getText();
                String reviewText = reviewCounts.get(i).getText().replaceAll("[^0-9]", ""); // Remove non-numeric characters
                
                int reviewCount = reviewText.isEmpty() ? 0 : Integer.parseInt(reviewText);
                WebElement imageElement = imageURL.get(i).findElement(By.xpath(".//img"));
                String imageUrl = imageElement.getAttribute("src");
                
                items.add(new Item(title, reviewCount, imageUrl));
            }
            // Print the title and image URL of the top 5 items with highest reviews
            for (int i = 0; i < Math.min(5, items.size()); i++) {
                Item item = items.get(i);
                System.out.println("Title: " + item.title);
                System.out.println("Image URL: " + item.imageUrl);
                System.out.println("Review Count: " + item.reviewCount);
                System.out.println();
            }
        }
        catch (Exception e) {
                System.out.println("Exception in Testcase03: " + e.getMessage());
            }
            System.out.println("End Testcase03");
        }
    
        // Helper class to store item details
        class Item{
            String title;
            int reviewCount;
            String imageUrl;

            Item(String title, int reviewCount, String imageUrl){
            this.title = title;
            this.reviewCount = reviewCount;
            this.imageUrl = imageUrl;
        }
    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        actions = new Wrappers(driver);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}

   //driver.findElement(By.xpath("/html/body/div[3]/div/span")).click();
            //Thread.sleep(3000);

//wait.until(ExpectedConditions.visibilityOfElementLocated(
                    //By.xpath("//input[@placeholder='Search for Products, Brands and More']")));

 // String discountText = discount.getText();
                // String outputResult = discountText.replaceAll("[^0-9]", "");

// if(!outputResult.isEmpty()){
                    //int discountValue = Integer.parseInt(outputResult);
                    //if(discountValue > 17){
                        // Find the parent container of the current discount
                        // WebElement parentContainer = discount
                        //         .findElement(By.xpath("//ancestor::div[contains(@class, 'tUxRFH')]"));

                        // Find the title within the same parent container
                        // WebElement title = parentContainer.findElement(By.xpath(
                        //         ".//div[contains(@class, 'yKfJKb')]//div[contains(@class, 'KzDlHZ')]//text()"));

                        // Print the title and discount value
                        // String titleText = title.getText();
                        // System.out.println("Title is: " + titleText + " | Discount is: " + discountValue + "%");
                    // }
                // }

                // By discountPercent = By.xpath("//div[contains(@class, 'UkUFwK')]/span[contains(text(), '% off')]");
            // List<WebElement> discounts = wait
            //         .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(discountPercent));

            // try {
            //     actions.click(By.xpath("//span[text()='✕']"));
            // } catch (Exception e) {
            //     System.out.println("No login pop-up appeared.");
            // }