package eschool.users.snippet

import org.junit.Assert._
import org.junit._
import org.openqa.selenium.chrome.{ChromeDriverService, ChromeDriver}
import org.openqa.selenium.chrome.ChromeDriverService.Builder
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatest.junit.{JUnitSuite, AssertionsForJUnit}
import java.io.File
import org.openqa.selenium.firefox.FirefoxDriver
import com.thoughtworks.selenium.Selenium
import org.openqa.selenium.{WebDriverBackedSelenium, By, WebElement, WebDriver}

class TestUserOps extends JUnitSuite with BeforeAndAfterAll {
  val service = new ChromeDriverService.Builder()
    .usingChromeDriverExecutable(new File("/opt/chromedriver"))
    .usingAnyFreePort()
    .build()
  service.start()
  var driver: WebDriver = null
  var selenium: Selenium = null

  override def afterAll() {
    service.stop()
  }

  @Before
  def createDriver() {
    driver = new ChromeDriver(service)
    selenium = new WebDriverBackedSelenium(driver, "http://localhost:8080")
  }

  @After
  def quitDriver() {
    driver.quit()
  }

  @Test
  def homepage() = {
    selenium.open("")
    assert(driver.getTitle === "JCPS eSchool")
    assert(selenium.isTextPresent("Welcome to your JCPS eSchool"))
    assert(selenium.isElementPresent("link=Login"))
  }

  @Test
  def login1() = {
    selenium.open("")
    selenium.click("link=Login")
    assert(driver.getTitle === "Login")
    assert(selenium.isTextPresent("Login"))
  }

  def inputByLabel(text: String): WebElement = {
    val label: WebElement = driver.findElement(By.xpath("//label@contains(text(), '%s'".format(text)))
    driver.findElement(By.id(label.getAttribute("for")))
  }
}