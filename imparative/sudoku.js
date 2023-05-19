// import { stat } from "fs";
import { Abstract_game_engine } from "./Abstract_game_engine.js";
import { SudokuG } from "./SudokuG.js";
export class sudoku extends Abstract_game_engine {
        
    constructor(){
        super();
      
    }

    createBoard(){
       
        let state= new SudokuG(30).generate().getBoard()
        for(let i=0;i<9;i++){
            for(let j=0;j<9;j++){
    
                if(state[i][j]!=0)
                    state[i][j]+=10
            }
        }
        console.log(state)
        return state;
        return state;
    }
   
    Drawer(state){

        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        const tbl = document.createElement("table");
        tbl.style='border:none';
        tbl.setAttribute("id","tablee");  
        const tblBody = document.createElement("tbody"); 
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#56829a';
        row.appendChild(cell)
        for (let i = 0 ;i< 9; i++){
            const cell = document.createElement("td");
            cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#56829a';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
        }
        tblBody.appendChild(row);
      
        for (let i = 0; i <9 ; i++) {
            const row = document.createElement("tr");
            const cell=document.createElement("td");
            cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#56829a';
            cell.innerHTML=i+1;
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
            for (let j = 0; j < 9; j++) { 
                const cell = document.createElement("td");
                cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#ffffff;border-color:#000;border:10;border-style:bold';
                cell.style.border="5px"
                if(j>2 && j<6 && !( i>2 && i<6 ))
                    cell.style.backgroundColor='#cecece';
                else if(i>2 && i<6 && !( j>2 && j<6 ))   
                    cell.style.backgroundColor='#cecece';
                else
                    cell.style.backgroundColor='#ffffff';
                
               if(state.board[i][j]>9){
                cell.style.color="#ec1e1e";
                cell.innerHTML=state.board[i][j]-10
               }
               else if(state.board[i][j]===0) cell.innerHTML=' ';
               else{
               cell.innerHTML=state.board[i][j];
               }

                row.appendChild(cell); 
            }
            tblBody.appendChild(row);    
        }
        tbl.appendChild(tblBody);

        const newtable=document.createElement("table")
        const newtablebody=document.createElement("tbody")
        const R=document.createElement("tr");
        R.appendChild(tbl)
        R.appendChild(tbl)      
        document.body.appendChild(tbl); 
        tbl.style="border-style:solid;border-color:#000;border:10";
    }
    InputMessage() {
        return "Enter Input position and value ex: 1a 2"
    }
    Controller(state,input) {
        let str=input.split(" ")  
        var position=str[0]
        var value=str[1]
        value = parseInt(value); 
        if (!this.isValidLength(str, 2) || isNaN(value) || this.FindRowCol(str[0]) === null){
            return null;
        }
        const {Row, Col} = this.FindRowCol(position)
        if (this.isCellInBounds(state.board,9, 9)) {
            return null;
        }
        if(state.board[Row][Col]>9) {
            // window.alert("can't edit this cell")
            return null
        }
        if(state.board[Row][Col]==value) {
            state.board[Row][Col]=0
            return state
        }
           
        for (let i = 0; i < 9; i++) {
                if (state.board[Row][i]%10 == value || state.board[i][Col]%10== value) {
                    return null
                }
        }

        var rDiv=Math.floor(Row/3)*3 
        var cDiv=Math.floor(Col/3)*3
        
        for(var i=rDiv; i< rDiv+3 ;i++){
            for(var j=cDiv; j< cDiv+3; j++){
                if(state.board[i][j]%10===value) return null
            }
        }
        state.board[Row][Col] = value; 
        return state
    }
}
