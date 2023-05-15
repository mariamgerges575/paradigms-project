import { Abstract_game_engine } from "./Abstract_game_engine.js";
import {eightQueens} from "./eightqueens.js"
import { tic_tac_toe } from "./tic-tac-toe.js";
import { Checkers } from "./Checker.js";
import { Chess } from "./Chess.js";
import { connect4 } from "./connect4.js";
import {sudoku} from "./sudoku.js";

var gamee;
var butt=document.getElementById("but");
switch(localStorage.getItem("choose"))
{
    case 'eightqueens':
    {
        gamee=new eightQueens ();
        break;
    }
    case 'sudoku':
    {
        gamee=new  sudoku();
        break;
    }
    case 'checkers':
    {
        gamee=new Checkers ();
        break;
    }
    case 'connect4':
    {
        gamee=new connect4();
        break;
    }
    case 'tictactoe':
    {
        gamee=new tic_tac_toe();
        break;
    }
    case 'chess':
    {
        gamee=new Chess ();
        break;
    }
}
gamee.Initialize()


// console.log(localStorage.getItem("choose"));
//     butt.addEventListener("click",function(){
//     gamee.takeUserInput()});