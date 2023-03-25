package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.saboor.scalapractice.DataFrames_API_Study.GroupingAggregates.spark

object MapTest extends App {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("MapTest").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
  import spark.sql

    val a = Array(("error",0),("warn",0))
    val keyMap = a.toMap
    println(keyMap)

  scala.io.StdIn.readLine()
  spark.stop()

}