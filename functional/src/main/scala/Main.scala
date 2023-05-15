import java.awt.event.ActionEvent
import javax.swing.{AbstractAction, JButton, JFrame, JOptionPane, JPanel, JTextField}
import scala.io.StdIn

    object Main extends App {

        def initialScreen(): Unit = {
            println("CHOOSE GAME :")
            println("1.Chess")
            println("2.Checkers")
            println("3.Sudoku")
            println("4.Eight Queens")
            println("5.Connect 4")
            println("6.Tic Tac Toe")
            println("7.Exit")
            var game = StdIn.readLine()
            var Exit = false
            while (!Exit) {
                game match {
                    case "1" => gameEngine(chessDrawer, initChessBoard, chessController)
                    case "2" => gameEngine(checkersDrawer, initCheckerState, checkersController)
                    case "3" => gameEngine(sudokuDrawer, initSudokuState, sudokuController)
                    case "4" => gameEngine(eightQueensDrawer, init8QsBoard, eightQueensController)
                    case "5" => gameEngine(connect4Drawer, initConnect4Board, connect4Controller)
                    case "6" => gameEngine(ticTacToeDrawer, initTTTBoard, tictactoeController)
                    case "7" => sys.exit()
                    case _ => println("Invalid Input")
                }
                game = StdIn.readLine("CHOOSE GAME :")
            }
        }
  
        initialScreen()


    }
