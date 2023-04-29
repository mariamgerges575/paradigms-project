import java.awt._
import java.awt.event.ActionEvent
import javax.swing._

object ChessGUI extends JFrame("Chess") {

  // Create the game state
  val gameState = Array(
    Array('R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'),
    Array('P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'),
    Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
    Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
    Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
    Array(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '),
    Array('p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'),
    Array('r', 'n', 'b', 'q', 'k', 'b', 'n', 'r')
  )


  // Create the ChessGame object
  val game = new ChessGame(gameState)

  // Create the label for displaying the current player turn
  val currentPlayerLabel = new JLabel("Current player: " + game.currentPlayer)

  val colLabelPanel = new JPanel(new GridLayout(1, numCols))
  val rowLabelPanel = new JPanel(new GridLayout(numRows, 1))


  // Create the input fields and button
  val fromField = new JTextField(3)
  val toField = new JTextField(3)
  val button = new JButton("Apply Move")
  val numRows = gameState.length
  val numCols = gameState(0).length
  val boardPanel = new JPanel(new GridLayout(numRows, numCols))

  // Add the input fields and button to the GUI
  val inputPanel = new JPanel()
  inputPanel.add(currentPlayerLabel)
  inputPanel.add(new JLabel("From:"))
  inputPanel.add(fromField)
  inputPanel.add(new JLabel("To:"))
  inputPanel.add(toField)
  inputPanel.add(button)
  this.add(inputPanel, BorderLayout.SOUTH)

  // Define the action to take when the button is clicked
  button.addActionListener(new AbstractAction() {
    override def actionPerformed(e: ActionEvent): Unit = {
      val fromString = fromField.getText()
      val toString = toField.getText()
      val from = (fromString.substring(0, 1).toInt - 1, fromString.substring(1).toLowerCase()(0) - 'a')
      val to = (toString.substring(0, 1).toInt - 1, toString.substring(1).toLowerCase()(0) - 'a')
      if (game.applyMove(from, to)) {
        game.draw()
        boardPanel.removeAll()
        rowLabelPanel.removeAll()
        colLabelPanel.removeAll()
        drawBoard()
      } else {
        JOptionPane.showMessageDialog(null, "Invalid move", "Error", JOptionPane.ERROR_MESSAGE)
      }
    }
  })

  // Define the dimensions of the board squares
  val squareSize = 50

  def getPieceASCII(piece: String): String = {
    if (piece == 'p')
      return "&#9823"

    if (piece == 'P') {
      return "&#9817"
    }
    if (piece == 'n') {
      return "&#9822"
    }
    if (piece == 'N') {
      return "&#9816"
    }
    if (piece == 'b') {
      return "&#9821"
    }
    if (piece == 'B') {
      return "&#9815"
    }
    if (piece == 'q') {
      return "&#9819"
    }
    if (piece == 'Q') {
      return "&#9813"
    }
    if (piece == 'k') {
      return "&#9818"
    }
    if (piece == 'K') {
      return "&#9812"
    }
    if (piece == 'r') {
      return "&#9820"
    }
    if (piece == "R") {
      return "&#9814"
    }
    else return " "

  }

  // Define the Drawer function
  def drawBoard(): Unit = {
    // Create the panel for the row labels
    currentPlayerLabel.setText("Current player: " + game.currentPlayer)
  }
}