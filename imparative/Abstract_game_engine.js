class Abstract_game_engine {

    constructor() {
        if (new.target === Abstract_game_engine) {
            throw new TypeError("Cannot construct Abstract instances directly");
        }

        this.board = this.createBoard();
        this.row=this.board.length;
        this.col=this.board[0].length;
        this.currentPlayer=1;

    }

     Initialize() {
        this.Drawer(this.board);
        const loop = () => {
            this.takeUserInput().then(userInput => {
                this.Controller(userInput);
                this.Drawer(this.board);
                loop(); // Call the function again to repeat the loop
            });
        };
        loop(); // Call the function to start the loop
    }


    // Used in Constructor to create the game initial board (2D array) based on its type
    createBoard() {
        throw new Error("createBoard method must be implemented");
    }
    Drawer(gameState) {
        throw new Error("Drawer method must be implemented");
    }
    takeUserInput() {
        throw new Error("takeUserInput method must be implemented");
    }
    Controller(Input) {
        throw new Error("Controller method must be implemented");
    }

    SwitchPlayers(){
        this.currentPlayer===1? this.currentPlayer=0:this.currentPlayer=1;
    }



    FindRowCol(Input){
        const Row = parseInt(Input[0]) - 1;
        const Col = Input.charCodeAt(1) - 97;

        return {Row,Col};

    }
    isValidLength(Input,expectedLength){
        return Input.length === expectedLength;
    }


    isCellInBounds(row, col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }




}
