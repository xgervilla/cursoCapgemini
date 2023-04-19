import logo from './logo.svg'
import './App.css'
import React, { Component } from 'react'
import { Card, Contador, Esperando } from './componentes'
import { Calculadora } from './calculadora'
import Muro from './muro'

export default class App extends Component {
  constructor(props){
    super(props)

    this.state = {
      main: 2
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
      <div>
        <Header menu={this.menu} active = {this.state.main} onSelectMenu={index => this.setState({main: index})}/>
        <main>
          {this.menu[this.state.main].componente}
        </main>
      </div>
      
      </>
    )
  }
}

function Header(props){
  return(
    <header>
      <nav className="navbar navbar-expand-lg">
      <a className="navbar-brand" href="/">Pruebas de frontend</a>
      <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span className="navbar-toggler-icon" />
      </button>
      <div className="collapse navbar-collapse" aria-labelledby="navbarSupportedContent">
        <Menu {...props}/>
      </div>
      
    </nav>
    </header>
  )
}



function Menu({menu, active, onSelectMenu}){
  return(
    <ul className="navbar-nav mr-auto">
      {menu.map((item, index) => <li className="nav-item" key={index}><a className={`nav-link ${active===index ? "activeMenu" : ""}`} href="#" onClick={() => {onSelectMenu && onSelectMenu(index)}}>{item.texto}</a></li>)
      }
    </ul>
  )
}

function Footer(){
  return null
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