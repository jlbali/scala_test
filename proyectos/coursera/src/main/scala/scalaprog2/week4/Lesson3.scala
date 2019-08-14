package scalaprog2.week4

import scala.util.{Try, Success, Failure}



object Lesson3 extends App {
  // Basado en Hortsmann "Scala for the Impatient".
  // Capítulo de Futures.
  // Leer también del libro de Odersky.
  
  def test1 = {
    import java.time._
    import scala.concurrent._
    import ExecutionContext.Implicits.global

    Future {
      Thread.sleep(5000)
      println("This is the future at " + LocalTime.now)
    }
    println("This is the present at " + LocalTime.now)    
    Thread.sleep(10000) // Sin esto no se muestra lo de arriba pues termina el programa.
  }
  
  def test2 = {
    //import java.time._
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    Future {
      for (i <- 1 to 20) {
        print("A")
        Thread.sleep(10)
      }
    }
    Future {
      for (i <- 1 to 20) {
        print("B")
        Thread.sleep(10)
      }
    }
    Thread.sleep(1000)
  }

  def test3 = {
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    val f = Future {
      Thread.sleep(2500)
      42
    }
    println("f vale " + f)
    Thread.sleep(5000)
    println("f vale ahora " + f)
  }
  
  def test4 = {
    import scala.concurrent._
    import scala.concurrent.duration._
    import ExecutionContext.Implicits.global
    val f = Future {
      Thread.sleep(5000)
      42
    }
    val result = Await.result(f, 10.seconds)
    println("Resultado: " + result)
  }
  
  def test5 = {
    import scala.concurrent._
    import scala.concurrent.duration._
    import ExecutionContext.Implicits.global
    val f = Future {
      Thread.sleep(5000)
      42
    }
    Await.ready(f, 10.seconds)
    val Some(t) = f.value
    println("Resultado: " + t)
    //val Success(res) = t
    //println("Resultado descompuesto: " + res)
  }
  
  // Exceptions.
  def test6 = {
    val result = Try("344".toInt)
    result match {
      case Success(v) => println("El numero fue parseado")
      case Failure(ex) => println("Error: " + ex.getMessage)
    }
    val result2 = Try("34df4".toInt)
    result2 match {
      case Success(v) => println("El numero fue parseado")
      case Failure(ex) => println("Error: " + ex.getMessage)
    }
    
  }
  
  def test7 = {
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    import scala.util.Random
    
    val f = Future {
      Thread.sleep(2500)
      if (Random.nextFloat() < 0.5) throw new Exception("Horror!")
      else 42
    }
    f.onComplete {
      case Success(v) => println("The answer is " + v)
      case Failure(ex) => println(ex.getMessage) 
    }
    Thread.sleep(5000)

  }
  
  def test8= {
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    val future1 = Future {
      Thread.sleep(2500)
      42
    }
    val future2 = Future {
      Thread.sleep(3000)
      61
    }
    Thread.sleep(5000)
    val combined = for (n1 <- future1; n2 <- future2) yield n1 + n2
    println("Resultado: " + combined)
    // No está andando!!!
  }

  def test9= {
    import scala.concurrent._
    import ExecutionContext.Implicits.global
    val future1 = Future {
      Thread.sleep(2500)
      42
    }
    val future2 = Future {
      Thread.sleep(3000)
      61
    }
    //Thread.sleep(5000)
    // Necesita una libreria esto...
    //val combined = async { await(future1) + await(future2))}
    val combined = 45
    println("Resultado: " + combined)
    // No está andando!!!
  }
  
  
  test8
 
  
  // pagina 252 Horstmann, problemas con el Future y onComplete.
}