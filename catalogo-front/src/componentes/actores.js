import React, { Component, useState } from "react";
import { ValidationMessage, ErrorMessage, Esperando, PaginacionCmd as Paginacion } from "../biblioteca/comunes";
import { titleCase } from '../biblioteca/formateadores';

export class Actores extends Component {
    constructor(props) {
        super(props);
        this.state = {
            modo: "list",
            listado: null,
            elemento: null,
            error: null,
            loading: true,
            pagina: 0,
            paginas: 0,
            films: null
        };
        this.idOriginal = null;
        this.url = (process.env.REACT_APP_API_URL || 'http://localhost:8080/catalogo/api/') + 'actores/v1';
    }

    setError(msg) {
        this.setState({ error: msg, loading: false });
    }


    list(num) {
        let pagina = this.state.pagina
        if (num || num === 0) pagina = num
        this.setState({ loading: true });
        fetch(`${this.url}?sort=firstName&page=${pagina}&size=10`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    this.setState({
                        modo: "list",
                        listado: data.content,
                        loading: false,
                        pagina: data.number,
                        paginas: data.totalPages
                    })
                } : error => this.setError(`${error.status}: ${error.error}`))
            })
            .catch(error => this.setError(error))
    }

    add() {
        this.setState({
            modo: "add",
            elemento: { id: 0, nombre: "", apellidos: "" }
        });
    }
    edit(key) {
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    this.setState({
                        modo: "edit",
                        elemento: data,
                        loading: false
                    });
                    this.idOriginal = key;
                } : error => this.setError(`${error.status}: ${error.error}`))
            })
            .catch(error => this.setError(error))
    }

    view(key) {
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    this.setState({
                        modo: "view",
                        elemento: data
                    });
                fetch(`${this.url}/${key}/pelis`).then(response => {
                    response.json().then(response.ok ? data => this.setState({
                        loading: false,
                        films: data
                    }) : error => this.setError(`${error.status}: ${error.error}`))
                })
                } : error => this.setError(`${error.status}: ${error.error}`))
            })
            .catch(error => this.setError(error))
    }

    delete(key) {
        if (!window.confirm(`¿Seguro que quieres eliminar al actor con ID ${key}?`)) return;
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok)
                    this.list()
                else
                    response.json().then(error => this.setError(`${error.status}:
            ${error.error}`))
                this.setState({ loading: false });
            })
            .catch(error => this.setError(error))
    }

    componentDidMount() {
        this.list(0);
    }
    cancel() {
        this.list();
    }
    send(elemento) {
        this.setState({ loading: true });
        // eslint-disable-next-line default-case
        switch (this.state.modo) {
            case "add":
                fetch(`${this.url}`, {
                    method: 'POST',
                    body: JSON.stringify(elemento),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok)
                            this.cancel()
                        else
                            response.json().then(error => this.setError(`${error.status}:
            ${error.detail}`))
                        this.setState({ loading: false });
                    })
                    .catch(error => this.setError(error))
                break;
            case "edit":
                fetch(`${this.url}/${this.idOriginal}`, {
                    method: 'PUT',
                    body: JSON.stringify(elemento),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok)
                            this.cancel()
                        else
                            response.json().then(error => this.setError(`${error.status}:
            ${error.detail}`))
                        this.setState({ loading: false });
                    })
                    .catch(error => this.setError(error))
                break;
        }
    }

    render() {
        if (this.state.loading) return <Esperando />;
        let result = [<ErrorMessage key="error" msg={this.state.error} onClear={() =>
            this.setState({ error: null })} />]
        switch (this.state.modo) {
            case "add":
            case "edit":
                result.push(
                    <ActoresForm key="main"
                        isAdd={this.state.modo === "add"}
                        elemento={this.state.elemento}
                        onCancel={e => this.cancel()}
                        onSend={e => this.send(e)}
                    />
                )
                break
            case "view":
                result.push(
                    <ActoresView key="main"
                        elemento={this.state.elemento}
                        listFilms={this.state.films}
                        onCancel={e => this.cancel()}
                    />
                )
                break
            default:
                if (this.state.listado) result.push(
                    <ActoresList key="main"
                        listado={this.state.listado}
                        pagina={this.state.pagina}
                        paginas={this.state.paginas}
                        onAdd={e => this.add()}
                        onView={key => this.view(key)}
                        onEdit={key => this.edit(key)}
                        onDelete={key => this.delete(key)}
                        onChangePage={num => this.list(num)}
                    />
                );
                break;
        }
        return result
    }
}

