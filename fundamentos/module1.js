let nombre = 'Modulo de pruebas'
function resta(a,b) {return a - b}

function calc(a,b) {return resta(a,b)}

function display() {console.log("Ejecuto desde el modulo")}
export {nombre, calc, display}