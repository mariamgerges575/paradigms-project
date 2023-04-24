class Abstract_game_engine {
    constructor() {
        if (new.target === Abstract_game_engine) {
            throw new TypeError("Cannot construct Abstract instances directly");
        }
        this.board = this.createBoard();

    }


   // Drawer Functions
    Draw(gameState) {
        throw new Error("Draw method must be implemented");
    }
    getPieceASCII(piece) {
        throw new Error("getPieceASCII method must be implemented");
    }


    // Used in Constructor to create the game initial board (2D array) based on its type
    createBoard() {
        throw new Error("createBoard method must be implemented");
    }


    //Controller functions

    // Read user Input from textBox
    takeUserInput() {
        throw new Error("takeUserInput method must be implemented");
    }
    // first function in the controller which tries to make the move and applies it if possible
    // Calls takeUserInput() then validates input using isValidMove() and finally calls applyMove(move) to change state
    makeMove() {
        throw new Error("makeMove method must be implemented");
    }
    isValidMove(move) {
        throw new Error("isValidMove method must be implemented");
    }
    //helper function for isValidMove()
    isCellInBounds(row, col) {
        throw new Error("isCellInBounds method must be implemented");
    }
    // Change the game state
    // takes object move which contains the data for a specific game move as parameter
    // example : chess-> move{fromRow, fromCol,toRow,toCol}
    // example : sudoko-> move{number,toRow,toCol}
    applyMove(move) {
        throw new Error("applyMove method must be implemented");
    }





    //TODO
    // start() {
    //     throw new Error("start method must be implemented");
    // }
    // SOME GAMES HAVE 1 PLAYER ONLY
    // switchTurn() {
    //     throw new Error("switchTurn method must be implemented");
    // }
    // SUDOKO AND XO DO NOT NEED getPiecColor()
    // SUGGESTED SOLUTION : CHANGE IT TO getPieceInfo() TO BE MORE GENERAL AND RETURN THE NEEDED INFO BASED ON THE GAME
    // RETURN TYPE WOULD BE AN OBJECT PIECE{color,text,etc}
    // getPieceColor(piece) {
    //     throw new Error("getPieceColor method must be implemented");
    // }
    // New method to get the game state
    // getGameState() {
    //     return {
    //         board: this.board,
    //         currentPlayer: this.currentPlayer
    //     };
    // }
}
