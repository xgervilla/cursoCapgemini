import React, { Component } from 'react'
import { Loading } from '../Componentes/componentes'

export default class NovedadesView extends Component {
    constructor(props){
        super(props)
        this.state = {
            loading: true,
            error: null
        }
    }
    render() {
        if (this.state.loading) {
            return (
                <div className='container'>
                    <div className='row justify-content-center'>
                        <div className='col-4'>
                            <Loading />
                            <p>Loading novedades</p>
                        </div>
                    </div>
                </div>
            )
        }
        return (
            <h1>Novedades</h1>
        )
    }

    setError(msg) {
        this.setState({ error: msg })
    }

    load() {
        //const url = `http://localhost:8080/catalogo/api`
    }
}
