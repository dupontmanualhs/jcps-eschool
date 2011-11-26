/*package eschool.courses.model

import bootstrap.liftweb.DataStore

import jdo.{QTerm, Term}

object ITerm {
  lazy val current: Term = {
    val cand = QTerm.candidate
    DataStore.pm.query[Term].filter(cand.name.eq("Fall 2011")).executeOption().get
  }
}*/