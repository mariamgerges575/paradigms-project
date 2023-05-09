var choose
function chosenGame(game)
{
    console.log(game);
    localStorage.removeItem("choose");
    switch(game)
    {
      case "eightqueens":
        localStorage.setItem("choose","eightqueens");
      break;
      case "sudoku":
        localStorage.setItem("choose","sudoku");
      break;
      case "checkers":
        localStorage.setItem("choose","checkers");
      break;
      case "connect4":
        localStorage.setItem("choose","connect4");
      break;
      case "tictactoe":
        localStorage.setItem("choose","tictactoe");
      break;
      case "chess":
        localStorage.setItem("choose","chess");
      break;
    }
    
}
