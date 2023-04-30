import java.awt._
import javax.swing._
//3yza a3rf eh ely mmkn yt7at fel class 3dy ka attributesss
//el elgoz2 elsabeet ely 3latol mawgod fel gui ??(mmkn nb2a bn pass function btrsem el hgat el initial we7na bncreate el le3ba w el function dy kan already mt-pass-leha 7gat
//3yzen nfkr el pattern matching 7y7sal ezy 8aleban f 7ett en mra 7nakhod etnen iputs w mara wa7ed
//lesa fl al3ab ely b etnenat mfkrtesh el label byt-handle ezy
//yarab ya mosahellllllllllllllllllllll
object CheckersGUI extends JFrame("Checkers") {
  val currentPlayer=1
  val gameState =Array( Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0))
  val squareSize = 50 // mmkn yb2a hardcoded
  val numRows = gameState.length //   mmkn nb2a ngbhom f sa3etha
  val numCols = gameState(0).length

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////initializationnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn/////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //3yz yt2asem functions brdo ka2enena bnteach it how to initialize
  //function for letters row
  //function for numbers row
  //function textboxes
  //maybe function for label
  val boardPanel = new JPanel(new GridLayout(numRows, numCols))

  // Create the label for displaying the current player turn
  val currentPlayerLabel = new JLabel("Current player: " )
  val colLabelPanel = new JPanel(new GridLayout(1, numCols))
  val rowLabelPanel = new JPanel(new GridLayout(numRows, 1))

  // Create the input fields and button
  val fromField = new JTextField(3)
  val toField = new JTextField(3)
  val button = new JButton("Apply Move")

  // Add the input fields and button to the GUI
  val inputPanel = new JPanel()
  inputPanel.add(currentPlayerLabel)
  inputPanel.add(new JLabel("From:"))
  inputPanel.add(fromField)
  inputPanel.add(new JLabel("To:"))
  inputPanel.add(toField)
  inputPanel.add(button)
  this.add(inputPanel, BorderLayout.SOUTH)
  //el arkam ely fel gmb
  for (row <- numRows until 0 by -1 ) {
    val label = new JLabel((row).toString)
    label.setHorizontalAlignment(SwingConstants.CENTER)
    label.setPreferredSize(new Dimension(squareSize, squareSize))
    rowLabelPanel.add(label)
  }
  //el7orof ely fo2
  this.add(rowLabelPanel, BorderLayout.WEST)
  val label = new JLabel()
  label.setHorizontalAlignment(SwingConstants.CENTER)
  label.setPreferredSize(new Dimension(squareSize, squareSize))
  colLabelPanel.add(label)
  for (col <- 0 until numCols) {
    val label = new JLabel(('a' + col).toChar.toString)
    label.setHorizontalAlignment(SwingConstants.CENTER)
    label.setPreferredSize(new Dimension(squareSize, squareSize))
    colLabelPanel.add(label)
  }
  this.add(colLabelPanel, BorderLayout.NORTH)
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  val getAscii:Int=>Char=(p)=> p match { //btakhod int w t-return el ascii
    case 0 => 9898
    case 1 => 9899
    case -1 => ' '
  }
  val getBackgroundColor:(Int,Int)=>Color= (i, j)=> //this function takes row , coloumn and return the suitable back ground colour ,
    ((i+j) % 2 == 0) match {                        //mesh 3rfa lw el7eta dy ynf3 ne3mel beha pattern matching based on type fel al3ab ely el background feha sbta
      case true => new Color(232, 235, 239)
      case false => new Color(125, 135, 150)
    }

  def drawBoard(howToDrawFunction:(Int,Int,Int)=>Unit): (Array[Array[Int]])=>Unit = (state)=>{ //el function dy btb2a ghza takhod el grid 3latol
    state.zipWithIndex.foreach((row)=>{
      row._1.zipWithIndex.foreach(
        tile=>howToDrawFunction(tile._1,row._2,tile._2))
    })
    this.add(boardPanel, BorderLayout.CENTER)
    this.pack()
  }
  def draw(getWhichAscii:Int=>Char,getBackColor:(Int,Int)=>Color):(Int,Int,Int)=>Unit={  //btakhod el function ely bt3rf tgeb el ascii w el function ely bt3rf tgeb elbackground
    //mesh 3rfa lw khalenaha btkhod function bgeb elcurrentplayer 7nb2a keda sy2enha wla la w 7yb2a eh elwad3 3shan tmshy 3la kolo m7taga search

    (piece,i,j)=>{
      val label = new JLabel(getWhichAscii(piece).toString)
      label.setFont(new Font("Serif", Font.PLAIN, 50))
      label.setHorizontalAlignment(SwingConstants.CENTER)
      label.setBackground(getBackColor(i,j))
      println()
      label.setOpaque(true)
      boardPanel.add(label)
    }

  }
  /////////////test////////////////////////////////////////////
  val drawBasedOnAsciiAndColourFunction=draw(getAscii,getBackgroundColor)
  val readyToTakeTheState=drawBoard(drawBasedOnAsciiAndColourFunction)
  val trying_with_one=draw(getAscii,_)
  val ha=trying_with_one(_)


  val trying_to_use_a_function_with_one=try
    readyToTakeTheState(this.gameState)
//  this.setVisible(true)
}