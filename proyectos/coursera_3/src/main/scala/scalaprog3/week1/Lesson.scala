package scalaprog3.weekl

object Lesson_3_1 {
    
    println("Curso 3, Week 1")

    class HelloThread(val x: Int) extends Thread{
        override def run(){
            println("Hello " + x)
            println("World! " + x)
        }
    }

    def test1(){
        val t = new HelloThread(1)
        t.start()
        t.join()    
    }
    
    def test2(){
        val t1 = new HelloThread(1)
        val t2 = new HelloThread(2)
        t1.start()
        t2.start()
        t1.join()
        t2.join()
    }

    def test3(){

        var uidCount:Long = 0
        def getUniqueId(): Long = {
            uidCount += 1
            uidCount
        }
        def startThread() = {
            val t = new Thread {
                override def run(){
                    val uids = for (i <- 0 until 10) yield getUniqueId()
                    println(uids)
                }
            }
            t.start()
            t
        }
    

        startThread()
        startThread()
    }
    // Surgen repeticiones de números.

    // Esta versión incorpora un monitor para evitar accesos simultáneos.
    def test4(){

        var uidCount:Long = 0
        val x = new AnyRef{}
        def getUniqueId(): Long = x.synchronized {
            uidCount += 1
            uidCount
        }
        def startThread() = {
            val t = new Thread {
                override def run(){
                    val uids = for (i <- 0 until 10) yield getUniqueId()
                    println(uids)
                }
            }
            t.start()
            t
        }
    

        startThread()
        startThread()
    }

    def test5(){
        class Account(private var amount: Int = 0){
            def transfer(target: Account, n:Int) =
                this.synchronized {
                    target.synchronized {
                        this.amount -= n
                        target.amount += n    
                    }
                }    
        }

        def startThread(a: Account, b: Account, n: Int)={
            var t = new Thread{
                override def run(){
                    for (i <- 0 until n){
                        a.transfer(b,1)
                    }
                }
            }
            t.start()
            t
        }
        val a1 = new Account(500000)
        val a2 = new Account(700000)
        val t1 = startThread(a1,a2,150000)
        val t2 = startThread(a2,a1,150000)
        t1.join()
        t2.join()
    }

    def test6(){
        var uidCount:Long = 0
        val x = new AnyRef{}
        def getUniqueId(): Long = x.synchronized {
            uidCount += 1
            uidCount
        }


        class Account(private var amount: Int = 0){
            
            private val uid = getUniqueId()
            private def lockAndTransfer(target:Account, n:Int) = {
                this.synchronized {
                    target.synchronized {
                        this.amount -= n
                        target.amount += n    
                    }
                }    

            }
            
            def transfer(target: Account, n:Int) =
                if (this.uid < target.uid) this.lockAndTransfer(target, n)
                else target.lockAndTransfer(this, -n)
        }

        def startThread(a: Account, b: Account, n: Int)={
            var t = new Thread{
                override def run(){
                    for (i <- 0 until n){
                        a.transfer(b,1)
                    }
                }
            }
            t.start()
            t
        }
        val a1 = new Account(500000)
        val a2 = new Account(700000)
        val t1 = startThread(a1,a2,150000)
        val t2 = startThread(a2,a1,150000)
        t1.join()
        t2.join()
    }

