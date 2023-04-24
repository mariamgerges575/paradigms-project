import Abstract_game_engine from Abstract_game_engine.js
class Checkers extends Abstract_game_engine{
    map= new Map();
    constructor(){
        this.board = [
            [-1, 1,-1, 1,-1, 1,-1, 1],
            [ 1,-1, 1,-1, 1,-1, 1,-1],
            [-1, 1,-1, 1,-1, 1,-1, 1],
            [-1]*8,
            [-1]*8,
            [ 0,-1, 0,-1, 0,-1, 0,-1],
            [-1, 0,-1, 0,-1, 0,-1, 0],
            [ 0,-1, 0,-1, 0,-1, 0,-1]
        ];
        this.currentPlayer = 'white';
        this.map.set(1,'&#9898');
        this.map.set(0, '&#9890')
        console.log("constructor called")

    }
    draw(){
        this.drawer(this.board);
    }
    drawer(state){
        const to_be_del=document.getElementById("tablee")
        if (to_be_del!=null){
            to_be_del.remove()
        }
        const tbl = document.createElement("table");
        tbl.setAttribute("id","tablee");
        const tblBody = document.createElement("tbody");
        console.log(state)
        for (let i = 0; i < state.length; i++) {

            const row = document.createElement("tr");
            for (let j = 0; j < state[0].length; j++) {  

                const cell = document.createElement("td");
                cell.style='height:80px;width:80px;margin:1px;vertical-align: middle;text-align:center;font-size: 50px;box-shadow: #000;';
              
                if (!(i%2==1 ^ j%2==0 ))
                    cell.style.backgroundColor='#000';
     
                if (state[i][j]==1) //black player
                    cell.innerHTML='&#9899'
                else if (state[i][j]==0) //white player
                    cell.innerHTML='&#9898'

                row.appendChild(cell);
                
            }
            tblBody.appendChild(row);
        }
        tbl.appendChild(tblBody);
        document.body.appendChild(tbl);
        tbl.style="border-style:solid;border:2"
    
    }
}
const checkers=new Checkers();
checkers.drawer(checkers.board);