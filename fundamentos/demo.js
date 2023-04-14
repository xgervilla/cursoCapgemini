console.log('Ejecuto desde un fichero externo')

var x;
function getCountdown(){
	clearInterval(x);
	var selectedTime = document.getElementById("time-value").value;
	console.log(selectedTime);
	var countDownDate = new Date(selectedTime).getTime();

	x = setInterval(function(){
		var now = new Date().getTime();
		var timeLeft = countDownDate - now;

		var days = Math.floor((timeLeft / (1000 * 60 * 60 * 24)));
		var hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		var minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);
		document.getElementById("countdown-timer").innerHTML = days+"d "+ hours+"h " + minutes+"m "+ seconds+"s";

		if (timeLeft < 0){
			clearInterval(x);
			document.getElementById("countdown-timer").innerHTML = "FIN DE SEMANA";
		}
	}, 1000);
}

class Persona {
	constructor(id, nombre, apellidos){
		this.id = id
		this.nombre = nombre
		this.apellidos = apellidos
	}
	
	//acceso como un atributo obj.nombreCompleto --> el get es lo que lo identifica como 'atiributo' pese a estar generado como una función
	get nombreCompleto() { return `${this.apellidos}, ${this.nombre}`}
	
	//acceso como una funcion obj.pinta()
	pinta(){
		console.log(this.nombreCompleto)
	}
}

let p1 = new Persona(1, "Andres", "Iniesta")
let p2 = new Persona(2, "Iker","Casillas")

p1.pinta()
console.log(p2.nombreCompleto)