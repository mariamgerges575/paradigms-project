import java.awt._
import java.awt.event.ActionEvent
import javax.swing._
import javax.swing.border.Border

type currentPlayer = Option[Int]
type Board =  Array[Array[Int]]
type GameState = (Board, currentPlayer)
def gameEngine(drawerfn: (GameState )=> Unit,initState:()=>(GameState),controller:(GameState,String)=>Option[ GameState]) = {

  //creating the input frame
  val frame=new JFrame("input")
  val inputPanel = new JPanel()
  val button = new JButton("Apply Move")
  val solve = new JButton("Solve")
  val inputbox=new JTextField(5)
  inputPanel.add(button)
  inputPanel.add(solve)
  inputPanel.add(inputbox)
  frame.add(inputPanel, "South")
  frame.setBounds(1000,400,300,100)
  frame.setVisible(true)

  //initializing the game state
  var state = initState()
  drawerfn(state._1,state._2)
  solve.addActionListener(new AbstractAction(){
    override def actionPerformed(e: ActionEvent): Unit = {

      Solve8Queens(state._1) match
      case Some(solution)=> drawerfn(solution,state._2)
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





