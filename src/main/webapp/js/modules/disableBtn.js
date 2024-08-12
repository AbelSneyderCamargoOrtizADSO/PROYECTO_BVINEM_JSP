/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const disableBtn = (checkbox, submitButton) => {
    submitButton.disabled = true;
    checkbox.addEventListener('change', function () {
        submitButton.disabled = !checkbox.checked;
    });
};

export const disableSearch = (input, submitButton) => {
    if (input.value.trim() === "") {
        submitButton.disabled = true;
    } else {
        submitButton.disabled = false;
    }
};

export default disableBtn;