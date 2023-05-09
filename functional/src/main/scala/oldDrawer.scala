//import java.awt._
//import java.awt.event.ActionEvent
//import javax.swing._
//import javax.swing.border.Border
//
//
//object gameEngine  {
//  val chessObj= new ChessGamee
//  val connect4Obj = new Connect4Gamee
//
//  //  val currentPlayer=1
//  //  val gameState =Array( Array(0, 1, 1, 1, 1, 1, 1, 1),
//  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
//  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
//  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
//  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
//  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
//  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
//  //                        Array(0, 0, 0, 0, 0, 0, 0, 0))
//
//  type currentPlayer=Option[Int]
//  type Board=Array[Array[Int]]
//  type GameState=(Board,currentPlayer)
//
//  val initCheckerState:()=>(GameState)=()=>(Array(
//    Array(-1, 1,-1, 1,-1, 1,-1, 1),
//    Array( 1,-1, 1,-1, 1,-1, 1,-1),
//    Array(-1, 1,-1, 1,-1, 1,-1, 1),
//    Array(-1,-1,-1,-1,-1,-1,-1,-1),
//    Array(-1,-1,-1,-1,-1,-1,-1,-1),
//    Array(0,-1, 0,-1, 0,-1, 0,-1),
//    Array(-1, 0,-1, 0,-1, 0,-1, 0),
//    Array(0,-1, 0,-1, 0,-1, 0,-1)),Some(1))
//
//  val initSudokuState:()=>(GameState)=()=>(Array(
//    Array(0,15,17,0,16, 0,0,11,0),
//    Array(18,0,16,12,0, 15,0,0, 17),
//    Array(0,14,0,0,0,0,0,0, 16),
//    Array(0,12,14,13,15,0,0,0,0),
//    Array(0,0,0,0,11, 12,0,0, 15),
//    Array(15,13,0,0,0,0,0,17, 12),
//    Array(0,0,0,11,0,0,19,16,0),
//    Array(0,0,0,16,0,0,0,0,0),
//    Array(11,16,19,15,17,13,0,0,0),
//  ),None)
//
//  val getCheckersAscii:Any=>Char= p => p match { //btakhod int w t-return el ascii
//    case 0 => 9898
//    case 1 => 9899
//    case 2 => 9812
//    case 3 => 9818
//    case -1 => ' '
//    case '0'=> ' '
//    case s:Int => s.toChar
//  }
//
//  val getCheckersBackgroundColor:(Int,Int)=>Color= (i, j)=> //this function takes row , coloumn and return the suitable back ground colour ,
//    (i==0||j==0)match {
//      case true=> new Color(255,255,255)          //literals
//      case false =>
//        ((i+j) % 2 == 0) match {
//          case true => new Color(232, 235, 239)
//          case false => new Color(125, 135, 150)
//        }
//    }
//
//  //  def drawBoard(howToDrawFunction:JPanel=>(Int,Option[(Int,Int)])=>Unit,frame: JFrame):(Array[Array[Int]],Option[Int])=>Unit ={ //el function dy btb2a ghza takhod el grid 3latol
//  //    (state,player)=> {
//  //      //removing the previous draw
//  //      frame.getContentPane.removeAll()
//  //      //creating the board
//  //      val panel = new JPanel(new GridLayout(state.length+1, state(0).length+1))
//  //      Array.concat(Array(Array.range('a','a'+state(0).length)),state).zipWithIndex.foreach((row) => {
//  //        Array.concat(Array((row._2+48)),row._1).zipWithIndex.foreach(
//  //          tile => howToDrawFunction(panel)(tile._1, Some(row._2, tile._2))
//  //      )})
//  //      //in case the game is multiplayer
//  //      player match {
//  //        case Some(value)=>
//  //          val lbl =new JLabel()
//  //          lbl.setText("        current player is:"+ value.toString)
//  //          frame.add(lbl,"South")
//  //      }
//  //      frame.add(panel,"Center")
//  //      frame.setSize(800,800)
//  //      frame.setVisible(true)
//  //    }
//  //  }
//  //
//  //  def draw(getWhichAscii:Any=>Char,getBackColor:Any):(JPanel)=>(Int,Option[(Int,Int)])=>Unit={
//  //    (panel)=>(piece:Int,ij:Option[(Int,Int)])=>{
//  //
//  //      val label = new JLabel(getWhichAscii(piece).toString)
//  //      label.setFont(new Font("Serif", Font.PLAIN, 50))
//  //      label.setHorizontalAlignment(SwingConstants.CENTER)
//  //      getBackColor match{
//  //        case fun:((Int,Int)=>Color)=>
//  //          ij match {
//  //            case Some(value)=>label.setBackground(fun(value._1,value._2))
//  //          }
//  //        case fun2:(()=>Color)=>label.setBackground(fun2())
//  //        case clr:Color=>label.setBackground(clr)
//  //        case _ =>println("no colour function was passed")
//  //
//  //      }
//  //      label.setOpaque(true)
//  //      panel.add(label)
//  ////      this.setVisible(true)
//  ////      this.setSize(600,100)
//  //
//  ////      this.pack()
//  //    }
//  //  }
//  //  //7n pass drawer w controller w create initial badal mykhod state
//  //  //btakhod drawer w controller w 2 strings ely 7ytktbo 3la el labels bto3 el textbox w function b init el board
//  //  def initGame(drawerfn: (Array[Array[Int]],Option[Int] )=> Unit,input1:String,input2:Option[String],initState:()=>(Array[Array[Int]],Option[Int]),controller:(GameState,String)=>Option[ GameState]) = {
//  //    /////////controller (board,Option[ player ], input1,Option[ input2 ])//////////////////////////////////////////
//  //    /////////make object of type newState 3shan n -update el variable ely 7 passo lelcontroller tny
//  //    /////////kol el 2 players el player of type string
//  //    ///////// drawer btakhod tuple (string,label) //returned from initial
//  //    ///////// controller return type Either[board,(board,Int)]
//  //    //initializing input GUI
//  //    var state = initState()
//  //    val inputPanel = new JPanel()
//  //    val button = new JButton("Apply Move")
//  //    val t1=new JTextField(5)
//  //    val frame=new JFrame("input")
//  //    inputPanel.add(button)
//  //    inputPanel.add(t1)
//  //    frame.add(inputPanel, "South")
//  //    frame.setBounds(1000,400,300,100)
//  //    frame.setVisible(true)
//  //    button.addActionListener(new AbstractAction() {
//  //      override def actionPerformed(e: ActionEvent): Unit = {
//  //        val input1 = t1.getText()
//  //            println(input1)
//  //            controller(state, input1) match {
//  //              case Some(newState) => drawerfn(newState._1,newState._2)
//  //                                      state=newState
//  //              case None => JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")
//  //            }
//  //          }})
//  //
//  //    drawerfn(state._1,state._2)
//  //  }
//  def drawBoard(howToDrawFunction:(Int,Int,Int)=>JLabel,title:String,hgap:Int,vgap:Int):(Array[Array[Int]],Option[Int])=>Unit ={ //el function dy btb2a ghza takhod el grid 3latol
//
//    val frame=new JFrame(title)
//    (state,player)=> {
//
//      //removing the previous game state
//      frame.getContentPane.removeAll()
//
//      //creating the board
//      val panel = new JPanel(new GridLayout(state.length+1, state(0).length+1,hgap,vgap))
//      //    panel.setLayout(new CircleLayout(true));
//      Array.concat(Array(Array.range('a','a'+state(0).length)),state).zipWithIndex.foreach((row) => {
//        Array.concat(Array((row._2+48)),row._1).zipWithIndex.foreach(
//          tile => panel.add(howToDrawFunction(tile._1, row._2, tile._2))
//        )})
//      frame.add(panel,"Center")
//
//      //in case the game is multiplayer
//      player match {
//        case Some(value)=>
//          val lbl =new JLabel("current player is:"+ value.toString,SwingConstants.CENTER)
//          lbl.setFont(new Font("Verdana", Font.PLAIN, 18))
//          lbl.setPreferredSize(new Dimension(80*state.length, 50))
//          lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3))
//          frame.add(lbl,"South")
//        case None=> println("no player")
//      }
//
//      frame.setSize(80*state.length,80*state(0).length+100)
//      frame.setVisible(true)
//    }
//  }
//
//  def draw(getWhichAscii:Any=>Any,getBackColor:Any):(Int,Int,Int)=>JLabel={
//
//    (piece,i,j)=>{
//      val label = new JLabel(getWhichAscii(piece).toString)
//      if (9<piece && piece<20)  label.setForeground(Color.RED);
//      label.setForeground(Color.RED)
//      label.setFont(new Font("Serif", Font.PLAIN, 50))
//      label.setHorizontalAlignment(SwingConstants.CENTER)
//      getBackColor match{
//        case fun:((Int,Int)=>Color)=> label.setBackground(fun(i,j))
//        case fun2:(()=>Color)=> if (i!=0 && j==0) label.setBackground(fun2())
//        case clr:Color=> if (i!=0 && j!=0) label.setBackground(clr)
//        case _ =>println("no colour function was passed")
//      }
//      //      label.setIcon(new ImageIcon(RED_ICON))
//      label.setOpaque(true)
//      label
//    }
//  }
//  def initGame(drawerfn: (Array[Array[Int]],Option[Int] )=> Unit,initState:()=>(Array[Array[Int]],Option[Int]),controller:(GameState,String)=>Option[ GameState]) = {
//
//    //creating the input frame
//    val frame=new JFrame("input")
//    val inputPanel = new JPanel()
//    val button = new JButton("Apply Move")
//    val inputbox=new JTextField(5)
//    inputPanel.add(button)
//    inputPanel.add(inputbox)
//    frame.add(inputPanel, "South")
//    frame.setBounds(1000,400,300,100)
//    frame.setVisible(true)
//
//    //initializing the game state
//    var state = initState()
//    drawerfn(state._1,state._2)
//
//    //adding button event listener ....
//    button.addActionListener(new AbstractAction() {
//      override def actionPerformed(e: ActionEvent): Unit = {
//        val input = inputbox.getText()
//        controller(state, input) match {
//          case Some(newState) => drawerfn(newState._1,newState._2)
//            state=newState
//          case None => JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")
//        }
//      }})
//
//  }
//
//  ////////////////TEST chess//////////////////////////////////////////////////////
//
//  val howToDrawBoardTile=draw(connect4Obj.getChessASCII,getCheckersBackgroundColor)
//  val drawer=drawBoard(howToDrawBoardTile,"chess",0,0)
//  //  initGame(drawer,connect4Obj.initChessBoard,connect4Obj.chessController)
//  //  this.setVisible(true)
//
//  ////////////////TEST CONNECT4//////////////////
//  val connect4Tile=draw(connect4Obj.getConnect4Ascii,new Color(0,0,255))
//  val connect4drawer= drawBoard(connect4Tile,"connect 4",3,0)
//  //  initGame(connect4drawer,connect4Obj.initConnect4Board,connect4Obj.connect4Controller)
//
//  //////////////Test 8qs///////////////////////////////////////////
//  val eightQueensTile=draw(connect4Obj.get8QsAscii,connect4Obj.get8QsBackgroundColor)
//  val eightQueensDrawer=drawBoard(eightQueensTile,"eight queens",0,0)
//  //  initGame(eightQueensDrawer,connect4Obj.init8QsBoard,connect4Obj.eightQueensController)
//  ///////////////////////Test tic tac toe/////////////////////////////////////////////////
//
//  val TicTacToeTile = draw(connect4Obj.gettictactoesAscii, connect4Obj.get8QsBackgroundColor)
//  val TicTacToeDrawer = drawBoard(TicTacToeTile ,"Tic tac toe",2,2)
//  //  initGame(TicTacToeDrawer, connect4Obj.initTTTBoard, connect4Obj.tictactoeController)
//
//  ////////////////////////TEST CHECKERS//////////////////////////////////////////
//
//  val checkersTile= draw(getCheckersAscii,getCheckersBackgroundColor)
//  val checkersDrawer= drawBoard(checkersTile,"checkers",0,0)
//  //  initGame(checkersDrawer,initCheckerState,connect4Obj.checkersController)
//
//  ///////////////////////TEST SUDOKU /////////////////////////////////
//
//  val sudokoTile=draw(connect4Obj.getSudokuAscii,connect4Obj.sudokuColours)
//  val sudokuDrawer=drawBoard(sudokoTile,"Sudoku",3,3)
//  //  initGame(sudokuDrawer,initSudokuState,connect4Obj.sudokuController)
//}