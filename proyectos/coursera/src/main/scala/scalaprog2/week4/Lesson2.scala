package scalaprog2.week4

import scalaprog2.week4.frp._


object Lesson2 extends App {
  println("Functional Reactive Programming")
  
  class BankAccount {
    val balance = Var(0)
    def currentBalance = balance
    def deposit(amount: Int):Unit = 
      if (amount > 0 ) {
        val b = balance() // No se puede poner directamente balance(), es ciclo infinito
        balance() = b + amount
      }
    def withdraw(amount:Int):Unit = 
      if (0 < amount && amount <= balance()) {
        val b = balance()
        balance() = b - amount
      } else throw new Error("Insufficient funds")
  
  }
  
  def consolidated(accts: List[BankAccount]): Signal[Int] =
    Signal(accts.map(_.balance()).sum)
    
  val a1 = new BankAccount
  val a2 = new BankAccount
  val c = consolidated(List(a1,a2))
  println("Balance: " + c())
  a1 deposit 30
  a2 deposit 90
  println("Balance: " + c())
  val xchange = Signal(246.00)
  val inDollar = Signal(c()*xchange())
  println("Balance in USD: " + inDollar())
  
  
}