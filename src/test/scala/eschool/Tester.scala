package eschool

import java.io.File
import org.openqa.selenium.remote.RemoteWebDriver
import com.thoughtworks.selenium.Selenium
import org.openqa.selenium.chrome.{ChromeDriver, ChromeDriverService}
import org.openqa.selenium.{By, WebElement, WebDriverBackedSelenium}

object Tester {
  val service = new ChromeDriverService.Builder()
    .usingChromeDriverExecutable(new File("/opt/chromedriver"))
    .usingAnyFreePort()
    .build()
  service.start()
  val driver: RemoteWebDriver = new ChromeDriver(service)
  val selenium: Selenium = new WebDriverBackedSelenium(driver, "http://localhost:8080")

  def inputByLabel(text: String): WebElement = {
    val label: WebElement = driver.findElementByXPath("//label[contains(text(), '%s')]".format(text))
    driver.findElementById(label.getAttribute("for"))
  }

  def login(username: String, password: String) = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys(username)
    inputByLabel("Password").sendKeys(password)
    driver.findElementByXPath("//button[contains(text(), 'Finish')]").click()
  }
}