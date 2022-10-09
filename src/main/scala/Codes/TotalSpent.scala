package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object TotalSpent {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("TotalSpent").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql

    val input = sc.textFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//customerorders-201008-180523.csv")
    val res = input.map(x=>(x.split(",")(0),x.split(",")(2).toFloat))
    val final_res = res.reduceByKey((x,y) => (x+y)).sortBy(x=>x._2,false)
    final_res.collect().foreach(println)

    scala.io.StdIn.readLine()
  }
}