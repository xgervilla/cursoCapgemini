import React, { Component } from 'react'
import { Loading, ErrorMessage } from '../Componentes/componentes'

export default class LenguajesView extends Component {
  constructor(props) {
    super(props)
    this.state = {
      loading: true,
      error: null,
      languagesList: null
    }
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
                {this.state.languagesList && this.state.languagesList.map((lang, index) =>
                  <div className='row rowTable'>
                    <div className='col'>
                          <p>{lang.ID}: {lang.Name}</p>
                    </div>
                    <div className='col'>
                      <button type='button' className='btn btn-primary btnTable'>See</button>
                      <button type='button' className='btn btn-secondary btnTable'>Modify</button>
                      <button type='button' className='btn btn-danger btnTable'>Delete</button>
                    </div>
                  </div>
                )}
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

  load() {
    fetch(`http://localhost:8080/catalogo/api/lenguajes`)
      .then(resp => {
        if (resp.ok) {
          resp.json().then(data => this.setState({ languagesList: data }))
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
    this.load()
  }
}
