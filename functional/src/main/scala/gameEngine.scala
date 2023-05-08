import java.awt._
import java.awt.event.ActionEvent
import javax.swing._
import javax.swing.border.Border


object gameEngine  {
  val drawerObj= new Drawers
  val initObj =new InitialBoards
  val connect4Obj = new Connect4Gamee

  type currentPlayer=Option[Int]
  type Board=Array[Array[Int]]
  type GameState=(Board,currentPlayer)


  def gameEngine(drawerfn: (GameState )=> Unit,initState:()=>(GameState),controller:(GameState,String)=>Option[ GameState]) = {

    //creating the input frame
    val frame=new JFrame("input")
    val inputPanel = new JPanel()
    val button = new JButton("Apply Move")
    val inputbox=new JTextField(5)
    inputPanel.add(button)
    inputPanel.add(inputbox)
    frame.add(inputPanel, "South")
    frame.setBounds(1000,400,300,100)
    frame.setVisible(true)

    //initializing the game state
    var state = initState()
    drawerfn(state._1,state._2)

    //adding button event listener ....
    button.addActionListener(new AbstractAction() {
      override def actionPerformed(e: ActionEvent): Unit = {
        val input = inputbox.getText()
        controller(state, input) match {
          case Some(newState) => drawerfn(newState._1,newState._2)
            state=newState
          case None => JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")
        }
      }})

  }

//  ////////////////TEST chess//////////////////////////////////////////////////////
//  gameEngine(drawerObj.chessDrawer,initObj.initChessBoard,connect4Obj.chessController)
//
//  ////////////////TEST CONNECT4//////////////////
//  gameEngine(drawerObj.connect4Drawer,initObj.initConnect4Board,connect4Obj.connect4Controller)
//
//  //////////////Test 8qs///////////////////////////////////////////
//  gameEngine(drawerObj.eightQueensDrawer,initObj.init8QsBoard,connect4Obj.eightQueensController)
//
//  ///////////////////////Test tic tac toe/////////////////////////////////////////////////
//  gameEngine(drawerObj.ticTacToeDrawer, initObj.initTTTBoard, connect4Obj.tictactoeController)
//
//  ////////////////////////TEST CHECKERS//////////////////////////////////////////
//  gameEngine(drawerObj.checkersDrawer,initObj.initCheckerState,connect4Obj.checkersController)

  ///////////////////////TEST SUDOKU /////////////////////////////////

  gameEngine(drawerObj.sudokuDrawer,initObj.initSudokuState,connect4Obj.sudokuController)
}

