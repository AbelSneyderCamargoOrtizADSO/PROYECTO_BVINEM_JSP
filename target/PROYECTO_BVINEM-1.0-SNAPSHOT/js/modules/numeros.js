/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const soloNumeros = (event) => {
  if (event.keyCode < 48 || event.keyCode > 57) event.preventDefault();
};

export default soloNumeros;
