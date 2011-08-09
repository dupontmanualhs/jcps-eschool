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
import org.apache.http.client.protocol.RequestClientConnControl

class TestUserOps extends JUnitSuite with BeforeAndAfterAll {
  val service = new ChromeDriverService.Builder()
    .usingChromeDriverExecutable(new File("/opt/chromedriver"))
    .usingAnyFreePort()
    .build()
  service.start()
  var driver: RemoteWebDriver = null
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

  //@Test
  def homepage() = {
    selenium.open("")
    assert(driver.getTitle === "Home")
    assert(selenium.isTextPresent("Welcome to your JCPS eSchool"))
    assert(selenium.isElementPresent("link=Login"))
  }

  //@Test
  def login1() = {
    selenium.open("")
    selenium.click("link=Login")
    assert(driver.getTitle === "Login")
    assert(selenium.isTextPresent("Login"))
  }

  //@Test
  def login2() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("Logged in as: Bob Smith"))
    assert(selenium.isTextPresent("(Logout)"))
    selenium.click("link=Logout")
  }

   //@Test
   def login3() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Logout")
    selenium.click("link=Login")
    inputByLabel("Username").sendKeys("mjones02")
    inputByLabel("Password").sendKeys("mary1")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("Logged in as: Mary Jones"))
    assert(selenium.isTextPresent("(Logout)"))
    selenium.click("link=Logout")
  }

  //@Test
   def login4() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robertoo1")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("Incorrect"))
    selenium.click("link=Home")
  }

  //@Test
  def sites1() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Sites")
    assert(selenium.isTextPresent("All About Bob"))
    assert(selenium.isTextPresent("Bob's Soccer Site"))
    assert(selenium.isTextPresent("Create a New Site"))
    driver.findElementByLinkText("All About Bob").click()
    assert(selenium.isTextPresent("Owner: Bob Smith"))
    assert(selenium.isTextPresent("Alternative Home"))
    assert(selenium.isTextPresent("Home"))
    driver.findElementsByLinkText("Home").get(1).click()
    assert(selenium.isTextPresent("Food"))
    selenium.goBack()
    selenium.click("link=Logout")
  }

  //@Test
  def sites2() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Sites")
    assert(selenium.isTextPresent("All About Bob"))
    driver.findElementsByLinkText("All About Bob").get(0).click()
    assert(selenium.isTextPresent("Alternative Home"))
    driver.findElementsByLinkText("edit").get(1).click()
    //driver.findElementsByName("<\\li>").get(1), "<li>Frog Gigging<\\li>")
    driver.findElementsByTagName("button").get(1).click()
    driver.findElementsByLinkText("Alternative Home").get(0).click()
    assert(selenium.isTextPresent("Frog Gigging"))
    selenium.goBack()
    selenium.click("link=Logout")
  }

  //@Test
  def changeName() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    inputByLabel("Preferred").sendKeys("Bobby")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("Logged in as: Bobby Smith"))
    selenium.click("link=Settings")
    inputByLabel("Preferred").sendKeys("Bob")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("Logged in as: Bob Smith"))
    selenium.click("link=Logout")
  }

  //@Test
  def changeEmail() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    inputByLabel("Email").sendKeys("bobby@jcpsky.net")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Contact")
    assert(selenium.isTextPresent("Teachers"))
    assert(selenium.isTextPresent("bobby@jcpsky.net"))
    selenium.click("link=Settings")
    inputByLabel("Email").sendKeys("bob@jcpsky.net")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Contact")
    assert(selenium.isTextPresent("Teachers"))
    assert(selenium.isTextPresent("bob@jcpsky.net"))
    selenium.click("link=Logout")
  }

  //@Test
  def changePassword1() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    selenium.click("link=here")
    inputByLabel("Current Password").sendKeys("Robert1")
    inputByLabel("New Password").sendKeys("Bobbob1")
    inputByLabel("Re-enter New Password").sendKeys("Bobbob1")
    driver.findElementsByTagName("button").get(1).click()
    //assert(!selenium.isTextPresent("The"))
    selenium.click("link=Logout")
    selenium.click("link=Login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Bobbob1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    selenium.click("link=here")
    inputByLabel("Current Password").sendKeys("Bobbob1")
    inputByLabel("New Password").sendKeys("Robert1")
    inputByLabel("Re-enter New Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    //assert(!selenium.isElementPresent("id=fieldErrorIcon")
    selenium.click("link=Logout")
  }

  //@Test
  def changePassword2() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    selenium.click("link=here")
    inputByLabel("Current Password").sendKeys("Bobert1")
    inputByLabel("New Password").sendKeys("Bobbob1")
    inputByLabel("Re-enter New Password").sendKeys("Bobbob1")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("The current password is incorrect"))
    selenium.click("link=Logout")
  }

  //@Test
  def changePassword3() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    selenium.click("link=here")
    inputByLabel("Current Password").sendKeys("Robert1")
    inputByLabel("New Password").sendKeys("Bobbob1")
    inputByLabel("Re-enter New Password").sendKeys("Robbob1")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("New passwords do not match"))
    selenium.click("link=Logout")
  }

  //@Test
  def changePassword4() = {
    selenium.open("/users/login")
    inputByLabel("Username").sendKeys("rsmith1")
    inputByLabel("Password").sendKeys("Robert1")
    driver.findElementsByTagName("button").get(1).click()
    selenium.click("link=Settings")
    selenium.click("link=here")
    inputByLabel("Current Password").sendKeys("Robert1")
    inputByLabel("New Password").sendKeys("bob")
    inputByLabel("Re-enter New Password").sendKeys("bob")
    driver.findElementsByTagName("button").get(1).click()
    assert(selenium.isTextPresent("The new password must be at least 5 characters"))
    assert(selenium.isTextPresent("New password must have at least 1 digit (0-9)"))
    assert(selenium.isTextPresent("New password must have at least 1 uppercase character (A-Z)"))
    selenium.click("link=Logout")
  }


  def inputByLabel(text: String): WebElement = {
    val label: WebElement = driver.findElement(By.xpath("//label[contains(text(), '%s')]".format(text)))
    driver.findElement(By.id(label.getAttribute("for")))
  }
}
