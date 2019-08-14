package scalaprog2.week3

import scala.annotation.tailrec

object Lesson extends App {
  
  // State
  var x: String = "abc"
  var count = 111
  count = count + 1
  x = "hi"
  
  class BankAccount {
    private var balance = 0
    def deposit(amount: Int):Unit = 
      if (amount > 0 ) balance = balance + amount
    def withdraw(amount:Int):Int = 
      if (0 < amount && amount <= balance) {
        balance = balance - amount
        balance
      } else throw new Error("Insufficient funds")
  }
  
  val account = new BankAccount
  account deposit 100
  val balance = account withdraw 30
  println("Dinero remanente: " + balance)
  
  // Other implementation of Streams.

  /*
  // No se puede, Stream es Sealed.
  def cons[T](hd: T, tl: => Stream[T]) = new Stream[T]{
    def head = hd
    private var tlOpt: Option[Stream[T]] = None
    def tail: T = tlOpt match {
      case Some(x) => x
      case None => tlOpt = Some(tl); tail
    }
  }
  */
  val a1 = new BankAccount
  val a2 = new BankAccount
  println("a1 == a2 " + (a1==a2))
  
  val a3 = a1
  println("a1 == a3 " + (a1==a3))
  
  // Loops
  
  // while.
  def power(x:Double, exp:Int):Double = {
    var r = 1.0
    var i = exp
    while (i>0) {
      r = r*x
      i = i-1
    }
    r
  }
  
  println("2 al cubo: " + power(2,3))
  
  @tailrec
  def WHILE(condition : => Boolean)(command: => Unit): Unit = 
    if (condition){
      command
      WHILE(condition)(command)
    } else ()
   
  // Se debe pasar por name (=>) para que no se evalúe una vez y sea siempre lo mismo.

  // este al menos ejecuta una vez el command, es un REPEAT UNTIL.
  @tailrec
  def REPEAT(condition : => Boolean)(command: => Unit): Unit = {
    command
    if (condition) () else REPEAT(condition)(command)
  }

  // for loops.
  for (i <- 1 to 3) {
    print(i + " ")
  }
  println("")
  
  (1 to 5) foreach (i => print(i + " "))
  println("")
  
}