
import scala.collection.mutable.Set
import scala.util.Random

  val initCheckerState:()=>(GameState)=()=>(Array(
    Array(-1, 1,-1, 1,-1, 1,-1, 1),
    Array( 1,-1, 1,-1, 1,-1, 1,-1),
    Array(-1, 1,-1, 1,-1, 1,-1, 1),
    Array(-1,-1,-1,-1,-1,-1,-1,-1),
    Array(-1,-1,-1,-1,-1,-1,-1,-1),
    Array(0,-1, 0,-1, 0,-1, 0,-1),
    Array(-1, 0,-1, 0,-1, 0,-1, 0),
    Array(0,-1, 0,-1, 0,-1, 0,-1)),Some(1))

  val initSudokuState:()=>(GameState)=()=>{
    def remove(a: Array[Array[Int]], count: Int) ={
      val rs = Random.shuffle(List.range(0, 81))
      for (i <- 0 until count)
        a(rs(i) / 9)(rs(i) % 9) = 0
    }
    def generate():Array[Array[Int]] ={
      val a:Array[Array[Int]]=Array.fill(9, 9)(0)

      val r = Array.fill(9)(Set[Int]())
      val c = Array.fill(9)(Set[Int]())
      val z = Array.fill(3, 3)(Set[Int]())

      for (x <- 0 to 8; y <- 0 to 8)
        if (a(x)(y) != 0)
          setExist(a(x)(y), x, y)

      def setExist(v: Int, x: Int, y: Int)= {
        r(x) += v
        c(y) += v
        z(x / 3)(y / 3) += v
      }

      def fill(x: Int, y: Int): Boolean = {
        if (a(x)(y) == 0) {
          val candidates = Set() ++ (1 to 9) -- r(x) -- c(y) -- z(x / 3)(y / 3)

          def current(): Boolean = {
            if (candidates.isEmpty)
              false
            else {
              val v = Random.shuffle(candidates.toList).iterator.next
              candidates -= v
              a(x)(y) = v
              setExist(v, x, y)
              val good = if (y < 8) fill(x, y + 1) else if (x < 8) fill(x + 1, 0) else true
              if (good)
                true
              else {
                a(x)(y) = 0
                r(x) -= v
                c(y) -= v
                z(x / 3)(y / 3) -= v
                current()
              }
            }
          }

          current()
        }
        else if (y < 8) fill(x, y + 1) else if (x < 8) fill(x + 1, 0) else true
      }

      fill(0, 0)
      a
    }
    val puzzle :Array[Array[Int]]=generate()
    // create a puzzle by remove a number of cells
    remove(puzzle, 60);
    for(i<-0 to 8) {
            for (j <- 0 to 8) {
              if(puzzle(i)(j)!=0) puzzle(i)(j)+=10
            }
    }

    (puzzle,None)
  }


  val initTTTBoard:()=>(GameState)=()=> (Array.fill(3,3)(' '),Some(1))

  val initConnect4Board:()=>(GameState)=()=> (Array.fill(6,7)(' '),Some(1))

  val init8QsBoard:()=>(GameState)=()=> (Array.fill(8,8)(' '),None)

  val initChessBoard:()=>(GameState)={()=>
    (Array.tabulate(8, 8)((i, j) => {
      (i, j) match {
        case (0, 0) => 'r'
        case (0, 1) => 'n'
        case (0, 2) => 'x'
        case (0, 3) => 'q'
        case (0, 4) => 'k'
        case (0, 5) => 'x'
        case (0, 6) => 'n'
        case (0, 7) => 'r'
        case (1, j) => 'p'
        case (6, j) => 'P'
        case (7, 0) => 'R'
        case (7, 1) => 'N'
        case (7, 2) => 'X'
        case (7, 3) => 'Q'
        case (7, 4) => 'K'
        case (7, 5) => 'X'
        case (7, 6) => 'N'
        case (7, 7) => 'R'
        case _ => ' '
      }
    }),Some(0))



}