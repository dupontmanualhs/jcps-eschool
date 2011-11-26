package eschool.courses.model

import scala.collection.JavaConverters._

import eschool.courses.model.jdo.{Period, Section, Term}

object ISection {
  def getTerms(section: Section): Set[Term] = {
    section.getTerms.asScala.toSet
  }
  
  def setTerms(section: Section, terms: Set[Term]) {
    section.setTerms(terms.asJava)
  }
  
  def getPeriods(section: Section): Set[Period] = {
    section.getPeriods.asScala.toSet
  }
  
  def setPeriods(section: Section, periods: Set[Period]) {
    section.setPeriods(periods.asJava)
  }

  def getPeriodNames(section: Section): String = {
    getPeriods(section).toList.map((p: Period) => p.getName).mkString(", ")
  }
}

