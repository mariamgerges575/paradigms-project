
import ChessGUI._

import java.awt.event.ActionEvent
import javax.swing.JOptionPane

class ChessGame() {
  var chessGUI = ChessGUI
  // Define the dimensions of the board
  val numRows = 8
  val numCols = 8

  // Define the piece characters
  val whitePieces = Set('K', 'Q', 'R', 'B', 'N', 'P')
  val blackPieces = Set('k', 'q', 'r', 'b', 'n', 'p')

  //IMMUTABLE DATA//
  type currentPlayer=String
  type Board=Array[Array[Char]]
  type GameState= (Board,currentPlayer)
  val initialCurrentPlayer:currentPlayer="black"
  val initialBoard: Board = (Array.tabulate(numRows, numCols)((i, j) => {
    (i, j) match {
      case (0, 0) => 'r'
      case (0, 1) => 'n'
      case (0, 2) => 'b'
      case (0, 3) => 'q'
      case (0, 4) => 'k'
      case (0, 5) => 'b'
      case (0, 6) => 'n'
      case (0, 7) => 'r'
      case (1, j) => 'p'
      case (6, j) => 'P'
      case (7, 0) => 'R'
      case (7, 1) => 'N'
      case (7, 2) => 'B'
      case (7, 3) => 'Q'
      case (7, 4) => 'K'
      case (7, 5) => 'B'
      case (7, 6) => 'N'
      case (7, 7) => 'R'
      case _ => ' '
    }
  }))

  val initialState=(initialBoard,initialCurrentPlayer)


  // INITIALIZE//
  def Initialize():Unit={

    stateGUI=initialState
    chessGUI.drawScreen()
    Drawer(initialState)
    chessGUI.setVisible(true)
    chessGUI.applyMove.addActionListener((e: ActionEvent) =>gameAction(takeUserInput)

    )


  }
  //FUNCTIONS RESPONSIBLE OF DEALING WITH GUI
  def takeUserInput(): (String,String)= {
    val fromString = fromField.getText()
    val toString = toField.getText()
    (fromString,toString)
  }


  //IMPURE FUNCTION
  def gameAction(getInput:(String,String)): Unit = {
    val fromString=getInput._1
    val toString=getInput._2
    if (fromString == "" && toString == "") {
      return
    }
    controller(stateGUI, fromString, Option(toString)) match {
      case Some(newState) => Drawer(newState); stateGUI = newState
      case None =>  showErrorMessage; clearInput

    }

  }

  //DRAWER AND CONTROLLER//

  def Drawer(gameState: GameState): Unit = {
    clearGui()
    val currentSize = chessGUI.getSize()
    chessGUI.setPreferredSize(currentSize)
    chessGUI.drawBoard(gameState)
  }

  def controller(gameState: GameState, fromString: String, toString: Option[String]): Option[ GameState] = {

    ValidateAndApply(gameState, fromString, toString)(checkInputLength)(parseInput)(validateMove)(applyMove)

  }






  //MAIN FUNCTIONS USED IN CONTROLLER//

  def ValidateAndApply(gameState: GameState,fromString: String, toString: Option[String])
                      (isValidLength: (String, Option[String]) => Boolean)
                      (ParsedInput: (String, Option[String]) => ((Int, Option[Int]), Option[(Int, Option[Int])]))
                      (isValidMove: (GameState,(Int, Option[Int]), Option[(Int, Option[Int])]) => Boolean)
                      (ApplyMove: ((GameState,(Int, Option[Int]), Option[(Int, Option[Int])]) => Option[GameState]))

