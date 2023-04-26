import {Abstract_game_engine} from "./Abstract_game_engine.js"
export class  eightQueens extends Abstract_game_engine{
    constructor() {
        super();
     } 
/////////////////////////////////////////////////////////////////////////////////////////
    createBoard(){
        let state=[
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0'],
            ['0','0','0','0','0','0','0','0']
        ];
      
       return state;
    }
/////////////////////////////////////////////////////////////////////////////////////
    Drawer(state){
        const to_del=document.getElementById("secondInput")
        if (to_del!=null)
        {
            to_del.remove()
        }
        const todel=document.getElementById("label2")
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
        cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
        row.appendChild(cell)
        for (let i = 0 ;i< this.colNumbers; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
        }
        tblBody.appendChild(row);
      
        for (let i = 0; i <this.rowNumbers ; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara 
            const row = document.createElement("tr");
            const cell=document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            cell.innerHTML=i+1;
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
           
            for (let j = 0; j <this.colNumbers; j++) { 
               
                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8;border-color:#000';
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#d18b47';
                else 
                  cell.style.backgroundColor='#ffce9e';
                if (this.board[i][j]=='♕')
                    cell.innerHTML='♕'      
                else if (this.board[i][j]=='0')
                    cell.innerHTML=''

                row.appendChild(cell); 
            }
            tblBody.appendChild(row);    
        }
        
        tbl.appendChild(tblBody);// append table body to table nfso
        document.getElementById("label1").innerHTML="To Cell :"
        document.getElementById("turn").innerHTML="";
        document.body.appendChild(tbl); // b append kol dah lel document 
        tbl.style="border-style:solid;border-color:#000;border:10";
       
    }
///////////////////////////////////////////////////////////////////////////////////////////////
    takeUserInput(){
        this.takeUserInput1();
    }
////////////////////////////////////////////////////////////////////////////////////////////////
    Controller(input)
    {
        this.ClearInput("firstInput");
       console.log(input);
       if(!this.isValidLength(input,2))
       {
        window.alert("INVALID INPUT!!");
        return ;
       }
    //    const {row,column}=this.FindRowCol(input);
    const r=this.FindRowCol(input);
      let row=r.Row
      let column=r.Col;
       console.log(row);
       console.log(column);
       if(! this.isCellInBounds(row,column))
       {
        window.alert("INVALID INPUT!");
        return ;
       }   
       var col=true
       var ro=true
       var diagonal=true
       //valid column
       for(let i=0;i<this.rowNumbers;i++)
       {
        if(this.board[row][i]=='♕')
          col=false
       }
       //valid row
       for(let j=0;j<this.rowNumbers;j++)
       {
        if(this.board[j][column]=='♕')
        {
          ro=false
          console.log(j);
          console.log("false y bnt rl nas");
          break;
        }
       }
       //valid diagonal in right down corner
       var l=column
       for(let i=row; i<this.rowNumbers && l<8;i++)
       {
        if(this.board[i][l++]=='♕')
        {
          diagonal=false;
          break;
        }

       }
       //valid diagonal in left up corner
       var l=column
       for(let i=row;i>-1 && l>-1;i--)
       {
        if(this.board[i][l--]=='♕')
        {
          diagonal=false;
          break;
        }
       }
       //valid diagonal in up right corner
       var l=column
       for(let i=row;i>-1 && l<8;i--)
       {
        if(this.board[i][l++]=='♕')
        {
          diagonal=false;
          break;
        }
       }
       //valid diagonal in down left corner
       var l=column
       for(let i=row;i<this.rowNumbers && l>-1;i++)
       {
        if(this.board[i][l--]=='♕')
        {
          diagonal=false;
          break;
        }
       }
           if(this.board[row][column]=='0' && diagonal==true && col==true && ro==true)
           {
              this.board[row][column]='♕'
              this.Drawer(this.board);
           }
           else
           {
            window.alert("Cannot be placed here!");
           }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////
}
///////////////////////////////////////////////////////////////////////////////////////////////////

// const eightqueens=new eightQueens ();
// eightqueens.Initialize();

