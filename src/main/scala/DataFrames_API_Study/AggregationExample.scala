package org.saboor.scalapractice
package DataFrames_API_Study

import org.apache.log4j.{Logger,Level}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object AggregationExample extends App{
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("AggregationExample").getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._
    import spark.sql

  var invoice_df = spark.read
    .format("csv")
    .option("header",true)
    .option("inferSchema",true)
    .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//order_data-201025-223502.csv")
    .load()

//   invoice_df.select(
//     count("*").as("RowCount"),
//     sum("Quantity").as("TotalQuantity"),
//     avg("UnitPrice").as("AvgPrice")
//   ).show()

  invoice_df.selectExpr(
    "count(StockCode) as RowCount",
    "sum(Quantity) as TotalQuantity",
    "avg(UnitPrice) as AvgPrice "
    ).show(2,false)

    scala.io.StdIn.readLine()
    spark.stop()

}