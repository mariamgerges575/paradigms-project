import { Abstract_game_engine } from "./Abstract_game_engine.js";
export class connect4 extends Abstract_game_engine{
    style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #1f59df;background-color:#ffffff;border-radius: 30px;';
    constructor(){
        super();
    }
    createBoard(){
      let  state=[
        [' ',' ',' ',' ',' ', ' ',' ' ],
        [' ',' ',' ',' ',' ', ' ',' ' ],
        [' ',' ',' ',' ',' ', ' ',' ' ],
        [' ',' ',' ',' ',' ', ' ',' ' ],
        [' ',' ',' ',' ',' ', ' ',' ' ],
        [' ',' ',' ',' ',' ', ' ',' ' ]
       
     ];
        return state;
    }
    
    Drawer(state){
    
        const to_del=document.getElementById("to")
        if (to_del!=null)
        {
            to_del.remove()
        }
        const todel=document.getElementById("to")
        if (todel!=null)
        {
            todel.remove()
        }
        
        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        const tbl = document.createElement("table");
        tbl.style='border:none';
        tbl.setAttribute("id","tablee");  // create table
        const tblBody = document.createElement("tbody"); 
        const row = document.createElement("tr");
        for (let i = 0 ;i<7; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #1f59df;background-color:#ffffff;';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            row.appendChild(cell);
        }
        tblBody.appendChild(row);
      
        for (let i = 0; i <6 ; i++) {
            const row = document.createElement("tr");
            const cell=document.createElement("td");
           
            for (let j = 0; j <7; j++) { 
               
                const cell = document.createElement("td");
                cell.style=this.style;
               console.log(state.board[i][j]) ;
                if (state.board[i][j]=='r')
                    cell.style.backgroundColor="#f52b2b";
                else if (state.board[i][j]=='y')
                    cell.style.backgroundColor="#e1f52b";  
               

                row.appendChild(cell); 
            }
            tblBody.appendChild(row);    
        }
        
        tbl.appendChild(tblBody); 
        document.body.appendChild(tbl); 
        tbl.style="border-style:solid;background-color:#1f59df;border:10";
       
    }
    InputMessage() {
        return "Enter Input just the column ex: a"
    }

    Controller (state,input)
    {
        
        if (!this.isValidLength(input,1)){
            // window.alert("INVALID INPUT!!");
            return null;
        }
        var column = input.charCodeAt(0) - 97;
        console.log(column);
        var row;
        if(!this.isCellInBounds(state.board,2,column)) return null;
        
        if(state.board[0][column]!=' ') return null;
       
        for(let i=5;i>=0;i--)
        {
            if(state.board[i][column]==' ')
            {
                row=i;
                break;
            }
        }
        if(state.currentPlayer==1)
            state.board[row][column]='r';
        else
            state.board[row][column]='y';

        this.SwitchPlayers(state);
        return state
    }
   

}