function ActoresList(props) {
    return (
        <>
            <table className="table table-hover table-striped">
                <thead className="table-info">
                    <tr>
                        <th>Lista de Actores y Actrices</th>
                        <th className="text-end">
                            <input
                                type="button" className="btn btnEdit"
                                value="Añadir" onClick={e => props.onAdd()}
                            />
                        </th>
                    </tr>
                </thead>
                <tbody className="table-group-divider">
                    {props.listado.map(item => (
                        <tr key={item.actorId}>
                            <td>
                                {titleCase(item.nombre)}
                            </td>
                            <td className="text-end">
                                <div className="btn-group text-end" role="group">
                                    <input type="button" className="btn btnView"
                                        value="Ver" onClick={e => props.onView(item.actorId)}
                                    />
                                    <input type="button" className="btn btnEdit"
                                        value="Editar"
                                        onClick={e => props.onEdit(item.actorId)}
                                    />
                                    <input type="button" className="btn btnDelete"
                                        value="Borrar"
                                        onClick={e => props.onDelete(item.actorId)}
                                    />
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <Paginacion actual={props.pagina} total={props.paginas} onChange={num =>
                props.onChangePage(num)} />
        </>
    )
}

function ActoresView({ elemento, onCancel, listFilms }) {
    const [hideFilms, setHideFilms] = useState(true)
    return (
        <div>
            <p>
                <b>Código:</b> {elemento.id}
                <br />
                <b>Nombre:</b> {elemento.nombre}
                <br />
                <b>Apellidos:</b> {elemento.apellidos}
            </p>
            <button type="button" className="btn btnEdit" onClick={() => setHideFilms(!hideFilms)}>{hideFilms ?  "Muestra las películas con este actor" : "Oculta las películas"}</button>
            <div hidden={hideFilms}>
                {listFilms.map((film) => <p>Id: {film.key}; título: {titleCase(film.value)}</p>)}
            </div>
            <p>
                <br/>
                <button
                    className="btn btnEdit"
                    type="button"
                    onClick={e => onCancel()}
                >
                    Volver
                </button>
            </p>
        </div>
    )
}

class ActoresForm extends Component {
    constructor(props) {
        super(props);
        this.state = { elemento: props.elemento, msgErr: [], invalid: false };
        this.handleChange = this.handleChange.bind(this);
        this.onSend = () => {
            if (this.props.onSend) this.props.onSend(this.state.elemento);
        };
        this.onCancel = () => {
            if (this.props.onCancel) this.props.onCancel();
        };
    }
    handleChange(event) {
        const cmp = event.target.name;
        const valor = event.target.value;
        this.setState(prev => {
            prev.elemento[cmp] = valor;
            return { elemento: prev.elemento };
        });
        this.validar();
    }
    validarCntr(cntr) {
        if (cntr.name) {
            // eslint-disable-next-line default-case
            switch (cntr.name) {
                case "apellidos":
                    cntr.setCustomValidity(cntr.value !== cntr.value.toUpperCase()
                        ? "Debe estar en mayúsculas" : '');
                    break;
            }
        }
    }
    validar() {
        if (this.form) {
            const errors = {};
            let invalid = false;
            for (var cntr of this.form.elements) {
                if (cntr.name) {
                    this.validarCntr(cntr);
                    errors[cntr.name] = cntr.validationMessage;
                    invalid = invalid || !cntr.validity.valid;
                }
            }
            this.setState({ msgErr: errors, invalid: invalid });
        }
    }
    componentDidMount() {
        this.validar();
    }
    render() {
        return (
            <form
                ref={tag => {
                    this.form = tag;
                }}
            >
                <div className="form-group">
                    <label htmlFor="id">Código</label>
                    <input type="number"
                        className={'form-control' + (this.props.isAdd ? '' : '-plaintext')}
                        id="id" name="id"
                        value={this.state.elemento.id}
                        onChange={this.handleChange}
                        required readOnly={!this.props.isAdd}
                    />
                    <ValidationMessage msg={this.state.msgErr.id} />
                </div>
                <div className="form-group">
                    <label htmlFor="nombre">Nombre</label>
                    <input type="text" className="form-control"
                        id="nombre" name="nombre"
                        value={this.state.elemento.nombre}
                        onChange={this.handleChange}
                        required minLength="2" maxLength="45"
                    />
                    <ValidationMessage msg={this.state.msgErr.nombre} />
                </div>
                <div className="form-group">
                    <label htmlFor="apellidos">Apellidos</label>
                    <input type="text" className="form-control"
                        id="apellidos" name="apellidos"
                        value={this.state.elemento.apellidos}
                        onChange={this.handleChange}
                        minLength="2" maxLength="45"
                    />
                    <ValidationMessage msg={this.state.msgErr.apellidos} />
                </div>
                <div className="form-group">
                    <button className="btn btn-primary" type="button"
                        disabled={this.state.invalid}
                        onClick={this.onSend}
                    >
                        Enviar
                    </button>
                    <button className="btn btn-primary" type="button"
                        onClick={this.onCancel}
                    >
                        Volver
                    </button>
                </div>
            </form>
        );
    }
}