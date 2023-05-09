import { Abstract_game_engine } from "./Abstract_game_engine.js";
import {eightQueens} from "./eightqueens.js"
import { tic_tac_toe } from "./tic-tac-toe.js";
import { Checkers } from "./Checker.js";
import { Chess } from "./Chess.js";
import { connect4 } from "./connect4.js";
import {sudoku} from "./sudoku.js";
// console.log(localStorage.getItem("choose"));
var gamee;
var butt=document.getElementById("but");
switch(localStorage.getItem("choose"))
{
    case 'eightqueens':
    {
        gamee=new eightQueens ();
        gamee.Initialize();
        break;
    }
    case 'sudoku':
    {
        gamee=new  sudoku();
        gamee.Initialize();
        break;
    }
    case 'checkers':
    {
        gamee=new Checkers ();
        gamee.Initialize();
        break;
    }
    case 'connect4':
    {
        gamee=new connect4();
        gamee.Initialize();
        break;
    }
    case 'tictactoe':
    {
        gamee=new tic_tac_toe();
        console.log("hoho");
        gamee.Initialize();


        break;
    }
    case 'chess':
    {
        gamee=new Chess ();
        gamee.Initialize();
        break;
    }


}

console.log(localStorage.getItem("choose"));
   
    // butt.addEventListener("click",gamee.takeUserInput)  ;
    butt.addEventListener("click",function(){
    gamee.takeUserInput()});