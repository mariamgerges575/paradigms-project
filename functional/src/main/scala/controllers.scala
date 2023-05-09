
  def chessController (gameState:GameState,input:String):Option[GameState]={
    val whitePieces = Set('K', 'Q', 'R', 'X', 'N', 'P')
    val blackPieces = Set('k', 'q', 'r', 'x', 'n', 'p')
    /////////////Helper Functions to Validate each piece move///////////////////
    def isValidRookMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
      val (fromRow, fromCol) = fromPos
      val (toRow, toCol) = toPos
      println((fromRow == toRow || fromCol == toCol)&& PathClear(board,fromPos,toPos))
      (fromRow == toRow || fromCol == toCol)&& PathClear(board,fromPos,toPos)
    }
    def isValidKnightMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
      val (fromRow, fromCol) = fromPos
      val (toRow, toCol) = toPos
      // Check if the move is a L-shape move
      println(Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 || Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2)
      Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 ||
      Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2
    }
    def isValidBishopMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:( Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
      val (fromRow, fromCol) = fromPos
      val (toRow, toCol) = toPos
      // Check if the move is a diagonal move
      println(Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(board,fromPos,toPos))
      Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)&&PathClear(board,fromPos,toPos)
    }
    def isValidQueenMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int))(PathClear:(Board,(Int,Int),(Int,Int))=>Boolean): Boolean = {
      println(isValidRookMove(board,Player,fromPos, toPos)(PathClear) || isValidBishopMove(board,Player,fromPos, toPos)(PathClear))
      isValidRookMove(board,Player,fromPos, toPos)(PathClear) || isValidBishopMove(board,Player,fromPos, toPos)(PathClear)
    }

    def isValidKingMove(board: Board,Player:currentPlayer,fromPos: (Int, Int), toPos: (Int, Int)): Boolean = {
      val (fromRow, fromCol) = fromPos
      val (toRow, toCol) = toPos
      // Check if the move is a one-square move
      println(Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1)
      Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1
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
    //HELPER FUNCTIONS//
    def isEmpty(board: Board,loc: (Int, Int)): Boolean = board(loc._1)(loc._2) == ' '



    ////////////function validate move//////////
    def validateMove(gameState: GameState,arrInput:Array[Int]): Boolean = {
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



    def applyMove(gameState:GameState,arrInput:Array[Int]): GameState = {
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
    /////validate correctness of input//////

    if(input.length==5 && input.charAt(2)==' '){
      val arrayInput:Array[Int] =Array(input.charAt(0).toInt - 49, input.charAt(1).toInt - 97, input.charAt(3).toInt - 49, input.charAt(4).toInt - 97)
      arrayInput.foreach(u => {
        if (u >= 8 || u < 0) {
          return None
        }
      })
      if(validateMove(gameState,arrayInput))  Some(applyMove(gameState,arrayInput)) else  None

    }else  None


  }

  def connect4Controller(gameState:GameState,input:String):Option[GameState]={
      def applyMove(gameState:GameState,col:Int):GameState ={
        val newState:GameState=(gameState._1.clone(),switchPlayers(gameState._2))
        def getRow(row:Int):Int={
          if(gameState._1(row)(col)==' ' )row else if(row==0) -1  else getRow(row-1)
        }
        val row=getRow(5)
        if(gameState._2.getOrElse(-1) == 1) newState._1(row)(col)='r' else newState._1(row)(col)='y'

        newState
      }

      if (input.length == 1){
        val colInt: Int = input.charAt(0).toInt - 97
        if (colInt >= 0 && colInt < 7 && gameState._1(0)(colInt) == ' ') Some(applyMove(gameState, colInt)) else None
      } else None


    }
    def checkersController(gameState:GameState,input:String):Option[GameState]={

      def isValidMove(gameState:GameState,arrInput:Array[Int]):Boolean = {
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
      def applyMove(gameState:GameState,arrInput:Array[Int]):GameState ={
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
      if(input.length==5 && input.charAt(2)==' '){
        val arrayInput:Array[Int] =Array(input.charAt(0).toInt - 49, input.charAt(1).toInt - 97, input.charAt(3).toInt - 49, input.charAt(4).toInt - 97)
        arrayInput.foreach(u => {
          if (u >= 8 || u < 0) {
            return None
          }
        })
        if(isValidMove(gameState,arrayInput))  Some(applyMove(gameState,arrayInput)) else  None

      }else  None

    }

  def sudokuController(gameState: GameState, input: String): Option[ GameState ]= {

    def isValidGame(gameState: GameState,  r: Int, c: Int, value: Int): Boolean = {
      if (gameState._1(r)(c) > '9' && gameState._1(r)(c) != ' ') {
        return false
      } else if (gameState._1(r)(c) == value) return true

      for (i <- 0 to 8) {
          if ((gameState._1(i)(c) == value || gameState._1(i)(c) % 10 == value) ||
            (gameState._1(r)(i) == value || gameState._1(r)(i) % 10 == value)) return false

      }
      true
    }

    def applyChange(gameState: GameState, r: Int, c: Int, value: Int): GameState = {
      val newState: GameState = (gameState._1.clone(), switchPlayers(gameState._2))
      if (gameState._1(r)(c) == value) newState._1(r)(c) = 0 else newState._1(r)(c) = value
      newState
    }

    if (input.length == 4 && input.charAt(2) == ' ') {
      val r: Int = input.charAt(0).toInt - 49
      val c: Int = input.charAt(1).toInt - 97
      val value: Int = input.charAt(3).toInt - 48
      if(r>=0 && r<9 && c>=0 && c<9 && value>0 && value<10 && isValidGame(gameState,r,c,value) ) Some(applyChange(gameState,r,c,value)) else None
    }
    else None
  }


  def tictactoeController(gameState:GameState,input:String):Option[GameState]={
      def validTTmove(gameState: GameState, row:Int,col:Int): Boolean = {
        if ( gameState._1(row)(col) == '❌' || gameState._1(row)(col) == '⭕' ) false else true
      }
      def applyMoveTT(gameState: GameState, row:Int,col:Int): GameState = {
        var newState: GameState = (gameState._1, switchPlayers(gameState._2))
        if (gameState._2.getOrElse(-1) == 1) newState._1(row)(col) = '❌' else newState._1(row)(col) = '⭕'
        newState
      }
    if(input.length==2){
      val row= input.charAt(0).toInt - 49
      val col= input.charAt(1).toInt - 97
      if(row>=0 && row<3 && col>=0 && col<3 && validTTmove(gameState, row,col)) Some(applyMoveTT(gameState,row,col)) else None
    }
    else None

    }


    def eightQueensController(gameState:GameState,input:String):Option[GameState]={

      def validMove(gameState: GameState,row:Int,col:Int):Boolean={
        //validate row
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

      def applyMove(gameState: GameState,row:Int,col:Int):GameState=
      {
        gameState._1(row)(col)=if(gameState._1(row)(col)==' ' )'♕' else  ' '
        gameState
      }
      if(input.length==2){
        val row= input.charAt(0).toInt - 49
        val col= input.charAt(1).toInt - 97
        if(row>=0 && row<3 && col>=0 && col<3 && validMove(gameState,row,col)) Some(applyMove(gameState, row,col)) else None
      }
      else None
    }



  def switchPlayers(player: currentPlayer): currentPlayer = {
    player match {
      case Some(0)=>Some(1)
      case Some(1)=>Some(0)
      case None=>None
    }
  }






