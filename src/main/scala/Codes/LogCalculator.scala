package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object LogCalculator extends App {

  Logger.getLogger("org").setLevel(Level.OFF)
  val spark = SparkSession.builder.master("local[*]").appName("LogCalculator").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql
    val myList = List("WARN: Tuesday 4 September 0405",
                      "Error: Tuesday 4 September 0408",
                      "Error: Tuesday 4 September 0408",
                      "Error: Tuesday 4 September 0408",
                      "Error: Tuesday 4 September 0408",
                      "Error: Tuesday 4 September 0408")
  var originallogsRdd = sc.parallelize(myList)
  var mappedRdd = originallogsRdd.map(x => {
    val columns = x.split(":")
    val logLevel = columns(0)
    (logLevel,1)
  })
  var resRdd = mappedRdd.reduceByKey(_+_)
  resRdd.collect().foreach(println)
  scala.io.StdIn.readLine()
}