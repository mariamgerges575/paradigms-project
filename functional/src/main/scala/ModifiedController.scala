
import ChessGUI._

import java.awt.event.ActionEvent
import javax.swing.{JLabel, JOptionPane}

class ChessGamee() {
  var chessGUI = ChessGUI
  // Define the dimensions of the board
  val numRows = 8
  val numCols = 8

  // Define the piece characters
  val whitePieces = Set('K', 'Q', 'R', 'X', 'N', 'P')
  val blackPieces = Set('k', 'q', 'r', 'x', 'n', 'p')

  //IMMUTABLE DATA//
  type currentPlayer=Option[Int]
  type Board=Array[Array[Int]]
  type state=(Board,currentPlayer)

  val initChessBoard:()=>(Array[Array[Int]],Option[Int],Option[JLabel])={()=>
    ( (Array.tabulate(8, 8)((i, j) => {
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
    })),Some(1),Some(new JLabel()))
  }
  def getPieceASCII(piece: Any): Char = {
    if (piece == 'p')
      return 9823

    if (piece == 'P') {
      return 9817
    }
    if (piece == 'n') {
      return 9822
    }
    if (piece == 'N') {
      return 9816
    }
    if (piece == 'x') {
      return 9821
    }
    if (piece == 'X') {
      return 9815
    }
    if (piece == 'q') {
      return 9819
    }
    if (piece == 'Q') {
      return 9813
    }
    if (piece == 'k') {
      return 9818
    }
    if (piece == 'K') {
      return 9812
    }
    if (piece == 'r') {
      return 9820
    }
    if (piece == 'R') {
      return 9814
    }
    if (piece == ' '||piece=='0'){
      return 32
    }
    else{
      piece match {
        case s:Int=>s.toChar
        case c:Char=>c
        case _=>' '
      }
    }
  }

  //DRAWER AND CONTROLLER//
  def controller(board: Board,Player:currentPlayer, fromString: String, toString: Option[String]): Option[(Board,currentPlayer)] = {
    println("currentPlayer :"+Player )
    ValidateAndApply(board,Player, fromString, toString)(checkInputLength)(parseInput)(validateMove)(applyMove)
  }


  //MAIN FUNCTIONS USED IN CONTROLLER//

  def ValidateAndApply(board: Board,Player:currentPlayer,fromString: String, toString: Option[String])
                      (isValidLength: (String, Option[String]) => Boolean)
                      (ParsedInput: (String, Option[String]) => ((Int, Option[Int]), Option[(Int, Option[Int])]))
                      (isValidMove: (Board,currentPlayer,(Int, Option[Int]), Option[(Int, Option[Int])]) => Boolean)
                      (ApplyMove: ((Board,currentPlayer,(Int, Option[Int]), Option[(Int, Option[Int])]) => Option[(Board,currentPlayer)]))

  : Option[(Board,currentPlayer)] = {
    isValidLength(fromString, toString) &&
      isValidMove(board,Player, ParsedInput(fromString,v2 = toString)._1,ParsedInput(fromString,toString)._2)
    match {
      case false => None
      case true =>
        println("here")
        ApplyMove(board,Player,ParsedInput(fromString, toString)._1, ParsedInput(fromString, toString)._2)
    }
  }

  def checkInputLength(str1: String, str2: Option[String]): Boolean = {
    str2 match {
      case None=> (str1.length==2||str1.length==1)
      case Some(str2)=> (str1.length==2) && (str2.length==2||str2.length==1)
    }
  }

  def parseInput(str1: String,str2: Option[String]): ((Int, Option[Int]), Option[(Int, Option[Int])]) = {
    val Input1=str1.length match {
      case 1=> (str1.charAt(1).toInt - 97,None)
      case 2=>(str1.charAt(0).toInt - 49, Some(str1.charAt(1).toInt - 97))
    }

    str2 match {
      case None=>(Input1,None)
      case Some(str2)=> val Input2:Option[(Int,Option[Int])] = str2.length match {
        case 1=> Some(str2.charAt(0).toInt -49,None)
        case 2=> Some(str2.charAt(0).toInt - 49, Some(str2.charAt(1).toInt - 97))
      }
        (Input1,Input2)
    }
  }


