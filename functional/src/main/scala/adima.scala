//import javax.swing.JOptionPane
//
//class ChessGame() {
//
//  // Define the dimensions of the board
//  var chessGUI = ChessGUI
//  ChessGUI.game = this
//  var gameState = this.createBoard()
//  val numRows = gameState.length
//  val numCols = gameState(0).length
//
//  // Define the piece characters
//  val blackKing = 'K'
//  val blackQueen = 'Q'
//  val blackRook = 'R'
//  val blackBishop = 'B'
//  val blackKnight = 'N'
//  val blackPawn = 'P'
//  val whiteKing = 'K'
//  val whiteQueen = 'q'
//  val whiteRook = 'r'
//  val whiteBishop = 'b'
//  val whiteKnight = 'n'
//  val whitePawn = 'p'
//
//  // Define the current player
//  var currentPlayer = "black"
//  val isValidLength: (String, String) => Boolean = (fromString, toString) => {
//    (fromString.length == 2) && (toString.length == 2)
//  }
//  def Drawer(): Unit = {
//    chessGUI.clearGui()
//    val currentSize = chessGUI.getSize()
//    chessGUI.setPreferredSize(currentSize)
//    chessGUI.drawBoard()
//  }
//
//  // Define the Drawer function
//  def draw(): Unit = {
//    for (row <- 0 until numRows) {
//      for (col <- 0 until numCols) {
//        print(gameState(row)(col))
//        print(" ")
//      }
//      println()
//    }
//    println("Current player: " + currentPlayer)
//  }
//
//  def createBoard(): Array[Array[Char]] = {
//    val gameState = Array(
//
//      Array('r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'),
//      Array('p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'),
//      Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
//      Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
//      Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
//      Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
//      Array('P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'),
//      Array('R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R')
//    )
//    return gameState
//  }
//
//  def isCellInBounds(from: (Int, Int), to: (Int, Int)): Boolean = {
//    (from._1 < gameState.length && from._1 >= 0) && (from._2 < gameState(0).length && from._2 >= 0) && (to._1 < gameState.length && to._1 >= 0) && (to._2 < gameState(0).length && to._2 >= 0)
//  }
//
//  def ParseInput(fromString:String,toString:String):  ((Int, Int), (Int, Int)) = {
//
//      val from = (fromString.substring(0, 1).toInt - 1, fromString.substring(1).toLowerCase()(0) - 'a')
//      val to = (toString.substring(0, 1).toInt - 1, toString.substring(1).toLowerCase()(0) - 'a')
//      (from, to)
//    }
//  // Define the Controller function
//  def controller(fromString: String, toString: String): Unit = {
//    if (isValidLength(fromString, toString)) {
//      val (from, to) = ParseInput(fromString, toString)
//      if (isCellInBounds(from, to)) {
//        val piece = gameState(from._1)(from._2)
//        if(isValidMove(piece, from, to)){
//          gameState(to._1)(to._2) = piece
//          gameState(from._1)(from._2) = ' '
//
//          // Switch the current player
//          if (currentPlayer == "white") {
//            currentPlayer = "black"
//          } else {
//            currentPlayer = "white"
//          }
//          Drawer()
//          return
//
//        }
//      }
//
//    }
//
//    JOptionPane.showMessageDialog(null, "Invalid move", "Error", JOptionPane.ERROR_MESSAGE)
//
//
//    }
//
//
//
//  // Define the Model function
//  def isValidMove(piece: Char, from: (Int, Int), to: (Int, Int)): Boolean = {
//
//    // Define helper functions to check if the "to" location is empty or occupied by an opponent piece
//    def isEmpty(loc: (Int, Int)): Boolean = gameState(loc._1)(loc._2) == ' '
//    def isOpponent(loc: (Int, Int)): Boolean = {
//      val oppPiece = if (currentPlayer == "white") blackPieces else whitePieces
//      oppPiece.contains(gameState(loc._1)(loc._2))
//    }
//
//    // Define helper functions to check if the move is valid for each piece type
//    def isValidKingMove(): Boolean = {
//      val rowDiff = Math.abs(from._1 - to._1)
//      val colDiff = Math.abs(from._2 - to._2)
//      (rowDiff == 1 || colDiff == 1) && !(rowDiff == 1 && colDiff == 1) // King can move one square in any direction
//    }
//    def isValidQueenMove(): Boolean = {
//      val rowDiff = Math.abs(from._1 - to._1)
//      val colDiff = Math.abs(from._2 - to._2)
//      (rowDiff == colDiff || rowDiff == 0 || colDiff == 0) && isPathClear(from, to) // Queen can move any number of squares diagonally, horizontally, or vertically
//    }
//    def isValidRookMove(): Boolean = {
//      val rowDiff = Math.abs(from._1 - to._1)
//      val colDiff = Math.abs(from._2 - to._2)
//      (rowDiff == 0 || colDiff == 0) && isPathClear(from, to) // Rook can move any number of squares horizontally or vertically
//    }
//    def isValidBishopMove(): Boolean = {
//      val rowDiff = Math.abs(from._1 - to._1)
//      val colDiff = Math.abs(from._2 - to._2)
//      rowDiff == colDiff && isPathClear(from, to) // Bishop can move any number of squares diagonally
//    }
//    def isValidKnightMove(): Boolean = {
//      val rowDiff = Math.abs(from._1 - to._1)
//      val colDiff = Math.abs(from._2 - to._2)
//      (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2) // Knight can move in an "L" shape
//    }
//    def isValidPawnMove(): Boolean = {
//      val rowDiff = to._1 - from._1
//      val colDiff = to._2 - from._2
//      val forwardDir = if (currentPlayer == "white") -1 else 1
//      val startRow = if (currentPlayer == "white") numRows - 2 else 1
//      val isTwoSquares = rowDiff == 2 * forwardDir && colDiff == 0 && from._1 == startRow && isEmpty((from._1 + forwardDir, from._2)) && isEmpty(to)
//      val isOneSquare = rowDiff == forwardDir && colDiff == 0 && isEmpty(to)
//      val isCapture = rowDiff == forwardDir && Math.abs(colDiff) == 1 && isOpponent(to)
//      isTwoSquares || isOneSquare || isCapture
//    }
//
//    // Define helper function to check if the path between "from" and "to" is clear
//    def isPathClear(from: (Int, Int), to: (Int, Int)): Boolean = {
//      val rowDiff = to._1 - from._1
//      val colDiff = to._2 - from._2
//      val rowDir = if (rowDiff < 0) -1 else if (rowDiff > 0) 1 else 0
//      val colDir = if (colDiff < 0) -1 else if (colDiff > 0) 1 else 0
//      val numSteps = Math.max(Math.abs(rowDiff), Math.abs(colDiff))
//      (1 until numSteps).forall(step => isEmpty((from._1 + step * rowDir, from._2 + step * colDir)))
//    }
//
//    // Check if the move is valid based on the piece type
//    piece match {
//      case `whiteKing` | `blackKing` => isValidKingMove()
//      case `whiteQueen` | `blackQueen` => isValidQueenMove()
//      case `whiteRook` | `blackRook` => isValidRookMove()
//      case `whiteBishop` | `blackBishop` => isValidBishopMove()
//      case `whiteKnight` | `blackKnight` => isValidKnightMove()
//      case `whitePawn` | `blackPawn` => isValidPawnMove()
//      case _ => false // Invalid piece character
//    }
//  }
//
//  // Define the sets of white and black pieces for use in the isOpponent helper function
//  val blackPieces = Set(blackKing, blackQueen, blackRook, blackBishop, blackKnight, blackPawn)
//  val whitePieces = Set(whiteKing, whiteQueen, whiteRook, whiteBishop, whiteKnight, whitePawn)
//}
