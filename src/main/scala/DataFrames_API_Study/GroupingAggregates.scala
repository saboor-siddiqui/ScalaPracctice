package org.saboor.scalapractice
package DataFrames_API_Study

import org.apache.spark.sql._
import org.apache.log4j.{Level, Logger}

object GroupingAggregates extends App {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("GroupingAggregates").getOrCreate()
    var sc = spark.sparkContext

    import spark.implicits._
    import spark.sql

    var orders_df = spark.read
       .format("csv")
       .option("header",true)
       .option("inferSchema",true)
       .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//order_data-201025-223502.csv")
      .load()
    var customers_df = spark.read
        .format("csv")
        .option("header",true)
        .option("inferSchema",true)
        .option("path","C:/Users/sabbi/OneDrive/Desktop/Shared Folder//customers-201025-223502.csv")
        .load()

    val joinCondition = orders_df.col("customerID") === customers_df.col("customer_id")
    val joinType = "outer"
    val joined_df = orders_df.join(customers_df,joinCondition,joinType).where("customer_id is not null").sort("customer_id")

    joined_df show(10,false)

    scala.io.StdIn.readLine()
    spark.stop()
}