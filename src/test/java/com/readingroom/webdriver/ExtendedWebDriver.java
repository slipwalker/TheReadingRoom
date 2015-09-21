package com.readingroom.webdriver;

import com.google.common.base.Function;
import com.readingroom.util.common.Log;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.remote.CapabilityType.HAS_NATIVE_EVENTS;
import static org.openqa.selenium.remote.CapabilityType.SUPPORTS_JAVASCRIPT;
import static org.openqa.selenium.remote.CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR;

public class ExtendedWebDriver implements SearchContext,WebDriver {
    private static final long LONG_TIMEOUT_MS = 30 * 1000;
    private static final long SHORT_TIMEOUT_MS = 1000;
    private static final long PULL_UP_INTERVAL_MS = 100;
    private static final String SCREENSHOT_FOLDER = "target/screenshots/";
    private static final String SCREENSHOT_FORMAT = ".png";
    private static final String CHROME_DRIVER_LOCATION = "src/test/resources/drivers/chrome/chromedriver";
    private final WebDriver browserDriver;
    private final File downloadFolder;
    private final Wait<WebDriver> wait;

    public ExtendedWebDriver() {
        this(Browser.FIREFOX);
    }

    public ExtendedWebDriver(Browser browser) {
        this(browser, null);
    }

    public ExtendedWebDriver(Browser browser, File downloadFolder) {
        if (downloadFolder == null) downloadFolder = new File(System.getProperty("java.io.tmpdir"));
        if (!downloadFolder.exists()) downloadFolder.mkdirs();
        this.downloadFolder = downloadFolder;
        this.browserDriver = prepareBrowser(browser);

        turnImplicitlyWaitOn();
        this.wait = new WebDriverWait(this.browserDriver, getLongTimeoutSEC(), getPullUpIntervalMS());
        maximizeWindow();
    }

    protected WebDriver prepareBrowser(Browser browser) {
        WebDriver readyBrowser;
        URL remoteUrl = getRemoteUrlFromSystemProperty();
        Log.DEBUG("init " + browser.toString());
        switch (browser) {
            case FIREFOX:
                readyBrowser = new FirefoxDriver(buildFFCapabilities());
                break;
            case CHROME:
                setChromeDriver();
                readyBrowser = new ChromeDriver();
                break;
            case IE:
                readyBrowser = new InternetExplorerDriver();
                break;
            case SAFARI:
                readyBrowser = new SafariDriver();
                break;
            case REMOTE_FIREFOX:
                readyBrowser = new RemoteWebDriver(remoteUrl, buildFFCapabilities());
                ((RemoteWebDriver) readyBrowser).setFileDetector(new LocalFileDetector());
                break;
            case REMOTE_IE:
                readyBrowser = new RemoteWebDriver(remoteUrl, DesiredCapabilities.internetExplorer());
                ((RemoteWebDriver) readyBrowser).setFileDetector(new LocalFileDetector());
                break;
            case REMOTE_CHROME:
                readyBrowser = new RemoteWebDriver(remoteUrl, DesiredCapabilities.chrome());
                ((RemoteWebDriver) readyBrowser).setFileDetector(new LocalFileDetector());
                break;
            case REMOTE_SAFARI:
                readyBrowser = new RemoteWebDriver(remoteUrl, DesiredCapabilities.safari());
                ((RemoteWebDriver) readyBrowser).setFileDetector(new LocalFileDetector());
                break;
            default:
                readyBrowser = new HtmlUnitDriver();
                break;
        }
        return readyBrowser;
    }

    public WebDriver getBrowserDriver() {
        return browserDriver;
    }

