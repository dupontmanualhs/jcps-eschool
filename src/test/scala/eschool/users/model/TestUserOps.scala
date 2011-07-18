package eschool.users.model

import org.junit.Assert._
import org.junit._
import org.openqa.selenium.chrome.{ChromeDriverService, ChromeDriver}
import org.openqa.selenium.chrome.ChromeDriverService.Builder
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}
import org.openqa.selenium.{By, WebElement, WebDriver}
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import org.scalatest.junit.{JUnitSuite, AssertionsForJUnit}
import java.io.File
import org.openqa.selenium.firefox.FirefoxDriver

class TestUserOps extends JUnitSuite with BeforeAndAfterAll {
//  val service = new ChromeDriverService.Builder()
//    .usingChromeDriverExecutable(new File("/opt/chromedriver"))
//    .usingAnyFreePort()
//    .build()
//  service.start()
  var driver: WebDriver = null

//  override def afterAll() {
//    service.stop()
//  }

  @Before
  def createDriver() {
    driver = new FirefoxDriver()
  }

  @After
  def quitDriver() {
    driver.quit()
  }

  @Test
  def googleSearch() = {
    driver.get("http://www.google.com");
    val searchBox = driver.findElement(By.name("q"))
    searchBox.sendKeys("webdriver")
    searchBox.submit()
    assertEquals("webdriver - Google Search", driver.getTitle)
  }
}