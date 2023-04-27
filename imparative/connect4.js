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
    takeUserInput(){
        this.takeUserInput1();
    }
    Drawer(state){
        const td=document.getElementById("secondInput")
        if (td!=null)
        {
            td.remove()
        }
        const tdel=document.getElementById("label2")
        if (tdel!=null)
        {
            tdel.remove()
        }
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
        const cell = document.createElement("td");
        cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #1f59df;background-color:#ffffff;';
        row.appendChild(cell)
        for (let i = 0 ;i< state[0].length; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #1f59df;background-color:#ffffff;';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            row.appendChild(cell);
        }
        tblBody.appendChild(row);
      
        for (let i = 0; i <state.length  ; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara 
            const row = document.createElement("tr");
            const cell=document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #1f59df;background-color:#ffffff;';
            cell.innerHTML=i+1;
            row.appendChild(cell);
           
            for (let j = 0; j < state[0].length; j++) { 
               
                const cell = document.createElement("td");
                cell.style=this.style;
                
                if (state[i][j]=='r')
                    cell.style.backgroundColor="#f52b2b";
                else if (state[i][j]=='y')
                    cell.style.backgroundColor="#e1f52b";  
               

                row.appendChild(cell); 
            }
            tblBody.appendChild(row);    
        }
        
        tbl.appendChild(tblBody);// append table body to table nfso 
        document.body.appendChild(tbl); // b append kol dah lel document 
        tbl.style="border-style:solid;background-color:#1f59df;border:10";
       
    }
    Controller (input)
    {
        this.ClearInput("firstInput");
        if (!this.isValidLength(input,1)){
            window.alert("INVALID INPUT!!");
            // return ;
        }
        var column = input.charCodeAt(0) - 97;
        var row;
        if(!this.isCellInBounds(2,column)) {
            window.alert("INVALID INPUT!!");
            // return ;
        }
           for(let i=0;i<6;i++)
           {
             if(this.board[i][column]==' ')
             {
              if(i==5 && this.board[i][column]==' ')
               row=5
             }
            else{
              row=i-1;
             console.log(row);
             break;
            }
           }
            if(this.currentPlayer==1)
                this.board[row][column]='r';
            else
                this.board[row][column]='y';
            this.SwitchPlayers();
            this.Drawer(this.board);
    }

}