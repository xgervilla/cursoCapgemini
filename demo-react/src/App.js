import './App.css'
import React, { Component } from 'react'
import { Card, Contador, Loading, Starter } from './Componentes/componentes'
import { Calculadora } from './Modulos/calculadora'
import Muro from './Modulos/muro'
import {Header, Footer} from './Componentes/basicos'

export default class App extends Component {
  constructor(props){
    super(props)

    this.state = {
      main: 0
    }

    this.menu = [
      {texto: 'Muro', url:'/', componente: <Muro/>},
      {texto: 'Inicio', url: '/home', componente: <Starter/>},
      {texto: 'Calculadora',  url: '/calculadora', componente: <Calculadora/>},
      {texto: 'Contador', url: '/contador', componente: <><Card title='Contador' ><Contador init={10}/></Card></>},
      {texto: 'Loading', url:'/loading', componente: <Loading/>}
    ]
  }

  render() {
    return (
      <>
      <div className='mainBody'>
        <Header menu={this.menu} active = {this.state.main} onSelectMenu={index => this.setState({main: index})}/>
        <main>
          {this.menu[this.state.main].componente}
        </main>
        <Footer/>
      </div>
      {/*<Catalogo/>*/}
      
      </>
    )
  }
}