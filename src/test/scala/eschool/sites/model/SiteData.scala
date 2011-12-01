package eschool.sites.model

import eschool.users.model.User
import collection.immutable.ListMap
import scala.xml.NodeSeq
import bootstrap.liftweb.DataStore

import scala.collection.JavaConversions._

object SiteData {
  def create() {
    DataStore.pm.beginTransaction()
    val bob: User = User.getByUsername("rsmith1").get
    val bobPersonal: Site = new Site(bob, "All About Bob", "personal")
    DataStore.pm.makePersistent(bobPersonal)
    val hpContent: NodeSeq = (
      <h2>All About Bob</h2>
      <p>Stuff Bob likes:
        <ul>
          <li>Music</li>
          <li>Food</li>
          <li>Egyptian Anime</li>
        </ul>
      </p>
    )
    val homepage = new Page("Home")
    homepage.content = hpContent
    DataStore.pm.makePersistent(homepage)
    val ahContent: NodeSeq = (
      <h2>Bob Smith - The Professional</h2>
      <p>Stuff Bob does:
        <ul>
          <li>Programming</li>
          <li>Paint Ball</li>
        </ul>
      </p>
    )
    val altHome = new Page("Alternative Home")
    altHome.content = ahContent
    DataStore.pm.makePersistent(altHome)
    bobPersonal.children = List(homepage, altHome)
    val aliceContent = (
      <h2>Alice</h2>
      <p>Alice plays goalie.</p>
    )
    val alice = new Page("Alice")
    alice.content = aliceContent
    DataStore.pm.makePersistent(alice)
    val clarissaContent = (
      <h2>Clarissa</h2>
      <p>Clarissa explains it all.</p>
    )
    val clarissa = new Page("Clarissa")
    clarissa.content = clarissaContent
    DataStore.pm.makePersistent(clarissa)
    val rosterContent = (
      <h2>Roster</h2>
      <p>The players are:
        <ul>
          <li>Alice</li>
          <li>Barbara</li>
          <li>Clarissa</li>
        </ul>
      </p>
    )
    val roster = new Page("Roster")
    roster.content = rosterContent
    roster.children = List(alice, clarissa)
    DataStore.pm.makePersistent(roster)
    val schedContent = (
      <h2>Schedule</h2>
      <table>
        <tr><th>Date</th><th>Opponent</th></tr>
        <tr><td>July 15</td><td>West High</td></tr>
        <tr><td>July 20</td><td>East High</td></tr>
      </table>
    )
    val sched = new Page("Schedule")
    sched.content = schedContent
    DataStore.pm.makePersistent(sched)
    val soccerHomeContent = (
      <h2>Girls Soccer</h2>
      <p>Click below to access:
        <ul>
          <li>Schedule</li>
          <li>Roster</li>
        </ul>
      </p>
    )
    val soccerHome = new Page("Soccer Home")
    soccerHome.content = soccerHomeContent
    DataStore.pm.makePersistent(soccerHome)
    soccerHome.children = List(roster, sched)
    val bobSoccer: Site = new Site(bob, "Bob's Soccer Site", "soccer")
    bobSoccer.children = List(soccerHome)
    DataStore.pm.makePersistent(bobSoccer)
    val mary = User.getByUsername("mjones02").open_!
    val maryHomepage = new Page("Mary's Homepage")
    maryHomepage.content = (
      <h1>Mary</h1>
      <p>Mary only has one page and it doesn't have much on it.</p>
    )
    DataStore.pm.makePersistent(maryHomepage)
    val marysSite = new Site(mary, "Mary's Site", "site")
    marysSite.children = List(maryHomepage)
    DataStore.pm.makePersistent(marysSite)
    DataStore.pm.commitTransaction()
  }
}