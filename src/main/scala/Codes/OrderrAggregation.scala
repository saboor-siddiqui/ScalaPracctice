package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
object OrderrAggregation extends App {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder.master("local[*]").appName("OrderrAggregation").getOrCreate()
    val sc = spark.sparkContext

  var df = spark.read.option("inferSchema","true").option("header","true").csv("C:/Users/sabbi/OneDrive/Desktop/Shared Folder//orders_data.csv")
  df.createOrReplaceTempView("df_view")
  df.printSchema()
 var df_2 = spark.sql(""" Select
                          |order_customer_id,
                          |date_format(order_date,"MMMM") as order_dt,
                          |Cast(date_format(order_date,"M") as month_num as Integer),
                          |count(*) as count_of_orders
                          |from df_view
                          |group by order_customer_id , date_format(order_date,"MMMM")
                          |order by month_num """.stripMargin)
  df_2.show(false)
  scala.io.StdIn.readLine()

}