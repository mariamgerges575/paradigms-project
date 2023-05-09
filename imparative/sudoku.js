// import { stat } from "fs";
import { Abstract_game_engine } from "./Abstract_game_engine.js";
import { SudokuG } from "./SudokuG.js";
export class sudoku extends Abstract_game_engine {
    

    //style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#ffffff;border-style:solid;border-color:#000;border:0';
    constructor(){
        super();
        // this.initianList=[
        //     [' ','5','7',' ','6', ' ',' ','1', ' ' ],
        //     ['8',' ','6','2',' ', '5',' ',' ', '9' ],
        //     [' ','4',' ',' ',' ', ' ',' ',' ', '6' ],
        //     [' ','2','4','3','5', ' ',' ',' ', ' ' ],
        //     [' ',' ',' ',' ','1', '2',' ',' ', '5' ],
        //     ['5','3',' ',' ',' ', ' ',' ','7', '2' ],
        //     [' ',' ',' ','1',' ', ' ','9','6', ' ' ],
        //     [' ',' ',' ','6',' ', ' ',' ',' ', ' ' ],
        //     ['1','6','9','5','7', '3',' ',' ', ' ' ]
        // ];
    }

    createBoard(){
        //there should be sudoku generator
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
    // takeUserInput() {
    //    this.takeUserInput2();
    // }
    Drawer(state){

        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        const tbl = document.createElement("table");
        tbl.style='border:none';
        tbl.setAttribute("id","tablee");  // create table
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
      
        for (let i = 0; i <9 ; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara 
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
        tbl.appendChild(tblBody);// append table body to table nfso

        const newtable=document.createElement("table")
        const newtablebody=document.createElement("tbody")
        const R=document.createElement("tr");
        R.appendChild(tbl)
        R.appendChild(tbl)

        

        // document.getElementById("label1").innerHTML="To Cell :"

      
        document.body.appendChild(tbl); // b append kol dah lel document 
        tbl.style="border-style:solid;border-color:#000;border:10";
    }
    InputMessage() {
        return "Enter Input position and value ex: 1a 2"
    }
    Controller(state,input) { //c is char
        // console.log(position)
        let i = 0;
        let j = 0;
        let valid = 1
        let str=input.split(" ")  ////////
        var position=str[0]//////////////
        var value=str[1]
        value = parseInt(value); //momkn mtb2ash number
        if (!this.isValidLength(str, 2) || isNaN(value) || this.FindRowCol(str[0]) === null){
            // window.alert("mistake in input")
            return null;
        }

        const {Row, Col} = this.FindRowCol(position)


        if (this.isCellInBounds(state.board,9, 9)) {
            // window.alert("out of bounds")
            return null;
        }
        console.log(Row);
        console.log(Col);
        if(state.board[Row][Col]>9) {
            // window.alert("can't edit this cell")
            return null
        }
        if(state.board[Row][Col]===value) 
           state.board[Row][Col]=0

    console.log(Row);
    console.log(Col);

        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                if ((state.board[i][j] == value || state.board[i][j]-10== value)&& (i === Row || Col === j)) {
                    console.log(state.board[i][j] === value )
                    console.log(state.board[i][j]%10+1=== value )
                    console.log((i === Row || Col === j))
                    console.log(Row,Col)
                    // window.alert("cant put this value here")
                    valid = 0;
                    break;
                }
            }
        }
        if (valid) {
            state.board[Row][Col] = value; 
            return state
 
        }
        else 
        return null
        
    }
}
// c=new sudoku();
// c.Initialize();