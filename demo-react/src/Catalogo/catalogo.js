import React, { Component } from 'react'
import { Header } from '../Componentes/basicos'
import { Actores } from './componentes/actores'
import { Peliculas }from './componentes/peliculas'
import {Categorias} from './componentes/categorias'
import { Lenguajes } from './componentes/lenguajes'
import Muro from '../Modulos/muro'
import {PadreFormulario } from '../Modulos/formulario'

export class Catalogo extends Component {
  constructor(props){
    super(props)

    this.state = {
      main: 1
    }

    this.menu = [
      {texto: 'Actores', url:'/actor', componente: <Actores/>},
      {texto: 'Peliculas', url:'/peliculas', componente: <Peliculas/>},
      {texto: 'Lenguajes', url:'/lenguajes', componente: <Lenguajes/>},
      {texto: 'Categorias', url:'/categorias', componente: <Categorias/>},
      //{texto: 'Novedades', url:'/novedades', componente: <Novedades/>},
      {texto: 'Muro', url: '/muro', componente: <Muro/>},
      {texto: 'Formulario', url:'/formulario', componente: <PadreFormulario/>}
    ]
  }

  render() {
    return (
      <div className='mainBody'>
        <Header menu={this.menu} active = {this.state.main} onSelectMenu={index => this.setState({main: index})}/>
        <main>
          {this.menu[this.state.main].componente}
        </main>
      </div>
    )
  }
}
