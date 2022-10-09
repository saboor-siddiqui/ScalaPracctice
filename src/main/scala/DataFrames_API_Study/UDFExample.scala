package org.saboor.scalapractice
package DataFrames_API_Study

import org.apache.log4j.{Level,Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object UDFExample extends App {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("UDFExample").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql
    var df = spark.read
      .format("csv")
      .option("header","true")
      .option("inferSchema","true")
      .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//-201025-223502.dataset1")
      .load()

    df.printSchema()
    df.show(false)

    scala.io.StdIn.readLine()
    spark.stop()
}