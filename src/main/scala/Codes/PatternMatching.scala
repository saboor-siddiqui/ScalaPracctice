package org.saboor.scalapractice
package Codes

import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object PatternMatching extends App{

  val anInteger = 55
  val order = anInteger match{
    case 1 => "First"
    case 2 => "Second"
    case 3 => "Third"
    case _ => anInteger + "th"

  }
}