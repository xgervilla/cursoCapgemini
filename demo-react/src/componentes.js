import React, { Component } from 'react'

function Pantalla(props){
    return <output>{props.valor}</output>
}

export class Contador extends Component {
    constructor(props){
        super(props)
        this.cont = this.props.init ?? 0
    }
  render() {
    return (
      <div>
        <Pantalla valor={this.cont}/>
        <div>
            <input type='button' value='+'/>
            <input type='button' value='-'/>
        </div>
      </div>
    )
  }
}
