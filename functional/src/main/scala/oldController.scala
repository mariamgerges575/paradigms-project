import java.awt.Color
import javax.swing.{JLabel, JOptionPane}

class Connect4Gamee() {
  ///////checkkkk the lengthhh bt3 el input arraaay awel m ndkhollllllll
  ////////////////fen el patternn matchinggggggggggggggg
  //IMMUTABLE DATA//
  type currentPlayer = Option[Int]
  type Board = Array[Array[Int]]
  type state = (Board, currentPlayer)

  val setBackgroundColors:(Color,Color)=>(Int,Int)=>Color= (c1,c2)=>(i, j)=> //this function takes row , coloumn and return the suitable back ground colour ,
    (i==0||j==0)match {
      case true=> new Color(255,255,255)          //literals
      case false =>
        (i+j) % 2 == 0 match {
          case true => c1
          case false => c2
        }
    }
  val sudokoBackgroundColors:(Int)=>(Color,Color)=>(Int,Int)=>Color= (cellNo)=>(c1,c2)=>(i, j)=> //this function takes row , coloumn and return the suitable back ground colour ,
    (i==0||j==0)match {
      case true=> new Color(255,255,255)          //literals
      case false =>
        val newI = (i-1)/cellNo
        val newJ = (j-1)/cellNo
        (newI+newJ) % 2 == 0  match {
          case true => c1
          case false => c2
        }
    }
  val sudokuColours=sudokoBackgroundColors(3)(new Color(237, 248, 241),new Color(129, 139, 159))

  val getSudokuAscii: Any => Any = (p) => p match { //btakhod int w t-return el ascii

    case '0' | 0 =>' '
    case s: Int =>
      if (s>19) (s).toChar else s%10



  }

  val isValidLength:(Int)=>(String)=>Boolean=length=>input=>input.length==length
  val isValidSudokoLength=isValidLength(4)
  def controllerUtil(checkValidMove: (state, Array[Int]) => Boolean, checkInputLength: (String => Boolean), applyMove: (state, Array[Int]) => state): (state, String)=> Option[state] = {
    (gameState, input) => {

      val parseInput: String => Array[Int] = input => {
        input.length match {
          case 1 => Array(input.charAt(0).toInt - 97)
          case 2 => Array(input.charAt(0).toInt - 49, input.charAt(1).toInt - 97)
          case 4 => Array(input.charAt(0).toInt - 49, input.charAt(1).toInt - 97, input.charAt(3).toInt - 49)
          case 5 => Array(input.charAt(0).toInt - 49, input.charAt(1).toInt - 97, input.charAt(3).toInt - 49, input.charAt(4).toInt - 97)
        }
      }

      def isCellBounds(arr: Array[Int]): Boolean = {
        val arr1=arr.clone()
        //        if (arr1.length ==3)
        //          arr1(2)-=1
        arr1.foreach(u => {
          println(u)
          if (u > gameState._1.length || u < 0) {

            println("henaa is cell in bounds")
            return false
          }
        })
        true
      }
      //controller implementationn
      checkInputLength(input) match {
        case false =>
          println("length 8lt")
          None


        case true =>
          val inputArr = parseInput(input)
          isCellBounds(inputArr) && checkValidMove(gameState, inputArr) match{
            case false=>
              println("boumds 8lt")
              None
            case true=> Some(applyMove(gameState,inputArr))
          }
      }
    }
  }

