import React, { Component } from 'react'
import { Loading, ErrorMessage } from '../../Componentes/componentes'
import { PaginationButtons } from '../../Componentes/basicos'


export default class ActoresView extends Component {
  constructor(props) {
    super(props)
    this.state = {
      loading: true,
      error: null,
      pageNumber: 0,
      pageSize: 13,
      actorList: null
    }
      this.previousPage = this.previousPage.bind(this)
      this.nextPage = this.nextPage.bind(this)
  }

  previousPage(){
    let newPage = Math.max(0,this.state.pageNumber-1)
    this.setState({pageNumber: newPage})
    this.load(newPage, this.state.pageSize)
  }

  nextPage(){
    let newPage = this.state.pageNumber+1
    this.setState({pageNumber: newPage})
    this.load(newPage, this.state.pageSize)
  }

  render() {
    if (this.state.loading) {
      return (
        <div className='container'>
          <div className='row justify-content-center'>
            <div className='col-4'>
              <Loading />
            </div>
          </div>
        </div>
      )
    }
    return (
      <>
        {this.state.error && <ErrorMessage msg={this.state.error} />}
        <div className='container'>
          <div className='row'>
            <div className='col'>
              <div className='row'>
                {this.state.actorsList && this.state.actorsList.content.map((actor, index) =>
                  <div className='row rowTable'>
                    <div className='col'>
                          <p>{actor.actorId}: {actor.nombre}</p>
                    </div>
                    <div className='col'>
                      <button type='button' className='btn btn-primary btnTable'>See</button>
                      <button type='button' className='btn btn-secondary btnTable'>Modify</button>
                      <button type='button' className='btn btn-danger btnTable'>Delete</button>
                    </div>
                  </div>
                )}
                <PaginationButtons pageNumber={this.state.pageNumber} nextPage={this.nextPage} previousPage={this.previousPage} firstPage={1}/>
              </div>
            </div>
          </div>
        </div>
      </>
    )
  }

  setError(msg) {
    this.setState({ error: msg })
  }

  load(pageNumber, pageSize) {
    this.setState({loading: true})
    fetch(`http://localhost:8080/catalogo/api/actores?page=${pageNumber}&size=${pageSize}`)
      .then(resp => {
        if (resp.ok) {
          resp.json().then(data => this.setState({ actorsList: data }))
        }
        else {
          this.setError(resp.status)
        }
      })
      .catch(err => {
        this.setError(JSON.stringify(err))
      })
      .finally(() => this.setState({ loading: false }))
  }

  componentDidMount() {
    this.load(this.state.pageNumber, this.state.pageSize)
  }
}
