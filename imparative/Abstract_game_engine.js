export class  Abstract_game_engine {

    constructor() {
        if (new.target === Abstract_game_engine) {
            throw new TypeError("Cannot construct Abstract instances directly");
        }



    }

     async Initialize(theGame) {
         let board = this.createBoard() //intial board
         console.log(board)
         let currentPlayer = 1
         let state={board,currentPlayer}
         state.board=board
         state.currentPlayer=currentPlayer
         this.Drawer(state)
         await this.sleep(1000)

         const loop = async () => {
             await this.sleep(1000)
             let new_state = this.Controller(state, this.takeUserInput(theGame));
             console.table(state)
             if (new_state != null) {
                 this.Drawer(new_state)
                 state=new_state
             } else {
                 alert("Invalid Move")
             }
             loop(); // Call the function again to repeat the loop
         }
         loop(); // Call the function to start the loop
     }


    // Used in Constructor to create the game initial board (2D array) based on its type
    createBoard() {
        throw new Error("createBoard method must be implemented");
    }
    Drawer(gameState) {
        throw new Error("Drawer method must be implemented");
        
    }
    // takeUserInput1() {
    //     let input1 = document.getElementById("firstInput").value;
    //     this.Controller(input1);
    // }
    // takeUserInput2() {
    //     let input1 = document.getElementById("firstInput").value;
    //     let input2 = document.getElementById("secondInput").value;
    //     this.Controller(input1,input2);
    // }
    takeUserInput()
    {
        let input= prompt(this.InputMessage())
        return input

    }
    InputMessage(){
        throw new Error("InputMessage method must be implemented");
    }
    Controller(state,Input) {
        throw new Error("Controller method must be implemented");
    }

    SwitchPlayers(state){
        state.currentPlayer=!state.currentPlayer;
    }

    ClearInput(id){
        document.getElementById(id).value='';
    }

    FindRowCol(Input){
        const Row = parseInt(Input[0].charCodeAt(0)) - 49;
        const Col = Input.toLowerCase().charCodeAt(1) - 97;
        return {Row,Col};
    }
   
    isValidLength(Input,expectedLength){
        return Input.length === expectedLength;
    }
    // isValidLength(Input1,expectedLength1,Input2,expectedLength2){
    //     return (Input1.length === expectedLength1 )&&(Input2.length === expectedLength2);
    // }

    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    isCellInBounds(board,row, col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }
    drawFisrtRow(style){
        for (let i = 0 ;i< state[0].length; i++){
            const cell = document.createElement("td");
            cell.style=style//'height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            row.appendChild(cell);
        }
    }

    // isCellInBounds(row1, col1,row2,col2) {
    //     return (row1 >= 0 && row1 < this.rowNumbers && col1 >= 0 && col1 < this.colNumbers)&&(row2 >= 0 && row2 < this.rowNumbers && col2 >= 0 && col2 < this.colNumbers);
    // }





}
