class Checkers{
    map= new Map();
    toR
    toC
    fromR
    fromC
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
        // this.board = [
        //     [2,2,-1,-1,-1,-1,-1,-1],
        //     [-1,-1,-1,-1,-1,-1,-1,-1],
        //     [-1,-1,-1,0,-1,-1,-1,-1],
        //     [-1,-1,-1,-1,-1,-1,-1,-1],
        //     [-1,-1,-1,-1,-1,-1,-1,-1],
        //     [-1,-1,-1,-1,-1,-1,-1,-1],
        //     [-1,-1,-1,-1,-1,-1,-1,-1],
        //     [ 0,-1, 0,-1, 0,-1, 0,-1]
        // ];
        this.currentPlayer = 1;
        this.deads0=4
        this.deads1=4
        this.map.set(1,'&#9898');
        this.map.set(0, '&#9890');
        console.log("constructor called")
        this.clicks=0
        /////////////////////////////////

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
    clicked() {
        if (this.clicks%2==0){
            console.log(0)
            this.fromR=this.parentNode.rowIndex-1
            this.fromC=this.cellIndex-1
        }
        else{
            console.log(1)
            this.toR=this.parentNode.rowIndex-1
            this.toC=this.cellIndex-1
        }
        this.clicks+=1
        console.log(this.clicks)
        
        // alert("clicked cell at: " + this.cellIndex + ", " + this.parentNode.rowIndex);
    }
    controller(from,to){
        console.log(from)
        console.log(to)
        if (this.isNotValidLength(from,to)){
            alert("invalid input")
            return
        }
        let fromRow = from[0] - 1;
        let fromCol = from.charCodeAt(1) - 97;
        let toRow = to[0] - 1;
        let toCol = to.charCodeAt(1) - 97;
        ///////////////////////////////////////////
        // // eb2y emsa7ihhhhhhhhhh
        // fromRow=this.fromR
        // fromCol=this.fromC
        // toRow=this.toR
        // toCol=this.toC
        // console.log(toRow)
        // console.log(toCol)
        ///////////////////////////////////////////
        if (this.isValidMove(fromRow, fromCol, toRow, toCol)){
            let vSteps=toRow-fromRow
            let hSteps=toCol-fromCol
            // const piece = this.board[fromRow][fromCol];
            //check if piece is kinged
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
            //move your piece
            this.applyMove(fromRow, fromCol, toRow, toCol)
            //elmafrod lw wasal akhalih kinggg 
            
            
            //keda keda 7n apply move
            //lw wasal l akher row 7n7wlo l kinged awel wa7ed mbyb2ash kinged!!!!
            //case 1 step 7n apply move bs 
            //if move was a jump then kill the opponent you jumped over and check if there is any possible jumps
            if (Math.abs(hSteps)==2){
                //khlet ely felnos ytakel
                this.board[fromRow+(vSteps/2)][fromCol+(hSteps/2)]=-1
                const {bool,jumpMoves}=this.getJumpingMoves()
                if (this.currentPlayer)
                    this.deads0++
                else
                    this.deads1++
                if(!bool)
                    this.switchTurn()
                
                //7ykmel le3b fy 7alet en fy jump availableeeeeeeeeeeeee m7taga a check bs lw fy w adelo hwa ydkhal w lw mdkhlsh ely hwa 3yza
            
            }
            else
                this.switchTurn()
            
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
    isCurrentPlayer(piece){ //3ksaha isEnemy
        console.log(piece%2)
        console.log(this.currentPlayer)
        console.log((piece%2) == this.currentPlayer)
       return ((piece%2) == this.currentPlayer) 
    
    }
    isCellInBounds(row, col) {
        return row >= 0 && row < this.board.length && col >= 0 && col < this.board[0].length;
    }
    isValidCheckersMove(fromRow, fromCol, toRow, toCol){


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
            
            if (count==12)
                break
            
        }
        let bool=jumpMoves.length==0?false:true
        return {bool,jumpMoves};
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
        document.body.appendChild(tbl);
        tbl.style="border-style:solid;border:2;border-collapse: collapse;"
    
    }
}
const checkers =new Checkers();
checkers.drawer(checkers.board);