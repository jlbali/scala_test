package scalaprog1.week4

object Lesson extends App {
 
  // Peano numbers
  abstract class Nat {
    def isZero: Boolean
    def predecessor: Nat
    def successor = new Succ(this)
    def +(that:Nat):Nat
    def -(that:Nat):Nat
  }
  
  object Zero extends Nat {
    def isZero = true
    def predecessor = throw new Error("Zero.predecessor")
    def +(that: Nat) = that  
    def -(that: Nat) = 
      if (that.isZero) this
      else throw new Error("negative number")
  }
  
  class Succ(n: Nat) extends Nat {
    def isZero = false
    def predecessor = n
    def +(that: Nat) = new Succ(n + that)
    def -(that: Nat) = 
      if (that.isZero) this 
      else n - that.predecessor
  }

  // Repaso de Lista.
  trait List[T] {
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
  }
  
  // val en parameters de Clases los hace
  // directamente field parameters
  class Cons[T](val head:T, val tail: List[T]) extends List[T] {
    def isEmpty = false
    override def toString:String = head.toString + "::" + tail.toString
  }
  class Nil[T] extends List[T] {
    def isEmpty = true
    def head =  throw new NoSuchElementException("Nil.head")
    def tail = throw new NoSuchElementException("Nil.tail")
    override def toString:String = "Nil"
  }
  
  object List {
    def apply[T]() = new Nil
    def apply[T](x: T) = new Cons(x, new Nil)
    def apply[T](x1: T, x2: T): List[T] =
      new Cons(x1, new Cons(x2, new Nil))
  }
  val l = List(5,1)
  println("lista l: " + l)
  
  // Decomposition
  
  trait Expr {
    def isNumber: Boolean
    def isSum: Boolean
    def numValue: Int
    def leftOp: Expr
    def rightOp: Expr
  }
  class Number(n: Int) extends Expr {
    def isNumber: Boolean = true
    def isSum:Boolean = false
    def numValue: Int = n
    def leftOp: Expr = throw new Error("Number.leftOp")
    def rightOp: Expr = throw new Error("Number.rightOp")
  }
  class Sum(e1: Expr, e2: Expr) extends Expr {
    def isNumber:Boolean = false
    def isSum:Boolean = true
    def numValue: Int = throw new Error("Sum.numValue")
    def leftOp:Expr = e1
    def rightOp:Expr = e2
  }
  def eval(e: Expr): Int = {
    if (e.isNumber) e.numValue
    else if (e.isSum) eval(e.leftOp) + eval(e.rightOp)
    else throw new Error("Unknown expression for " + e)
  }
  // It does become tedious...
  // Solución "hacky" desprolija... pero sin classification methods.
  def eval2(e: Expr): Int =
    if (e.isInstanceOf[Number]) 
      e.asInstanceOf[Number].numValue // Type cast.
    else if (e.isInstanceOf[Sum])
      eval(e.asInstanceOf[Sum].leftOp) +
      eval(e.asInstanceOf[Sum].rightOp)
    else throw new Error("Unknown expression " + e)

  // Otra forma, mejor.
  trait Expr2 {
      def eval: Int
  }
  class Number2(n: Int) extends Expr2{
    def eval: Int = n
  }
  class Sum2(e1: Expr2, e2:Expr2) extends Expr2{
    def eval: Int = e1.eval + e2.eval
  }
  // Problema si se quiere hacer un "show" para 
  // ver expresiones. Debería estar en todos lados.
  // (no sería tan grave...).
  // Hay casos en donde el Decomposition no se aplica.
  // Por ejemplo, para simplificar expresiones, no es propio
  // de un solo nodo.
  
  // Pattern Matching.
  
  trait Expr3
  case class Number3(n: Int) extends Expr3
  case class Sum3(e1: Expr3, e2: Expr3) extends Expr3
  
  val n = Number3(12) // No se necesita el new, vienen con apply constructores.
  println("n vale: " +n) // Un toString ya hecho.
  
  def eval3(e: Expr3): Int = e match {
    case Number3(n) => n
    case Sum3(e1, e2) => eval3(e1) + eval3(e2)
  }
  // Podría estar definido dentro del Trait Expr3.
  
  
  println("Evaluando : " + eval3(Sum3(Number3(10), Number3(2))))
  
  def show3(e: Expr3): String = e match {
    case Number3(n) => n.toString()
    case Sum3(l,r) => show3(l) + " + " + show3(r)
  }
  
  println("Examinando : " + show3(Sum3(Number3(10), Number3(2))))
 
  
  
}

