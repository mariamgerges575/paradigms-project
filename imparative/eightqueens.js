import {Abstract_game_engine} from "./Abstract_game_engine.js"
export class  eightQueens extends Abstract_game_engine{
    constructor()
    {
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
        console.log("ana fy eldrawer")
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
        console.log("ana fy eldrawer2")

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
        for (let i = 0 ;i<8; i++){
            const cell = document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            let ascii=i+97
            ascii='&#0'+ascii
            cell.innerHTML=ascii
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);
        }
        tblBody.appendChild(row);

        for (let i = 0; i <8 ; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara
            const row = document.createElement("tr");
            const cell=document.createElement("td");
            cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
            cell.innerHTML=i+1;
            cell.style.fontFamily="Copperplate";
            row.appendChild(cell);

            for (let j = 0; j <8; j++) {

                const cell = document.createElement("td");
                cell.style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8;border-color:#000';
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#d18b47';
                else
                    cell.style.backgroundColor='#ffce9e';
                if (state.board[i][j]=='♕')
                    cell.innerHTML='♕'
                else if (state.board[i][j]=='0')
                    cell.innerHTML=''

                row.appendChild(cell);
            }
            tblBody.appendChild(row);
        }

        tbl.appendChild(tblBody);// append table body to table nfso
        // document.getElementById("label1").innerHTML="To Cell :"
        document.getElementById("turn").innerHTML="";
        document.body.appendChild(tbl); // b append kol dah lel document
        tbl.style="border-style:solid;border-color:#000;border:10";

    }
///////////////////////////////////////////////////////////////////////////////////////////////
    // takeUserInput(){
    //     this.takeUserInput1();
    // }
    InputMessage() {
        return "Enter  Input to Cell ex: 3d "
    }
////////////////////////////////////////////////////////////////////////////////////////////////
    Controller(state,input)
    {
        // this.ClearInput("firstInput");
        console.log(input);
        if(!this.isValidLength(input,2))
        {
            // window.alert("INVALID INPUT!!");
            return null ;
        }
        //    const {row,column}=this.FindRowCol(input);
        const r=this.FindRowCol(input);
        let row=r.Row
        let column=r.Col;
        console.log(row);
        console.log(column);
        if(! this.isCellInBounds(state.board,row,column))
        {
            // window.alert("INVALID INPUT!");
            return null ;
        }
        var col=true
        var ro=true
        var diagonal=true
        //delete the queen if you insert its place twice
        if(state.board[row][column]=='♕')
        {state.board[row][column]='0'; return state}
        //valid column
        for(let i=0;i<8;i++)
        {
            if(state.board[row][i]=='♕')
                col=false
        }
        //valid row
        for(let j=0;j<8;j++)
        {
            if(state.board[j][column]=='♕')
            {
                ro=false
                console.log(j);
                console.log("false y bnt rl nas");
                break;
            }
        }
        //valid diagonal in right down corner
        var l=column
        for(let i=row; i<8 && l<8;i++)
        {
            if(state.board[i][l++]=='♕')
            {
                diagonal=false;
                break;
            }

        }
        //valid diagonal in left up corner
        var l=column
        for(let i=row;i>-1 && l>-1;i--)
        {
            if(state.board[i][l--]=='♕')
            {
                diagonal=false;
                break;
            }
        }
        //valid diagonal in up right corner
        var l=column
        for(let i=row;i>-1 && l<8;i--)
        {
            if(state.board[i][l++]=='♕')
            {
                diagonal=false;
                break;
            }
        }
        //valid diagonal in down left corner
        var l=column
        for(let i=row;i<8 && l>-1;i++)
        {
            if(state.board[i][l--]=='♕')
            {
                diagonal=false;
                break;
            }
        }
        if( diagonal===true && col===true && ro===true)
        {
            state.board[row][column]='♕'
            return state
            //   this.Drawer(this.board);
        }
        else
        {
            console.log(state.board[row][column]);
            console.log(diagonal);
            console.log(col);
            console.log(ro);

            // window.alert("Cannot be placed here!");
            return null
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////
}
///////////////////////////////////////////////////////////////////////////////////////////////////

// const eightqueens=new eightQueens ();
// eightqueens.Initialize();

