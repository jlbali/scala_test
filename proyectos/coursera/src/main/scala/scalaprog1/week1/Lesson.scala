// Notas (Scala IDE): crear folders para armar los paquetes, no usar new Package.



package scalaprog1.week1

import scala.annotation.tailrec

object Lesson extends App {
  println("Hello World!")
  
  def square(x: Double) = x*x
  
  println("2 al cuadrado " + square(2))

  def abs(x: Int) = if (x >= 0) x else -x
   
  println("Valor absoluto de -5: " + abs(-5))

  // def is by-name
  // val is by-value
  val x = 2
  val y = square(x)
  def loop:Boolean = loop // this is ok.
  
  def and(x: Boolean, y: Boolean) = if (x) y else false
  def or(x: Boolean, y: Boolean) = if (x) true else y
  
  // Call by name, 
  def and2(x: Boolean, y: => Boolean) = if (x) y else false
  println("test: " + and2(false, loop))
  
  // Square roots with Newton's Method.
  
  def sqrtIter(guess: Double, x:Double): Double =
    if (isGoodEnough(guess,x)) guess
    else sqrtIter(improve(guess,x), x)
  
  def absDouble(x: Double) = if (x >= 0) x else -x
  
  /*
  def isGoodEnough(guess: Double, x:Double) =
    absDouble(guess*guess - x) < 0.001
  */
  def isGoodEnough(guess: Double, x: Double) =
    absDouble(guess*guess - x)/x < 0.001
    
  def improve(guess: Double, x: Double) =
    (guess + x/guess)/2
  
  def sqrt(x: Double) = sqrtIter(1.0, x)
  
  println("Sqrt of 2: " + sqrt(2))
  println("sqrt of 1e-6: " + sqrt(1e-6)) // En este caso anda medio mal.
  // Puede haber non-termination con valores grandes.
  // Mejora con el arreglo relativo en isGoodEnough
  
  // Blocks
  
  def sqrt2(x: Double) = {
    def sqrtIter(guess: Double, x:Double): Double =
      if (isGoodEnough(guess,x)) guess
      else sqrtIter(improve(guess,x), x)
    
    def absDouble(x: Double) = if (x >= 0) x else -x
    
    def isGoodEnough(guess: Double, x: Double) =
      absDouble(guess*guess - x)/x < 0.001
      
    def improve(guess: Double, x: Double) =
      (guess + x/guess)/2
    
    sqrtIter(1.0, x)
  }
  
  println("Sqrt of 16: " + sqrt2(16))
  
  // Lexical scoping.
  
  def sqrt3(x: Double) = {
    def sqrtIter(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))
    
    def absDouble(x: Double) = if (x >= 0) x else -x
    
    def isGoodEnough(guess: Double) =
      absDouble(guess*guess - x)/x < 0.001
      
    def improve(guess: Double) =
      (guess + x/guess)/2
    
    sqrtIter(1.0)
  }
  
  println("Sqrt of 20: " + sqrt3(20))
  
  // Tail Recursion.
  
  @tailrec
  def gcd(a: Int, b: Int) : Int =
    if (b == 0) a else gcd(b, a % b)
  
  println("GCD of 20 and 12: " + gcd(20,12))

  // Not tail-recursive (stack space increases)
  //@tailrec // No se puede aplicar esto a esta función.
  def factorial(n: Int) : Int =
    if (n == 0) 1 else n * factorial(n-1)
  
  println("5!: " + factorial(5))
  
  def factorialRec(n: Int): Int = {
    @tailrec
    def loop(acc: Int, n: Int): Int =
      if (n == 0) acc else loop(acc*n, n-1)
    loop(1,n)
  }

  println("6!: " + factorial(6))
  
  
  
}


