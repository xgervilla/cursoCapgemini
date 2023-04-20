import React, { Component } from 'react'
import { Header } from '../Componentes/basicos'
import Actores from './actores'
import Peliculas from './peliculas'
import Categorias from './categorias'
import Novedades from './novedades'
import Lenguajes from './lenguajes'
import Muro from '../Modulos/muro'
import {PadreFormulario } from '../Modulos/formulario'

/*
  ** 4 tipo de operaciones:
    - Lectura (visualización de objetos/atributos)
        - Lista de todos los objetos de una entidad
        - Lista modo pageable
        - Todos los atributos de un único objeto
    - Modificación de un objeto ya existente
        - Varios tipos de modificación?
        - Posibilidad de dejar inputs como 'opcionales' y sobreescribir aquellos inputs que se hayan
        rellenado a la hora de hacer el put: if inputValue = default/empty then ignore else attribute = inputValue
        - Qué pasa con las referencias de otras entidades?
    - Creación de un nuevo objeto
        - Varios tipos de creación?
            - Todos los atributos
            - Atributos principales/esenciales
            - Todos los atributos excepto las referencias externas (lista de actores en film, lista de peliculas en categories...)
    - Eliminación de un objeto (puede o no existir)
        - Una única operación para eliminar, si el id no existe no hay ningún problema

  ** esquema de diseño:
    - Página principal
        - cabecera para seleccionar con qué entidad se quiere trabajar --> desplegable para seleccionar el tipo de operación?
        - cuerpo: ??
        - footer
    - Página de entidad --> pagina inicial con la información de las entidades? de los tipos de operaciones que se pueden aplicar? o directamente una vista general (pages)?
        - cabecera de selección
        - subcabecera con el tipo de operación?
        - cuerpo:
            - separando por operaciones: una página por operación?
            - sin separar: tabs/similar para diferenciar?
            - todo en una misma página?
        - paginas/separaciones por operacion:
            - GET: sólo es información que se muestra, no hay inputs --> de las vistas generales se pueden añadir botones para ver el elemento individual, modificarlo o eliminarlo -> donde añadir el crear?
            - PUT/POST: todos los parámetros/inputs necesarios
            - DELETE: ni siquiera se muestra información, hace falta indicar algo?
*/
export class Catalogo extends Component {
  constructor(props){
    super(props)

    this.state = {
      main: 0
    }

    this.menu = [
      {texto: 'Actores', url:'/actor', componente: <Actores/>},
      {texto: 'Peliculas', url:'/peliculas', componente: <Peliculas/>},
      {texto: 'Lenguajes', url:'/lenguajes', componente: <Lenguajes/>},
      {texto: 'Categorias', url:'/categorias', componente: <Categorias/>},
      {texto: 'Novedades', url:'/novedades', componente: <Novedades/>},
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
