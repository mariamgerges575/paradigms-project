import java.awt._
import java.awt.event.ActionEvent
import javax.swing._


object gameEngine extends JFrame("boardGames") {
  val chessObj= new ChessGame

  //  val currentPlayer=1
  //  val gameState =Array( Array(0, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0),
  //                        Array(1, 1, 1, 1, 1, 1, 1, 1),
  //                        Array(0, 0, 0, 0, 0, 0, 0, 0))

  val initChessBoard:()=>(Array[Array[Int]],Option[(String,JLabel)])={()=>
    (this.initialBoard,Some("black",new JLabel()))
  }
  type currentPlayer=String
  type Board=Array[Array[Int]]
  type GameState= (Board,currentPlayer)

  val initialBoard:Array[Array[Int]] = (Array.tabulate(8, 8)((i, j) => {
    (i, j) match {
      case (0, 0) => 'r'
      case (0, 1) => 'n'
      case (0, 2) => 'x'
      case (0, 3) => 'q'
      case (0, 4) => 'k'
      case (0, 5) => 'x'
      case (0, 6) => 'n'
      case (0, 7) => 'r'
      case (1, j) => 'p'
      case (6, j) => 'P'
      case (7, 0) => 'R'
      case (7, 1) => 'N'
      case (7, 2) => 'X'
      case (7, 3) => 'Q'
      case (7, 4) => 'K'
      case (7, 5) => 'X'
      case (7, 6) => 'N'
      case (7, 7) => 'R'
      case _ => ' '
    }
  }))
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

  def drawBoard(howToDrawFunction:JPanel=>(Int,Option[(Int,Int)])=>Unit):Array[Array[Int]]=>(Option[(String,JLabel)])=>Unit ={ //el function dy btb2a ghza takhod el grid 3latol
    (state)=>(player)=> {
      val panel = new JPanel(new GridLayout(state.length+1, state.length+1))
      Array.concat(Array(Array.range('a','a'+state.length)),state).zipWithIndex.foreach((row) => {
        Array.concat(Array((row._2+48)),row._1).zipWithIndex.foreach(
          tile => howToDrawFunction(panel)(tile._1, Some(row._2, tile._2)))})
      this.add(panel,"Center")
      player match {
        case Some(value)=>
          value._2.setText("Current player: " + value._1)
          println("entered")
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
  def initGame(drawerfn: Array[Array[Int]]=>Option[(String,JLabel)] => Unit,input1:String,input2:Option[String],initState:()=>(Array[Array[Int]],Option[(String,JLabel)]),controller:(GameState,String,String)=>Option[ GameState]) = {
    /////////controller (board,Option[ player ], input1,Option[ input2 ])//////////////////////////////////////////
    /////////make object of type newState 3shan n -update el variable ely 7 passo lelcontroller tny
    /////////kol el 2 players el player of type string
    ///////// drawer btakhod tuple (string,label) //returned from initial
    ///////// controller return type Either[board,(board,Int)]
    val init=initState()
    var state = init._1
    val inputPanel = new JPanel()
    val button = new JButton("Apply Move")
    var currentPLayer:Option[String]=None //will get passed to the controller
    init._2 match {
      case Some(value)=>
        inputPanel.add(value._2)
        currentPLayer=Some(value._1)
    }
    inputPanel.add(button)
    val l1=new JLabel(input1)
    val t1=new JTextField(3)
    inputPanel.add(l1)
    inputPanel.add(t1)
    var currPlayer: String = currentPLayer.getOrElse("black")
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

            //call controller
//            val currPlayer=

            val lbl:(String,JLabel)=init._2.getOrElse("str",new JLabel())

            controller((state,currPlayer), input1, input2) match {
              case Some(newState) => {drawer(newState._1)(Some((newState._2,lbl._2)))
                                      currPlayer=newState._2
                                        state=newState._1}
              case None => chessObj.showErrorMessage();

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
    drawerfn(state)(init._2)
  }

  ////////////////TEST//////////////////////////////////////////////////////

  val howToDrawBoardTile=draw(chessObj.getPieceASCII,getCheckersBackgroundColor)
  val drawer=drawBoard(howToDrawBoardTile)
  initGame(drawer,"to",Some("from"),initChessBoard,chessObj.controller)
//  initGame(drawer,"from",Some("to"),initCheckerState)
  this.setVisible(true)
}