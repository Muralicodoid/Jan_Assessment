import com.cucumber.listener.Reporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.security.acl.AclEntryImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Trello_Task {
    // Instantiate a ChromeDriver class.
    static String driverPath = System.setProperty(
            "webdriver.chrome.driver",
            "C:\\Users\\SG\\Downloads\\chromedriver_win32\\chromedriver.exe");

    static WebDriver driver = new ChromeDriver();

    //    Initializing the Wait.
    static WebDriverWait wait = new WebDriverWait(driver, 20);

    protected static WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected static WebElement waitForElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void enterValue(WebElement elmnt, String strValue) {
        waitForElement(elmnt);
        elmnt.click();
        elmnt.clear();
        elmnt.sendKeys(strValue);
    }

    public static void TakeScreenshot(String FileName)
            throws IOException {
        // Creating instance of File
        File File = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(File,
                new File("D:\\Trello Task\\src\\main\\java\\ScreenShot"
                        + FileName + ".jpeg"));
    }

    public static void main(String args[]) throws IOException {
        // Maximize the browser
        driver.manage().window().maximize();

        // Launch Website
        driver.get("https://trello.com/b/iwbECKTn/basic-board");
        WebElement trelloHeader = driver.findElement(By.xpath("//div[@class='DYTOPoMmZrLR1p']"));
        waitForElement(trelloHeader);
        WebElement btnLogIn = driver.findElement(By.xpath("//a[normalize-space()='Log in']"));
        waitForElementClickable(btnLogIn);
        btnLogIn.click();
        WebElement txtLoginToTrello = driver.findElement(By.xpath("//h1[normalize-space()='Log in to Trello']"));
        waitForElement(txtLoginToTrello);
        String txtLogin = txtLoginToTrello.getText();
        System.out.println("Now we are in Login Page " + txtLogin);
        TakeScreenshot("LoginPage");
        WebElement inputUserId = driver.findElement(By.xpath("//input[@id='user']"));
        waitForElementClickable(inputUserId);
        enterValue(inputUserId, "muralid.800@gmail.com");
        WebElement btnContinue = driver.findElement(By.xpath("//input[@type='submit']"));
        btnContinue.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@id='login-submit']")));
        WebElement btnSubmit = driver.findElement(By.xpath("//button[@id='login-submit']"));
        waitForElement(btnSubmit);
        WebElement inputPassword = driver.findElement(By.xpath("//input[@id='password']"));
        waitForElementClickable(inputPassword);
        enterValue(inputPassword, "Welcome@2023");
        btnSubmit.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='board']")));
        WebElement elmntBoard = driver.findElement(By.xpath("//div[@id='board']"));
        waitForElement(elmntBoard);
        System.out.println("We are now successfully logged in >>>>>>>>>>>");
        TakeScreenshot("HomePage");
        List<WebElement> boardLists = driver.findElements(By.xpath("//div[@class='list js-list-content']/div[contains(@class,'list-header')]"));
        List<String> lstActualItems = new ArrayList<>();
        for (WebElement strItems : boardLists) {
            lstActualItems.add(strItems.getText());
        }
        System.out.println("Actual Items are : " + lstActualItems);

