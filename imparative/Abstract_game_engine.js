export class  Abstract_game_engine {

    constructor() {
        if (new.target === Abstract_game_engine) {
            throw new TypeError("Cannot construct Abstract instances directly");
        }

        this.board = this.createBoard();
        this.rowNumbers=this.board.length;
        this.colNumbers=this.board[0].length;
        this.currentPlayer=1;

    }

     Initialize() {
        this.Drawer(this.board);
        // const loop = () => {
        //     this.takeUserInput().then(userInput => {
        //         this.Controller(userInput);
        //         this.Drawer(this.board);
        //         loop(); // Call the function again to repeat the loop
        //     });
        // };
        // loop(); // Call the function to start the loop
    }


    // Used in Constructor to create the game initial board (2D array) based on its type
    createBoard() {
        throw new Error("createBoard method must be implemented");
    }
    Drawer(gameState) {
        throw new Error("Drawer method must be implemented");
        
    }
    takeUserInput1() {
        let input1 = document.getElementById("firstInput").value;
        this.Controller(input1);
    }
    takeUserInput2() {
        let input1 = document.getElementById("firstInput").value;
        let input2 = document.getElementById("secondInput").value;
        this.Controller(input1,input2);
    }
    takeUserInput()
    {
        throw new Error("Controller method must be implemented");
    }
    Controller(Input) {
        throw new Error("Controller method must be implemented");
    }

    SwitchPlayers(){
        this.currentPlayer=!this.currentPlayer;
    }

    ClearInput(id){
        document.getElementById(id).value='';
    }

    FindRowCol(Input){
        const Row = parseInt(Input[0]) - 1;
        const Col = Input.charCodeAt(1) - 97;
        return {Row,Col};
    }
   
    isValidLength(Input,expectedLength){
        return Input.length === expectedLength;
    }
    // isValidLength(Input1,expectedLength1,Input2,expectedLength2){
    //     return (Input1.length === expectedLength1 )&&(Input2.length === expectedLength2);
    // }


    isCellInBounds(row, col) {
        return row >= 0 && row < this.rowNumbers && col >= 0 && col < this.colNumbers;
    }

    // isCellInBounds(row1, col1,row2,col2) {
    //     return (row1 >= 0 && row1 < this.rowNumbers && col1 >= 0 && col1 < this.colNumbers)&&(row2 >= 0 && row2 < this.rowNumbers && col2 >= 0 && col2 < this.colNumbers);
    // }





}