  def isValidsudoku(gameState: state,arrInput:Array[Int]):Boolean={
    val r:Int=arrInput(0)
    val c:Int=arrInput(1)
    val value:Int=arrInput(2)+1
    println("elvalue")
    println(value)
    println(r)
    println(c)
    println(gameState._1(r)(c))
    println(9.toChar)
    if(gameState._1(r)(c)>'9' && gameState._1(r)(c)!=' ') {
      println("awel if")
      return false} else if(gameState._1(r)(c)==value) return true

    for(i<-0 to 8) {
      for (j <- 0 to 8) {
        if((gameState._1(i)(j)==value || gameState._1(i)(j)%10==value)&& (i==r || j==c)) {
          println("gwa el nested lopp")
          return false
        }
      }
    }
    true
  }
  def applyMoveSudoku4( gameState:state,arrInput:Array[Int]):state ={
    val r:Int=arrInput(0)
    val c:Int=arrInput(1)
    val value:Int=arrInput(2)
    val newState: state = (gameState._1.clone(), switchPlayers(gameState._2))
    if(gameState._1(r)(c)==value+1) newState._1(r)(c)=0 else newState._1(r)(c)=value+1
    newState
  }
  def isValidMoveCheckers(gameState:state,arrInput:Array[Int]):Boolean = {
    //returns true if the piece belongs to the current player
    def isCurrentPlayer(piece:Int) :Boolean= (piece%2) == gameState._2.getOrElse(-1)

    val fromRow=arrInput(0)
    val fromCol=arrInput(1)
    val toRow=arrInput(2)
    val toCol=arrInput(3)
    val piece :Int = gameState._1(fromRow)(fromCol);
    // Check if the piece belongs to the current player
    if (! isCurrentPlayer(piece)) {
      println("not your piece!");
      return false;
    }
    // Check if the destination cell is not occupied
    if (gameState._1(toRow)(toCol) != -1) {
      println("not empty place");
      return false;
    }
    //get the horizontal and vertical offsets
    val vSteps=toRow-fromRow
    val hSteps=toCol-fromCol
    //check that the offsets are equal and <=2
    if (hSteps.abs >2  || vSteps.abs >2  || hSteps.abs !=vSteps.abs){ //msh 3rfa dy and wla eh
      println("step>2 or not moving in diagonal")
      return false
    }
    //check if an unkinged piece is trying to move backward
    if ((vSteps<0 && piece<2 && gameState._2.getOrElse(-1)==1)||(vSteps>0 && piece<2 &&  gameState._2.getOrElse(-1) ==0) ){
      println("unkinged moving backward!!")
      return false
    }
    if (Math.abs(hSteps)==2){
      //check that the in-between piece belongs to the enemy
      val inBetweenPiece=gameState._1(fromRow+(vSteps/2))(fromCol+(hSteps/2));
      if (isCurrentPlayer(inBetweenPiece)){
        println("attempt to kill your self")
        return false
      }
    }
    true
  }
  def applyMoveCheckers(gameState:state,arrInput:Array[Int]):state ={
    ///es2ly nagui em 3yzen nkhleha btbadel bssssssssss
    val fromRow=arrInput(0)
    val fromCol=arrInput(1)
    val toRow=arrInput(2)
    val toCol=arrInput(3)
    val vSteps=toRow-fromRow
    val hSteps=toCol-fromCol
    val newState=(gameState._1.clone(),switchPlayers(gameState._2))
    if((toRow==0 && gameState._1(fromRow)(fromCol)==0 ) || (toRow==7 && gameState._1(fromRow)(fromCol)==0 ))
      newState._1(fromRow)(fromCol)+=2
    if (Math.abs(hSteps)==2)
      newState._1(fromRow+(vSteps/2))(fromCol+(hSteps/2))= -1
    newState._1(toRow)(toCol) = gameState._1(fromRow)(fromCol);
    newState._1(fromRow)(fromCol)= -1;
    newState
  }

  def valid8Qmove(gameState: state,arr: Array[Int]):Boolean={

    //valid row
    val col=arr(0)
    val row=arr(1)
    if (gameState._1(row)(col)=='♕') return true
    for (i <- 0 to  7) {
      if(gameState._1(i)(col)=='♕') return false
    }
    //valid column
    for (j <- 0 until  8) {
      if (gameState._1(row)(j) == '♕') return false
    }
    //valid diagonal in right down corner
    var l: Int = col
    for (i <- row until 8 if l < 8) {
      if (gameState._1(i)(l) == '♕') return false
      l=l+1
    }
    //valid diagonal in left up corner
    var ll: Int = col
    for (i <- row until -1 by -1 if ll > -1) {
      if (gameState._1(i)(ll) == '♕') return false
      ll = ll-1
    }
    //valid diagonal in up right corner
    var lll: Int = col
    for (i <- row until -1 by -1 if lll < 8) {
      if (gameState._1(i)(lll) == '♕') return false
      lll = lll + 1
    }
    //valid diagonal in down left corner
    var llll: Int = col
    for (i <- row until 8  if llll > -1) {
      if (gameState._1(i)(llll) == '♕') return false
      llll = llll - 1
    }
    true
  }

