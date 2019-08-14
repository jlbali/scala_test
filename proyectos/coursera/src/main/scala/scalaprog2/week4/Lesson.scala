package scalaprog2.week4

object Lesson extends App {
  
  println("The Observer Pattern")
  
  trait Publisher {
    private var subscribers: Set[Subscriber] = Set()
    def subscribe(subscriber: Subscriber): Unit =
      subscribers += subscriber
    def unsubscribe(subscriber: Subscriber): Unit =
      subscribers -= subscriber
    def publish(): Unit =
      subscribers foreach (_.handler(this))
  }
  
  trait Subscriber {
    def handler(pub: Publisher)
  }
  
  // Example.
  class BankAccount extends Publisher {
    private var balance = 0
    def currentBalance = balance
    def deposit(amount: Int):Unit = 
      if (amount > 0 ) {
        balance = balance + amount
        publish()
      }
    def withdraw(amount:Int):Unit = 
      if (0 < amount && amount <= balance) {
        balance = balance - amount
        publish()
      } else throw new Error("Insufficient funds")
  
  }
  
  class Consolidator(observed: List[BankAccount]) extends Subscriber {
    observed foreach (_.subscribe(this))
    private var total: Int = _ // uninitialized
    compute()
    private def compute() =
      total = observed.map(_.currentBalance).sum
    def handler(pub: Publisher) = compute()
    def totalBalance = total
  }
 
  val a1 = new BankAccount
  val a2 = new BankAccount
  val c = new Consolidator(List(a1,a2))
  println("Total Balance: " + c.totalBalance)
  a1 deposit 50
  a2 deposit 30
  println("Total Balance: " + c.totalBalance)
  a2 withdraw 20
  println("Total Balance: " + c.totalBalance) 
  
  
}