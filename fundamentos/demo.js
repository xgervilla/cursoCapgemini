console.log('Soy el fichero en node')

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