  def applyMove8q(gameState: state,arrInput:Array[Int]):state=
  {

    val row=arrInput(0)
    val col=arrInput(1)
    gameState._1(row)(col)=if(gameState._1(row)(col)==' ' )'♕' else  ' '
    gameState
  }
  /////////////////////////////////////////////tic tac toe tarbatatooooo ////////////////////////////////////////////////////////////////
  def validTTmove(gameState: state, arr: Array[Int]): Boolean = {
    val col = arr(1)
    val row = arr(0)
    if ( gameState._1(row)(col) == '❌' || gameState._1(row)(col) == '⭕' ) return false else return true
  }
  def applyMoveTT(gameState: state, arr: Array[Int]): state = {
    val row = arr(0)
    val col = arr(1)
    var newState: state = (gameState._1, switchPlayers(gameState._2))
    if (gameState._2.getOrElse(-1) == 1) newState._1(row)(col) = '❌' else newState._1(row)(col) = '⭕'
    newState
  }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def applyMoveConnect4(gameState:state,arrInput:Array[Int]):state ={
    val col=arrInput(0)
    val newState:state=(gameState._1.clone(),switchPlayers(gameState._2))
    def getRow(row:Int):Int={
      if(gameState._1(row)(col)==' ' )row else if(row==0) -1  else getRow(row-1)
    }
    val row=getRow(5)
    if(gameState._2.getOrElse(-1) == 1) newState._1(row)(col)='r' else newState._1(row)(col)='y'

    newState
  }
  val gettictactoesAscii: Any => Any = (p) => p match { //btakhod int w t-return el ascii

    case '❌' => '❌'
    case '⭕' => '⭕'
    case '0' => ' '
    case s: Int => s.toChar

  }
  val initTTTBoard:()=>(Board,currentPlayer)=()=> (Array.fill(3,3)(' '),Some(1))
  val tictactoeController=controllerUtil(validTTmove,check8QsLength,applyMoveTT)
  def isValidConnect4(gameState:state,arrInput:Array[Int]):Boolean=gameState._1(0)(arrInput(0))==' '
  def checkConnect4Length (str:String):Boolean=(str.length==1)
  def check8QsLength(str:String):Boolean=(str.length==2)
  def checkCheckersLength(str:String):Boolean=(str.length==5)
  val initConnect4Board:()=>(Board,currentPlayer)=()=> (Array.fill(6,7)(' '),Some(1))
  val init8QsBoard:()=>(Board,currentPlayer)=()=> (Array.fill(8,8)(' '),None)
  val get8QsBackgroundColor:(Int,Int)=>Color= (i, j)=> //this function takes row , coloumn and return the suitable back ground colour ,
    (i==0||j==0)match {
      case true=> new Color(255,255,255)          //literals
      case false =>
        ((i+j) % 2 == 0) match {
          case true => new Color(255, 255, 0)
          case false => new Color(0, 255, 255)
        }
    }

  val getConnect4Ascii:Any=>Char=(p)=> p match { //btakhod int w t-return el ascii
    case 'y' => 9898
    case 'r' => 9899
    case ' ' => 9899
    case s:Int =>
      if (s<58  ) ' ' else s.toChar

  }
  val get8QsAscii:Any=>Any=(p)=> p match { //btakhod int w t-return el ascii


    case '♕' => '♕'
    case '0' => ' '
    case s:Int => s.toChar


  }

