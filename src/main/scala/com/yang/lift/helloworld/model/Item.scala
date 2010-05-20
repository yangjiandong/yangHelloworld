package com.yang.lift.helloworld {
package model {

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object ItemMetaData extends Item with KeyedMetaMapper[Long, Item]{
    override def dbTableName = "items"
    override def fieldOrder = List(name, description, reserve, expiration)
}

class Item extends KeyedMapper[Long, Item]
//with LongCRUDify[Item]
//with CRUDify[Long, Item] TODO
{
  def getSingleton = ItemMetaData
  def primaryKeyField = id

  object id extends MappedLongIndex(this)
  object reserve extends MappedInt(this)
  object name extends MappedString(this, 100)
  object description extends MappedText(this)
  object expiration extends MappedDateTime(this)

  def bids = BidMetaData.findAll(By(BidMetaData.item, id))

  lazy val detailUrl = "/details.html?itemId=" + id.is

  def tr = {
      <tr>
          <td><a href={detailUrl}>{name}</a></td>
          <td>{highBid.amount}</td>
          <td>{expiration}</td>
      </tr>
  }

  def highBid:Bid = {
      val allBids = bids
      if (allBids.size > 0){
        return allBids.sort(_.amount.is > _.amount.is)(0)
      }
      BidMetaData.create
  }
}

//https://liftweb.assembla.com/spaces/liftweb/tickets/518-crudify-view--edit-and-delete-views-are-broken
class Miner extends LongKeyedMapper[Miner] with IdPK {
  def getSingleton = Miner
  object name extends MappedString(this, 50)
  object url_template extends MappedString(this, 255)
  object override_function extends MappedText(this)
  object user extends MappedLongForeignKey(this, User)
}

object Miner extends Miner with LongKeyedMetaMapper[Miner] with
LongCRUDify[Miner] {
  override def dbTableName = "miners"
}

}
}
