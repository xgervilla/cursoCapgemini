import React, { Component } from 'react'
import { ErrorMessage, Loading } from '../Componentes/componentes'
import './muro.css'
import { PaginationButtons } from '../Componentes/basicos'

export default class Muro extends Component {
    constructor(props){
        super(props)
        this.state = {
            listado: null,
            loading: true,
            error: null,
            pageNumber: 1,
            pageLimit: 10,
            imgShown: []

        }

        this.nextPage = this.nextPage.bind(this)
        
        this.previousPage = this.previousPage.bind(this)

        this.changeImageShown = this.changeImageShown.bind(this)

    }

    changeImageShown(index){
        const updatedImages = this.state.imgShown.map((shown, it) => {
            if(it === index){
                return !shown
            }
            else
                return shown
        })
        this.setState({imgShown: updatedImages})
    }

    previousPage(){
        let newPage = Math.max(1,this.state.pageNumber-1)
        this.setState({pageNumber: newPage})
        this.load(newPage)
    }

    nextPage(){
        let newPage = this.state.pageNumber+1
        this.setState({pageNumber: newPage})
        this.load(newPage)
    }

    render() {
        if(this.state.loading){
            return <Loading/>
        }
        return (
            <>
            {this.state.error && <ErrorMessage msg = {this.state.error}/>}
            <div className='container'>
                <div className='row'>
                    <div className='col'>
                        <div className='row'>
                        {this.state.listado && this.state.listado.map((item, index) =>
                            <>
                                <div className='col'>
                                    <div className='card cardBlock'>
                                        <img className="card-img-top" src={item.download_url} alt={'Image with id: '+ item.id} hidden={!this.state.imgShown[index]}/>
                                        <div className='card-body'>
                                            <h5>{item.author}</h5>
                                            <p>Original size: {item.width}x{item.height}</p>
                                            <button className='buttonMuro' type='button' onClick={() => this.changeImageShown(index)}>{this.state.imgShown[index] ? 'Hide picture' : 'Show picture'}</button>
                                        </div>
                                    </div>
                                </div>
                            </>
                        )}
                        </div>
                        <PaginationButtons pageNumber={this.state.pageNumber} nextPage={this.nextPage} previousPage={this.previousPage}/>
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
                    resp.json().then(data => this.setState({listado: data}))
                    let imagesToShow = []
                    for(let i = 0; i<this.state.pageLimit; ++i){
                        imagesToShow[i] = false
                    }
                    this.setState({imgShown: imagesToShow})
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