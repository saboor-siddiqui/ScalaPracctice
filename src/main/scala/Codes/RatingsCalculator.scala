package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object RatingsCalculator {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("RatingsCalculator").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql
    val input = sc.textFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//moviedata-201008-180523.data")
    val mappedInput = input.map(x => x.split("\t")(2))
    val ratingsAsKey = mappedInput.map(x =>(x,1))
    val res = ratingsAsKey.reduceByKey((x,y) => (x+y)).collect().foreach(println)
    scala.io.StdIn.readLine()
  }
}