  : Option[GameState] = {
    isValidLength(fromString, toString) &&
      isValidMove(gameState,v2 = ParsedInput(fromString,v2 = toString)._1,ParsedInput(fromString,toString)._2)
    match {
      case false => None
      case true =>
        ApplyMove(gameState,ParsedInput(fromString, toString)._1, ParsedInput(fromString, toString)._2)


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


  def validateMove(gameState: GameState,from_opt: (Int, Option[Int]), to_opt: Option[(Int, Option[Int])]): Boolean = {

    val fromRow=from_opt._1
    val fromCol: Int = from_opt._2.getOrElse(-1)
    val from=(fromRow,fromCol)
    val tuple:(Int,Option[Int])=to_opt.getOrElse((-1,Option(-1)))
    val toCol:Int=tuple._2.getOrElse(-1)
    val toRow=tuple._1
    val to=(toRow,toCol)



    def isCellInBounds(from: (Int, Int), to:(Int, Int)): Boolean = {
      (from._1 < gameState._1.length && from._1 >= 0) && (from._2 < gameState._1(0).length && from._2 >= 0) && (to._1 < gameState._1.length && to._1 >= 0) && (to._2 < gameState._1(0).length && to._2 >= 0) match {
        case false => false
        case true => def isCurrentPlayerPiece(from: (Int, Int),to:(Int,Int)): Boolean = {
          val oppPieces = if (gameState._2 == "white") blackPieces else whitePieces
          val myPieces=if (gameState._2 == "black") blackPieces else whitePieces
          oppPieces.contains(gameState._1(from._1)(from._2))||myPieces.contains(gameState._1(to._1)(to._2)) match {
            case true => false
            case false => def isValidPieceMove(fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
              val piece = gameState._1(fromPos._1)(fromPos._2)
              piece.toLower match {
                case 'p' => isValidPawnMove(gameState,fromPos, toPos)
                case 'r' => isValidRookMove(gameState,fromPos, toPos)(isPathClear)
                case 'n' => isValidKnightMove(gameState,fromPos, toPos)
                case 'b' => isValidBishopMove(gameState,fromPos, toPos)(isPathClear)
                case 'q' => isValidQueenMove(gameState,fromPos, toPos)(isPathClear)
                case 'k' => isValidKingMove(gameState,fromPos, toPos)
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

  def applyMove(gameState: GameState,fromPos: (Int, Option[Int]), toPos: Option[(Int, Option[Int])]): Option[GameState] = {
    val (fromRow, fromCol) = (fromPos._1,fromPos._2.getOrElse(-1))
    val tuple: (Int, Option[Int]) = toPos.getOrElse((-1,Option(-1)))
    val toCol: Int = tuple._2.getOrElse(-1)
    val toRow = tuple._1
    var newState=(gameState._1,switchPlayers(gameState._2))
    newState._1(toRow)(toCol) = newState._1(fromRow)(fromCol)
    newState._1(fromRow)(fromCol) = ' '
    Some(newState)

  }

  def switchPlayers(player: currentPlayer): currentPlayer = {
    player match {
      case "white"=>"black"
      case "black"=>"white"
    }
  }

  def showErrorMessage(): Unit = {
    JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")
  }





  //HELPER FUNCTIONS//
  def isEmpty(gameState: GameState,loc: (Int, Int)): Boolean = gameState._1(loc._1)(loc._2) == ' '

  // Define helper function to check if the path between "from" and "to" is clear
  def isPathClear(gameState: GameState,from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val rowDir = if (rowDiff < 0) -1 else if (rowDiff > 0) 1 else 0
    val colDir = if (colDiff < 0) -1 else if (colDiff > 0) 1 else 0
    val numSteps = Math.max(Math.abs(rowDiff), Math.abs(colDiff))
    (1 until numSteps).forall(step => isEmpty(gameState,(from._1 + step * rowDir, from._2 + step * colDir)))
  }

  def isOpponent(gameState: GameState,loc: (Int, Int)): Boolean = {
    val oppPiece = if (gameState._2 == "white") blackPieces else whitePieces
    oppPiece.contains(gameState._1(loc._1)(loc._2))

  }


  def isValidPawnMove(gameState: GameState,from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val forwardDir = if (gameState._2 == "white") -1 else 1
    val startRow = if (gameState._2 == "white") numRows - 2 else 1
    val isTwoSquares = rowDiff == 2 * forwardDir && colDiff == 0 && from._1 == startRow && isEmpty(gameState,(from._1 + forwardDir, from._2)) && isEmpty(gameState,to)
    val isOneSquare = rowDiff == forwardDir && colDiff == 0 && isEmpty(gameState,to)
    val isCapture = rowDiff == forwardDir && Math.abs(colDiff) == 1 && isOpponent(gameState,to)

    isTwoSquares || isOneSquare || isCapture
  }


  def isValidRookMove(gameState: GameState,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(GameState,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos

    (fromRow == toRow || fromCol == toCol)&& PathClear(gameState,fromPos,toPos)
  }

  def isValidKnightMove(gameState: GameState,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a L-shape move
    Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 ||
      Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2
  }


  def isValidBishopMove(gameState: GameState,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:( GameState,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a diagonal move
    Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(gameState,fromPos,toPos)
  }


  def isValidQueenMove(gameState: GameState,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(GameState,(Int,Int),(Int,Int))=>Boolean): Boolean = {
    isValidRookMove(gameState,fromPos, toPos)(PathClear) || isValidBishopMove(gameState,fromPos, toPos)(PathClear)
  }



  def isValidKingMove(gameState: GameState,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a one-square move
    Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1
  }














}

