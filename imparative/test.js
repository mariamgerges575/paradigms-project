Input="22"
const Row = parseInt(Input[0].charCodeAt(0)) - 49;
const Col = Input.toLowerCase().charCodeAt(1) - 97;
console.log(Row,Col)