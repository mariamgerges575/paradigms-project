import java.awt._
import java.awt.event.ActionEvent
import javax.swing._


object gameEngine extends JFrame("boardGames") {
  val chessObj= new ChessGamee

  //  val currentPlayer=1
  //  val gameState =Array( Array(0, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0))


  type currentPlayer=Option[Int]
  type Board=Array[Array[Int]]
  type GameState=(Board,currentPlayer)

  val initCheckerState:()=>(Array[Array[Int]],Option[(Int,JLabel)])=()=>(Array( Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(1, 1, 1, 1, 1, 1, 1, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0)),Some(1,new JLabel()))

  val getCheckersAscii:Any=>Char=(p)=> p match { //btakhod int w t-return el ascii
    case 0 => 9898
    case 1 => 9899
    case -1 => ' '
    case '0'=> ' '
    case s:Int => s.toChar
  }
  val getCheckersBackgroundColor:(Int,Int)=>Color= (i, j)=> //this function takes row , coloumn and return the suitable back ground colour ,
    (i==0||j==0)match {
      case true=> new Color(255,255,255)          //literals
      case false => {
        ((i+j) % 2 == 0) match {
          case true => new Color(232, 235, 239)
          case false => new Color(125, 135, 150)
        }
      }
    }

  def drawBoard(howToDrawFunction:JPanel=>(Int,Option[(Int,Int)])=>Unit):Array[Array[Int]]=>Option[((Option[Int],Option[JLabel]))]=>Unit ={ //el function dy btb2a ghza takhod el grid 3latol
    (state)=>(player)=> {
      val panel = new JPanel(new GridLayout(state.length+1, state.length+1))
      Array.concat(Array(Array.range('a','a'+state.length)),state).zipWithIndex.foreach((row) => {
        Array.concat(Array((row._2+48)),row._1).zipWithIndex.foreach(
          tile => howToDrawFunction(panel)(tile._1, Some(row._2, tile._2)))})
      this.add(panel,"Center")
      player match {
        case Some(value)=>
          value._2 match {
            case Some(lbl)=>lbl.setText("Current player: " + value._1.getOrElse(5).toString)
          }
        case _=>println("none")
      }
      this.pack()
    }
  }

  def draw(getWhichAscii:Any=>Char,getBackColor:Any):(JPanel)=>(Int,Option[(Int,Int)])=>Unit={
    (panel)=>(piece:Int,ij:Option[(Int,Int)])=>{

      val label = new JLabel(getWhichAscii(piece).toString)
      label.setFont(new Font("Serif", Font.PLAIN, 50))
      label.setHorizontalAlignment(SwingConstants.CENTER)
      getBackColor match{
        case fun:((Int,Int)=>Color)=>
          ij match {
            case Some(value)=>label.setBackground(fun(value._1,value._2))
          }
        case fun2:(()=>Color)=>label.setBackground(fun2())
        case clr:Color=>label.setBackground(clr)
        case _ =>println("no colour function was passed")

      }
      label.setOpaque(true)
      panel.add(label)
      this.pack()
    }
  }
  //7n pass drawer w controller w create initial badal mykhod state
  //btakhod drawer w controller w 2 strings ely 7ytktbo 3la el labels bto3 el textbox w function b init el board
  def initGame(drawerfn: Array[Array[Int]]=>Option[((Option[Int],Option[JLabel]))] => Unit,input1:String,input2:Option[String],initState:()=>(Array[Array[Int]],Option[Int],Option[JLabel]),controller:(Board,currentPlayer,String,Option[String])=>Option[ GameState]) = {
    /////////controller (board,Option[ player ], input1,Option[ input2 ])//////////////////////////////////////////
    /////////make object of type newState 3shan n -update el variable ely 7 passo lelcontroller tny
    /////////kol el 2 players el player of type string
    ///////// drawer btakhod tuple (string,label) //returned from initial
    ///////// controller return type Either[board,(board,Int)]
    val init=initState()
    var state = init._1
    var currentPlayer=init._2
    val playerlbl=init._3
    val inputPanel = new JPanel()
    val button = new JButton("Apply Move")
//    var currentPLayer:Option[Int]=None //will get passed to the controller
    inputPanel.add(playerlbl.getOrElse(new JPanel()))
    inputPanel.add(button)
    val l1=new JLabel(input1)
    val t1=new JTextField(3)
    inputPanel.add(l1)
    inputPanel.add(t1)

    input2 match {
      case Some(value) =>
        val l2 = new JLabel(value)
        val t2 = new JTextField(3)
        inputPanel.add(l2)
        inputPanel.add(t2)
        button.addActionListener(new AbstractAction() {
          override def actionPerformed(e: ActionEvent): Unit = {
            val input1 = t1.getText()
            val input2 = t2.getText()
            println(input2)
            println(input1)
            controller(state,currentPlayer, input1, Some(input2)) match {
              case Some(newState) => drawerfn(newState._1)(Some((newState._2,playerlbl)))
                                      currentPlayer=newState._2
                                    state=newState._1
              case None => JOptionPane.showMessageDialog(null, "Invalid move. Please try again.")

            }

          }})

      case None =>
        button.addActionListener(new AbstractAction() {
          override def actionPerformed(e: ActionEvent): Unit = {
            val input1 = t1.getText()
            println(input1)
            //call controller
          }})
    }

    this.add(inputPanel, "South")
    drawerfn(state)(Some(currentPlayer,playerlbl))
  }

  ////////////////TEST//////////////////////////////////////////////////////

  val howToDrawBoardTile=draw(chessObj.getPieceASCII,getCheckersBackgroundColor)
  val drawer=drawBoard(howToDrawBoardTile)
  initGame(drawer,"to",Some("from"),chessObj.initChessBoard,chessObj.controller)
//  initGame(drawer,"from",Some("to"),initCheckerState)
  this.setVisible(true)
}