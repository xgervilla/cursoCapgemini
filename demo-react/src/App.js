import logo from './logo.svg'
import './App.css'
import React, { Component } from 'react'
import { Card, Contador, Esperando } from './Componentes/componentes'
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
      {texto: 'Inicio', url: '/home', componente: <Home/>},
      {texto: 'Calculadora',  url: '/calculadora', componente: <Calculadora/>},
      {texto: 'Contador', url: '/contador', componente: <><Card title='Contador' ><Contador init={10}/></Card></>},
      {texto: 'Loading', url:'/loading', componente: <Esperando/>}
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
        {/*<Footer/>*/}
      </div>
      
      </>
    )
  }
}


function Home() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Hello world</h1>
        <h2>url: {process.env.REACT_APP_API_URL}</h2>
        <h2>environment: {process.env.NODE_ENV}</h2>

        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  )
}