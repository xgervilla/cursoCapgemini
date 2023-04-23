import './App.css';
import React, { Component } from 'react'

import { Header } from './biblioteca/comunes'
import { Actores } from './componentes/actores'
import { Peliculas }from './componentes/peliculas'
import {Categorias} from './componentes/categorias'
import { Lenguajes } from './componentes/lenguajes'

export default class App extends Component {
  constructor(props){
    super(props)

    this.state = {
      main: 0
    }

    this.menu = [
      {texto: 'Actores', url:'/actor', componente: <Actores/>},
      {texto: 'Peliculas', url:'/peliculas', componente: <Peliculas/>},
      {texto: 'Lenguajes', url:'/lenguajes', componente: <Lenguajes/>},
      {texto: 'Categorias', url:'/categorias', componente: <Categorias/>}
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
        </div>
      </>
    )
  }
}