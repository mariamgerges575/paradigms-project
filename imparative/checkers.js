import {Abstract_game_engine} from "./Abstract_game_engine.js"
export class Checkers extends Abstract_game_engine{
   
    constructor(){
       super();
        this.deads0=0
        this.deads1=0
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
    takeUserInput(){
         this.takeUserInput2();
    }
//////////////////////////////////////////////////////////////////////////////
    applyMove(fromRow, fromCol, toRow, toCol) {
       ///es2ly nagui em 3yzen nkhleha btbadel bssssssssss

        this.board[toRow][toCol]=this.board[fromRow][fromCol];
        this.board[fromRow][fromCol]=-1;
        
        console.log(this.board);

    }
//////////////////////////////////////////////////////////////////
    Controller(from,to){
        this.ClearInput("firstInput");
        this.ClearInput("secondInput");
        console.log(from)
        console.log(to)
        if (!this.isValidLength(from,2)|| !this.isValidLength(to,2)){
            alert("invalid input")
            return
        }
        const r=this.FindRowCol(from);
        let fromRow=r.Row
        let fromCol=r.Col;
        const c=this.FindRowCol(to);
        let toRow=c.Row
        let toCol=c.Col;
        
        if (this.isValidMove(fromRow, fromCol, toRow, toCol)){
            let vSteps=toRow-fromRow
            let hSteps=toCol-fromCol
            if((toRow==0 && this.board[fromRow][fromCol]==0 && this.deads0!=0) || (toRow==7 && this.board[fromRow][fromCol]==1 && this.deads1!=0 )){
                console.log("yessssssssssssss")
                this.board[fromRow][fromCol]+=2
                if (this.currentPlayer)
                    this.deads1-=1
                else
                    this.deads0-=1
            }
            else{
                console.log(toRow)
                console.log(this.board[fromRow][fromCol])
                console.log(this.deads0)
                console.log(this.deads1)
                console.log("noooooooooooooo")
            }
            this.applyMove(fromRow, fromCol, toRow, toCol)
            if (Math.abs(hSteps)==2){
                this.board[fromRow+(vSteps/2)][fromCol+(hSteps/2)]=-1
                const {bool,jumpMoves}=this.getJumpingMoves()
                if (this.currentPlayer)
                    this.deads0++
                else
                    this.deads1++
                if(!bool)
                    this. SwitchPlayers()
            }
            else
                this. SwitchPlayers()
            
            this.Drawer(this.board)
        }
        else {alert("Invalid Input")}
    }
    
    isCurrentPlayer(piece){ //3ksaha isEnemy
       return ((piece%2) == this.currentPlayer) 
    }
   
    isJump(i_2,j_2,i_1,j_1){
        
        let DDP=this.board[i_2][j_2]
        if (DDP==-1){
            let DP=this.board[i_1][j_1]
            if (DP==-1)
                return false
            return (!this.isCurrentPlayer(DP))
        }
        return false
    }
    getJumpingMoves(){
        //return a boolean(true if there is a possible jump and false otherwise)
        let jumpMoves=[];        
        let start=0,count=0
        if (this.currentPlayer==0){
            start=-7
        }
        for (let i = start ; i <start+8; i++){
            for (let j=0;j<8;j++){

                let currPiece=this.board[Math.abs(i)][j]
                if (this.isCurrentPlayer(currPiece)){
                    count++
                    if (j>1){/////check leftttt
                        if (i<6){
                            if (this.isJump(Math.abs(i+2),j-2,Math.abs(i+1),j-1))
                                jumpMoves.push([Math.abs(i),j,Math.abs(i+2),j-2])
                        }
                        if (currPiece>1){
                            if (i>1){
                                if (this.isJump(Math.abs(i-2),j-2,Math.abs(i-1),j-1))
                                    jumpMoves.push([Math.abs(i),j,Math.abs(i-2),j-2])
                            }
                        }
                    }            
                    if(j<6){//checkkk right
                        if (i<6){
                            if (this.isJump(Math.abs(i+2),j+2,Math.abs(i+1),j+1))
                            jumpMoves.push([Math.abs(i),j,Math.abs(i+2),j+2])
                        }
                        if (currPiece>1){
                            if (i>1){
                                if (this.isJump(Math.abs(i-2),j-2,Math.abs(i-1),j+1))
                                jumpMoves.push([Math.abs(i),j,Math.abs(i-2),j-2])
                            }
                            
                        }       
                    }
                }     
            }
            
            if (count==12) ///count -deadssssssssssssssssssssssssssssssssss
                break
            
        }
        let bool=jumpMoves.length==0?false:true
        return {bool,jumpMoves};
    }
/////////////////////////////////////////////////////////////////////////////////
    isValidMove(fromRow, fromCol, toRow, toCol) {
        
        // console.log(this.board);
        const piece = this.board[fromRow][fromCol];
        // console.log(fromRow);
        // console.log(fromCol);
        // console.log(toRow);
        // console.log(toCol);
        // console.log(this.currentPlayer);
        // console.log(this.board)
        //check if input is within bounds
        if( !(this.isCellInBounds(fromRow,fromCol)&& this.isCellInBounds(toRow,toCol))){
            console.log("out of bounds");
            return false
        }
        ///////////////m7taga ageb el available jump movessss w ashof lw how mesh ray7 lw7da menhaaaaa
        // Check if the piece belongs to the current player
        if (!this.isCurrentPlayer(piece)) {
            console.log("not your piece!");
            return false;
        }

        // Check if the destination cell is not occupied
        //logic related to checkerssssssss
        if ((this.board[toRow][toCol]) != -1) {
            console.log("not empty place");
            return false;
        }
        //keda keda el far2 lazem yb2a <=2
        //normal case lazem ymsho feldiagonals forward 
        //lw homa king ye2dro ymsho fel diagonals forward and backward
        // lw 2 steps fel diagonal lazem ely fel nos ykon  lon mokhtalef
        let vSteps=toRow-fromRow
        let hSteps=toCol-fromCol
        if (Math.abs(hSteps)>2 || Math.abs(vSteps)>2 || Math.abs(hSteps)!=Math.abs(vSteps)){
            console.log("step>2 or not moving in diagonal")
            return false
        }
        if ((vSteps<0 && piece<2 && this.currentPlayer==1)||(vSteps>0 && piece<2 && this.currentPlayer==0) ){
            console.log("unkinged moving backward!!")
            return false
        }
        
        if (Math.abs(hSteps)==2){
            //3yzen n-check en ely mabenhom bta3et el 3dw
            let inBetweenPiece=this.board[fromRow+(vSteps/2)][fromCol+(hSteps/2)];
            if (this.isCurrentPlayer(inBetweenPiece)){
                console.log("7t2tel nfsk")
                return false
            }
            
        }
        if (Math.abs(hSteps)==1){
            //7shofffff lw kan fy step b 2 w hwa m3mlhashhhhhh
            // let bool,jumpMoves=[[]]
            const {bool,jumpMoves}=this.getJumpingMoves()
            console.log(bool)
            console.log(jumpMoves)
            return !bool
            
        }
        return true;

        // Check if the move is valid for the piece
        
    }
    

    Drawer(state){
        // document.remove()
        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        let player="white's player turn"
        if (this.currentPlayer%2==1){
            player="black's player turn"
        }
        // document.getElementById('turn').innerText=player+" Player Turn";
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
            cell.style.fontFamily="Copperplate";
            
            row.appendChild(cell);
        }
        
        tblBody.appendChild(row);
        for (let i = 0; i < state.length; i++) {

            const row = document.createElement("tr");
            const cell = document.createElement("td");
            // first cell (numbers)
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 30px;box-shadow: #000;background-color:#e0e0d8';
            cell.innerText=i+1
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
            for (let j = 0; j < state[0].length; j++) {  

                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:0;vertical-align: middle;text-align:center;font-size: 42px;box-shadow: #000;border-style:solid';
              
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#762209';
                    else 
                    cell.style.backgroundColor=' #daa061';
                   
     
                if (state[i][j]==1)//black player
                    cell.innerHTML= '&#9899'
                else if (state[i][j]==0) //white player
                    cell.innerHTML='&#9898'
                else if(state[i][j]==3)//black player kinged
                    cell.innerHTML='&#9818'
                else if (state[i][j]==2){ //white player kinged
                    cell.innerHTML='&#9812'
                    console.log("kingg walahyyy")
                }
                    
                // cell.addEventListener("mouseup",this.clicked)
                row.appendChild(cell);
                
            }
            row.style="border:5;border-style:solid"
            tblBody.appendChild(row);
        }

        tbl.appendChild(tblBody);
        document.getElementById("label1").innerHTML="From Cell :"
        document.getElementById("label2").innerHTML="To   Cell :"
        if (this.currentPlayer==0){
            document.getElementById("turn").innerHTML="WHITE Player Turn";
        }
        else { document.getElementById("turn").innerHTML="BLACK Player Turn";}
        document.body.appendChild(tbl);
        tbl.style="border-style:solid;border:2;border-collapse: collapse;"
    
    }
}
// const checkers =new Checkers();
// checkers.drawer(checkers.board);