package com.yang.lift.helloworld {
package model {

import _root_.net.liftweb.mapper._
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._

object BidMetaData extends Bid with KeyedMetaMapper[Long, Bid]{
    override def dbTableName = "t_bids"
    override def fieldOrder = amount :: Nil
    override def dbIndexes = Index(item) :: Index(user) :: super.dbIndexes
}

class Bid extends KeyedMapper[Long, Bid]{
    def getSingleton = BidMetaData
    def primaryKeyField = id

    object id extends MappedLongIndex(this)
    object amount extends MappedLong(this)
    object item extends MappedLongForeignKey(this, ItemMetaData)
    object user extends MappedLongForeignKey(this, User)
}
}
}