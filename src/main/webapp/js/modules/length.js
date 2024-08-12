/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

const limitedInputs = (selectors) => {
    const limitedInputs = document.querySelectorAll(selectors);

    limitedInputs.forEach(input => {
        const limit = input.getAttribute('maxlength');

        input.addEventListener('input', () => {
            if (input.value.length > limit) {
                input.value = input.value.slice(0, limit);
            }
        });
    });
};

export default limitedInputs;
