import React, { Component } from 'react'
import '../src/calculadora.css'

export class Calculadora extends Component {

    constructor(){
      super()
      this.lastOP = 'none'
      this.accumulator = 0
      this.lastResult = 0
      this.nextDecimal = 0
  
      this.applyOperation = this.applyOperation.bind(this)
      this.accumulateValues = this.accumulateValues.bind(this)
      this.startDecimals = this.startDecimals.bind(this)
    }
  
      applyOperation(value){
        console.log(value)
          switch(this.lastOP){
              case('suma'):
                  this.lastResult = this.lastResult + this.accumulator
                  this.accumulator = 0
                  break
              case('resta'):
                  this.lastResult = this.lastResult - this.accumulator
                  this.accumulator = 0
                  break;
              case('division'):
                  this.lastResult = this.lastResult / this.accumulator
                  this.accumulator = 0
                  break;
              case('multiplicacion'):
                  this.lastResult = this.lastResult * this.accumulator
                  this.accumulator = 0
                  break;
              case('modulo'):
                  this.lastResult = this.lastResult % this.accumulator
                  this.accumulator = 0
                  break;
              case('exponenciacion'):
                  this.lastResult = this.lastResult ** this.accumulator
                  this.accumulator = 0
                  break;
              case('inversa'):
                  if(this.accumulator !== 0)
                    this.lastResult = 1 / this.accumulator
                  else if(this.lastResult !== 0)
                    this.lastResult = 1 / this.lastResult
                  this.accumulator = 0
                  break
              case('none'):
                  this.lastResult = this.accumulator
                  this.accumulator = 0
                  break
              case('calculate'):
                  console.log(this.accumulator)
                  console.log(this.lastResult)
                  break
              default:
                break
          }
      
          if (value === 'clear'){
            this.lastResult = 0
            this.accumulator = 0
            this.nextDecimal = 0
            document.getElementById('output').value = 'Total: 0'
            this.lastOP = 'none'
          }
          else{
              let valueRounded = this.lastResult
              document.getElementById('output').value = 'Acumulado: ' + parseFloat(valueRounded.toFixed(10))
              this.lastOP = value
          }
          this.nextDecimal = 0
          document.getElementById('decimal').disabled = false
      }
  
      accumulateValues(value){
        if(this.lastOP === 'calculate'){
            this.lastOP = 'none'
            this.lastResult = 0
            this.accumulator = 0
            this.nextDecimal = 0
    
        }
        if(this.nextDecimal>0){
            this.accumulator =  +this.accumulator + (value / (10**this.nextDecimal))
            this.nextDecimal += 1
        }
        else {
            this.accumulator = (this.accumulator * 10) + value;
            document.getElementById('output').value = 'Acumulado: ' + this.accumulator
        }
        console.log(this.accumulator)
      }
  
      startDecimals(){
        console.log('clicked on decimal')
        this.nextDecimal = 1
        document.getElementById('decimal').disabled = true
      }
  
      render(){
      return (
          <>
            <header>
              <h1 id="pagetitle">Calculadora</h1>
            </header>
            <div className="container">
              <div className="row justify-content-center">
                <div className="col-lg-6 calculadora">
                  <div className="row-cols-6">
                    <input type="button" id="inversa" defaultValue="1/x" onClick={() => this.applyOperation('inversa')}/>
                    <input type="button" id="modulo" defaultValue="%" onClick={() => this.applyOperation('modulo')}/>
                    <input type="button" id="exponencial" defaultValue="exp" onClick={() => this.applyOperation('exponenciacion')}/>
                    <input type="button" id="multiplica" defaultValue="*" onClick={() => this.applyOperation('multiplicacion')}/>
                  </div>
                  <div className="row-cols-6">
                    <input type="button" id="number7" defaultValue={7} onClick={() => this.accumulateValues(7)}/>
                    <input type="button" id="number8" defaultValue={8} onClick={() => this.accumulateValues(8)}/>
                    <input type="button" id="number9" defaultValue={9} onClick={() => this.accumulateValues(9)}/>
                    <input type="button" id="divide" defaultValue="/" onClick={() => this.applyOperation('division')}/>
                  </div>
                  <div className="row-cols-6">
                    <input type="button" id="number4" defaultValue={4} onClick={() => this.accumulateValues(4)}/>
                    <input type="button" id="number5" defaultValue={5} onClick={() => this.accumulateValues(5)}/>
                    <input type="button" id="number6" defaultValue={6} onClick={() => this.accumulateValues(6)}/>
                    <input type="button" id="suma" defaultValue="+" onClick={() => this.applyOperation('suma')}/>
                  </div>
                  <div className="row-cols-6">
                    <input type="button" id="number1" defaultValue={1} onClick={() => this.accumulateValues(1)}/>
                    <input type="button" id="number2" defaultValue={2} onClick={() => this.accumulateValues(2)}/>
                    <input type="button" id="number3" defaultValue={3} onClick={() => this.accumulateValues(3)}/>
                    <input type="button" id="resta" defaultValue="-" onClick={() => this.applyOperation('resta')}/>
                  </div>
                  <div className="row-cols-6">
                    <input type="button" id="number0" defaultValue={0} onClick={() => this.accumulateValues(0)}/>
                    <input type="button" id="clear" defaultValue="C" onClick={() => this.applyOperation('clear')}/>
                    <input type="button" id="decimal" defaultValue="." onClick={() => this.startDecimals()}/>
                    <input type="button" id="calcula" defaultValue="=" onClick={() => this.applyOperation('calculate')}/>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="offset-md-4 col-md-3 result">
                  <output id="output">Total: 0</output>
                </div>
              </div>
            </div>
          </>
      )}
  } 