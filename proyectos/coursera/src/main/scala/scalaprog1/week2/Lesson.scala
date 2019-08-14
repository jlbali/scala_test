package scalaprog1.week2

import scala.annotation.tailrec

object Lesson extends App{
  
  // Higher-order functions.
  def sumInts(a: Int, b: Int): Int =
    if (a > b) 0 else a + sumInts(a+1,b)
  
  def cube(x: Int): Int = x*x*x

  def factorial(n: Int): Int = {
    @tailrec
    def loop(acc: Int, n: Int): Int =
      if (n == 0) acc else loop(acc*n, n-1)
    loop(1,n)
  }
 
  def id(x: Int) = x 
  
  def sumCubes(a:Int, b: Int) : Int =
    if (a > b) 0 else cube(a) + sumCubes(a+1,b)
  
  def sumFactorials(a: Int, b: Int): Int =
    if (a > b) 0 else factorial(a) + sumFactorials(a+1, b)
   
  
  def sum(f: Int => Int, a: Int, b: Int): Int = 
    if (a > b) 0
    else f(a) + sum(f,a+1,b)
  
 
  // Higher-order way...
  def sumInts2(a:Int, b: Int) = sum(id, a,b)
  def sumCubes2(a:Int, b:Int) = sum(cube, a,b)
  def sumFactorials2(a:Int, b:Int) = sum(factorial, a,b)
  
  // Another way... with no auxiliary functions
  def sumCubes3(a:Int, b: Int) = sum((x:Int) => x*x*x, a,b)
  println("SumCubes 4 a 10: " + sumCubes3(4, 10))
  
  // Tailrec version.
  
  def sum2(f:Int => Int, a: Int, b: Int) = {
    @tailrec
    def loop(a: Int, acc:Int): Int = {
      if (a > b) acc
      else loop(a+1, acc + f(a))
    }
    loop(a,0)
  }

  def sumCubes4(a:Int, b: Int) = sum2((x:Int) => x*x*x, a,b)
  println("SumCubes 4 a 10: " + sumCubes4(4, 10))
  def sumSquares(a: Int, b: Int) = sum2(x => x*x, a,b)
  println("SumSquares 4 a 10: " + sumSquares(4, 10))
  
  // Currying
  // This function is not optimized as a tail-recursive.
  def sumC(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int =
      if (a > b) 0
      else f(a) + sumF(a+1,b)
    sumF
  }
  def sumSquares2 = sumC(x => x*x)
  println("SumSquares 4 a 10: " + sumSquares2(4, 10))
  println("SumSquares 4 a 10: " + sumC(x=>x*x)(4, 10))
  // Syntactic sugar
  def sumC2(f: Int => Int)(a: Int, b:Int): Int =
    if (a > b) 0 else f(a) + sumC2(f)(a+1,b)
  println("SumSquares 4 a 10: " + sumC2(x=>x*x)(4, 10))
  
  def product(f: Int => Int)(a: Int, b: Int): Int = 
    if (a > b) 1 else f(a)*product(f)(a+1,b)
  println("Prod of squares 4 a 10: " + product(x=>x*x)(4,10))
  
  def factorial2(n: Int) = product(x=>x)(1,n)
  println("Factorial of 5: " + factorial2(5))
  
  def mapReduce(f: Int => Int, combine: (Int, Int) => Int, unitValue:Int)(a:Int, b:Int):Int =
    if (a > b) unitValue else combine(f(a), mapReduce(f,combine,unitValue)(a+1,b))
 
  def factorial3(n: Int) = mapReduce(x=>x, (x,y)=>x*y, 1)(1,n)  
  println("Factorial of 5: " + factorial3(5))
 
 
  // Example: Finding Fixed Points.
  def fixedPoint(f: Double => Double)(firstGuess:Double) = {
    val tolerance = 0.0001
    def abs(x:Double) = if(x >= 0) x else -x
    def isCloseEnough(x:Double, y:Double) =
      abs((x-y)/x) < tolerance
    @tailrec 
    def loop(guess: Double): Double = {
      val next = f(guess)
      if (isCloseEnough(guess, next)) next
      else loop(next)
    }
    loop(firstGuess)
  }
  println("Fixed point: " + fixedPoint(x => 1+x/2)(1))
  
  def sqrt(x:Double) = fixedPoint(y => x/y)(1)
  // Does not converge!
  //println("Square root of 9 " + sqrt(9))
  // Agregando println, se ve que oscila.
  
  // Average Damping.
  def sqrt2(x:Double) = fixedPoint(y => (y+x/y)/2)(1)
  println("Square root of 9 " + sqrt2(9)) // Ahi anda, medio rebuscado...
  
  // Más prolijo.
  def averageDamp(f: Double => Double)(x: Double) = (x+f(x))/2
  def sqrt3(x:Double) = fixedPoint(averageDamp(y => x/y))(1)
  println("Square root of 9 " + sqrt3(9))
  
  // Functions and Data
  
  class Rational(x: Int, y: Int) {
    def numer = x
    def denom = y
    
    def add(that:Rational) =
      new Rational(
          numer*that.denom + denom*that.numer,
          denom*that.denom)
    
    def neg:Rational = new Rational(-numer, denom)
    
    def sub(that:Rational):Rational = add(that.neg)
    
    override def toString = numer + "/" + denom
  }
  
  val r1 = new Rational(3,5)
  println("Numerador: " + r1.numer)
  val r2 = new Rational(4,9)
  println("Suma: " + (r1.add(r2)))
  
  
}