  val connect4Controller=controllerUtil(isValidConnect4,checkConnect4Length,applyMoveConnect4)
  val eightQueensController=controllerUtil(valid8Qmove,check8QsLength,applyMove8q)
  val checkersController=controllerUtil(isValidMoveCheckers,checkCheckersLength,applyMoveCheckers)
  val sudokuController=controllerUtil(isValidsudoku,isValidSudokoLength,applyMoveSudoku4)
  val chessController=controllerUtil(validateMoveChess,checkCheckersLength,applyMoveChess)
  //  val controller=controllerUtil()


  def switchPlayers(player: currentPlayer): currentPlayer = {
    player match {
      case Some(0)=>Some(1)
      case Some(1)=>Some(0)
      case None=>None
    }
  }

  //HELPER FUNCTIONS//
  def isEmpty(board: Board,loc: (Int, Int)): Boolean = board(loc._1)(loc._2) == ' '
  def validateMoveChess(gameState: state,arrInput:Array[Int]): Boolean = {
    val fromRow=arrInput(0)
    val fromCol: Int =arrInput(1)
    val from=(fromRow,fromCol)

    val toRow:Int=arrInput(2)
    val toCol=arrInput(3)
    val to=(toRow,toCol)

    def isCurrentPlayerPiece(from: (Int, Int),to:(Int,Int)): Boolean = {
      val oppPieces = if (gameState._2.getOrElse(-1)==  1) blackPieces else whitePieces
      val myPieces=if (gameState._2.getOrElse(-1) ==0 ) blackPieces else whitePieces
      oppPieces.contains(gameState._1(from._1)(from._2).toChar)||myPieces.contains(gameState._1(to._1)(to._2).toChar) match {
        case true => println("not currentPlayer");false
        case false => def isValidPieceMove(fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
          val piece = gameState._1(fromPos._1)(fromPos._2)
          piece.toChar.toLower match {
            case 'p' => isValidPawnMove(gameState._1,gameState._2,fromPos, toPos)
            case 'r' => isValidRookMove(gameState._1,gameState._2,fromPos, toPos)(isPathClear)
            case 'n' => isValidKnightMove(gameState._1,gameState._2,fromPos, toPos)
            case 'x' => isValidBishopMove(gameState._1,gameState._2,fromPos, toPos)(isPathClear)
            case 'q' => isValidQueenMove(gameState._1,gameState._2,fromPos, toPos)(isPathClear)
            case 'k' => isValidKingMove(gameState._1,gameState._2,fromPos, toPos)
            case _ => false
          }
        }
          isValidPieceMove(from, to)
      }
    }
    isCurrentPlayerPiece(from, to)
  }


