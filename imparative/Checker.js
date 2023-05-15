import {Abstract_game_engine} from "./Abstract_game_engine.js"
export class Checkers extends Abstract_game_engine{
   
    constructor(){
       super();
        // this.deads0=0
        // this.deads1=0
    }
   
////////////////////////////////////////////////////////////////////////////////
    createBoard(){
        let  state=[
            [-1, 1,-1, 1,-1, 1,-1, 1],
            [ 1,-1, 1,-1, 1,-1, 1,-1],
            [-1, 1,-1, 1,-1, 1,-1, 1],
            [-1,-1,-1,-1,-1,-1,-1,-1],
            [-1,-1,-1,-1,-1,-1,-1,-1],
            [ 0,-1, 0,-1, 0,-1, 0,-1],
            [-1, 0,-1, 0,-1, 0,-1, 0],
            [ 0,-1, 0,-1, 0,-1, 0,-1]
         ];
         
         
         return state;
     }
////////////////////////////////////////////////////////////////////////////////
    // takeUserInput(){
    //      this.takeUserInput2();
    // }
    InputMessage() {
        return "Enter Input from and to cell ex: 3b 4a"
    }

//////////////////////////////////////////////////////////////////////////////
    applyMove(state,fromRow, fromCol, toRow, toCol) {
       ///es2ly nagui em 3yzen nkhleha btbadel bssssssssss

        state.board[toRow][toCol]=state.board[fromRow][fromCol];
        state.board[fromRow][fromCol]=-1;
        
        console.log(state.board);

    }
//////////////////////////////////////////////////////////////////
    Controller(state,input){
        // this.ClearInput("firstInput");
        // this.ClearInput("secondInput");
        let str=input.split(" ")  ////////
        if(!this.isValidLength(str,2)){
            console.log("sama");
            return null}////////
        const fromCell=str[0]//////////////
        const toCell=str[1]
        // this.ClearInput('firstInput');
        // this.ClearInput('secondInput');
        // Convert the user input to the corresponding row and column indices
        let fromMove = this.FindRowCol(fromCell);
        let fromRow=fromMove.Row;
        let fromCol=fromMove.Col;

        let toMove = this.FindRowCol(toCell);
        let toRow=toMove.Row;
        let toCol=toMove.Col;


        // const r=this.FindRowCol(from);
        // let fromRow=r.Row
        // let fromCol=r.Col;
        // const c=this.FindRowCol(to);
        // let toRow=c.Row
        // let toCol=c.Col;
        
        if (this.isValidMove(state,fromRow, fromCol, toRow, toCol)){
            let vSteps=toRow-fromRow
            let hSteps=toCol-fromCol
            // if((toRow==0 && state.board[fromRow][fromCol]==0 && this.deads0!=0) || (toRow==7 && state.board[fromRow][fromCol]==1 && this.deads1!=0 )){
                if((toRow==0 && state.board[fromRow][fromCol]==0 ) || (toRow==7 && state.board[fromRow][fromCol]==1  )){
                //check if a piece reached the last row -> king
                console.log("kinged")
                state.board[fromRow][fromCol]+=2
                // if (this.currentPlayer)
                //     this.deads1-=1
                // else
                //     this.deads0-=1
            }
            
            this.applyMove(state,fromRow, fromCol, toRow, toCol)
            if (Math.abs(hSteps)==2){
                state.board[fromRow+(vSteps/2)][fromCol+(hSteps/2)]=-1
                const {bool,jumpMoves}=this.getJumpingMoves(state)
                // if (this.currentPlayer)
                //     this.deads0++
                // else
                //     this.deads1++
                if(!bool)
                    // state.currentPlayer!=state.currentPlayer
                    this. SwitchPlayers(state)
            }
            else
                //  state.currentPlayer!=state.currentPlayer
                this.SwitchPlayers(state)
            
            // this.Drawer(state.board)
        }
        else {return null}
        return state
    }
    
    isCurrentPlayer(state,piece){ 
    //returns true if the piece belongs to the current player
       return ((piece%2) == state.currentPlayer) 
    }
   
