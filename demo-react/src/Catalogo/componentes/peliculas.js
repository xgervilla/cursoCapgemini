import React, { Component } from "react";
import { ValidationMessage, ErrorMessage, Esperando, PaginacionCmd as Paginacion } from "../biblioteca/comunes";
import { titleCase } from '../biblioteca/formateadores';

export class Peliculas extends Component {
    constructor(props) {
        super(props);
        this.state = {
            modo: "list",
            listado: null,
            elemento: null,
            error: null,
            loading: true,
            pagina: 0,
            paginas: 0
        };
        this.idOriginal = null;
        this.url = (process.env.REACT_APP_API_URL || 'http://localhost:8080/catalogo/api/') + 'peliculas/v1';
    }

    setError(msg) {
        this.setState({ error: msg, loading: false });
    }


    list(num) {
        let pagina = this.state.pagina
        if (num || num === 0) pagina = num
        this.setState({ loading: true });
        fetch(`${this.url}?sort=title&page=${pagina}&size=10`)
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
            elemento: { id: 0, titulo: "", duracion: 0, descripcion: "",
                valoracion: "", release_year: 0, rental_duration: 0,
                rental_rate: 0, replacement_cost: 0, lenguaje: "", vo: ""}
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
                        elemento: data,
                        loading: false
                    });
                } : error => this.setError(`${error.status}: ${error.error}`))
            })
            .catch(error => this.setError(error))
    }
    delete(key) {
        if (!window.confirm("¿Seguro?")) return;
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
                    <FilmsForm key="main"
                        isAdd={this.state.modo === "add"}
                        elemento={this.state.elemento}
                        onCancel={e => this.cancel()}
                        onSend={e => this.send(e)}
                    />
                )
                break
            case "view":
                result.push(
                    <FilmsView key="main"
                        elemento={this.state.elemento}
                        onCancel={e => this.cancel()}
                    />
                )
                break
            default:
                if (this.state.listado) result.push(
                    <FilmsList key="main"
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

function FilmsList(props) {
    return (
        <>
            <table className="table table-hover table-striped">
                <thead className="table-info">
                    <tr>
                        <th>Lista de películas disponibles para alquilar</th>
                        <th className="text-end">
                            <input
                                type="button" className="btn btn-primary"
                                value="Añadir" onClick={e => props.onAdd()}
                            />
                        </th>
                    </tr>
                </thead>
                <tbody className="table-group-divider">
                    {props.listado.map(item => (
                        <tr key={item.filmId}>
                            <td>
                                {titleCase(item.title)}
                            </td>
                            <td className="text-end">
                                <div className="btn-group text-end" role="group">
                                    <input type="button" className="btn btn-primary"
                                        value="Ver" onClick={e => props.onView(item.filmId)}
                                    />
                                    <input type="button" className="btn btn-primary"
                                        value="Editar"
                                        onClick={e => props.onEdit(item.filmId)}
                                    />
                                    <input type="button" className="btn btn-danger"
                                        value="Borrar"
                                        onClick={e => props.onDelete(item.filmId)}
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

function FilmsView({ elemento, onCancel }) {
    return (
        <div>
            <p>
                <b>Código:</b> {elemento.id}
                <br />
                <b>Titulo:</b> {elemento.titulo}
                <br />
                <b>Descripción:</b> {elemento.descripcion}
                <br/>
                <b>Duración:</b> {elemento.duracion}
                <br/>
                <b>Valoración:</b> {elemento.valoracion}
                <br/>
                <b>Año de estreno:</b> {elemento.release_year}
                <br/>
                <b>Duración del alquiler:</b> {elemento.rental_duration}
                <br/>
                <b>Coste del alquiler por día:</b> {elemento.rental_rate}
                <br/>
                <b>Coste del reemplazo:</b> {elemento.replacement_cost}
                <br/>
                <b>Lenguaje:</b> {elemento.lenguaje.Name}
                <br/>
                {/* elemento.vo?.Name && <b>Lenguaje original:</b> elemento.vo.Name */}
            </p>
            <p>
                <button
                    className="btn btn-primary"
                    type="button"
                    onClick={e => onCancel()}
                >
                    Volver
                </button>
            </p>
        </div>
    )
}

class FilmsForm extends Component {
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
                }}>
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
                    <label htmlFor="titulo">Title</label>
                    <input type="text" className="form-control"
                        id="titulo" name="titulo"
                        value={this.state.elemento.titulo}
                        onChange={this.handleChange} required
                    />
                    <ValidationMessage msg={this.state.msgErr.title} />
                </div>
                <div className="form-group">
                    <label htmlFor="descripcion">Description</label>
                    <input type="text" className="form-control"
                        id="descripcion" name="descripcion"
                        value={this.state.elemento.descripcion}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.descripcion} />
                </div>
                <div className="form-group">
                    <label htmlFor="duracion">Length</label>
                    <input type="number" className="form-control"
                        id="duracion" name="duracion"
                        value={this.state.elemento.duracion}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.duracion} />
                </div>
                <div className="form-group">
                    <label htmlFor="valoracion">Rating</label>
                    <input type="text" className="form-control"
                        id="valoracion" name="valoracion"
                        value={this.state.elemento.valoracion}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.valoracion} />
                </div>
                <div className="form-group">
                    <label htmlFor="release_year">Release year</label>
                    <input type="text" className="form-control"
                        id="release_year" name="release_year"
                        value={this.state.elemento.release_year}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.release_year} />
                </div>
                <div className="form-group">
                    <label htmlFor="rental_duration">Rental duration</label>
                    <input type="text" className="form-control"
                        id="rental_duration" name="rental_duration"
                        value={this.state.elemento.rental_duration}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.rental_duration} />
                </div>
                <div className="form-group">
                    <label htmlFor="rental_rate">Rental rate</label>
                    <input type="text" className="form-control"
                        id="rental_rate" name="rental_rate"
                        value={this.state.elemento.rental_rate}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.rental_rate} />
                </div>
                <div className="form-group">
                    <label htmlFor="replacement_cost">Replacement cost</label>
                    <input type="text" className="form-control"
                        id="replacement_cost" name="replacement_cost"
                        value={this.state.elemento.replacement_cost}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.replacement_cost} />
                </div>
                <div className="form-group">
                    <label htmlFor="language">Language</label>
                    <input type="text" className="form-control"
                        id="language" name="language"
                        value={this.state.elemento.language}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.language} />
                </div>
                <div className="form-group">
                    <label htmlFor="vo">Language in VO</label>
                    <input type="text" className="form-control"
                        id="vo" name="vo"
                        value={this.state.elemento.vo}
                        onChange={this.handleChange}
                    />
                    <ValidationMessage msg={this.state.msgErr.vo} />
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