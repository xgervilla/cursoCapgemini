import React, { Component } from 'react'
import './calculadora.css'

export class Calculadora extends Component {

    constructor(){
      super()
      this.state = {
        lastOP: 'none',
        accumulator: 0,
        lastResult: 0,
        nextDecimal: 0
      }
  
      this.applyOperation = this.applyOperation.bind(this)
      this.accumulateValues = this.accumulateValues.bind(this)
      this.startDecimals = this.startDecimals.bind(this)
    }
  
      applyOperation(value){
        console.log(value)
          switch(this.state.lastOP){
              case('suma'):
                  this.setState({lastResult: this.state.lastResult + this.state.accumulator})
                  this.setState({accumulator: 0})
                  break
              case('resta'):
                  this.setState({lastResult: this.state.lastResult - this.state.accumulator})
                  this.setState({accumulator: 0})
                  break;
              case('division'):
                  this.setState({lastResult: this.state.lastResult / this.state.accumulator})
                  this.setState({accumulator: 0})
                  break;
              case('multiplicacion'):
                  this.setState({lastResult: this.state.lastResult * this.state.accumulator})
                  this.setState({accumulator: 0})
                  break;
              case('modulo'):
                  this.setState({lastResult: this.state.lastResult % this.state.accumulator})
                  this.setState({accumulator: 0})
                  break;
              case('exponenciacion'):
                  this.setState({lastResult: this.state.lastResult ** this.state.accumulator})
                  this.setState({accumulator: 0})
                  break;
              case('inversa'):
                  if(this.state.accumulator !== 0)
                    this.setState({lastResult: 1 / this.state.accumulator})
                  else if(this.state.lastResult !== 0)
                    this.setState({lastResult: 1 / this.state.lastResult})
                  this.setState({accumulator: 0})
                  break
              case('none'):
                  this.setState({lastResult: this.state.accumulator})
                  this.setState({accumulator: 0})
                  break
              case('calculate'):
                  break
              default:
                break
          }
      
          if (value === 'clear'){
            this.setState({lastResult: 0})
            this.setState({accumulator: 0})
            this.setState({nextDecimal: 0})
            this.setState({lastOP:'none'})
          }
          else{
              this.setState({lastOP: value})
          }
          this.setState({nextDecimal:0})
          document.getElementById('decimal').disabled = false
      }
  
      accumulateValues(value){
        if(this.state.lastOP === 'calculate'){
          this.setState({lastResult: 0})
          this.setState({accumulator: 0})
          this.setState({nextDecimal: 0})
          this.setState({lastOP:'none'})
        }

        if(this.state.nextDecimal>0){
            this.setState({accumulator: +this.state.accumulator + (value / (10**this.state.nextDecimal))})
            this.setState({nextDecimal: this.state.nextDecimal + 1})
        }

        else {
            this.setState({accumulator: (this.state.accumulator * 10) + value})
        }
        
        console.log(this.state.accumulator)
      }
  
      startDecimals(){
        console.log('clicked on decimal')
        this.setState({nextDecimal: 1})
        document.getElementById('decimal').disabled = true
      }
  
      render(){
      return (
          <>
            <div className="container">
              <div className='row justify-content-center'>
                <div className='col-4'>
                  <div className="row">
                    <div className="result">
                      <output style={{'color':'grey'}}>{parseFloat(this.state.lastResult.toFixed(10))}</output>
                      <br/>
                      <output>{parseFloat(this.state.accumulator.toFixed(10))}</output>
                    </div>
                  </div>

                  <div className="row justify-content-center calculadora">
                      
                      <div className="row">
                        <input type="button" className="col" id="inversa" defaultValue="1/x" onClick={() => this.applyOperation('inversa')}/>
                        <input type="button" className="col" id="modulo" defaultValue="%" onClick={() => this.applyOperation('modulo')}/>
                        <input type="button" className="col" id="exponencial" defaultValue="exp" onClick={() => this.applyOperation('exponenciacion')}/>
                        <input type="button" className="col" id="multiplica" defaultValue="*" onClick={() => this.applyOperation('multiplicacion')}/>
                      </div>
                      
                      <div className="row">
                        <input type="button" className="col" id="number7" defaultValue={7} onClick={() => this.accumulateValues(7)}/>
                        <input type="button" className="col" id="number8" defaultValue={8} onClick={() => this.accumulateValues(8)}/>
                        <input type="button" className="col" id="number9" defaultValue={9} onClick={() => this.accumulateValues(9)}/>
                        <input type="button" className="col" id="divide" defaultValue="/" onClick={() => this.applyOperation('division')}/>
                      </div>
                      
                      <div className="row">
                        <input type="button" className="col" id="number4" defaultValue={4} onClick={() => this.accumulateValues(4)}/>
                        <input type="button" className="col" id="number5" defaultValue={5} onClick={() => this.accumulateValues(5)}/>
                        <input type="button" className="col" id="number6" defaultValue={6} onClick={() => this.accumulateValues(6)}/>
                        <input type="button" className="col" id="suma" defaultValue="+" onClick={() => this.applyOperation('suma')}/>
                      </div>
                      
                      <div className="row">
                        <input type="button" className="col" id="number1" defaultValue={1} onClick={() => this.accumulateValues(1)}/>
                        <input type="button" className="col" id="number2" defaultValue={2} onClick={() => this.accumulateValues(2)}/>
                        <input type="button" className="col" id="number3" defaultValue={3} onClick={() => this.accumulateValues(3)}/>
                        <input type="button" className="col" id="resta" defaultValue="-" onClick={() => this.applyOperation('resta')}/>
                      </div>
                      
                      <div className="row">
                        <input type="button" className="col" id="number0" defaultValue={0} onClick={() => this.accumulateValues(0)}/>
                        <input type="button" className="col" id="clear" defaultValue="C" onClick={() => this.applyOperation('clear')}/>
                        <input type="button" className="col" id="decimal" defaultValue="." onClick={() => this.startDecimals()}/>
                        <input type="button" className="col" id="calcula" defaultValue="=" onClick={() => this.applyOperation('calculate')}/>
                      </div>
                  </div>
                </div>
              </div>
            </div>
          </>
      )}
  } 