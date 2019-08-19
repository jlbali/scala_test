package sparkTests

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SQLContext


object Test3 {
    println("Pruebas con DataFrames")

    def test1 = {
        
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        
        import sqlContext.implicits._ // Esto es necesario para el toDF.
        import scala.util.Random

        val rdd = sc.parallelize(1 to 10).map(x => (x, x + Random.nextGaussian()))
        println("Items en RDD: " + (rdd.collect().mkString(",")))
        // Conversion a DataFrame
        val df = rdd.toDF("key","value")
        df.printSchema()
        df.show()
        
        sc.stop()
    }

    def test2 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        
        import sqlContext.implicits._ // Esto es necesario para el toDF.
        import org.apache.spark.sql.Row
        import org.apache.spark.sql.types._

        val schema = StructType(Array(
            StructField("id", LongType, true),
            StructField("name", StringType, true),
            StructField("age", LongType, true)
        ))

        val peopleRDD = sc.parallelize(Array(Row(1L, "John Doe",  30L),
            Row(2L, "Mary Jane", 25L)
        ))
        
        val peopleDF = sqlContext.createDataFrame(peopleRDD, schema)
        
        peopleDF.printSchema()
        peopleDF.show()

        sc.stop()
    }

    def test3 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        
        import sqlContext.implicits._ // Esto es necesario para el toDF.

        val df1 = sqlContext.range(5).toDF("num")
        df1.printSchema
        df1.show
        
        val df2 = sqlContext.range(10,15).toDF("num")
        df2.printSchema
        df2.show
        
        sc.stop()
    }

    def test4 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        
        import sqlContext.implicits._ // Esto es necesario para el toDF.

        val movies = Seq(("Damon, Matt", "The Bourne Ultimatum", 2007L),
                 ("Damon, Matt", "Good Will Hunting", 1997L))
        val moviesDF = movies.toDF("actor", "title", "year")
        moviesDF.printSchema
        moviesDF.show

        sc.stop()
    }

    def test5 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        import sqlContext.implicits._ // Esto es necesario para el toDF.

        val path = "../data/example.txt"
        val textFile = sqlContext.read.text(path)
        textFile.printSchema()
        textFile.show

        sc.stop()

    }

    def test6 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        import sqlContext.implicits._ // Esto es necesario para el toDF.

        val path = "../data/movies.csv"
        //val movies = sqlContext.read.option("header","true").csv(path)
        val movies = sqlContext.read.option("header","true").option("inferSchema", "true").csv(path)
        movies.printSchema()
        movies.show

        sc.stop()

    }

    def test7 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        import sqlContext.implicits._ // Esto es necesario para el toDF.
        import org.apache.spark.sql.types._

        val path = "../data/movies.csv"
        val movieSchema = StructType(Array(StructField("actor_name", StringType, true),
            StructField("movie_title", StringType, true),
            StructField("produced_year", LongType, true)
        ))
        val movies = sqlContext.read.option("header","true").schema(movieSchema).csv(path)
        movies.printSchema
        movies.show
        
        sc.stop()

    }

    def test8 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        import sqlContext.implicits._ // Esto es necesario para el toDF.
        import org.apache.spark.sql.types._

        val path = "../data/movies.json"
        val movies = sqlContext.read.json(path)
        movies.printSchema()
        movies.show()

        sc.stop()
    }

    // Idem anterior pero especificando el schema.
    def test9 = {
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)
        import sqlContext.implicits._ // Esto es necesario para el toDF.
        import org.apache.spark.sql.types._

        val path = "../data/movies.json"
        val movieSchema = StructType(Array(StructField("actor_name", StringType, true),
            StructField("movie_title", StringType, true),
            StructField("produced_year", LongType, true)
        ))
        val movies = sqlContext.read.option("header","true").schema(movieSchema).json(path)
        movies.printSchema
        movies.show
        
        sc.stop()

    }



    //test1
    //test2
    //test3
    //test4
    //test5
    //test6
    //test7
    //test8
    test9
}