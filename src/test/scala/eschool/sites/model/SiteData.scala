package eschool.sites.model

import xml.NodeSeq

import eschool.users.model.User
import net.liftweb.common.Empty

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
    val b1: Site = Site.createRecord.owner(bob.id.get).name("All About Bob")
        .ident("personal").pages(Map("home" -> homepage.id.get, "altHome" -> altHome.id.get))
    b1.save(true)
  }
}