//        Now, Create 2 new lists

        WebElement btnAddNewList = driver.findElement(By.xpath("//a/following::span[@class='placeholder']"));
        WebElement inputListName = driver.findElement(By.xpath("//input[contains(@placeholder,'Enter list title')]"));
        WebElement iconCancelList = driver.findElement(By.xpath("//a[@aria-label='Cancel list editing']"));
        WebElement btnAddList = driver.findElement(By.xpath("//input[@type='submit']"));
        waitForElementClickable(btnAddNewList);
        String firstList = "List A";
        String secondList = "List B";
        btnAddNewList.click();
        waitForElementClickable(inputListName);
        enterValue(inputListName, firstList);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        btnAddList.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@aria-label='Cancel list editing']")));
        WebElement iconCancelLst = driver.findElement(By.xpath("//a[@aria-label='Cancel list editing']"));
        waitForElementClickable(iconCancelLst);
        iconCancelLst.click();
        WebElement btnAddNewList1 = driver.findElement(By.xpath("//a/following::span[@class='placeholder']"));
        waitForElementClickable(btnAddNewList1);
        btnAddNewList1.click();
        WebElement inputListName2 = driver.findElement(By.xpath("//input[contains(@placeholder,'Enter list title')]"));
        enterValue(inputListName2, secondList);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        WebElement btnAddList2 = driver.findElement(By.xpath("//input[@type='submit']"));
        btnAddList2.click();
        WebElement iconCancelList1 = driver.findElement(By.xpath("//a[@aria-label='Cancel list editing']"));
        waitForElementClickable(iconCancelList1);
        iconCancelList1.click();
        System.out.println("Lists are created");
        List<WebElement> newBoardLists = driver.findElements(By.xpath("//div[@class='list js-list-content']/div[contains(@class,'list-header')]"));
        List<String> lstItemsAfterCreationExpected = Arrays.asList("Done", "List A", "List B");
        List<String> lstItemsAfterCreation = new ArrayList<>();
        for (WebElement strItems : newBoardLists) {
            lstItemsAfterCreation.add(strItems.getText());
        }
        System.out.println("Lists are as follows " + lstItemsAfterCreation);
        if (lstItemsAfterCreation.containsAll(lstItemsAfterCreationExpected)) {
            System.out.println("All the lists are created as expected");
        }
        TakeScreenshot("After Lists Creation");

        //Creating sub-tasks inside List A

        WebElement btnAddACardListA = driver.findElement(By.xpath("(//h2[normalize-space()='List A']//following::span[text()='Add a card'])[1]"));
        waitForElementClickable(btnAddACardListA);
        btnAddACardListA.click();
        WebElement inputListATask1 = driver.findElement(By.xpath("//textarea[contains(@placeholder,'Enter a title for this card')]"));
        waitForElementClickable(inputListATask1);
        enterValue(inputListATask1, "Morning Task");
        WebElement btnAddCardATask1 = driver.findElement(By.xpath("//input[@type='submit' and @value='Add card']"));
        waitForElementClickable(btnAddCardATask1);
        btnAddCardATask1.click();
        WebElement inputListATask2 = driver.findElement(By.xpath("//textarea[contains(@placeholder,'Enter a title for this card')]"));
        waitForElementClickable(inputListATask2);
        enterValue(inputListATask2, "Afternoon's Task");
        WebElement btnAddCardATask2 = driver.findElement(By.xpath("//input[@type='submit' and @value='Add card']"));
        waitForElementClickable(btnAddCardATask2);
        btnAddCardATask2.click();
        System.out.println("Sub-Tasks Have been created for List A");
        TakeScreenshot("After List A Sub-Tasks Creation");

        //Creating a sub-task inside List B

        WebElement btnAddACardListB = driver.findElement(By.xpath("//h2[normalize-space()='List B']//following::span[text()='Add a card']"));
        waitForElementClickable(btnAddACardListB);
        btnAddACardListB.click();
        WebElement inputListBTask1 = driver.findElement(By.xpath("//textarea[contains(@placeholder,'Enter a title for this card')]"));
        waitForElementClickable(inputListBTask1);
        enterValue(inputListBTask1, "Morning Task");
        WebElement btnAddCardBTask1 = driver.findElement(By.xpath("//input[@type='submit' and @value='Add card']"));
        waitForElementClickable(btnAddCardBTask1);
        btnAddCardBTask1.click();
        System.out.println("Sub-Task Has been created for List B");

        TakeScreenshot("After List B Sub-Task Creation");

        //Now, Performing Drag and Drop
        WebElement ListASubtaskDraggable = driver.findElement(By.xpath("(//h2[normalize-space()='List A']/following::span[contains(@class,'list-card-title')])[2]"));
        List<WebElement> LstBSubtaskDroppable = driver.findElements(By.xpath("//h2[normalize-space()='List B']/following::span[contains(@class,'list-card-title')]"));
        System.out.println("Before Dropping the Task To List B, The List B length is >>>> " + LstBSubtaskDroppable.size());
        Actions dragAndDrop = new Actions(driver);
        WebElement ListBSubtaskDroppable = driver.findElement(By.xpath("//h2[normalize-space()='List B']/following::span[contains(@class,'list-card-title')]"));
        dragAndDrop.dragAndDrop(ListASubtaskDraggable, ListBSubtaskDroppable).build().perform();
        List<WebElement> LstBSubtaskAfterDropped = driver.findElements(By.xpath("//h2[normalize-space()='List B']/following::span[contains(@class,'list-card-title')]"));
        System.out.println("After Dropping the Task To List B, The List B length is >>>> " + LstBSubtaskAfterDropped.size());
        if (LstBSubtaskAfterDropped.size() > 1) {
            System.out.println("Successfully Dropped the List A SubTask in the List B");
        }
        TakeScreenshot("After Performing the Drag And Drop");
        List<WebElement> currentBoardLists = driver.findElements(By.xpath("//div[@class='list js-list-content']/div[contains(@class,'list-header')]"));
        List<String> currentListItems = new ArrayList<>();
        for (WebElement strItems : currentBoardLists) {
            currentListItems.add(strItems.getText());
        }
        System.out.println("Actual Items are : " + currentListItems);
        driver.quit();


    }
}
