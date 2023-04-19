import React, { Component } from 'react'
import { ErrorMessage, Esperando } from './componentes'
import './muro.css'

export default class Muro extends Component {
    constructor(props){
        super(props)
        this.state = {
            listado: null,
            loading: true,
            error: null,
            pageNumber: 1,
            pageLimit: 18
        }

        this.nextPage = () => {this.setState({pageNumber: this.state.pageNumber+1}); console.log(this.state.pageNumber) ; this.load(this.state.pageNumber)}
        
        this.previousPage = () => {this.setState({pageNumber: Math.max(1,this.state.pageNumber-1)}); console.log(this.state.pageNumber) ; this.load(this.state.pageNumber)}

    }

    render() {
        if(this.state.loading){
            return <Esperando/>
        }
        return (
            <>
            {this.state.error && <ErrorMessage msg = {this.state.error}/>}
            <h1>
                Muro
            </h1>
                <div className='container'>
                    <div className='row'>
                        <div className='col'>
                            <div className='row'>
                            {this.state.listado && this.state.listado.map((item) =>
                                <>
                                    <div className='col'>
                                        <img src={item.download_url} width={100} height={100} alt="imagen obtenida del api"/>
                                        <p>{item.author}</p>
                                    </div>
                                </>
                            )}
                            </div>
                            <div className='row advance'>
                                <button className='col' type='button' onClick={this.previousPage}>Previous</button>
                                <button className='col' type='button' onClick={this.nextPage}>Next</button>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        )
    }
    setError(msg){
        this.setState({error: msg})
    }

    load(num){
        fetch(`https://picsum.photos/v2/list?page=${num}&limit=${this.state.pageLimit}`)
            .then(resp => {
                if(resp.ok){
                    resp.json().then(
                        data => this.setState({listado: data})
                    )
                }
                else {
                    this.setError(resp.status)
                }
            })
            .catch(err => {
                this.setError(JSON.stringify(err))
            })
            .finally(() => this.setState({loading: false}))
    }
    componentDidMount(){
        this.load(this.state.pageNumber)
    }
}
