package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object MovieAnalyser extends App {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("MovieAnalyser").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql

  //GETTING RATINGS DATA
  val input = sc.textFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//ratings-201019-002101.dat")
  val mappedInout = input.map(x => {
    val fields = x.split("::")
    (fields(1),fields(2))
  })
  val finalMappedInput = mappedInout.mapValues(x => (x.toFloat,1.0))
  val reduceInput = finalMappedInput.reduceByKey((x,y) =>(x._1 + y._1,x._2+y._2)).filter(x => x._2._2 > 100)
  val ratingsProcessed = reduceInput.mapValues(x => x._1/x._2).filter(x => x._2 > 4.5)

  //GETTING MOVIES DATA
  val movies_input = sc.textFile("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//movies-201019-002101.dat")
  val mappedMoviesInput = movies_input.map(x => {
    val fields = x.split("::")
    (fields(0),fields(1))
  })
  //val broadcastedMoviesInput = broadcast(mappedMoviesInput)
  val finalResult = mappedMoviesInput.join(ratingsProcessed).map(x => (x._2._1))
  finalResult.collect().foreach(println)

  scala.io.StdIn.readLine()
}