function Calculadora(){
    let referencia = this
    let lastResult = 0;
	let accumulator = 0;
	let lastOP = 'none';
	let nextDecimal = 0;

    let result = document.querySelector('output')

    referencia.applyOperation = function(value) {
        console.log(value)
        switch(lastOP){
            case('suma'):
                lastResult = +lastResult + +accumulator
                accumulator = +0
                break
            case('resta'):
                lastResult = +lastResult - +accumulator
                accumulator = 0
                break;
            case('division'):
                lastResult = lastResult / accumulator
                accumulator = 0
                break;
            case('multiplicacion'):
                lastResult = lastResult * accumulator
                accumulator = 0
                break;
            case('modulo'):
                lastResult = lastResult % accumulator
                break;
            case('exponenciacion'):
                lastResult = lastResult ** accumulator
                accumulator = 0
                break;
            case('inversa'):
                if(accumulator != 0)
                    lastResult = 1 / accumulator
                else if(lastResult != 0)
                    lastResult = 1 / lastResult
                accumulator = 0
                break
            case('none'):
                lastResult = +accumulator
                accumulator = 0
                break
            case('calculate'):
                break
        }
    
        if (value == 'clear'){
                lastResult = +0
                accumulator = +0
                nextDecimal = +0
                result.textContent = 'Total: 0'
                lastOP = 'none'
        }
        else{
            let valueRounded = +lastResult
            result.textContent = 'Acumulado:    ' + parseFloat(valueRounded.toFixed(10))
            lastOP = value
        }
        nextDecimal = 0
        document.getElementById('decimal').disabled = false
    }
    
    referencia.accumulateValues = function(value){
        if(lastOP == 'calculate'){
            lastOP = 'none'
            lastResult = +0
            accumulator = +0
            nextDecimal = 0
    
        }
        if(nextDecimal>0){
            accumulator = +accumulator + +(value / (10**nextDecimal))
            nextDecimal +=1
        }
        else
            accumulator = +(accumulator * 10) + value;
        result.textContent = 'Acumulado: ' + +accumulator
    }

    referencia.startDecimals = function(){
            console.log('clicked on decimal')
            nextDecimal = 1
            document.getElementById('decimal').disabled = true
    }
}