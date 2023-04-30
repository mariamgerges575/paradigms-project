
import javax.swing.JOptionPane

class ChessGame() {
  var chessGUI = ChessGUI
  ChessGUI.game = this
  // Define the dimensions of the board
  val numRows = 8
  val numCols = 8

  // Define the piece characters
  val whitePieces = Set('K', 'Q', 'R', 'B', 'N', 'P')
  val blackPieces = Set('k', 'q', 'r', 'b', 'n', 'p')

  // Define the current player as a var
  var currentPlayer: String = "black"


  var gameState: Array[Array[Char]] = Array.tabulate(numRows, numCols)((i, j) => {
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
  })

  //DRAWER AND CONTROLLER//

  def Drawer(): Unit = {
    chessGUI.clearGui()
    val currentSize = chessGUI.getSize()
    chessGUI.setPreferredSize(currentSize)
    chessGUI.drawBoard()
  }

  def controller(fromString: String, toString: String): Unit = {

    ValidateAndApply(fromString, toString)(checkInputLength)(parseInput)(validateMove)(applyMove)(switchPlayers)
    match {
      case true => Drawer
      case false => showErrorMessage
    }

  }






  //MAIN FUNCTIONS USED IN CONTROLLER//

  def ValidateAndApply(fromString: String, toString: String)
                      (isValidLength: (String, String) => Boolean)
                      (ParsedInput: (String, String) => ((Int, Int), (Int, Int)))
                      (isValidMove: ((Int, Int), (Int, Int)) => Boolean)
                      (ApplyMove: (((Int, Int), (Int, Int)) => Unit))
                      (SwitchPlayers: () => Unit)
  : Boolean = {
    isValidLength(fromString, toString) &&
      isValidMove(v1 = ParsedInput(fromString, toString)._1, v2 = ParsedInput(fromString, toString)._2)
    match {
      case false => false
      case true => ApplyMove(ParsedInput(fromString, toString)._1, ParsedInput(fromString, toString)._2)
                   SwitchPlayers()
                   true

    }


  }

  def checkInputLength(str1: String, str2: String): Boolean = {
    (str1.length==2) && (str2.length==2)
  }

  def parseInput(str1: String,str2: String): ((Int, Int), (Int, Int)) = {
    val from = (str1.substring(0, 1).toInt - 1, str1.substring(1).toLowerCase()(0) - 'a')
    val to = (str2.substring(0, 1).toInt - 1, str2.substring(1).toLowerCase()(0) - 'a')
    (from, to)
  }

  def validateMove(from: (Int, Int), to: (Int, Int)): Boolean = {

    def isCellInBounds(from: (Int, Int), to: (Int, Int)): Boolean = {
      (from._1 < gameState.length && from._1 >= 0) && (from._2 < gameState(0).length && from._2 >= 0) && (to._1 < gameState.length && to._1 >= 0) && (to._2 < gameState(0).length && to._2 >= 0) match {
        case false => false
        case true => def isCurrentPlayerPiece(loc: (Int, Int)): Boolean = {
          val oppPiece = if (currentPlayer == "black") blackPieces else whitePieces
          oppPiece.contains(gameState(loc._1)(loc._2)) match {
            case true => false
            case false => def isValidPieceMove(fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
              val piece = gameState(fromPos._1)(fromPos._2)
              piece.toLower match {
                case 'p' => isValidPawnMove(fromPos, toPos)
                case 'r' => isValidRookMove(fromPos, toPos)(isPathClear)
                case 'n' => isValidKnightMove(fromPos, toPos)
                case 'b' => isValidBishopMove(fromPos, toPos)(isPathClear)
                case 'q' => isValidQueenMove(fromPos, toPos)(isPathClear)
                case 'k' => isValidKingMove(fromPos, toPos)
                case _ => false
              }

            }

              isValidPieceMove(from, to)
          }
        }

          isCurrentPlayerPiece(to)
      }
    }

    isCellInBounds(from, to)

  }

  def applyMove(fromPos: (Int, Int), toPos: (Int, Int)): Unit = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    gameState(toRow)(toCol) = gameState(fromRow)(fromCol)
    gameState(fromRow)(fromCol) = ' '

  }

  def switchPlayers(): Unit = {
    currentPlayer =  currentPlayer match {
      case "white"=>"black"
      case "black"=>"white"
    }
  }

  def showErrorMessage(): Unit = {
    JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")
  }





  //HELPER FUNCTIONS//
  def isEmpty(loc: (Int, Int)): Boolean = gameState(loc._1)(loc._2) == ' '

  // Define helper function to check if the path between "from" and "to" is clear
  def isPathClear(from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val rowDir = if (rowDiff < 0) -1 else if (rowDiff > 0) 1 else 0
    val colDir = if (colDiff < 0) -1 else if (colDiff > 0) 1 else 0
    val numSteps = Math.max(Math.abs(rowDiff), Math.abs(colDiff))
    (1 until numSteps).forall(step => isEmpty((from._1 + step * rowDir, from._2 + step * colDir)))
  }

  def isOpponent(loc: (Int, Int)): Boolean = {
    val oppPiece= currentPlayer match {
      case "white"=>blackPieces
      case "black"=>whitePieces
    }
    oppPiece.contains(gameState(loc._1)(loc._2))

  }


  def isValidPawnMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    val rowDiff = to._1 - from._1
    val colDiff = to._2 - from._2
    val forwardDir = if (currentPlayer == "white") -1 else 1
    val startRow = if (currentPlayer == "white") numRows - 2 else 1
    val isTwoSquares = rowDiff == 2 * forwardDir && colDiff == 0 && from._1 == startRow && isEmpty((from._1 + forwardDir, from._2)) && isEmpty(to)
    val isOneSquare = rowDiff == forwardDir && colDiff == 0 && isEmpty(to)
    val isCapture = rowDiff == forwardDir && Math.abs(colDiff) == 1 && isOpponent(to)
    print( isTwoSquares || isOneSquare || isCapture)
    isTwoSquares || isOneSquare || isCapture
  }


  def isValidRookMove(fromPos: (Int, Int), toPos: (Int, Int))(PathClear:((Int,Int),(Int,Int))=>Boolean): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    if(PathClear(fromPos,toPos)) print("SALKAAAAAA")
    else {print("msh salka")}
    (fromRow == toRow || fromCol == toCol)&& PathClear(fromPos,toPos)
  }

  def isValidKnightMove(fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a L-shape move
    Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 ||
      Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2
  }


  def isValidBishopMove(fromPos: (Int, Int), toPos: (Int, Int))(PathClear:((Int,Int),(Int,Int))=>Boolean): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a diagonal move
    Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(fromPos,toPos)
  }


  def isValidQueenMove(fromPos: (Int, Int), toPos: (Int, Int))(PathClear:((Int,Int),(Int,Int))=>Boolean): Boolean = {
    isValidRookMove(fromPos, toPos)(PathClear) || isValidBishopMove(fromPos, toPos)(PathClear)
  }



  def isValidKingMove(fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
    val (fromRow, fromCol) = fromPos
    val (toRow, toCol) = toPos
    // Check if the move is a one-square move
    Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1
  }














}

