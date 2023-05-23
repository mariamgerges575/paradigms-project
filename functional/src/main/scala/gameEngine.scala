import java.awt._
import java.awt.event.ActionEvent
import javax.swing._
import javax.swing.border.Border

type currentPlayer = Option[Int]
type Board =  Array[Array[Int]]
type GameState = (Board, currentPlayer)
def gameEngine(drawerfn: (GameState )=> Unit,initState:()=>(GameState),controller:(GameState,String)=>Option[ GameState]) = {
  //initializing the game state
  var state = initState()

  //creating the input frame
  val frame=new JFrame("input")
  val inputPanel = new JPanel()
  val button = new JButton("Apply Move")
  val inputbox=new JTextField(5)
  val solve = new JButton("Solve")

  inputPanel.add(button)
  inputPanel.add(inputbox)
  var gameSolution: (Board) => Option[Board] = (board) => None
  if(state._2==None) {
    (state._1.length) match {
      case 8 => gameSolution = Solve8Queens;inputPanel.add(solve)
      case 9 => gameSolution = SolveSudoku;inputPanel.add(solve)
    }
  }


  frame.add(inputPanel, "South")
  frame.setBounds(1000,400,300,100)
  frame.setVisible(true)


  drawerfn(state._1,state._2)
  solve.addActionListener(new AbstractAction(){
    override def actionPerformed(e: ActionEvent): Unit = {

      gameSolution(state._1) match
        case Some(solution)=> drawerfn(solution,state._2);
        case None=>JOptionPane.showMessageDialog(null, "No Possible Solution")
    }
  })
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





