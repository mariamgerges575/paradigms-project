import Abstract_game_engine from Abstract_game_engine.js
class Checkers extends Abstract_game_engine{
    map= new Map();
    
    constructor(){
        this.board = [
            [-1, 1,-1, 1,-1, 1,-1, 1],
            [ 1,-1, 1,-1, 1,-1, 1,-1],
            [-1, 1,-1, 1,-1, 1,-1, 1],
            [-1,-1,-1,-1,-1,-1,-1,-1],
            [-1,-1,-1,-1,-1,-1,-1,-1],
            [ 0,-1, 0,-1, 0,-1, 0,-1],
            [-1, 0,-1, 0,-1, 0,-1, 0],
            [ 0,-1, 0,-1, 0,-1, 0,-1]
        ];
        this.currentPlayer = 1;
        this.map.set(1,'&#9898');
        this.map.set(0, '&#9890');
        console.log("constructor called")

    }
    getInputValue(){
        let input1 = document.getElementById("firstInput").value;
        let input2= document.getElementById("secondInput").value;
        this.controller(input1,input2)
    }
    draw(){
        this.drawer(this.board);
    }
    applyMove(fromRow, fromCol, toRow, toCol) {
       ///es2ly nagui em 3yzen nkhleha btbadel bssssssssss

        this.board[toRow][toCol]=this.board[fromRow][fromCol];
        this.board[fromRow][fromCol]=-1;
        
        console.log(this.board);

    }

    controller(from,to){
        console.log(from)
        console.log(to)
        if (this.isNotValidLength(from,to)){
            alert("invalid input")
            return
        }
        const fromRow = from[0] - 1;
        const fromCol = from.charCodeAt(1) - 97;
        const toRow = to[0] - 1;
        const toCol = to.charCodeAt(1) - 97;
        if (this.isValidMove(fromRow, fromCol, toRow, toCol)){
            let hSteps=toRow-fromRow
            let vSteps=toCol-fromCol
            this.applyMove(fromRow, fromCol, toRow, toCol)
            //keda keda 7n apply move
            //lw wasal l akher row 7n7wlo l kinged awel wa7ed mbyb2ash kinged!!!!
            //case 1 step 7n apply move bs 
            if (Math.abs(hSteps)==2){
                //khlet ely felnos ytakel
                this.board[fromRow+(hSteps/2)][fromCol+(vSteps/2)]=-1
            
            }
            else{
                this.switchTurn()
            }
            this.drawer(this.board)
        }
        
      
    }
    switchTurn(){
        this.currentPlayer=!(this.currentPlayer)
        
    }
    isNotValidLength(from,to){
        //return true if both lengths are 2
        return (from.length!=2 || to.length!=2)
    }
    isCurrentPlayer(piece,currentPlayer){ //3ksaha isEnemy
       return ((piece%2) === currentPlayer) 
    
    }
    isCellInBounds(row, col) {
        return row >= 0 && row < this.board.length && col >= 0 && col < this.board[0].length;
    }
    isValidCheckersMove(fromRow, fromCol, toRow, toCol){


    }
    getJumpingMoves(){
        //3yzaha trg3 boolean lw fy jumping move w lw feh trg3hom kolohom 
        let jumpMoves;
        let start,end,value;
        // if (this.currentPlayer==0){
        //     start=this.board.length-1
        //     end=0
        //     value=-1
        // }
        // else{
        //     start=0
        //     end=this.board[0].length
        // }
        for (let i = start ; i >=end; i+=value){
            for (let j=0;j<this.board[0].length;j++){
                let currPiece=this.board[i][j]
                if (this.isCurrentPlayer(currPiece,this.currentPlayer)){
                    if (currPiece>1){
                        //m7taga a check ely 3ksyyyyy

                    }
                }
            }
        }
        return ;
    }
    isValidMove(fromRow, fromCol, toRow, toCol) {
        
        console.log(this.board);
        const piece = this.board[fromRow][fromCol];
        console.log(fromRow);
        console.log(fromCol);
        console.log(toRow);
        console.log(toCol);
        console.log(this.currentPlayer);
        console.log(this.board)
        //check if input is within bounds
        if( !(this.isCellInBounds(fromRow,fromCol)&& this.isCellInBounds(toRow,toCol))){
            console.log("out of bounds");
            return false
        }
        ///////////////m7taga ageb el available jump movessss w ashof lw how mesh ray7 lw7da menhaaaaa
        // Check if the piece belongs to the current player
        if (!this.isCurrentPlayer(piece,this.currentPlayer)) {
            console.log("not your piece!");
            return false;
        }

        // Check if the destination cell is not occupied
        //logic related to checkerssssssss
        if ((this.board[toRow][toCol]) !== -1) {
            console.log("not empty place");
            return false;
        }
        //keda keda el far2 lazem yb2a <=2
        //normal case lazem ymsho feldiagonals forward 
        //lw homa king ye2dro ymsho fel diagonals forward and backward
        // lw 2 steps fel diagonal lazem ely fel nos ykon  lon mokhtalef
        let vSteps=toRow-fromRow
        let hSteps=toCol-fromCol
        if (Math.abs(hSteps)>2 || Math.abs(vSteps)>2 || Math.abs(hSteps)!==Math.abs(vSteps)){
            console.log("step>2 or not moving in diagonal")
            return false
        }
        if ((vSteps<0 && piece<2 && this.currentPlayer==1)||(vSteps>0 && piece<2 && this.currentPlayer==0) ){
            console.log("unkinged moving backward!!")
            return false
        }
        if (Math.abs(hSteps)==2){
            //3yzen n-check en ely mabenhom bta3et el 3dw
            let inBetweenPiece=this.board[fromRow+(hSteps/2)][fromCol+(vSteps/2)];
            if (this.isCurrentPlayer(inBetweenPiece,this.currentPlayer)){
                console.log("7t2tel nfsk")
                return false
            }
            
        }
        return true;

        // Check if the move is valid for the piece
        
    }
    

    drawer(state){
        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        let player="white's player turn"
        if (this.currentPlayer%2==1){
            player="black's player turn"
        }
        document.getElementById('turn').innerText=player+" Player Turn";
        const tbl = document.createElement("table");
        tbl.setAttribute("id","tablee");
        const tblBody = document.createElement("tbody");
        console.log(state)
        //letters row
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8';
        row.appendChild(cell)
        for (let i = 0 ;i< state[0].length; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8;border-style:solid;';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            row.appendChild(cell);
        }
        
        tblBody.appendChild(row);
        for (let i = 0; i < state.length; i++) {

            const row = document.createElement("tr");
            const cell = document.createElement("td");
            // first cell (numbers)
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8';
            cell.innerText=i+1
            row.appendChild(cell);
            for (let j = 0; j < state[0].length; j++) {  

                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:0;vertical-align: middle;text-align:center;font-size: 42px;box-shadow: #000;border-style:solid';
              
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#000';
     
                if (state[i][j]==1)//black player
                    cell.innerHTML='&#9899'
                else if (state[i][j]==0) //white player
                    cell.innerHTML='&#9898'
                else if(state[i][j]==3)//black player kinged
                    cell.innerHTML='&#9818'
                else if (state[i][j]==2) //white player kinged
                    cell.innerHTML='&#9812'

                row.appendChild(cell);
                
            }
            row.style="border:5;border-style:solid"
            tblBody.appendChild(row);
        }
        tbl.appendChild(tblBody);
        document.body.appendChild(tbl);
        tbl.style="border-style:solid;border:2;border-collapse: collapse;"
    
    }
}
const checkers =new Checkers();
checkers.drawer(checkers.board);