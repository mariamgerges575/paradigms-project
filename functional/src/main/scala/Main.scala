import java.awt.event.ActionEvent
import javax.swing.{AbstractAction, JButton, JFrame, JOptionPane, JPanel, JTextField}

object Main extends App {
    

  //  ////////////////TEST chess///////////////////////////////////////
    gameEngine(chessDrawer,initChessBoard,chessController)
  //
  //  ////////////////TEST CONNECT4///////////////////////////////////
  //  gameEngine(connect4Drawer,initConnect4Board,connect4Controller)
  //
  //  //////////////Test 8qs///////////////////////////////////////////
  //  gameEngine(eightQueensDrawer,init8QsBoard,eightQueensController)
  //
  //  ///////////////////////Test tic tac toe////////////////////////////
  //  gameEngine(ticTacToeDrawer, initTTTBoard, tictactoeController)
  //
  //  ////////////////////////TEST CHECKERS//////////////////////////////
  //  gameEngine(checkersDrawer,initCheckerState,checkersController)

  //  ///////////////////////TEST SUDOKU /////////////////////////////////
  //  gameEngine(sudokuDrawer,initSudokuState,sudokuController)
}