import {Abstract_game_engine} from "./Abstract_game_engine.js"
export class Chess extends Abstract_game_engine{
    constructor() {
       super();
    }

    Drawer(state){
        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        const tbl = document.createElement("table");
        tbl.setAttribute("id","tablee");
        const tblBody = document.createElement("tbody");
        console.log(state.board)
        let col_letter="A";
        let row_num=1;
        for (let i = -1; i < state.board.length; i++) {
            const row = document.createElement("tr");
            for (let j = -1; j < state.board[0].length; j++) {
                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:1px;background-color: white;vertical-align: middle;text-align:center;font-size: 50px;box-shadow: #000;';
                if(i===-1 && j!==-1){
                    cell.innerHTML=col_letter;
                    cell.style.backgroundColor = 'white';
                    col_letter=String.fromCharCode(col_letter.charCodeAt(0)+1)
                    cell.style.fontFamily="Copperplate";
                    cell.style.fontSize="40px";
                }
                if(i!==-1&& j===-1){
                    cell.innerHTML=row_num.toString();
                    cell.style.backgroundColor = 'white';
                    cell.style.fontFamily="Copperplate";
                    row_num=row_num+1;
                    cell.style.fontSize="40px";
                }

                else if(j!==-1&& i!==-1) {
                    if (!(i % 2 == 1 ^ j % 2 == 0)) {
                        cell.style.backgroundColor = '#aaaaaa';

                    }
                    else {
                        cell.style.backgroundColor = '#7e1616';
                    }

                    console.log(i);
                    console.log(j);
                    cell.innerHTML = this.getPieceASCII(state.board[i][j]);
                }

                row.appendChild(cell);

            }

            tblBody.appendChild(row);
        }
        tbl.appendChild(tblBody);
        // document.getElementById("label1").innerHTML="From Cell :"
        // document.getElementById("label2").innerHTML="To   Cell :"
        if (state.currentPlayer==1){
            document.getElementById("turn").innerHTML="WHITE Player Turn";
        }
        else { document.getElementById("turn").innerHTML="BLACK Player Turn";}


        document.body.appendChild(tbl);


        tbl.style="border-style:solid;border:2"

    }

