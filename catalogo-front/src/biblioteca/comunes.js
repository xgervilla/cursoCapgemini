import React, { Component } from 'react'
import './comunes.css'

export class ErrorBoundary extends Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false, error: null, errorInfo: null };
    }
    static getDerivedStateFromError(error) {
        return { hasError: true };
    }
    componentDidCatch(error, info) {
        this.setState({ hasError: true, error: error, errorInfo: info })
    }
    render() {
        if (this.state.hasError) { // Puedes renderizar cualquier interfaz de repuesto
            return <div>
                <h1>ERROR</h1>
                {this.state.error && <p>{this.state.error.toString()}</p>}
                {this.state.errorInfo && <p>{this.state.errorInfo.componentStack}</p>}
            </div>;
        }
        return this.props.children;
    }
}

export function Esperando() {
    return <div>
        <div className="ajax-wait"></div>
        <div className="loader"></div>
        {/*<img src='https://raw.githubusercontent.com/jmagit/BOOT20230320/main/demo-react/src/imagenes/loading.gif' alt='esperando respuesta del servidor'></img> */}
    </div>;
}


export function ValidationMessage({ msg }) {
    if (msg) {
        return <output className="errorMsg">{msg}</output>;
    }
    return null;
}

export function ErrorMessage({ msg, onClear }) {
    if (msg) {
        return (
            <div className="alert alert-danger alert-dismissible fade show" role="alert">
                {msg}
                <button
                    type="button"
                    className="btn-close"
                    data-dismiss="alert"
                    aria-label="Close"
                    onClick={e => onClear && onClear()}/>
            </div>
        );
    }
    return null;
}

export function PaginacionCmd({ actual, total, onChange }) {
    let click = (number, ev) => {
        ev.preventDefault()
        if (onChange) onChange(number)
    }
    if (total < 10){
        let items = [];
        for (let number = 0; number < total; number++) {
            items.push(
                number === actual ?
                    <li key={number} className="page-item active" aria-current="page"><a href='.'
                        className="page-link" onClick={click.bind(this, number)} >{number + 1}</a></li>
                    :
                    <li key={number} className="page-item"><a href='.' className="page-link"
                        onClick={click.bind(this, number)} >{number + 1}</a></li>
            );
        }
        return (
            <nav aria-label="Page navigation">
                <ul className="pagination">
                    {items}
                </ul>
            </nav>
        )
    }
    else if(actual <3 || actual >= total-3){
        let items = []
        for (let number = 0; number < 5; number++) {
            items.push(
                number === actual ?
                    <li key={number} className="page-item active" aria-current="page"><a href='.'
                        className="page-link" onClick={click.bind(this, number)} >{number + 1}</a></li>
                    :
                    <li key={number} className="page-item"><a href='.' className="page-link"
                        onClick={click.bind(this, number)} >{number + 1}</a></li>
            );
        }
        items.push(<li key='dissabled' className="page-item"><a href='#' className='page-link'>...</a></li>)
        for (let number = total-5; number < total; number++) {
            items.push(
                number === actual ?
                    <li key={number} className="page-item active" aria-current="page"><a href='.'
                        className="page-link" onClick={click.bind(this, number)} >{number + 1}</a></li>
                    :
                    <li key={number} className="page-item"><a href='.' className="page-link"
                        onClick={click.bind(this, number)} >{number + 1}</a></li>
            );
        }
        return (
            <nav aria-label="Page navigation">
                <ul className="pagination">
                    {items}
                </ul>
            </nav>
        )
    }
    else {
        let items = []
        items.push([
            <li key={0} className="page-item"><a href='.' className="page-link" onClick={click.bind(this, 0)} >{1}</a></li>,
            <li key='dissabledFirst' className="page-item"><a href='#' className='page-link'>...</a></li>])
        
        for (let number = actual-2; number <= actual+2; number++) {
            items.push(
                number === actual ?
                    <li key={number} className="page-item active" aria-current="page"><a href='.'
                        className="page-link" onClick={click.bind(this, number)} >{number + 1}</a></li>
                    :
                    <li key={number} className="page-item"><a href='.' className="page-link"
                        onClick={click.bind(this, number)} >{number + 1}</a></li>
            );
        }
        items.push([
            <li key='dissabledLast' className="page-item"><a href='#' className='page-link'>...</a></li>,
            <li key={total} className="page-item"><a href='.' className="page-link" onClick={click.bind(this, total)} >{total+1}</a></li>])
        return (
            <nav aria-label="Page navigation">
                <ul className="pagination">
                    {items}
                </ul>
            </nav>
        )
    }
}

export function Header(props){
    return(
        <header>
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className='container-fluid'>
                    <a className="navbar-brand" href="/">Curso de formación</a>
                    <button className="navbar-toggler"
                        type="button"
                        data-toggle="collapse"
                        data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent"
                        aria-expanded="false"
                        aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon" />
                    </button>


                    <div className="collapse navbar-collapse" aria-labelledby="navbarSupportedContent">
                        <Menu {...props}/>
                    </div>
                </div>
            
            </nav>
        </header>
    )
}
  
  
  
export function Menu({menu, active, onSelectMenu}){
    return(
        <ul className="navbar-nav mr-auto">
        {menu.map((item, index) => <li className="nav-item" key={index}><a className={`nav-link ${active===index ? "activeMenu" : ""}`} href="#" onClick={() => {onSelectMenu && onSelectMenu(index)}}>{item.texto}</a></li>)
        }
        </ul>
    )
}

export function Footer(){
    return (
        <p className='mainFooter'>Made by Xavi Gervilla</p>
    )
}

export function PaginationButtons({pageNumber, previousPage, nextPage, firstPage=2}){
    return (
        <div className='row advance justify-content-around'>
            <button className='col buttonMuro' type='button' onClick={previousPage} disabled={pageNumber<firstPage}>Previous</button>
            <button className='col buttonMuro' type='button' onClick={nextPage}>Next</button>
        </div>
    )
}