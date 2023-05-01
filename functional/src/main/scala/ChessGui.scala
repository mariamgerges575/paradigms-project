import java.awt._
import java.awt.event.ActionEvent
import javax.swing._

object ChessGUI extends JFrame("Chess") {

  type currentPlayer = String
  type Board = Array[Array[Char]]
  type GameState = (Array[Array[Char]], currentPlayer)
  // Create the ChessGame object

  var stateGUI:GameState= //THE MUTABLE GUI OBJECT WHICH WILL BE TAKEN AS PARAMETERS TO KEEP THE CONTROLLER CLASS PURE//
    (Array.tabulate(numRows, numCols)((i, j) => {
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
  }),"black")


  // Create the label for displaying the current player turn
  var currentPlayerLabel = new JLabel("Current player: black ")

  var colLabelPanel = new JPanel(new GridLayout(1, numCols))
  var rowLabelPanel = new JPanel(new GridLayout(numRows, 1))

  // Create the input fields and button
  var fromField = new JTextField(3)
  var toField = new JTextField(3)
  var applyMove = new JButton("Apply Move")
  var exit=new JButton("Return")
  var numRows = 8
  var numCols = 8
  var boardPanel = new JPanel(new GridLayout(numRows, numCols))
  var msgCounter=0
  // Add the input fields and button to the GUI
  var inputPanel = new JPanel()
  inputPanel.add(currentPlayerLabel)
  inputPanel.add(new JLabel("From:"))
  inputPanel.add(fromField)
  inputPanel.add(new JLabel("To:"))
  inputPanel.add(toField)
  inputPanel.add(applyMove)
  inputPanel.add(exit)
  this.add(inputPanel, BorderLayout.SOUTH)

  def clearGui(): Unit = {
    boardPanel.removeAll()
    rowLabelPanel.removeAll()
    colLabelPanel.removeAll()
    fromField.setText("")
    toField.setText("")
  }
  def clearInput():Unit={
    fromField.setText("")
    toField.setText("")
  }


  // Define the action to take when the button is clicked


  def Return (): Unit = {
    InitialScreen.setSize(this.getSize)
    InitialScreen.setVisible(true)
    this.dispose()
  }
  exit.addActionListener((e: ActionEvent) => Return)

  // Define the dimensions of the board squares
  val squareSize = 50

  def getPieceASCII(piece: Char): Char = {
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
    if (piece == 'b') {
      return 9821
    }
    if (piece == 'B') {
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
    else return 32

  }
  // Define the Drawer function
  def drawBoard(gameState: GameState): Unit = {
    // Create the panel for the row labels
    currentPlayerLabel.setText("Current player: " +gameState._2 )


    for (row <- numRows until 0 by -1 ) {
      val label = new JLabel((row).toString)
      label.setFont(new Font("Copperplate", Font.BOLD, 30))
      label.setHorizontalAlignment(SwingConstants.CENTER)
      label.setPreferredSize(new Dimension(squareSize, squareSize))
      rowLabelPanel.add(label)
    }
    this.add(rowLabelPanel, BorderLayout.WEST)

    // Create the panel for the column labels

    for (col <- 0 until numCols) {
      val label = new JLabel(('A' + col).toChar.toString)
      label.setFont(new Font("Copperplate", Font.BOLD, 30))
      label.setHorizontalAlignment(SwingConstants.CENTER)
      label.setPreferredSize(new Dimension(squareSize, squareSize))
      colLabelPanel.add(label)
    }

    this.add(colLabelPanel, BorderLayout.NORTH)

    // Create the panel for the board squares

    for (row <- numRows - 1 to 0 by -1) {
      for (col <- 0 until numCols) {
        val label = new JLabel(getPieceASCII(gameState._1(row)(col)).toString)

        label.setFont(new Font("Copperplate", Font.ROMAN_BASELINE, 60))

        label.setHorizontalAlignment(SwingConstants.CENTER)
        label.setPreferredSize(new Dimension(squareSize, squareSize))
        if ((row + col) % 2 == 0) {
          label.setBackground(new Color(200, 200, 200))
        } else {
          label.setBackground(new Color(122,20,10))
        }
        label.setOpaque(true)
        boardPanel.add(label)
      }
    }
    this.add(boardPanel, BorderLayout.CENTER)


    this.pack()
  }





}