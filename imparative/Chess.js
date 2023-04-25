class Chess extends Abstract_game_engine{
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
        console.log(state)

        let row_num=-1;
        for (let i = -1; i < state.length+1; i++) {
            row_num=row_num+1;
            let col_letter="A";
            const row = document.createElement("tr");
            for (let j = -1; j < state[0].length+1; j++) {


                const cell = document.createElement("td");
                cell.style='height:80px;width:80px;margin:1px;background-color: white;vertical-align: middle;text-align:center;font-size: 50px;box-shadow: #000;';

                if((i===-1||i===state.length) && (j!==-1&&j!==state.length)){
                    cell.innerHTML=col_letter;
                    cell.style.backgroundColor = 'white';
                    col_letter=String.fromCharCode(col_letter.charCodeAt(0)+1)
                    cell.style.fontFamily='Copperplate';
                }
                if((i!==-1&&i!==state.length)&& (j===-1||j=== state.length)){
                    cell.innerHTML=row_num.toString();
                    cell.style.backgroundColor = 'white';
                    cell.style.fontFamily='Copperplate';
                }

                else if(j!==-1&& i!==-1 && j!==state.length&& i!== state.length) {
                    if (!(i % 2 == 1 ^ j % 2 == 0)) {
                        cell.style.backgroundColor = '#aaaaaa';

                    }
                    else {
                        cell.style.backgroundColor = '#7e1616';
                    }

                    console.log(i);
                    console.log(j);
                    cell.innerHTML = this.getPieceASCII(state[i][j]);
                }

                row.appendChild(cell);

            }

            tblBody.appendChild(row);
        }
        tbl.appendChild(tblBody);

        if(this.currentPlayer===1){
        document.getElementById("turn").innerHTML="White Player Turn";}
        else {document.getElementById("turn").innerHTML="Black Player Turn";}

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


    isValidMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        console.log(this.board);
        const piece = this.board[fromRow][fromCol];
        console.log(fromRow);
        console.log(fromCol);
        console.log(toRow);
        console.log(toCol);
        console.log(this.currentPlayer);
        if( !(this.isCellInBounds(fromRow,fromCol)&& this.isCellInBounds(toRow,toCol))){
            // console.log(fromRow);
            // console.log(fromCol);
            // console.log(toRow);
            // console.log(toCol);
            console.log("e3");
            return false
        }
        // Check if the piece belongs to the current player
        if (this.getPieceColor(piece) !== this.currentPlayer) {
            console.log("e1");
            console.log(this.getPieceColor(piece));
            console.log(this.currentPlayer);
            return false;
        }

        // Check if the destination cell is not occupied by a piece of the same color
        if (this.getPieceColor(this.board[toRow][toCol]) === this.currentPlayer) {
            console.log("e2");
            return false;
        }



        // Check if the move is valid for the piece
        switch (piece.toLowerCase()) {
            case 'p':
                return this.isValidPawnMove(move);
            case 'r':
                return this.isValidRookMove(move);
            case 'n':
                return this.isValidKnightMove(move);
            case 'b':
                return this.isValidBishopMove(move);
            case 'q':
                return this.isValidQueenMove(move);
            case 'k':
                return this.isValidKingMove(move);
            default:
                return false;
        }
    }

    start(){
        console.log(this.board);


        this.makeMove(this);



    }

    applyMove(move) {
        // TODO: implement logic for applying chess moves

        this.board[move.toRow][move.toCol]=this.board[move.fromRow][move.fromCol];
        this.board[move.fromRow][move.fromCol]=' ';
        console.log(this.board);
        console.table(this.board);

    }




    isGameOver() {
        // TODO: implement logic for checking if the this is over
    }


    takeUserInput() {
        return new Promise(resolve => {
            const Input1 = document.getElementById("from");
            const Input2 = document.getElementById('to');
            const applyButton = document.getElementById("Move");
            applyButton.addEventListener("click", () => {
                const fromCell = Input1.value;
                const toCell = Input2.value;
                const userInput={fromCell,toCell};
                resolve(userInput);
            });
        });
    }
    ClearInput(){
        document.getElementById('from').value='';
        document.getElementById('to').value='';
    }
    Controller(userInput) {
        // Get the user input for the move

        this.ClearInput();
        // Convert the user input to the corresponding row and column indices
        let fromMove = this.FindRowCol(userInput.fromCell);
        let fromRow=fromMove.Row;
        let fromCol=fromMove.Col;

        let toMove = this.FindRowCol(userInput.toCell);
        let toRow=toMove.Row;
        let toCol=toMove.Col;

        console.log(fromRow);
        console.log(fromCol);


        // Check if the move is valid
        if ( this.isValidLength(userInput.fromCell,2)&& this.isValidLength(userInput.toCell,2)&&this.isValidMove({ fromRow, fromCol, toRow, toCol })) {
            // Apply the move to the this state
            this.applyMove({ fromRow, fromCol, toRow, toCol })
            this.SwitchPlayers();

            // Return the updated this state
            return ;
        } else {
            // If the move is not valid, throw an error or return null
            alert("Invalid move");
            return;
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
    isValidPawnMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = this.board[fromRow][fromCol];
        const color = this.getPieceColor(piece);
        const enemy_piece = this.board[toRow][toCol];
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

    isValidRookMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = this.board[fromRow][fromCol];

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
            if (this.board[row][col] !== ' ') {
                return false;
            }
            row += stepRow;
            col += stepCol;
        }

        return true;
    }

    isValidKnightMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = this.board[fromRow][fromCol];

        // Check if the knight is moving in an L-shape
        if (Math.abs(toRow - fromRow) === 2 && Math.abs(toCol - fromCol) === 1) {
            return true;
        } else if (Math.abs(toRow - fromRow) === 1 && Math.abs(toCol - fromCol) === 2) {
            return true;
        }

        return false;
    }

    isValidBishopMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = this.board[fromRow][fromCol];

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
            if (this.board[row][col] !== ' ') {
                return false;
            }
            row += stepRow;
            col += stepCol;
        }

        return true;
    }

    isValidQueenMove(move) {
        return this.isValidRookMove(move, this) || this.isValidBishopMove(move, this);
    }

    isValidKingMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = this.board[fromRow][fromCol];

        // Check if the king is moving to a neighboring cell
        if (Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1) {
            return true;} } }

const chessGame=new Chess();
// console.log(chessGame.board);
// console.log(chessGame.currentPlayer);
chessGame.Initialize();
// document.getElementById("Move").addEventListener("click",chessGame.makeMove)
