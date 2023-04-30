
import java.awt._
import java.awt.event._
import javax.swing._

object InitialScreen extends JFrame {

//  val chess=ChessGUI
  // Create the label and buttons
  val chessButton = new JButton("Chess")
  val checkersButton = new JButton("Checkers")
  val sudokuButton = new JButton("Sudoku")
  val ticTacToeButton = new JButton("Tic Tac Toe")
  val queensButton = new JButton("8 Queens")
  val connect4Button = new JButton("Connect 4")

  // Create the panel for the buttons
  val buttonPanel = new JPanel()
  buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS))
  val buttonSize = new Dimension(200, 40)
  override val insets = new Insets(5, 0, 5, 0)
  buttonPanel.add(Box.createVerticalGlue())
  buttonPanel.add(chessButton)
  buttonPanel.add(Box.createVerticalStrut(10))
  chessButton.setPreferredSize(buttonSize)
  chessButton.setMaximumSize(buttonSize)
  chessButton.setAlignmentX(Component.CENTER_ALIGNMENT)
  chessButton.setMargin(insets)
  buttonPanel.add(checkersButton)
  buttonPanel.add(Box.createVerticalStrut(10))
  checkersButton.setPreferredSize(buttonSize)
  checkersButton.setMaximumSize(buttonSize)
  checkersButton.setAlignmentX(Component.CENTER_ALIGNMENT)
  checkersButton.setMargin(insets)
  buttonPanel.add(sudokuButton)
  buttonPanel.add(Box.createVerticalStrut(10))
  sudokuButton.setPreferredSize(buttonSize)
  sudokuButton.setMaximumSize(buttonSize)
  sudokuButton.setAlignmentX(Component.CENTER_ALIGNMENT)
  sudokuButton.setMargin(insets)
  buttonPanel.add(ticTacToeButton)
  buttonPanel.add(Box.createVerticalStrut(10))
  ticTacToeButton.setPreferredSize(buttonSize)
  ticTacToeButton.setMaximumSize(buttonSize)
  ticTacToeButton.setAlignmentX(Component.CENTER_ALIGNMENT)
  ticTacToeButton.setMargin(insets)
  buttonPanel.add(queensButton)
  buttonPanel.add(Box.createVerticalStrut(10))
  queensButton.setPreferredSize(buttonSize)
  queensButton.setMaximumSize(buttonSize)
  queensButton.setAlignmentX(Component.CENTER_ALIGNMENT)
  queensButton.setMargin(insets)
  buttonPanel.add(connect4Button)
  buttonPanel.add(Box.createVerticalStrut(10))
  connect4Button.setPreferredSize(buttonSize)
  connect4Button.setMaximumSize(buttonSize)
  connect4Button.setAlignmentX(Component.CENTER_ALIGNMENT)
  connect4Button.setMargin(insets)
  buttonPanel.add(Box.createVerticalGlue())

  // Add the panel to the GUI
  this.add(buttonPanel)

  // Define the action to take when a button is clicked
  def buttonClicked(gameName: String): Unit = {

   if(gameName=="Chess") {
     var chess=new ChessGame()

     chess.chessGUI.setSize(getSize())
   chess.Initialize()
   }
    if(gameName=="Checkers"){
      var checkersGUI=CheckersGUI
      checkersGUI.setSize(this.getSize)
      checkersGUI.setVisible(true)

    }
    this.hide()
//    JOptionPane.showMessageDialog(null, "You clicked the " + gameName + " button!")

  }

  // Add event listeners to the buttons
  chessButton.addActionListener((e: ActionEvent) => buttonClicked("Chess"))
  checkersButton.addActionListener((e: ActionEvent) => buttonClicked("Checkers"))
  sudokuButton.addActionListener((e: ActionEvent) => buttonClicked("Sudoku"))
  ticTacToeButton.addActionListener((e: ActionEvent) => buttonClicked("Tic Tac Toe"))
  queensButton.addActionListener((e: ActionEvent) => buttonClicked("8 Queens"))
  connect4Button.addActionListener((e: ActionEvent) => buttonClicked("Connect 4"))

  // Set the title of the GUI
  this.setTitle("Choose Game")

//    this.setContentPane(new JPanel(){
//    val backgroundImage = javax.imageio.ImageIO.read(new File("src/main/scala/bg.jpg") );
//    setContentPane(new JPanel(new BorderLayout()) {
//      override def  paintComponent( g:Graphics) {
//        g.drawImage(backgroundImage, 0, 0, null);
//      }
//    })})


  // Load the background image
  val backgroundImageIcon = new ImageIcon("src/main/scala/bg.jpg")
  val backgroundImage = backgroundImageIcon.getImage()

  // Create a label to hold the background image
  val backgroundLabel = new JLabel(backgroundImageIcon)
  backgroundLabel.setBounds(0, 0, getWidth(), getHeight())

  // Create a panel to hold the label and buttons
  val mainPanel = new JPanel(null)
  mainPanel.add(backgroundLabel)
  mainPanel.add(buttonPanel)
  buttonPanel.setBounds(50, 50, buttonPanel.getPreferredSize().width, buttonPanel.getPreferredSize().height)

  // Set the content pane of the JFrame to the main panel
  this.setContentPane(mainPanel)

  // Add a component listener to the frame to listen for changes to its size
  this.addComponentListener(new ComponentAdapter {
    override def componentResized(e: ComponentEvent): Unit = {
      // Scale the background image to fit the frame
      val scaledBackgroundImage = backgroundImage.getScaledInstance(
        e.getComponent.getWidth,
        e.getComponent.getHeight,
        Image.SCALE_FAST
      )
      // Set the scaled image as the background of the label
      backgroundLabel.setIcon(new ImageIcon(scaledBackgroundImage))
      // Resize and reposition the button panel
      buttonPanel.setBounds(
        (e.getComponent.getWidth - buttonPanel.getPreferredSize().width) / 2,
        (e.getComponent.getHeight - buttonPanel.getPreferredSize().height) / 2,
        buttonPanel.getPreferredSize().width,
        buttonPanel.getPreferredSize().height
      )
    }
  })

  // Set the size and minimum size of the GUI
  this.setSize(new Dimension(2000, 2000))
//  this.setMinimumSize(new Dimension(300, 400))

  // Set the GUI to appear in the center of the screen
  val screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth()
  val screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight()
  val windowWidth = this.getSize().getWidth()
  val windowHeight = this.getSize().getHeight()
  val center_x = ((screenWidth - windowWidth) / 2).toInt
  val center_y = ((screenHeight - windowHeight) / 2).toInt
  this.setLocation(center_x, center_y)

  // Set the button size to adjust to the background image
  chessButton.setPreferredSize(new Dimension(backgroundImage.getWidth(null)/3, 50))
  checkersButton.setPreferredSize(new Dimension(backgroundImage.getWidth(null)/3, 50))
  sudokuButton.setPreferredSize(new Dimension(backgroundImage.getWidth(null)/3, 50))
  ticTacToeButton.setPreferredSize(new Dimension(backgroundImage.getWidth(null)/3, 50))
  queensButton.setPreferredSize(new Dimension(backgroundImage.getWidth(null)/3, 50))
  connect4Button.setPreferredSize(new Dimension(backgroundImage.getWidth(null)/3, 50))

  this.setVisible(true)
}

// Run the GUI

