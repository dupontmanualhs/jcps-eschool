package eschool.utils.record

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import net.liftweb.record.field.PasswordField
import com.sun.org.apache.xml.internal.security.algorithms.Algorithm
import net.liftweb.util.Safe

class TestPasswordHashField extends AssertionsForJUnit {
  val b1: Array[Byte] = List(10, 57, -1, -50, 127, 4).map(_.toByte).toArray

  @Test def textToHex() {
    assert(PasswordHashField.toHexString(b1) === "0a39ffce7f04")
  }

  @Test def hexToText() {
    assert(PasswordHashField.toByteArray("0a39ffce7f04") === b1)
  }

  @Test def hashAndUnhash() {
    List("SHA-512", "SHA-256", "SHA-384", "MD5") foreach ((alg: String) => {
      val password = Safe.randomString(10)
      val hash = PasswordHashField.newHash(password, alg)
      assert(PasswordHashField.hashMatches_?(hash, password))
    })
  }

}