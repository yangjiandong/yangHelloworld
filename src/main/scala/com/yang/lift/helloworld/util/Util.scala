package com.yang.lift.helloworld.util

import java.util.Date
import java.text.SimpleDateFormat

import scala.xml._
import net.liftweb._
import http._
import util._
import S._
import SHtml._
import scala.xml._
import Helpers._
import _root_.net.liftweb.common._

import com.yang.lift.helloworld.model._

object Util {
  val noSlashDate = new SimpleDateFormat("yyyyMMdd")

  val slashDate = new SimpleDateFormat("yyyy/MM/dd")

  def splitEvery[A](as : List[A], n : Int) : List[List[A]] = as.splitAt(n) match {
    case (a, Nil) => a :: Nil
    case (a, b)   => a :: splitEvery(b, n)
  }

  def getIntParam(name : String, default : Int) : Int = {
    try {
      S.param(name).map(_.toInt) openOr default
    }
    catch {
      case e => default // Should log something in this case
    }
  }

  type DateConverter = String => Date

  def parseDate(value : String, converter : DateConverter) : Box[Date] =
    try {
      Full(converter(value))
    } catch {
      case e => Empty
    }

  def getDateParam(name : String, converter : DateConverter) : Box[Date] = {
    S.param(name).map(parseDate(_, converter)) openOr Empty
  }
}
