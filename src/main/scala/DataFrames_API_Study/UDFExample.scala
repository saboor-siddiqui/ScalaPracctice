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

  case class Person(name: String ,age: Int ,city: String)
    var df = spark.read
      .format("csv")
      .option("inferSchema","true")
      .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//-201025-223502.dataset1")
      .load()

    val df_1: Dataset[Row] = df.toDF("name","age","city")
    val ds_1 = df_1.as[Person]
    ds_1.printSchema()
    ds_1.show(false)
    scala.io.StdIn.readLine()
    spark.stop()
}