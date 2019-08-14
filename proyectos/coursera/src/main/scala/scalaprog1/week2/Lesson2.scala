package scalaprog1.week2

import scala.annotation.tailrec

object Lesson2 extends App {
  
  class Rational(x: Int, y: Int) {
    
    require(y>0, "denominator must be positive")
    
    def this(x: Int) = this(x,1)
    
    @tailrec
    private def gcd(a:Int, b:Int):Int= if (b==0) a else gcd(b, a%b)
    private val g = gcd(x,y) 
    
    val numer = x / g
    val denom = y / g
    
    def add(that:Rational) =
      new Rational(
          numer*that.denom + denom*that.numer,
          denom*that.denom)
    
    //def neg:Rational = new Rational(-numer, denom)
    
    //def sub(that:Rational):Rational = add(that.neg)
    
    // Unary operator por "neg".
    def unary_- = new Rational(-numer, denom)
    
    def - (that:Rational):Rational = this + (-that)
    
    // No toma en cuenta signos, ojo.. Se puede suponer que el denominador es positivo.
    def less(that:Rational):Boolean = numer*that.denom < that.numer*denom
    
    def < (that:Rational):Boolean = numer*that.denom < that.numer*denom
    
    //def max(that:Rational):Rational = if (this.less(that)) that else this
    def max(that:Rational):Rational = if (this < that) that else this
    
    def +(that:Rational):Rational = this.add(that)
    
    override def toString = numer + "/" + denom
  }

  val r = new Rational(15,5)
  println("15/5: " + r)
  val r2 = r.add(r)
  println("Maximo entre r y r2: " + r.max(r2))
  val i = new Rational(4)
  println("Valor de i: " + i)
  println("r + i " + (r add i)) //infix notation
  println("r + r2 +i " + (r + r2 + i)) //infix notation
  println("r + r2 -i " + (r + r2 - i)) //infix notation
}