//import logo from './logo.svg'
import './App.css'
import React, { Component } from 'react'
import { Contador } from './componentes'
//import { Calculadora } from './calculadora'

export default class App extends Component {
  render() {
    return (
      <>
        <Contador init={10} delta={2} className="container-fluid"/>
      </>
    )
  }
}

/*
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
}*/