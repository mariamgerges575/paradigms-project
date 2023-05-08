import gameEngine.GameState

class InitialBoards() {
  val initCheckerState:()=>(GameState)=()=>(Array(
    Array(-1, 1,-1, 1,-1, 1,-1, 1),
    Array( 1,-1, 1,-1, 1,-1, 1,-1),
    Array(-1, 1,-1, 1,-1, 1,-1, 1),
    Array(-1,-1,-1,-1,-1,-1,-1,-1),
    Array(-1,-1,-1,-1,-1,-1,-1,-1),
    Array(0,-1, 0,-1, 0,-1, 0,-1),
    Array(-1, 0,-1, 0,-1, 0,-1, 0),
    Array(0,-1, 0,-1, 0,-1, 0,-1)),Some(1))

  val initSudokuState:()=>(GameState)=()=>(Array(
    Array(0,15,17,0,16, 0,0,11,0),
    Array(18,0,16,12,0, 15,0,0, 17),
    Array(0,14,0,0,0,0,0,0, 16),
    Array(0,12,14,13,15,0,0,0,0),
    Array(0,0,0,0,11, 12,0,0, 15),
    Array(15,13,0,0,0,0,0,17, 12),
    Array(0,0,0,11,0,0,19,16,0),
    Array(0,0,0,16,0,0,0,0,0),
    Array(11,16,19,15,17,13,0,0,0),
  ),None)

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
}