    createBoard() {


        let state=[
            ['R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'],
            ['P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'],
            [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
            [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
            [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
            [' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '],
            ['p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'],
            ['r', 'n', 'b', 'q', 'k', 'b', 'n', 'r']
        ];
        return state;

    }


    isValidMove(state,move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        console.log(state.board);
        console.log(fromRow);
        console.log(fromCol);
        console.log(toRow);
        console.log(toCol);
        if( !(this.isCellInBounds(state.board,fromRow,fromCol)&& this.isCellInBounds(state.board,toRow,toCol))){
            console.log(fromRow);
            console.log(fromCol);
            console.log(toRow);
            console.log(toCol);
            console.log("e");
            return false
        }
        const piece = state.board[fromRow][fromCol];
        console.log(fromRow);
        console.log(fromCol);
        console.log(toRow);
        console.log(toCol);
        console.log(state.currentPlayer);

        // Check if the piece belongs to the current player
        if (this.getPieceColor(piece) != state.currentPlayer) {
            console.log("e1");
            console.log(this.getPieceColor(piece));
            console.log(state.currentPlayer);
            return false;
        }

        // Check if the destination cell is not occupied by a piece of the same color
        if (this.getPieceColor(state.board[toRow][toCol]) === state.currentPlayer) {
            console.log("e2");
            return false;
        }



        // Check if the move is valid for the piece
        switch (piece.toLowerCase()) {
            case 'p':
                return this.isValidPawnMove(state,move);
            case 'r':
                return this.isValidRookMove(state,move);
            case 'n':
                return this.isValidKnightMove(state,move);
            case 'b':
                return this.isValidBishopMove(state,move);
            case 'q':
                return this.isValidQueenMove(state,move);
            case 'k':
                return this.isValidKingMove(state,move);
            default:
                return false;
        }
    }

    start(){
        console.log(state.board);


        this.makeMove(this);



    }
    InputMessage() {
        return "Enter Input from and to cell ex: 2a 3a"
    }

    applyMove(state,move) {
        // TODO: implement logic for applying chess moves

        state.board[move.toRow][move.toCol]=state.board[move.fromRow][move.fromCol];
        state.board[move.fromRow][move.fromCol]=' ';
        console.log(state.board);
        console.table(state.board);

    }




    isGameOver() {
        // TODO: implement logic for checking if the this is over
    }


    // takeUserInput(){
    //    this.takeUserInput2();
    // }
    
    Controller(state,Input) {
        // Get the user input for the move
        let str=Input.split(" ")  ////////
        if(!this.isValidLength(str,2)){return null}////////
        const fromCell=str[0]//////////////
        const toCell=str[1]
        console.log(fromCell);
        console.log(toCell);
        // this.ClearInput('firstInput');
        // this.ClearInput('secondInput');
        // Convert the user input to the corresponding row and column indices
        let fromMove = this.FindRowCol(fromCell);
        let fromRow=fromMove.Row;
        let fromCol=fromMove.Col;

        let toMove = this.FindRowCol(toCell);
        let toRow=toMove.Row;
        let toCol=toMove.Col;

        console.log(fromRow);
        console.log(fromCol);


        // Check if the move is valid
        if ( this.isValidLength(fromCell,2)&& this.isValidLength(toCell,2)&&this.isValidMove(state,{ fromRow, fromCol, toRow, toCol })) {
            // Apply the move to the this state
            this.applyMove(state,{ fromRow, fromCol, toRow, toCol })
            this.SwitchPlayers(state);
            // console.table(state.board)
            return state

            
            // Return the updated this state
          
        } else {
            // If the move is not valid, throw an error or return null

            return null
        

            // or return null
            // return null;
        }

    }

    // Helper functions for checking piece color and checking if a cell is within bounds
    getPieceColor(piece) {
        if (piece === ' ') {
            return null;
        } else if (piece.toUpperCase() === piece) {
            return 1;
        } else {
            return 0;
        }
    }

    getPieceASCII(piece){
        switch(piece){
            case 'p': return '&#9823';
            case 'r': return '&#9820';
            case 'n': return '&#9822';
            case 'b': return '&#9821';
            case 'q': return '&#9819';
            case 'k': return '&#9818';
            case 'P': return '&#9817';
            case 'R': return '&#9814';
            case 'N': return '&#9816';
            case 'B': return '&#9815';
            case 'Q': return '&#9813';
            case 'K': return '&#9812';
            default: return '';
        }




    }



    // Functions for validating moves for each piece type
    isValidPawnMove(state,move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = state.board[fromRow][fromCol];
        const color = this.getPieceColor(piece);
        const enemy_piece = state.board[toRow][toCol];
        const enemy_color = this.getPieceColor(enemy_piece);


        // Check if the pawn is moving in the correct direction
        if (color === 1 && toRow <= fromRow) {
            console.log("piece is white");
            return false;
        } else if (color === 0 && toRow >= fromRow) {
            console.log("piece is black");
            return false;
        }

        if(enemy_color!=null) //try to kill
        {
            if((toRow===fromRow+1 && (toCol===fromCol+1||toCol===fromCol-1))||(toRow===fromRow-1 && (toCol===fromCol+1||toCol===fromCol-1))){
                return true;

            }
            else return false;

        }
        else if(toCol!==fromCol){
            return false;
        }
        else if(fromRow===1|| fromRow===6){
            if(toRow===fromRow+1|| toRow===fromRow+2 || toRow===fromRow-1|| toRow===fromRow-2 ){
                return true;
            }
            return false;
        }
        else if(toRow===fromRow+1|| toRow===fromRow-1 ){
            return true;
        }


        return false;
    }

    isValidRookMove(state,move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = state.board[fromRow][fromCol];

        // Check if the rook is moving along a row or column
        if (fromRow !== toRow && fromCol !== toCol) {
            return false;
        }

        // Check if there are any pieces blocking the rook's path
        const stepRow = fromRow === toRow ? 0 : (toRow - fromRow) / Math.abs(toRow - fromRow);
        const stepCol = fromCol === toCol ? 0 : (toCol - fromCol) / Math.abs(toCol - fromCol);
        let row = fromRow + stepRow;
        let col = fromCol + stepCol;
        while (row !== toRow || col !== toCol) {
            if (state.board[row][col] !== ' ') {
                return false;
            }
            row += stepRow;
            col += stepCol;
        }

        return true;
    }

    isValidKnightMove(state,move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = state.board[fromRow][fromCol];

        // Check if the knight is moving in an L-shape
        if (Math.abs(toRow - fromRow) === 2 && Math.abs(toCol - fromCol) === 1) {
            return true;
        } else if (Math.abs(toRow - fromRow) === 1 && Math.abs(toCol - fromCol) === 2) {
            return true;
        }

        return false;
    }

    isValidBishopMove(state,move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = state.board[fromRow][fromCol];

        // Check if the bishop is moving along a diagonal
        if (Math.abs(toRow - fromRow) !== Math.abs(toCol - fromCol)) {
            return false;
        }

        // Check if there are any pieces blocking the bishop's path
        const stepRow = (toRow - fromRow) / Math.abs(toRow - fromRow);
        const stepCol = (toCol - fromCol) / Math.abs(toCol - fromCol);
        let row = fromRow + stepRow;
        let col = fromCol + stepCol;
        while (row !== toRow || col !== toCol) {
            if (state.board[row][col] !== ' ') {
                return false;
            }
            row += stepRow;
            col += stepCol;
        }

        return true;
    }

    isValidQueenMove(state,move) {
        return this.isValidRookMove(state,move) || this.isValidBishopMove(state,move);
    }

    isValidKingMove(state,move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = state.board[fromRow][fromCol];

        // Check if the king is moving to a neighboring cell
        if (Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1) {
            return true;} } }

// const chessGame=new Chess();
// chessGame.Initialize();

