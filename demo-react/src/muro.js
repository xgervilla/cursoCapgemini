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
            pageLimit: 14
        }

        this.nextPage = () => {this.setState({pageNumber: this.state.pageNumber+1}); console.log(this.state.pageNumber) ; this.load(this.state.pageNumber)}
        
        this.previousPage = () => {this.setState({pageNumber: Math.max(1,this.state.pageNumber-1)}); this.setState({loading: true}); this.load(this.state.pageNumber)}

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
                                        <BasePicture url={item.download_url} author={item.author} height={item.height} width={item.width}/>
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

function BasePicture({url, author, width, height}){
    return (
        <div className='card' style={{'marginTop':'10px'}}>
            <img className="card-img-top" width='100' height='100' src={url} alt="Image downloaded from api"/>
            <div className='card-body'>
                <h5>{author}</h5>
                <p>Size: {width}x{height}</p>
            </div>
        </div>)
}