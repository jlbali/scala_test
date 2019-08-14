package scalaprog2.week3

object DiscreteEventSimulator extends App {
  
  type Action = () => Unit
  
  trait Simulation {
    def currentTime: Int = ???
    def afterDelay(delay: Int)(block: => Unit): Unit = ???
    def run(): Unit = ???
  }
  
  def afterDelay(delay:Int)(block: =>Unit)  = ???
  val InverterDelay = ???
  val AndGateDelay = ???
  val OrGateDelay = ???
  
  class Wire {
    private var sigVal = false
    private var actions: List[Action] = List()
    def getSignal: Boolean = sigVal
    def setSignal(s: Boolean): Unit =
      if (s != sigVal) {
        sigVal = s
        actions foreach (_())
      }
    def addAction(a: Action): Unit = {
      actions = a :: actions
      a()
    }
  }
  
  def inverter(input: Wire, output: Wire): Unit = {
    def invertAction(): Unit = {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }

  def andGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def andAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(AndGateDelay){
        output setSignal (in1Sig & in2Sig)
      }
    }
    in1 addAction andAction
    in2 addAction andAction
  }

  def orGate(in1: Wire, in2: Wire, output: Wire): Unit = {
    def orAction(): Unit = {
      val in1Sig = in1.getSignal
      val in2Sig = in2.getSignal
      afterDelay(OrGateDelay){
        output setSignal (in1Sig | in2Sig)
      }
    }
    in1 addAction orAction
    in2 addAction orAction
  }
  // Incompleto
  
  
}