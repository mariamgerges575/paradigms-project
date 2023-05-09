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
          let state = {board, currentPlayer}
          state.board = board
          state.currentPlayer = currentPlayer
          this.Drawer(state)

          while (true) {
              await this.sleep(1000)
              let input = this.takeUserInput()
              console.log(input)
              let new_state = this.Controller(state, input);
              console.table(state)
              if (new_state != null) {
                this.Drawer(new_state)
                state = new_state
                  
              } else {
                  alert("Invalid Move")
              }

          }

      }


    // Used in Constructor to create the game initial board (2D array) based on its type
    createBoard() {
        throw new Error("createBoard method must be implemented");
    }
    Drawer(gameState) {
        throw new Error("Drawer method must be implemented");
        
    }
    Controller(state,Input) {
        throw new Error("Controller method must be implemented");
    }
    InputMessage(){
        throw new Error("InputMessage method must be implemented");
    }
    takeUserInput() {

        let input = prompt(this.InputMessage())
        return input

    }

    FindRowCol(Input){
        const Row = parseInt(Input[0].charCodeAt(0)) - 49;
        const Col = Input.toLowerCase().charCodeAt(1) - 97;
        return {Row,Col};
    }
   
    isValidLength(Input,expectedLength){
        return Input.length === expectedLength;
    }
    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
    isCellInBounds(board,row, col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }
    SwitchPlayers(state){
        state.currentPlayer=!state.currentPlayer;
    }


}
