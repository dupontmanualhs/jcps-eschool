package eschool.utils.record

import net.liftweb.record._
import net.liftweb.util.FieldError
import java.lang.Boolean
import xml.Text
import net.liftweb.http.S
import java.security.{SecureRandom, MessageDigest, InvalidAlgorithmParameterException, Security}
import net.liftweb.common.{Empty, Box}

object PasswordHashField {
  def toHexString(bytes: Array[Byte]): String = {
    def byteString(b: Byte): String = {
      val hex: String = java.lang.Integer.toHexString(if (b < 0) b + 256 else b)
      if (hex.length < 2) "0" + hex else hex
    }
    bytes.toList.map(byteString(_)).mkString
  }

  def toByteArray(s: String): Array[Byte] = {
    s.grouped(2).map(java.lang.Integer.valueOf(_, 16).toByte).toArray
  }

  def validHashAlgorithm_?(alg: String): Boolean = {
    Security.getAlgorithms("MessageDigest").contains(alg)
  }

  def checkHashAlgorithm_!(hashAlg: String) {
    if (!validHashAlgorithm_?(hashAlg)) {
      throw new InvalidAlgorithmParameterException(hashAlg + " is not a recognized MessageDigest method.")
    }
  }

  def newHash(password: String, hashAlg: String): String = {
    checkHashAlgorithm_!(hashAlg)
    val md = MessageDigest.getInstance(hashAlg)
    val salt = new Array[Byte](16)
    new SecureRandom().nextBytes(salt)
    md.update(salt)
    md.update(password.getBytes)
    "%s$%s$%s".format(hashAlg, toHexString(salt), toHexString(md.digest))
  }

  def hashMatches_?(hash: String, password: String): Boolean = {
    val parts = hash.split('$')
    if(parts.length != 3) {
      //TODO: log an error
      return false;
    } else {
      val hashAlg = parts(0)
      val salt = toByteArray(parts(1))
      val digest = parts(2)
      checkHashAlgorithm_!(hashAlg)
      val md = MessageDigest.getInstance(hashAlg)
      md.update(salt)
      md.update(password.getBytes)
      toHexString(md.digest) == digest
    }
  }
}

trait PasswordHashTypedField extends TypedField[String] {
  def defaultValidationFunction(s: String): List[FieldError] = {
    if (s.length < 5) {
      Text(S.??("password.too.short"))
    } else {
      Nil
    }
  }
}

abstract class PasswordHashField[OwnerType <: Record[OwnerType]](
  val owner: OwnerType,
  val hashAlgorithm: String,
  val validPassword_? : Box[String => List[FieldError]]
) extends Field[String, OwnerType] with MandatoryTypedField[String] with PasswordHashTypedField {
  PasswordHashField.checkHashAlgorithm_!(hashAlgorithm)

  def this(owner: OwnerType) = this(owner, "SHA-512", Empty)


}

abstract class OptionalPasswordHashField[OwnerType <: Record[OwnerType]](rec: OwnerType)
  extends Field[String, OwnerType] with OptionalTypedField[String] with PasswordHashTypedField {

  def this(rec: OwnerType, value: Box[String]) = {
    this(rec)
    setBox(value)
  }

  def owner = rec
}

