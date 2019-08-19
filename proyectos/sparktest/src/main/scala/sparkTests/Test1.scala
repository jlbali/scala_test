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
        //val conf = new SparkConf().setAppName("Prueba")
        val sc = new SparkContext(conf)
        //val lines = sc.parallelize(List("pandas", "I like pandas"))
        val lines = sc.textFile("/home/lbali/proyectos/Gits/scala_test/proyectos/data/example.txt")
        val contador = lines map(line => line.length())
        contador.collect().foreach(println)
        sc.stop()

    }

    def test5 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val lines = sc.textFile("/home/lbali/proyectos/Gits/scala_test/proyectos/data/example.txt")
        println("+++++ number of lines: " + lines.count())
        sc.stop()
    }


    def test6 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val lines = sc.textFile("/home/lbali/proyectos/Gits/scala_test/proyectos/data/example.txt")
        val pandas = lines filter(line => line.contains("pandas"))
        println("+++++ number of lines: " + lines.count())
        println("+++++ number of lines with pandas: " + pandas.count()) // This does not work
        sc.stop()
    }

    def test7 = {
        import org.apache.spark.{SparkConf, SparkContext}
        import org.apache.spark.SparkContext._
        println("Creating Context")
        val conf = new SparkConf().setMaster("local").setAppName("Test")
        val sc = new SparkContext(conf)
        val numbers = sc.parallelize(List(1,2,3,4))
        println("Numeros originales: " + (numbers.collect().mkString(",")))
        val squared = numbers map (x => x*x)
        println("Numeros al cuadrado: " + (squared.collect().mkString(","))) // Este no anda.
        sc.stop()
    }


    //test1
    //test2
    //test3 // Se cuelga...
    //test4  // Se cuelga...
    //test5  // Anda!
    //test6 // Se cuelga.
    test7 //Tampoco anda.

}

