
import java.awt.{Color, Dimension, Font, GridLayout, Window}
import javax.swing.{AbstractAction, BorderFactory, JButton, JFrame, JLabel, JOptionPane, JPanel, JTextField, SwingConstants}

  def removeFrameContent(title: String): JFrame = {
    var frame: JFrame = null
    val openWindows: Array[Window] = Window.getWindows
    for (window <- openWindows) {
      window match {
        case f: javax.swing.JFrame if f.getTitle == title => frame = f
        case _ =>
      }
    }
    if (frame != null) frame.getContentPane.removeAll()
    frame
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def checkersDrawer(state: GameState): Unit = {
    def getAscii(p: Any): Char = p match {
      case 0 => 9898
      case 1 => 9899
      case 2 => 9812
      case 3 => 9818
      case -1 | '0' => ' '
      case s: Int => s.toChar
    }

    def getBackgroundColor(i: Int, j: Int): Color =
      i == 0 || j == 0 match {
        case true => new Color(255, 255, 255) //literals
        case false =>
          (i + j) % 2 == 0 match {
            case true => new Color(232, 235, 239)
            case false => new Color(125, 135, 150)
          }
      }

    val frame = if (removeFrameContent("checkers") == null) new JFrame("checkers") else removeFrameContent("checkers")
    val panel = new JPanel(new GridLayout(state._1.length + 1, state._1(0).length + 1))
    Array.concat(Array(Array.range('a', 'a' + state._1(0).length)), state._1).zipWithIndex.foreach((row) => {
      Array.concat(Array((row._2 + 48)), row._1).zipWithIndex.foreach(
        tile => {
          val piece = tile._1
          val i = row._2
          val j = tile._2
          val label = new JLabel(getAscii(piece).toString)
          label.setFont(new Font("Serif", Font.PLAIN, 50))
          label.setBackground(getBackgroundColor(i, j))
          label.setHorizontalAlignment(SwingConstants.CENTER)
          label.setOpaque(true)
          panel.add(label)
        }
      )
    })
    val currentPlayer = if (state._2.getOrElse(-1) == 0) "white" else "black"
    val lbl = new JLabel("current player is:" + currentPlayer, SwingConstants.CENTER)
    lbl.setFont(new Font("Verdana", Font.PLAIN, 18))
    lbl.setPreferredSize(new Dimension(80 * state._1.length, 50))
    lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3))
    frame.add(lbl, "South")
    frame.add(panel, "Center")
    frame.setSize(80 * state._1.length, 80 * state._1(0).length + 100)
    frame.setVisible(true)
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  def chessDrawer(state: GameState): Unit = {
    def getAscii(piece: Int): Char = {
      if (piece == 'p')
        9823
      else if (piece == 'P')
        9817
      else if (piece == 'n')
        9822
      else if (piece == 'N')
        9816
      else if (piece == 'x')
        9821
      else if (piece == 'X')
        9815
      else if (piece == 'q')
        9819
      else if (piece == 'Q')
        9813
      else if (piece == 'k')
        9818
      else if (piece == 'K')
        9812
      else if (piece == 'r')
        9820
      else if (piece == 'R')
        9814
      else if (piece == ' ' || piece == '0')
        32
      else
        piece.toChar
    }

    def getBackgroundColor(i: Int, j: Int): Color =
      i == 0 || j == 0 match {
        case true => new Color(255, 255, 255) //literals
        case false =>
          (i + j) % 2 == 0 match {
            case true => new Color(232, 235, 239)
            case false => new Color(125, 135, 150)
          }
      }

    val frame = if (removeFrameContent("chess") == null) new JFrame("chess") else removeFrameContent("chess")
    val panel = new JPanel(new GridLayout(state._1.length + 1, state._1(0).length + 1))
    Array.concat(Array(Array.range('a', 'a' + state._1(0).length)), state._1).zipWithIndex.foreach((row) => {
      Array.concat(Array((row._2 + 48)), row._1).zipWithIndex.foreach(
        tile => {
          val piece = tile._1
          val i = row._2
          val j = tile._2
          val label = new JLabel(getAscii(piece).toString)
          label.setFont(new Font("Serif", Font.PLAIN, 50))
          label.setBackground(getBackgroundColor(i, j))
          label.setHorizontalAlignment(SwingConstants.CENTER)
          label.setOpaque(true)
          panel.add(label)
        }
      )
    })
    val currentPlayer = if (state._2.getOrElse(-1) == 0) "black" else "white"
    val lbl = new JLabel("current player is:" + currentPlayer, SwingConstants.CENTER)
    lbl.setFont(new Font("Verdana", Font.PLAIN, 18))
    lbl.setPreferredSize(new Dimension(80 * state._1.length, 50))
    lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3))
    frame.add(lbl, "South")
    frame.add(panel, "Center")
    frame.setSize(80 * state._1.length, 80 * state._1(0).length + 100)
    frame.setVisible(true)
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def ticTacToeDrawer(state: GameState): Unit = {
    def getAscii(p: Int): Char = p match { //btakhod int w t-return el ascii
      case '❌' => '❌'
      case '⭕' => '⭕'
      case '0' => ' '
      case s: Int => s.toChar
    }

    def getBackgroundColor(i: Int, j: Int): Color =
      i == 0 || j == 0 match {
        case true => new Color(255, 255, 255) //literals
        case false =>
          (i + j) % 2 == 0 match {
            case true => new Color(232, 235, 239)
            case false => new Color(125, 135, 150)
          }
      }

    val frame = if (removeFrameContent("Tic-Tac-Toe") == null) new JFrame("Tic-Tac-Toe") else removeFrameContent("Tic-Tac-Toe")
    val panel = new JPanel(new GridLayout(state._1.length + 1, state._1(0).length + 1))
    Array.concat(Array(Array.range('a', 'a' + state._1(0).length)), state._1).zipWithIndex.foreach((row) => {
      Array.concat(Array((row._2 + 48)), row._1).zipWithIndex.foreach(
        tile => {
          val piece = tile._1
          val i = row._2
          val j = tile._2
          val label = new JLabel(getAscii(piece).toString)
          label.setFont(new Font("Serif", Font.PLAIN, 50))
          label.setBackground(getBackgroundColor(i, j))
          label.setHorizontalAlignment(SwingConstants.CENTER)
          label.setOpaque(true)
          panel.add(label)
        }
      )
    })
    val currentPlayer = if (state._2.getOrElse(-1) == 0) " O's " else " X's "
    val lbl = new JLabel(currentPlayer + " player turn", SwingConstants.CENTER)
    lbl.setFont(new Font("Verdana", Font.PLAIN, 18))
    lbl.setPreferredSize(new Dimension(80 * state._1.length, 50))
    lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3))
    frame.add(lbl, "South")
    frame.add(panel, "Center")
    frame.setSize(80 * state._1.length, 80 * state._1(0).length + 100)
    frame.setVisible(true)
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def connect4Drawer(state: GameState): Unit = {

    val frame = if (removeFrameContent("connect4") == null) new JFrame("connect4") else removeFrameContent("connect4")
    val panel = new JPanel(new GridLayout(state._1.length + 1, state._1(0).length + 1))
    Array.concat(Array(Array.range('a', 'a' + state._1(0).length)), state._1).zipWithIndex.foreach((row) => {
      row._1.foreach(
        tile => {
          val piece = tile
          val i = row._2
          val label = new JLabel(if (piece == 'r' || piece == 'y' || piece == ' ') 9899.toChar.toString else piece.toChar.toString)
          label.setFont(new Font("Serif", Font.PLAIN, 50))
          label.setBackground(if (i == 0) new Color(255, 255, 255) else new Color(0, 0, 0))
          label.setForeground(if (piece == 'r') new Color(255, 0, 0) else if (piece == 'y') new Color(255, 255, 0) else if (piece == ' ') new Color(255, 255, 255) else new Color(0, 0, 0))
          label.setHorizontalAlignment(SwingConstants.CENTER)
          label.setOpaque(true)
          panel.add(label)
        }
      )
    })
    val currentPlayer = if (state._2.getOrElse(-1) == 0) " RED " else " YELLOW "
    val lbl = new JLabel(currentPlayer + " player turn", SwingConstants.CENTER)
    lbl.setFont(new Font("Verdana", Font.PLAIN, 18))
    lbl.setPreferredSize(new Dimension(80 * state._1.length, 50))
    lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3))
    frame.add(lbl, "South")
    frame.add(panel, "Center")
    frame.setSize(80 * state._1.length, 50 * state._1(0).length + 100)
    frame.setVisible(true)
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  def eightQueensDrawer(state: GameState): Unit = {

    def getBackgroundColor(i: Int, j: Int): Color =
      i == 0 || j == 0 match {
        case true => new Color(255, 255, 255) //literals
        case false =>
          (i + j) % 2 == 0 match {
            case true => new Color(232, 235, 239)
            case false => new Color(125, 135, 150)
          }
      }

    val frame = if (removeFrameContent("Eight Queens") == null) new JFrame("Eight Queens") else removeFrameContent("Eight Queens")
    val panel = new JPanel(new GridLayout(state._1.length + 1, state._1(0).length + 1))
    Array.concat(Array(Array.range('a', 'a' + state._1(0).length)), state._1).zipWithIndex.foreach((row) => {
      Array.concat(Array((row._2 + 48)), row._1).zipWithIndex.foreach(
        tile => {
          val piece = tile._1
          val i = row._2
          val j = tile._2
          val label = new JLabel(if (piece == '0') "" else piece.toChar.toString)
          label.setFont(new Font("Serif", Font.PLAIN, 50))
          label.setBackground(getBackgroundColor(i, j))
          label.setHorizontalAlignment(SwingConstants.CENTER)
          label.setOpaque(true)
          panel.add(label)
        }
      )
    })

    frame.add(panel, "Center")
    frame.setSize(80 * state._1.length, 80 * state._1(0).length)
    frame.setVisible(true)
  }

  def sudokuDrawer(state: GameState): Unit = {

    def getBackgroundColor(i: Int, j: Int): Color =
      i == 0 || j == 0 match {
        case true => new Color(255, 255, 255) //literals
        case false =>
          ((i - 1) / 3 + (j - 1) / 3) % 2 == 0 match {
            case true => new Color(232, 235, 239)
            case false => new Color(125, 135, 150)
          }
      }

    val frame = if (removeFrameContent("sudoku") == null) new JFrame("sudoku") else removeFrameContent("sudoku")
    val panel = new JPanel(new GridLayout(state._1.length + 1, state._1(0).length + 1, 3, 3))
    Array.concat(Array(Array.range('a', 'a' + state._1(0).length)), state._1).zipWithIndex.foreach((row) => {
      Array.concat(Array((row._2 + 48)), row._1).zipWithIndex.foreach(
        tile => {
          val piece = tile._1
          val i = row._2
          val j = tile._2
          val label = new JLabel(if (piece == '0' || piece == 0) "" else if (piece < 20) (piece % 10).toString else piece.toChar.toString)
          label.setFont(new Font("Serif", Font.PLAIN, 40))
          label.setBackground(getBackgroundColor(i, j))
          if (piece > 9 && piece < 20) label.setForeground(new Color(140, 0, 0))
          label.setHorizontalAlignment(SwingConstants.CENTER)
          label.setOpaque(true)
          panel.add(label)
        }
      )
    })
    frame.add(panel, "Center")
    frame.setSize(70 * state._1.length, 70 * state._1(0).length + 10)
    frame.setVisible(true)

}