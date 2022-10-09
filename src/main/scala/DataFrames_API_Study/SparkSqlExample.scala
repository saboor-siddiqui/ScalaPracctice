package org.saboor.scalapractice
package DataFrames_API_Study

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

import java.io.File

object SparkSqlExample extends  App {
  Logger.getLogger("org").setLevel(Level.ERROR)
  //Setting property for winutils path error
  val file = new File("src/main/resources/HadoopResources")
  System.setProperty("hadoop.home.dir", file.getAbsolutePath)

  val spark = SparkSession.builder.master("local[*]").appName("SparkSqlExample").enableHiveSupport().getOrCreate()
  val sc = spark.sparkContext
  import spark.implicits._
    import spark.sql
  var orders_df = spark.read
    .format("csv")
    .option("header","true")
    .option("inferSchema","true")
    .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//orders-201025-223502.csv")
    .load()
  orders_df.createOrReplaceTempView("orders")
  orders_df.printSchema()

  var result_df = spark.sql("select order_status,count(*) as status_count from orders group by order_status order by status_count desc")
  result_df.show(3,false)

  //Writing to custom made table in our own Database
  spark.sql("create database if not exists Retail")
  orders_df.write
    .format("csv")
    .mode(SaveMode.Overwrite)
    .bucketBy(4,"order_customer_id")
    .sortBy("order_customer_id")
    .saveAsTable("Retail.orders")
  //Listing all tables in database Retail
  spark.catalog.listTables("Retail").show()

  scala.io.StdIn.readLine()
  spark.stop()
}