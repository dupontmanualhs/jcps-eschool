/*package eschool.sites.model

import xml.NodeSeq

import eschool.users.model.User
import net.liftweb.common.Empty

object SiteData {
  def create() {
    val bob: User = User.getByUsername("rsmith1").open_!
    val b1: Site = Site.createRecord.owner(bob).name("All About Bob").ident("personal")
    b1.save(true)
    val homepage = Page.createRecord.site(b1).name("Home").ident("home").content(
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
    b1.pages(b1.pages.get)
  }
}*/