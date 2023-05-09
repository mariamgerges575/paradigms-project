import {Abstract_game_engine} from "./Abstract_game_engine.js"
export class tic_tac_toe extends Abstract_game_engine {
    constructor() {
        super();
     } 
/////////////////////////////////////////////////////////////////////////////////////////
    createBoard(){
       let  state=[
            ['0','0','0'],
            ['0','0','0'],
            ['0','0','0'],
        ];
        return state;
    }
/////////////////////////////////////////////////////////////////////////////////////
    Drawer(state){
        // const to_del=document.getElementById("secondInput")
        // if (to_del!=null)
        // {
        //     to_del.remove()
        // }
        // const todel=document.getElementById("label2")
        // if (todel!=null)
        // {
        //     todel.remove()
        // }
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
        cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
        row.appendChild(cell)
        for (let i = 0 ;i< 3; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
        }
        tblBody.appendChild(row);
      
        for (let i = 0; i <3; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara 
            const row = document.createElement("tr");
            const cell=document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            cell.innerHTML=i+1;
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
            for (let j = 0; j <3; j++) { 
                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8;border-color:#000';
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#000';
                else 
                  cell.style.backgroundColor='#000';
                if (state.board[i][j]=='❌')
                    cell.innerHTML='❌'
                else if (state.board[i][j]=='⭕')
                    cell.innerHTML='⭕'      
                else if (state.board[i][j]=='0')
                    cell.innerHTML=''

                row.appendChild(cell); 
            }
            tblBody.appendChild(row);    
        }
        tbl.appendChild(tblBody);// append table body to table nfso
        // document.getElementById("label1").innerHTML="To Cell :"

        if (this.currentPlayer==1){
            document.getElementById("turn").innerHTML=" Player X Turn";
        }
        else { document.getElementById("turn").innerHTML=" Player O Turn";}
        document.body.appendChild(tbl); // b append kol dah lel document 
        tbl.style="border-style:solid;border-color:#000;border:10";
    }
//////////////////////////////////////////////////////////////////////
    // takeUserInput(){
    //     this.takeUserInput1();
    // }
    InputMessage() {
        return "Enter Input to Cell ex: 1a "
    }
//////////////////////////////////////////////////////////////////////////
    Controller(state,input)
    {
        // this.ClearInput("firstInput");
        if(!this.isValidLength(input,2))
        {
        //  window.alert("INVALID INPUT!!");
         return null ;
        }

        const r=this.FindRowCol(input);
        let row=r.Row
        let column=r.Col;
        if(! this.isCellInBounds(state.board,row,column))
       {
        // window.alert("INVALID INPUT!");
        return null;
       }   
       if(state.board[row][column]=='0')
       {
           if(state.currentPlayer)
             state.board[row][column]='❌';
           else
             state.board[row][column]='⭕';
           this.SwitchPlayers(state);
        //    this.Drawer(state.board);
        return state
       }
       else
            return null
        //    window.alert("Cannot be placed here!");
           
    }
   
}
///////////////////////////////////////////////////////////////////////////////////////////////////
// const tictactoe=new tic_tac_toe();
// tictactoe.drawboard();