    def test7(){
        def sumSegment(a: Array[Double], p: Double, s:Int, t:Int):Double = {
            var sum:Double = 0
            for (i <- s until t){
                sum += math.pow(a(i),p)
            }
            sum
        }
        def pNorm(a: Array[Double], p:Double): Double =
            math.pow(sumSegment(a,p,0,a.length), 1/p)
        
        def pNormTwoParts(a: Array[Double], p:Double): Double = {
            val m = a.length / 2
            val sum1 = sumSegment(a,p,0,m)
            val sum2 = sumSegment(a,p,m,a.length)
            math.pow(sum1+sum2, 1/p)
        }

        val x = Array(1.0, 3.0, -2.0, 5.2)
        println("2-norm: "+ pNorm(x,2))
        println("2-norm two parts: "+ pNormTwoParts(x,2))
        // Variante paralela recursiva.
        //def parallel[A,B](taskA: => A, taskB: => B): (A,B) = ???
        def parallel[A,B](taskA: => A, taskB: => B): (A,B) = (taskA,taskB)
        
        val threshold = 2
        def segmentRec(a:Array[Double], p:Double, s: Int, t: Int): Double = {
            if (t-s < threshold)
                sumSegment(a,p,s,t) // Small segment, do it sequentially
            else {
                val m = (s+t)/2
                val (sum1, sum2) = parallel(segmentRec(a,p,s,m), segmentRec(a,p,m,t))
                sum1 + sum2
            }
        } 
        def pNormRec(a: Array[Double], p:Double): Double =
            math.pow(segmentRec(a,p,0,a.length), 1/p)
        
        println("2-norm Recursive: "+ pNormRec(x,2))

    }

    def test8(){
        import scala.util.Random
        def mcCount(iter:Int):Int = {
            val random = new Random
            var hits = 0
            for (i <- 0 until iter){
                val (x,y) = (random.nextDouble, random.nextDouble)
                if (x*x + y*y < 1 ) hits += 1 
            }
            hits
        
        }
        def monteCarloPiSeq(iter:Int): Double = 4.0 *mcCount(iter) / iter
        
        println("Pi vale estimado " + monteCarloPiSeq(10000))

        def parallel[A,B](taskA: => A, taskB: => B): (A,B) = (taskA,taskB)
        def monteCarloPiPar(iter:Int): Double = {
            val frac = iter/4
            val ((sum1, sum2), (sum3, sum4)) = parallel(parallel(mcCount(frac),mcCount(frac)), 
                parallel(mcCount(frac),mcCount(iter - 3*frac)))
            4.0*(sum1+sum2+sum3+sum4)/iter
        }
        println("Pi vale estimado (pseudo par) " + monteCarloPiPar(10000))
    }

    def test9() = {
        
        trait Task[A] {
            def join:A
        }

        class BlockTask[A](task: => A) extends Task[A] {
            private var value:A = task
            def join:A = value
        }

        // No anda, se cuelga...
        class ThreadTask[A] (task: => A) extends Task[A]  {
            private var value:A = _
            val me = this
            private val t = new Thread {
                override def run(){
                    me.value = task // Ver si esto se ejecuta...
                }
            }
            //def start = this.t.start()
            t.start()
            def join:A = {
                this.t.join
                value
            }
        }

        import scala.util.Random
        def mcCount(iter:Int):Int = {
            val random = new Random
            var hits = 0
            for (i <- 0 until iter){
                val (x,y) = (random.nextDouble, random.nextDouble)
                if (x*x + y*y < 1 ) hits += 1 
            }
            hits        
        }
        def monteCarloPiPar2(iter:Int): Double = {
            val frac = iter/4
            //val t1 = new ThreadTask(mcCount(frac))
            //val t2 = new ThreadTask(mcCount(frac))
            //val t3 = new ThreadTask(mcCount(frac))
            //val t4 = new ThreadTask(mcCount(iter - 3*frac))
            val t1 = new BlockTask(mcCount(frac))
            val t2 = new BlockTask(mcCount(frac))
            val t3 = new BlockTask(mcCount(frac))
            val t4 = new BlockTask(mcCount(iter - 3*frac))

            4.0*(t1.join+t2.join+t3.join+t4.join)/iter
        }
        println("Pi vale estimado (task par) " + monteCarloPiPar2(10000))

        
    }

    //test1
    //test2
    //test3
    //test4
    //test5() // Never completes (deadlock!!
    //test6()
    //test7()
    //test8()
    test9()
}