  def isValidRookMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    println("rook")
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    println((fromRow == toRow || fromCol == toCol)&& PathClear(board,fromPos,toPos))
    (fromRow == toRow || fromCol == toCol)&& PathClear(board,fromPos,toPos)
  }

  def isValidKnightMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    println("knight")
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a L-shape move
    println(Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 || Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2)
    Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 ||
      Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2
  }


  def isValidBishopMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:( Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    println("bishop")
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a diagonal move
    println(Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(board,fromPos,toPos))
    Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(board,fromPos,toPos)
  }


  def isValidQueenMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    println("queen")
    println(isValidRookMove(board,Player,fromPos, toPos)(PathClear) || isValidBishopMove(board,Player,fromPos, toPos)(PathClear))
    isValidRookMove(board,Player,fromPos, toPos)(PathClear) || isValidBishopMove(board,Player,fromPos, toPos)(PathClear)
  }



  def isValidKingMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    println("king")
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a one-square move
    println(Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1)
    Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1
  }



  // Define helper function to check if the path between "from" and "to" is clear
  def isPathClear(board: Board,from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val rowDir = if (rowDiff < 0) -1 else if (rowDiff > 0) 1 else 0
    val colDir = if (colDiff < 0) -1 else if (colDiff > 0) 1 else 0
    val numSteps = Math.max(Math.abs(rowDiff), Math.abs(colDiff))
    (1 until numSteps).forall(step => isEmpty(board,(from._1 + step * rowDir, from._2 + step * colDir)))
  }

  def isOpponent(board: Board,Player:currentPlayer,loc: (Int, Int)): Boolean = {
    val oppPiece = if (Player.getOrElse(-1) ==  1) blackPieces else whitePieces
    oppPiece.contains(board(loc._1)(loc._2).toChar)

  }


  def isValidPawnMove(board: Board,Player:currentPlayer,from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val forwardDir = if (Player ==  Some(1)) -1 else 1
    val startRow = if (Player ==  Some(1)) 8 - 2 else 1
    val isTwoSquares = rowDiff == 2 * forwardDir && colDiff == 0 && from._1 == startRow && isEmpty(board,(from._1 + forwardDir, from._2)) && isEmpty(board,to)
    val isOneSquare = rowDiff == forwardDir && colDiff == 0 && isEmpty(board,to)
    val isCapture = rowDiff == forwardDir && Math.abs(colDiff) == 1 && isOpponent(board,Player,to)
    println(isTwoSquares || isOneSquare || isCapture)
    isTwoSquares || isOneSquare || isCapture
  }
  def applyMoveChess(gameState:state,arrInput:Array[Int]): state = {
    val fromRow =arrInput(0)
    val fromCol=arrInput(1)
    val toRow =arrInput(2)
    val toCol = arrInput(3)
    val newState=(gameState._1,switchPlayers(gameState._2))
    newState._1(toRow)(toCol) = newState._1(fromRow)(fromCol)
    newState._1(fromRow)(fromCol) = ' '
    println(newState._2)
    newState

  }
  def getChessASCII(piece: Any): Char = {
    if (piece == 'p')
      9823
    else if (piece == 'P')
      9817
    else if (piece == 'n')
      9822
    else if (piece == 'N')
      9816
    else if (piece == 'x')
      9821
    else if (piece == 'X')
      9815
    else if (piece == 'q')
      9819
    else if (piece == 'Q')
      9813
    else if (piece == 'k')
      9818
    else if (piece == 'K')
      9812
    else if (piece == 'r')
      9820
    else if (piece == 'R')
      9814
    else if (piece == ' '||piece=='0')
      32
    else{
      piece match {
        case s:Int=>s.toChar
        case c:Char=>c
        case _=>' '
      }
    }
  }
  val initChessBoard:()=>(Array[Array[Int]],Option[Int])={()=>
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
  // Define the piece characters
  val whitePieces = Set('K', 'Q', 'R', 'X', 'N', 'P')
  val blackPieces = Set('k', 'q', 'r', 'x', 'n', 'p')
  //  // Define helper function to check if the path between "from" and "to" is clear
  //  def isPathClear(board: Board,from: (Int, Int), to: (Int, Int)): Boolean = {
  //    val rowDiff = to._1 - from._1
  //    val colDiff = to._2 - from._2
  //    val rowDir = if (rowDiff < 0) -1 else if (rowDiff > 0) 1 else 0
  //    val colDir = if (colDiff < 0) -1 else if (colDiff > 0) 1 else 0
  //    val numSteps = Math.max(Math.abs(rowDiff), Math.abs(colDiff))
  //    (1 until numSteps).forall(step => isEmpty(board,(from._1 + step * rowDir, from._2 + step * colDir)))
  //  }
  //
  //  def isOpponent(board: Board,Player:currentPlayer,loc: (Int, Int)): Boolean = {
  //    val oppPiece = if (Player ==  Some(1)) blackPieces else whitePieces
  //    oppPiece.contains(board(loc._1)(loc._2).toChar)
  //
  //  }
  1
  0

  //  //IMPURE FUNCTION
  //  def gameAction(getInput:(String,String)): Unit = {
  //    val fromString=getInput._1
  //    val toString=getInput._2
  //    if (fromString == "" && toString == "") {
  //      return
  //    }
  //    controller(boardGUI,player, fromString, Option(toString)) match {
  //      case Some(newState) => Drawer(newState._1,newState._2);boardGUI=newState._1;player=newState._2;println("guiPlayer :"+player);println("logicPlayer :"+newState._2)
  //      case None =>  println("here2")
  //        showErrorMessage; clearInput
  //
  //    }
  //
  //  }









}

