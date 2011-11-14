package eschool.courses.model

import scala.collection.JavaConverters._

import jdo.Id

import javax.jdo.annotations._

class Section extends Id[Long] {
  private[this] var _course: Course = _
  @Unique
  private[this] var _sectionId: String = _
  @Element(types=Array(classOf[Term]))
  @Join
  private[this] var _terms: java.util.Set[Term] = _
  @Element(types=Array(classOf[Period]))
  @Join
  private[this] var _periods: java.util.Set[Period] = _
  private[this] var _room: Room = _
  
  def this(course: Course, sectionId: String, terms: Set[Term], periods: Set[Period], room: Room) = {
    this()
    _course = course
    _sectionId = sectionId
    terms_=(terms)
    periods_=(periods)
    _room = room
  }
  
  def course: Course = _course
  def course_=(theCourse: Course) { _course = theCourse }
  
  def sectionId: String = _sectionId
  def sectionId_=(theSectionId: String) { _sectionId = theSectionId }
  
  def terms: Set[Term] = _terms.asScala.toSet
  def terms_=(theTerms: Set[Term]) { _terms = theTerms.asJava }
  
  def room: Room = _room
  def room_=(theRoom: Room) { _room = theRoom }
  
  def periods: Set[Period] = _periods.asScala.toSet
  def periods_=(thePeriods: Set[Period]) { _periods = thePeriods.asJava }
  
  def periodNames: String = {
    periods.map(_.name).mkString(", ")
  }
}
