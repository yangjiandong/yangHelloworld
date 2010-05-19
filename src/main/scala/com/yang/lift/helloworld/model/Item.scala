package com.yang.lift.helloworld.model

import net.liftweb.mapper._
import net.liftweb.util._

object ItemMetaData extends Item with KeyedMetaMapper[Long, Item]{
    override def dbTableName = "items"
    override def fieldOrder = List(name, description, reserve, expiration)
}

class Item extends KeyedMapper[Long, Item]
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