  def validateMove(board: Board,Player:currentPlayer,from_opt: (Int, Option[Int]), to_opt: Option[(Int, Option[Int])]): Boolean = {

    val fromRow=from_opt._1
    val fromCol: Int = from_opt._2.getOrElse(-1)
    val from=(fromRow,fromCol)
    val tuple:(Int,Option[Int])=to_opt.getOrElse((-1,Option(-1)))
    val toCol:Int=tuple._2.getOrElse(-1)
    val toRow=tuple._1
    val to=(toRow,toCol)


    def isCellInBounds(from: (Int, Int), to:(Int, Int)): Boolean = {
      (from._1 < board.length && from._1 >= 0) && (from._2 < board(0).length && from._2 >= 0) && (to._1 < board.length && to._1 >= 0) && (to._2 < board(0).length && to._2 >= 0) match {
        case false => false
        case true => def isCurrentPlayerPiece(from: (Int, Int),to:(Int,Int)): Boolean = {
          val oppPieces = if (Player ==  Some(1)) blackPieces else whitePieces
          val myPieces=if (Player == Some( 0)) blackPieces else whitePieces
          oppPieces.contains(board(from._1)(from._2).toChar)||myPieces.contains(board(to._1)(to._2).toChar) match {
            case true => println("not currentPlayer");false
            case false => def isValidPieceMove(fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
              val piece = board(fromPos._1)(fromPos._2)
              piece.toChar.toLower match {
                case 'p' => isValidPawnMove(board,Player,fromPos, toPos)
                case 'r' => isValidRookMove(board,Player,fromPos, toPos)(isPathClear)
                case 'n' => isValidKnightMove(board,Player,fromPos, toPos)
                case 'x' => isValidBishopMove(board,Player,fromPos, toPos)(isPathClear)
                case 'q' => isValidQueenMove(board,Player,fromPos, toPos)(isPathClear)
                case 'k' => isValidKingMove(board,Player,fromPos, toPos)
                case _ => false
              }
            }
              isValidPieceMove(from, to)
          }
        }
          isCurrentPlayerPiece(from, to)
      }
    }
    isCellInBounds(from, to)
  }

  def applyMove(board: Board,Player:currentPlayer,fromPos: (Int, Option[Int]), toPos: Option[(Int, Option[Int])]): Option[(Board,currentPlayer)] = {
    val (fromRow, fromCol) = (fromPos._1,fromPos._2.getOrElse(-1))
    val tuple: (Int, Option[Int]) = toPos.getOrElse((-1,Option(-1)))
    val toCol: Int = tuple._2.getOrElse(-1)
    val toRow = tuple._1
    var newState=(board,switchPlayers(Player))
    newState._1(toRow)(toCol) = newState._1(fromRow)(fromCol)
    newState._1(fromRow)(fromCol) = ' '
    println(newState._2)
    Some(newState)

  }

  def switchPlayers(player: currentPlayer): currentPlayer = {
    player match {
      case Some(0)=>Some(1)
      case Some(1)=>Some(0)
      case None=>None
    }
  }

//  def showErrorMessage(): Unit = {
//    JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")
//  }




  //HELPER FUNCTIONS//
  def isEmpty(board: Board,loc: (Int, Int)): Boolean = board(loc._1)(loc._2) == ' '

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
    val oppPiece = if (Player ==  Some(1)) blackPieces else whitePieces
    oppPiece.contains(board(loc._1)(loc._2).toChar)

  }
  1
  0


  def isValidPawnMove(board: Board,Player:currentPlayer,from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val forwardDir = if (Player ==  Some(1)) -1 else 1
    val startRow = if (Player ==  Some(1)) numRows - 2 else 1
    val isTwoSquares = rowDiff == 2 * forwardDir && colDiff == 0 && from._1 == startRow && isEmpty(board,(from._1 + forwardDir, from._2)) && isEmpty(board,to)
    val isOneSquare = rowDiff == forwardDir && colDiff == 0 && isEmpty(board,to)
    val isCapture = rowDiff == forwardDir && Math.abs(colDiff) == 1 && isOpponent(board,Player,to)

    isTwoSquares || isOneSquare || isCapture
  }


  def isValidRookMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos

    (fromRow == toRow || fromCol == toCol)&& PathClear(board,fromPos,toPos)
  }

  def isValidKnightMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a L-shape move
    Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 ||
      Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2
  }


  def isValidBishopMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:( Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a diagonal move
    Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(board,fromPos,toPos)
  }


  def isValidQueenMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    isValidRookMove(board,Player,fromPos, toPos)(PathClear) || isValidBishopMove(board,Player,fromPos, toPos)(PathClear)
  }



  def isValidKingMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a one-square move
    Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1
  }





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

