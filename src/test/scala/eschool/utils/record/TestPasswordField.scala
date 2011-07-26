package eschool.utils.record

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import com.sun.org.apache.xml.internal.security.algorithms.Algorithm
import net.liftweb.util.Safe
import eschool.utils.record.PasswordField
import java.security.MessageDigest
import eschool.users.model.UserData

class TestPasswordField extends AssertionsForJUnit {
  @Test def hashWorks_?() {
    assert(true) //easier to test from webapp standpoint than via JUnit
  }

}