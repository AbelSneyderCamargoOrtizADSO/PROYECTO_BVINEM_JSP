/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const soloLetras = (event) => {
    if (!/^[a-zA-ZÀ-ÿ\s]*$/.test(event.key)) event.preventDefault();
};

export default soloLetras;