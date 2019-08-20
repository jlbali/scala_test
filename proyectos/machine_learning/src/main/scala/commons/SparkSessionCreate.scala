

package commons

import org.apache.spark.sql.SparkSession

object SparkSessionCreate {
    def createSession(): SparkSession = {
        val spark = SparkSession
            .builder
            .master("local")
            .appName("MySparkSession")
            .getOrCreate()
        return spark
    }
} 

