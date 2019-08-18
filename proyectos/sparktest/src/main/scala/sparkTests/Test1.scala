package sparkTests


object Test1 {
    println("Corriendo Test1")
    
    def test1 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Spark importado")
    }
    
    def test2 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creando context")
        val conf = new SparkConf().setMaster("local").setAppName("Prueba")
        val sc = new SparkContext(conf)
        sc.stop()
        // Tira excepciones, pero anda...
        // Con el stop no tira más excepciones, pero se debe después resetear VS Code.
    }
    
    def test3 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creando Context")
        val conf = new SparkConf().setMaster("local").setAppName("Prueba")
        val sc = new SparkContext(conf)
        //val lines = sc.parallelize(List("pandas", "I like pandas"))
        val lines = sc.textFile("/home/lbali/proyectos/Gits/scala_test/proyectos/data/example.txt")
        val pandas = lines filter(line => line.contains("pandas"))
        println("++++++ RDD inicial: " + lines)
        println("++++++ RDD de pandas: " + pandas)
        //println("++++++ Lineas lineas con 'pandas': " + (pandas count))
        println("++++++ Panda 1: " + (pandas.take(1))) // Se cuelga....
        println("++++++ Lineas lineas con 'pandas': " + pandas.count()) // Esto también.
        sc.stop()

    }

    def test4 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creando Context")
        val conf = new SparkConf().setMaster("local").setAppName("Prueba")
        val sc = new SparkContext(conf)
        //val lines = sc.parallelize(List("pandas", "I like pandas"))
        val lines = sc.textFile("/home/lbali/proyectos/Gits/scala_test/proyectos/data/example.txt")
        val contador = lines map(line => line.length())
        contador.collect().foreach(println)

        sc.stop()

    }



    //test1
    //test2
    //test3 // Se cuelga...
    test4  // Se cuelga...
}

