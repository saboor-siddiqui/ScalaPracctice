package org.saboor.scalapractice
package Codes

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object QueryPlanExplainer extends  App {
  Logger.getLogger("org").setLevel(Level.OFF)
  val spark = SparkSession.builder.master("local[*]").appName("QueryPlanExplainer").getOrCreate()
  val sc = spark.sparkContext
  import spark.implicits._
    import spark.sql
  val df_1 = spark.range(1,1000000)
  val df_2 = spark.range(1,1000000,2)
  // Performing Repartition on dataframes
  val df_3 = df_1.repartition(7)
  val df_4 = df_2.repartition(9)
  //Performing multiplication to each row of dataframe df_3
  val df_5 = df_3.selectExpr("(id * 5) as id")
  // Performing inner join on dataframes df_4 and df_5
  val joined = df_5.join(df_4,"id")
  // Performing Aggregation
  val sum = joined.selectExpr("sum(id)")
  sum.explain()
  scala.io.StdIn.readLine()
}