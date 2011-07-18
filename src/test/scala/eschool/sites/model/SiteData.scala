package eschool.sites.model

import eschool.users.model.User
import collection.immutable.ListMap

object SiteData {
  def create() {
    val bob: User = User.getByUsername("rsmith1").open_!
    val homepage = Page.createRecord.name("Home").content(
      <h2>All About Bob</h2>
      <p>Stuff Bob likes:
        <ul>
          <li>Music</li>
          <li>Food</li>
          <li>Egyptian Anime</li>
        </ul>
      </p>
    )
    homepage.save(true)
    val altHome = Page.createRecord.name("Alternative Home").content(
      <h2>Bob Smith - The Professional</h2>
      <p>Stuff Bob does:
        <ul>
          <li>Programming</li>
          <li>Paint Ball</li>
        </ul>
      </p>
    )
    altHome.save(true)
    val bobPersonal: Site = Site.createRecord.owner(bob.id.get).name("All About Bob")
        .ident("personal").pages(ListMap("home" -> homepage.id.get, "altHome" -> altHome.id.get))
    bobPersonal.save(true)
    val alice = Page.createRecord.name("Alice").content(
      <h2>Alice</h2>
      <p>Alice plays goalie.</p>
    )
    alice.save(true)
    val clarissa = Page.createRecord.name("Clarissa").content(
      <h2>Clarissa</h2>
      <p>Clarissa explains it all.</p>
    )
    clarissa.save(true)
    val roster = Page.createRecord.name("Roster").content(
      <h2>Roster</h2>
      <p>The players are:
        <ul>
          <li>Alice</li>
          <li>Barbara</li>
          <li>Clarissa</li>
        </ul>
      </p>
    ).pages(Map("alice" -> alice.id.get, "clarissa" -> clarissa.id.get))
    roster.save(true)
    val sched = Page.createRecord.name("Schedule").content(
      <h2>Schedule</h2>
      <table>
        <tr><th>Date</th><th>Opponent</th></tr>
        <tr><td>July 15</td><td>West High</td></tr>
        <tr><td>July 20</td><td>East High</td></tr>
      </table>
    )
    sched.save(true)
    val soccerHome = Page.createRecord.name("Home").content(
      <h2>Girls Soccer</h2>
      <p>Click below to access:
        <ul>
          <li>Schedule</li>
          <li>Roster</li>
        </ul>
      </p>
    )
    soccerHome.pages(ListMap("roster" -> roster.id.get, "sched" -> sched.id.get))
    soccerHome.save(true)
    val bobSoccer: Site = Site.createRecord.owner(bob.id.get).name("Bob's Soccer Site")
        .ident("soccer").pages(ListMap("home" -> soccerHome.id.get))
    bobSoccer.save(true)
    val mary = User.getByUsername("mjones02").open_!
    val maryHomepage = Page.createRecord.name("Mary's Homepage").content(
      <h1>Mary</h1>
      <p>Mary only has one page and it doesn't have much on it.</p>
    )
    maryHomepage.save(true)
    val marysSite = Site.createRecord.owner(mary.id.get).name("Mary's Site")
        .ident("site").pages(ListMap("home" -> maryHomepage.id.get))
    marysSite.save(true)
  }
}