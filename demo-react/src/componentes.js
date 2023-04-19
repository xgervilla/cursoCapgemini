import React, { Component } from 'react'

function Pantalla(props){
    return <output>{props.valor}</output>
}

function Botonera({onSube, onBaja}){
  return (
    <div>
      <input type='button' value='-' onClick={() => onBaja && onBaja()} />
      {onSube && <input type='button' value='+' onClick={onSube} />}
    </div>
  )
}

export class Contador extends Component {
  constructor(props){
      super(props)
      this.state = {
        contador: +(this.props.init ?? 0)
      }
      this.delta = +(this.props.delta ?? 1)

      this.sube = this.sube.bind(this)

      this.baja = () => { 
        this.changeContador(-this.delta)
      }
  }
  
  changeContador(value){
    this.setState(prev => ({contador: prev.contador + value}))
  }

  sube(){
    this.changeContador(this.delta)
  }
  
  render() {
    return (
      <div style={{marginLeft:'30px', marginTop:'30px'}}>
        <Pantalla valor={this.state.contador}/>
        <Botonera onSube={this.sube.bind(this)} onBaja={this.baja}/>
      </div>
    )
  }
}

export class Card extends Component {
  render() {
    return (
      <div>
        <h1>{this.props.title}</h1>
        {this.props.children}
      </div>
    )
  }
}

export function Esperando(){
  return (
    <img src='https://raw.githubusercontent.com/jmagit/BOOT20230320/main/demo-react/src/imagenes/loading.gif' alt='esperando respuesta del servidor'></img>
  )
}

export function ErrorMessage({msg}){
  return (
    <output style={{'color':'red'}}>{msg}</output>
  )
}