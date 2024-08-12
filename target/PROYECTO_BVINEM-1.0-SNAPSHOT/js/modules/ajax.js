/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const solicitud = async (endpoint) => {
  try {
    let solicitud = await fetch(`${endpoint}`);
    let data = await solicitud.json();
    return data;
  } catch (error) {
    return error;
  }
};

export default solicitud;

