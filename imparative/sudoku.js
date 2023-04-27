// import Generator from "yeoman-generator";
// import { Abstract_game_engine } from "./Abstract_game_engine.js";

// export class sudoku extends Abstract_game_engine {
    
//     style='height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#ffffff;border-style:solid;border-color:#000;border:0';
//     // constructor(){
//     //     this.initialize_state(6,7);//hy3ml init l any state
//     // }
//     createBoard(){
//         state=[[' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//            [' ',' ',' ',' ',' ', ' ',' ',' ', ' ' ],
//         ];
//         return this.state;
//     }
//     takeUserInput() {
//        this.takeUserInput2();
      
//     }
//     Drawer(state){
//         const to_del=document.getElementById("secondInput")
//         if (to_del!=null)
//         {
//             to_del.remove()
//         }
//         const todel=document.getElementById("label2")
//         if (todel!=null)
//         {
//             todel.remove()
//         }
        
//         const to_be_del=document.getElementById("tablee")
//         if (to_be_del!=null){
//             to_be_del.remove()
//         }

//         const tbl = document.createElement("table");
//         tbl.style='border:none';

//         tbl.setAttribute("id","tablee");  // create table
//         const tblBody = document.createElement("tbody"); 
//         const row = document.createElement("tr");
//         const cell = document.createElement("td");
//         cell.style=this.style;
//         row.appendChild(cell)
//         const cell2=document.createElement("td");
//         cell2.style.backgroundColor="#1f59df"
//         cell.appendChild(cell2)
//         tblBody.appendChild(row)

//         this.drawFisrtRow(this.style); //creating abc row

//         const col=document.createElement("tc");
//         col.appendChild(document.createElement("tc"))
//         for (i =0 ;i<this.rowNumbers;i++){
//             const cell = document.createElement("td");
//             cell.style=style//'height:60px;width:60px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;box-shadow: #000;background-color:#e0e0d8';
//             cell.innerHTML=i+1
//             col.appendChild(cell);
//         }
//         tblBody.appendChild(col);

//         for(i=0;i<3;i++){
//             const row=document.createElement("tr")

//             row.appendChild()
//         }
//         //     const cell = document.createElement("td");
//         //     cell.style=this.style;
//         //     let ascii=i+97
//         //     ascii='&#0'+ascii
//         //     cell.innerHTML=ascii
//         //     row.appendChild(cell);
//         // }
//         // tblBody.appendChild(row);
      
//         // for (let i = 0; i <state.length  ; i++) {// hlf 3la rows el state w el columns kol mara h create row w h3ml append kol mara 
//         //     // const cell=document.createElement("td");
//         //     // cell.style=this.style;
//         //     // cell.innerHTML=i+1;
//         //     // tblBody.appendChild(cell);
//         //     if(i%3==0){
//         //     const innerTable=document.createElement("tbody")
//         //     innerTable.style='height:180px;width:180px;margin:1px;vertical-align: middle;text-align:center;font-size: 25px;background-color:#ffffff;border-color:#ffffff;border:50;';
//         //     innerTable.style.backgroundColor="#ffffff"
//         //     tblBody.appendChild(innerTable);
//         //     }

//         //     const row = document.createElement("tr");
//         //     // const cell=document.createElement("td");
//         //     // cell.style=this.style;
//         //     // cell.innerHTML=i+1;
//         //     // row.appendChild(cell);
           
//         //     // for (let j = 0; j < 3; j++) { 
               
//         //     //     const cell = document.createElement("td");
//         //     //     cell.style=this.style;
//         //     //     cell.innerHTML=state[i][j];

//         //     //     row.appendChild(cell); 
//         //     // }
//         //     // innerTable.appendChild(row);    
//         // }
        
//         // tbl.appendChild(tblBody);// append table body to table nfso 
//         // document.body.appendChild(tbl); // b append kol dah lel document 
//         // tbl.style="border-style:solid;border-color:#000;border:10;background-color:#000";
       
//     }
//     Controller(position,value){ //c is char
//         valid=1
//         const value = Number(value);
//         if(this.isNotValidLength(position)){
//            const {r,c} =this.FindRowCol(position)
//            if(this.isCellInBounds(r,c)){
//                 for(i=0;i<9;i++){
//                     for(j=0;j<9;j++){
//                         if(this.board[i][j]==value && (i==r || c.charCodeAt(0)-97==j)){
//                             window.alert("cant put this value here")
//                             valid=0;
//                             break;
//                         }
//                     }
//                 }
//                 if(valid){
//                 this.state[i][j]=value;
//                 this.drawer(this.board);
//                 }
//             }
//             else
//                 window.alert("out of bounds")

//         }
//         else
//             window.alert("mistake in input")
//     }

// }
// // c=new sudoku();
// // c.Initialize();