class Chess extends Abstract_game_engine{
    constructor() {
       super();
        this.currentPlayer = 'white';
        this.specialMoves = []; // e.g. en passant, castling, promotion
        this.moveHistory = [];
    }

    Draw(gameState){
        console.log(gameState);
        console.log("draw");
        for(let i=0;i<8;i++){
            for(let j=0;j<8;j++){
                let id=i.toString()+j.toString();
                console.log(id);
                let htmlPiece=document.getElementById(id);
                console.log(htmlPiece);
                htmlPiece.innerHTML=this.getPieceASCII(gameState[i][j]);
                console.log(htmlPiece.innerHTML);
            }
        }

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
        // Check if the piece belongs to the current player
        if (this.getPieceColor(piece) !== this.currentPlayer) {
            console.log("e1");
            return false;
        }

        // Check if the destination cell is not occupied by a piece of the same color
        if (this.getPieceColor(this.board[toRow][toCol]) === this.currentPlayer) {
            console.log("e2");
            return false;
        }

        if( !(this.isCellInBounds(fromRow,fromCol)&& this.isCellInBounds(toRow,toCol))){
            // console.log(fromRow);
            // console.log(fromCol);
            // console.log(toRow);
            // console.log(toCol);
            console.log("e3");
            return false
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
    switchTurn(){
        if(this.currentPlayer==="white"){this.currentPlayer="black";}
        else{this.currentPlayer="white";}
        document.getElementById('turn').innerHTML=this.currentPlayer+" Player Turn";
    }
    applyMove(move) {
        // TODO: implement logic for applying chess moves

        this.board[move.toRow][move.toCol]=this.board[move.fromRow][move.fromCol];
        this.board[move.fromRow][move.fromCol]=' ';
        this.Draw(this.board);
        this.switchTurn(this);
        console.log(this.board);
        console.table(this.board);

    }




    isGameOver() {
        // TODO: implement logic for checking if the this is over
    }

    takeUserInput() {


        const fromCell = document.getElementById('from').value;
        const toCell = document.getElementById('to').value;
        return {fromCell,toCell};

    }

    makeMove() {
        // Get the user input for the move
        const { fromCell, toCell } = this.takeUserInput();
        document.getElementById('from').value='';
        document.getElementById('to').value='';

        // Convert the user input to the corresponding row and column indices
        const fromRow = parseInt(fromCell[0]) - 1;
        const fromCol = fromCell.charCodeAt(1) - 97;
        const toRow = parseInt(toCell[0]) - 1;
        const toCol = toCell.charCodeAt(1) - 97;

        console.log(fromRow);
        console.log(fromCol);


        // Check if the move is valid
        if (this.isValidMove({ fromRow, fromCol, toRow, toCol })) {
            // Apply the move to the this state
            this.applyMove({ fromRow, fromCol, toRow, toCol });

            // Return the updated this state
            return ;
        } else {
            // If the move is not valid, throw an error or return null
            alert("Invalid move");
            document.getElementById('from').value='';
            document.getElementById('to').value='';
            // or return null
            // return null;
        }
    }

    // Helper functions for checking piece color and checking if a cell is within bounds
    getPieceColor(piece) {
        if (piece === ' ') {
            return null;
        } else if (piece.toUpperCase() === piece) {
            return 'white';
        } else {
            return 'black';
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

    isCellInBounds(row, col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // Functions for validating moves for each piece type
    isValidPawnMove(move) {
        const { fromRow, fromCol, toRow, toCol } = move;
        const piece = this.board[fromRow][fromCol];
        const color = this.getPieceColor(piece);
        const enemy_piece = this.board[toRow][toCol];
        const enemy_color = this.getPieceColor(enemy_piece);


        // Check if the pawn is moving in the correct direction
        if (color === 'white' && toRow <= fromRow) {
            console.log("piece is white");
            return false;
        } else if (color === 'black' && toRow >= fromRow) {
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
chessGame.Draw(chessGame.board,chessGame);
// document.getElementById("Move").addEventListener("click",chessGame.makeMove)
