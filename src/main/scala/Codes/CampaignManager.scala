package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

import scala.io.Source

object CampaignManager extends App {

    Logger.getLogger("org").setLevel(Level.ERROR)
  def loadBoringOwrds() : Set[String] = {
    var boringWords : Set[String] = Set()
    val lines = Source.fromFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//boring_words.txt").getLines()
    for(line <- lines){
      boringWords += line
    }
    boringWords
  }
    val spark = SparkSession.builder.master("local[*]").appName("CampaignManager").getOrCreate()
    val sc = spark.sparkContext
    val nameSet = sc.broadcast(loadBoringOwrds())
    import spark.implicits._
    import spark.sql
    val input = sc.textFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//bigdatacampaigndata-201014-183159.csv")
    val initial_rdd = input.map(x =>(x.split(",")(10).toFloat,x.split(",")(0)))
    initial_rdd.collect()
    val res = initial_rdd.flatMapValues(x => x.split(" "))
    var rev_res = res.map(x => (x._2.toLowerCase(),x._1))
    val rev_res_filtered = rev_res.filter(x => !nameSet.value(x._1))
    val final_res = rev_res_filtered.reduceByKey((x,y) => (x+y)).sortBy(x => x._2,false)
    final_res.collect().foreach(println)
    scala.io.StdIn.readLine()

}