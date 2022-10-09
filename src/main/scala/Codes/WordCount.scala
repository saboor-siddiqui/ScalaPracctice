package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object WordCount {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("WordCount").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql
    val file = sc.textFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//search_data.txt")
    val rdd1 = file.flatMap(x => x.split(" "))
    val wordsLower = rdd1.map(_.toLowerCase())
    val rdd2 = wordsLower.map((_, 1))
    val final_output = rdd2.reduceByKey(_ + _)
    //val res = final_output.map(x => (x._2, x._1)).sortByKey(false).map(x => (x._2, x._1))
    val res = final_output.sortBy(x=>x._2,false)
    res.collect().foreach(println)
    for (result <- res) {
      if (result._2>30)
      println(s"${result._1} : ${result._2}")
    }
    scala.io.StdIn.readLine()
  }
}