    isJump(state,i_2,j_2,i_1,j_1){
        // checks if the piece between the player's initial and desired final position belongs to its enemy
        let DDP=state.board[i_2][j_2]
        if (DDP==-1){
            let DP=state.board[i_1][j_1]
            if (DP==-1)
                return false
            return (!this.isCurrentPlayer(state,DP))
        }
        return false
    }
    getJumpingMoves(state){
        //return a boolean(true if there is a possible jump and false otherwise) and returns the available jump moves (not used but for further improvement of the game)
        //it gets all possible jumps for all the pieces of the currentplayer
        let jumpMoves=[];        
        let start=0,count=0
        if (state.currentPlayer==0){
            start=-7
        }
        for (let i = start ; i <start+8; i++){
            for (let j=0;j<8;j++){

                let currPiece=state.board[Math.abs(i)][j]
                if (this.isCurrentPlayer(state,currPiece)){
                    count++
                    if (j>1){ //check left diagonal
                        if (i<6){
                            if (this.isJump(state,Math.abs(i+2),j-2,Math.abs(i+1),j-1))
                                jumpMoves.push([Math.abs(i),j,Math.abs(i+2),j-2])
                        }
                        if (currPiece>1){ //if the piece is a king it can move backward
                            if (i>1){
                                if (this.isJump(state,Math.abs(i-2),j-2,Math.abs(i-1),j-1))
                                    jumpMoves.push([Math.abs(i),j,Math.abs(i-2),j-2])
                            }
                        }
                    }            
                    if(j<6){//check right diagonal
                        if (i<6){
                            if (this.isJump(state,Math.abs(i+2),j+2,Math.abs(i+1),j+1))
                            jumpMoves.push([Math.abs(i),j,Math.abs(i+2),j+2])
                        }
                        if (currPiece>1){ //if the piece is a king it can move backward
                            if (i>1){
                                if (this.isJump(state,Math.abs(i-2),j-2,Math.abs(i-1),j+1))
                                jumpMoves.push([Math.abs(i),j,Math.abs(i-2),j-2])
                            }
                            
                        }       
                    }
                }     
            }
            // let deads=this.deads0
            // if (this.currentPlayer)
            //     deads=this.deads1
            
            if (count==(12)) //you already tested all the pieces of the current player //(count==(12-deads))
                break
            
        }
        let bool=jumpMoves.length==0?false:true
        return {bool,jumpMoves};
    }
/////////////////////////////////////////////////////////////////////////////////
    isValidMove(state ,fromRow, fromCol, toRow, toCol) {
        
        //check if input is within bounds
        if( !(this.isCellInBounds(state.board,fromRow,fromCol)&& this.isCellInBounds(state.board,toRow,toCol))){
            console.log("out of bound");
            return false
        }
        const piece = state.board[fromRow][fromCol];
       
        // Check if the piece belongs to the current player
        if (!this.isCurrentPlayer(state,piece)) {
            console.log(piece);
            console.log(state.currentPlayer);


            console.log("not your piece!");
            alert("not your piece!!!")
            return false;
        }

        // Check if the destination cell is not occupied
        if ((state.board[toRow][toCol]) != -1) {
            console.log("not empty place");
            alert("place not empty!!!")
            return false;
        }
        //get the horizontal and vertical offsets
        let vSteps=toRow-fromRow
        let hSteps=toCol-fromCol
        //check that the offsets are equal and <=2
        if (Math.abs(hSteps)>2 || Math.abs(vSteps)>2 || Math.abs(hSteps)!=Math.abs(vSteps)){
            console.log("step>2 or not moving in diagonal")
            alert("invalid input")
            return false
        }
        //check if an unkinged piece is trying to move backward
        if ((vSteps<0 && piece<2 && state.currentPlayer==1)||(vSteps>0 && piece<2 && state.currentPlayer==0) ){
            console.log("unkinged moving backward!!")
            alert("unkinged piece moving backward!!")
            return false
        }
        
        if (Math.abs(hSteps)==2){
            //check that the in-between piece belongs to the enemy
            let inBetweenPiece=state.board[fromRow+(vSteps/2)][fromCol+(hSteps/2)];
            if (inBetweenPiece==-1)
                return false;
            if (this.isCurrentPlayer(state,inBetweenPiece)){
                console.log("attempt to kill your self")
                alert("attempt to kill your self")
                return false
            }
            
        }
        if (Math.abs(hSteps)==1){
            //check if there was an available jump but the player didn't attempt it
            const {bool,jumpMoves}=this.getJumpingMoves(state)
            console.log(bool)
            console.log(jumpMoves)
            if (bool){
                console.log("there is a jump!")
                alert("there is a jump!")
            }
            return !bool
            
        }
        return true;
    
    }
    

    Drawer(state){
        //delete the previous state
        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        const tbl = document.createElement("table");
        tbl.setAttribute("id","tablee");
        const tblBody = document.createElement("tbody");
        console.log(state)
        //letters row
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8';
        row.appendChild(cell)
        for (let i = 0 ;i< 8; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8;border-style:solid;';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            cell.style.fontFamily="Copperplate";
            
            row.appendChild(cell);
        }        
        tblBody.appendChild(row);
        for (let i = 0; i < 8; i++) {

            const row = document.createElement("tr");
            const cell = document.createElement("td");
            // first cell (numbers)
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8';
            cell.innerText=i+1
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
            for (let j = 0; j <8; j++) {  

                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:0;vertical-align: middle;text-align:center;font-size: 42px;box-shadow: #000;border-style:solid';
              
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#762209';
                    else 
                    cell.style.backgroundColor=' #daa061';
                   
     
                if (state.board[i][j]==1)//black player
                    cell.innerHTML= '&#9899'
                else if (state.board[i][j]==0) //white player
                    cell.innerHTML='&#9898'
                else if(state.board[i][j]==3)//black player kinged
                    cell.innerHTML='&#9818'
                else if (state.board[i][j]==2){ //white player kinged
                    cell.innerHTML='&#9812'
                    console.log("kingg walahyyy")
                }
                row.appendChild(cell);
                
            }
            row.style="border:5;border-style:solid"
            tblBody.appendChild(row);
        }

        tbl.appendChild(tblBody);
        // document.getElementById("label1").innerHTML="From Cell :"
        // document.getElementById("label2").innerHTML="To   Cell :"
        if (state.currentPlayer==0){
            document.getElementById("turn").innerHTML="WHITE Player Turn";
        }
        else { document.getElementById("turn").innerHTML="BLACK Player Turn";}
        document.body.appendChild(tbl);
        tbl.style="border-style:solid;border:2;border-collapse: collapse;"
    
    }
}
// const checkers =new Checkers();
// checkers.drawer(checkers.board);