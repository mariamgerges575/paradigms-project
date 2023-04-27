import { Abstract_game_engine } from "./Abstract_game_engine.js";

export class sudoku extends Abstract_game_engine {
    

    //style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#ffffff;border-style:solid;border-color:#000;border:0';
    constructor(){
        super();
        this.initianList=[
            [' ','5','7',' ','6', ' ',' ','1', ' ' ],
            ['8',' ','6','2',' ', '5',' ',' ', '9' ],
            [' ','4',' ',' ',' ', ' ',' ',' ', '6' ],
            [' ','2','4','3','5', ' ',' ',' ', ' ' ],
            [' ',' ',' ',' ','1', '2',' ',' ', '5' ],
            ['5','3',' ',' ',' ', ' ',' ','7', '2' ],
            [' ',' ',' ','1',' ', ' ','9','6', ' ' ],
            [' ',' ',' ','6',' ', ' ',' ',' ', ' ' ],
            ['1','6','9','5','7', '3',' ',' ', ' ' ]
        ];
    }

    createBoard(){
        //there should be sudoku generator
        let state=[
            [' ','5','7',' ','6', ' ',' ','1', ' ' ],
            ['8',' ','6','2',' ', '5',' ',' ', '9' ],
            [' ','4',' ',' ',' ', ' ',' ',' ', '6' ],
            [' ','2','4','3','5', ' ',' ',' ', ' ' ],
            [' ',' ',' ',' ','1', '2',' ',' ', '5' ],
            ['5','3',' ',' ',' ', ' ',' ','7', '2' ],
            [' ',' ',' ','1',' ', ' ','9','6', ' ' ],
            [' ',' ',' ','6',' ', ' ',' ',' ', ' ' ],
            ['1','6','9','5','7', '3',' ',' ', ' ' ]
        ];
        return state;
    }
    takeUserInput() {
       this.takeUserInput2();
    }
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
        for (let i = 0 ;i< state[0].length; i++){
            const cell = document.createElement("td");
            cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#56829a';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
        }
        tblBody.appendChild(row);
      
        for (let i = 0; i <state.length  ; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara 
            const row = document.createElement("tr");
            const cell=document.createElement("td");
            cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#56829a';
            cell.innerHTML=i+1;
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
            for (let j = 0; j < state[0].length; j++) { 
                const cell = document.createElement("td");
                cell.style='height:55px;width:55px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#ffffff;border-color:#000;border:10;border-style:bold';
                cell.style.border="5px"
                if(j>2 && j<6 && !( i>2 && i<6 ))
                    cell.style.backgroundColor='#cecece';
                else if(i>2 && i<6 && !( j>2 && j<6 ))   
                    cell.style.backgroundColor='#cecece';
                else
                    cell.style.backgroundColor='#ffffff';

               if(this.initianList[i][j]!= " ")
                   cell.style.color="#ec1e1e";
                

               cell.innerHTML=state[i][j]
            

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

        

        document.getElementById("label1").innerHTML="To Cell :"

        if (this.currentPlayer==1){
            document.getElementById("turn").innerHTML=" Player X Turn";
        }
        else { document.getElementById("turn").innerHTML=" Player O Turn";}
        document.body.appendChild(tbl); // b append kol dah lel document 
        tbl.style="border-style:solid;border-color:#000;border:10";
    }
    Controller(position,value) { //c is char
        console.log(position)
        let i = 0;
        let j = 0;
        let valid = 1
        value = parseInt(value); //momkn mtb2ash number
        if (!this.isValidLength(position, 2) || isNaN(value) || this.FindRowCol(position) === null){
            window.alert("mistake in input")
            return;
        }

        const {Row, Col} = this.FindRowCol(position)


        if (this.isCellInBounds(9, 9)) {
            window.alert("out of bounds")
            return;
        }
        if(this.initianList[Row][Col]!==" "){
            window.alert("cant edit this cell")
            return;
        }
        for (let i = 0; i < 9; i++) {
            for (let j = 0; j < 9; j++) {
                if (this.board[i][j] === value && (i === Row || Col === j)) {
                    window.alert("cant put this value here")
                    valid = 0;
                    break;
                }
            }
        }
        if (valid) {
            this.board[i][j] = value;
            this.Drawer(this.board);
        }
    }





}
// c=new sudoku();
// c.Initialize();