    // support for remote run via grid
    private URL getRemoteUrlFromSystemProperty() {
        try {
            return new URL(System.getProperty("webDriverGridRemoteUrl", "http://localhost:4444/wd/hub"));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private DesiredCapabilities buildFFCapabilities() {
        FirefoxProfile profile;
        String customProfileDir = System.getProperty("firefoxCustomProfileDirectory");
        if (customProfileDir != null) {
            profile = new FirefoxProfile(new File(customProfileDir));
        } else {
            profile = new FirefoxProfile();
        }
        //profile.setEnableNativeEvents(true);
        //profile.setAlwaysLoadNoFocusLib(true);
        profile.setPreference("dom.max_chrome_script_run_time", 60);
        profile.setPreference("dom.max_script_run_time", 60);
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", downloadFolder.toString());
        profile.setPreference("browser.download.defaultFolder", downloadFolder.toString());
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream,application/x-zip-compressed,application/unknown,video/quicktime");
        profile.setPreference("signed.applets.codebase_principal_support", true);
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.setCapability(FirefoxDriver.PROFILE, profile);
        capability.setCapability(UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        capability.setCapability(HAS_NATIVE_EVENTS, true);
        capability.setCapability(SUPPORTS_JAVASCRIPT, true);
        capability.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        return capability;
    }

    private void maximizeWindow() {
        browserDriver.manage().window().maximize();
    }

    public void sleep(long sleepTimeInMs) {
        boolean interrupted = false;
        try {
            SystemClock clock = new SystemClock();
            long end = clock.laterBy(sleepTimeInMs);
            while (clock.isNowBefore(end)) {
                try {
                    Thread.sleep(getPullUpIntervalMS());
                } catch (InterruptedException e) {
                    //thread are interrupted, complete job and interrupt thread
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }
    }

    public void get(String url) {
        browserDriver.get(url);
    }

    public String getCurrentUrl() {
        return browserDriver.getCurrentUrl();
    }

    public String getTitle() {
        return browserDriver.getTitle();
    }

    public List<WebElement> findElements(By by) {
        return browserDriver.findElements(by);
    }

    public WebElement findElement(By by) {
        return browserDriver.findElement(by);
    }

    public String getPageSource() {
        return browserDriver.getPageSource();
    }

    public void close() {
        browserDriver.close();
    }

    public void quit() {
        browserDriver.quit();
    }

    public Set<String> getWindowHandles() {
        return browserDriver.getWindowHandles();
    }

    public String getWindowHandle() {
        return browserDriver.getWindowHandle();
    }

    public WebDriver.TargetLocator switchTo() {
        return browserDriver.switchTo();
    }

    public WebDriver.Navigation navigate() {
        return browserDriver.navigate();
    }

    public WebDriver.Options manage() {
        return browserDriver.manage();
    }

    public long getLongTimeoutMS() {
        return LONG_TIMEOUT_MS;
    }

    public long getLongTimeoutSEC() {
        return getLongTimeoutMS() / 1000;
    }

    public long getShortTimeoutMS() {
        return SHORT_TIMEOUT_MS;
    }

    public long getPullUpIntervalMS() {
        return PULL_UP_INTERVAL_MS;
    }

    public JavascriptExecutor getJavascriptExecutor() {
        return (JavascriptExecutor) browserDriver;
    }

    public void setAttributeThroughJavascript(String attribute, String value, By by) {
        setAttributeThroughJavascript(attribute, value, findElement(by));
    }

    public void setAttributeThroughJavascript(String attribute, String value, WebElement element) {
        getJavascriptExecutor().executeScript("arguments[0].setAttribute(\"" + attribute + "\",\"" + value + "\")", element);
    }

    public void setValueThroughJavascript(String value, WebElement element) {
        setAttributeThroughJavascript("value", value, element);
    }

    public void clickThroughJavascript(By elementLocator) {
        clickThroughJavascript(findElement(elementLocator));
    }

    public void clickThroughJavascript(WebElement element) {
        String script = "arguments[0].click();";
        getJavascriptExecutor().executeScript(script, element);
    }

    public void scrollToElement(WebElement element) {
        getJavascriptExecutor().executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /*
   click first time, then check visibility condition for waiting element
    */
    public void clicker(By locatorOfClickableElement, By locatorOfWaitingElement) {
        long end = System.currentTimeMillis() + SHORT_TIMEOUT_MS;
        do {
            this.findElement(locatorOfClickableElement).click();
            if (end < System.currentTimeMillis())
                throw new TimeoutException("Timed out after " + SHORT_TIMEOUT_MS + " ms");
            this.sleep(this.getPullUpIntervalMS());
        } while (!isElementVisible(locatorOfWaitingElement));
    }

    public void typeClean(By locatorOfInpitField, String text) {
        WebElement input = this.findElement(locatorOfInpitField);
        if (input.getAttribute("value").equals(text)) return;
        input.clear();
        if (text == null) return;
        if (text.isEmpty()) return;
        input.sendKeys(text);
    }

    public void click(By locatorOfClickableElement) {
        try {
            WebElement element = findElement(locatorOfClickableElement);
            //if (element instanceof Locatable)
            //    ((Locatable) element).getLocationOnScreenOnceScrolledIntoView();
            element.click();
        } catch (ElementNotVisibleException e) {
            throw new ElementNotVisibleException("you click on element " +
                    locatorOfClickableElement.toString() + " that currently invisible \n", e.getCause());
        }
    }

    public Boolean waitUntilElementDisappear(By by) {
        return waitUntil(ExpectedConditions.invisibilityOfElementLocated(by), "waitUntilElementDisappear");
    }

    public WebElement waitUntilElementAppearVisible(By by) {
        return waitUntil(ExpectedConditions.visibilityOfElementLocated(by), "waitUntilElementAppearVisible");
    }

    public <V> V waitUntil(Function<? super WebDriver, V> function, String timeoutMessage) {
        turnImplicitlyWaitOff();
        V result;
        try {
            result = wait.until(function);
        } catch (TimeoutException timeException) {
            turnImplicitlyWaitOn();
            throw new TimeoutException(timeException.getMessage() +
                    "\nTimeOut while waitUntil " + timeoutMessage, timeException.getCause());
        }
        turnImplicitlyWaitOn();
        return result;
    }

    public boolean isAlertPresent() {
        try {
            this.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public boolean isElementVisible(By locator) {
        boolean elementVisible = false;
        turnImplicitlyWaitOff();
        if (isElementPresent(locator)) {
            try {
                elementVisible = this.findElement(locator).isDisplayed();
            } catch (WebDriverException e) {
                elementVisible = false;
            }
        }
        turnImplicitlyWaitOn();
        return elementVisible;
    }

    public boolean isElementPresent(By locator) {
        boolean elementPresent;
        turnImplicitlyWaitOff();
        elementPresent = this.findElements(locator).size() > 0;
        turnImplicitlyWaitOn();
        return elementPresent;
    }

    private void turnImplicitlyWaitOff() {
        this.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
    }

    private void turnImplicitlyWaitOn() {
        this.manage().timeouts().implicitlyWait(getShortTimeoutMS(), TimeUnit.MILLISECONDS);
    }

    public File getDownloadDir() {
        return downloadFolder;
    }

    public String getAlertMessage() {
        String alertMessage = browserDriver.switchTo().alert().getText();
        browserDriver.switchTo().alert().accept();
        return alertMessage;
    }

    public boolean setCheckBoxTo(By by, boolean desiredStatement) {
        WebElement check_box = browserDriver.findElement(by);
        if (check_box.isEnabled()) {
            if (desiredStatement == check_box.isSelected()) {
                return true;
            } else {
                check_box.click();
                return true;
            }
        } else {
            if (desiredStatement == check_box.isSelected()) return true;
        }
        return false;
    }

    public void clickDbl(By by) {
        this.clickDbl(this.findElement(by));
    }

    public void clickDbl(WebElement element) {
        this.getActions().doubleClick(element).perform();
        /*this.getJavascriptExecutor().executeScript("var evt = document.createEvent('MouseEvents');" +
                "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
                "arguments[0].dispatchEvent(evt);", browserDriver.findElement(by));*/
    }

    public Actions getActions() {
        return new Actions(browserDriver);
    }

    public void setScreenshot(ITestResult result) {
        if (!result.isSuccess()) {
            try {
                Log.INFO("Creating screen shot on following test failure: " + result.getMethod().getMethodName(), true);
                WebDriver returned = new Augmenter().augment(browserDriver);
                if (returned != null) {
                    File f = ((TakesScreenshot) returned)
                            .getScreenshotAs(OutputType.FILE);
                    try {
                        FileUtils.copyFile(f, new File(SCREENSHOT_FOLDER
                                + result.getName() + SCREENSHOT_FORMAT));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ScreenshotException se) {
                se.printStackTrace();
            }
        }
    }

    private static void setChromeDriver() {
        String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
        String chromeBinary = CHROME_DRIVER_LOCATION + (os.equals("win") ? ".exe" : "");
        System.setProperty("webdriver.chrome.driver", chromeBinary);
    }
}