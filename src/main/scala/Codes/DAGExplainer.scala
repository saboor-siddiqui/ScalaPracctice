package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object DAGExplainer extends App {
  Logger.getLogger("org").setLevel(Level.OFF)
  val spark = SparkSession.builder.master("local[*]").appName("DAGExplainer").getOrCreate()
  val sc = spark.sparkContext
  import spark.implicits._
    import spark.sql
//  val nums = spark.range(1,1000000)
//  val times5 = nums.selectExpr("id * 5 as id")
//  times5.show(10,false)
//
//  val moreNums = spark.range(1,1000000,2)
//  val split7 = moreNums.repartition(7)
//  split7.take(2).foreach(println)

  val ds1 = spark.range(1,100000000)
  val ds2 = spark.range(1,100000000,2)
  val ds3 = ds1.repartition(7)
  val ds4 = ds2.repartition(9)
  val ds5 = ds3.selectExpr("id*5 as id")
  val joined = ds5.join(ds4,"id")
  val sum = joined.selectExpr("sum(id)")
  sum.show()
  scala.io.StdIn.readLine()
}