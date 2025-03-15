package dev.mcarr.la.data.sources

import dev.mcarr.la.classes.LinkGroup
import dev.mcarr.la.classes.LinkGroupArray
import dev.mcarr.la.interfaces.IDataSource
import dev.mcarr.la.data.daos.LinkDao
import dev.mcarr.la.data.daos.LinkGroupDao
import org.springframework.transaction.annotation.Transactional

/**
 * Data source which retrieves data from a database.
 * */
@Transactional
class DatabaseDataSource : IDataSource {

    override fun getLinkGroups(): List<LinkGroup> =
        LinkGroupDao.getAll()
            .map { g -> g.clone(LinkDao.getByGroupId(g.id)) }
            // TODO replace with join

    override fun getLinkGroup(id: Int): LinkGroup? {
        val g = LinkGroupDao.get(id) ?: return null
        val links = LinkDao.getByGroupId(g.id)
        return g.clone(links) // TODO replace with join
    }

    override fun deleteLinkGroup(id: Int): Boolean =
        LinkGroupDao.delete(id)

    override fun deleteLinkGroups(): Int =
        LinkGroupDao.deleteAll()

    override fun insertLinkGroup(row: LinkGroup): Int {
        val id = LinkGroupDao.insert(row.name)
        row.links.forEach { link ->
            LinkDao.insert(id, link)
        }
        return id
    }

    override fun updateLinkGroup(row: LinkGroup): Boolean {

        val success = LinkGroupDao.update(row)
        if (!success) return false

        LinkDao.deleteByGroupId(row.id)

        row.links.forEach { link ->
            LinkDao.insert(row.id, link)
        }

        return true
    }

}