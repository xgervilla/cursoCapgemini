import React, { Component } from "react";
import { ValidationMessage, ErrorMessage, Esperando, PaginacionCmd as Paginacion } from "../biblioteca/comunes";
import { titleCase } from '../biblioteca/formateadores';

export class Categorias extends Component {
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
        this.url = (process.env.REACT_APP_API_URL || 'http://localhost:8080/catalogo/api/') + 'categorias/v1';
    }

    setError(msg) {
        this.setState({ error: msg, loading: false });
    }


    list(num) {
        let pagina = this.state.pagina
        if (num || num === 0) pagina = num
        this.setState({ loading: true });
        fetch(`${this.url}?sort=name&page=${pagina}&size=10`)
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
            elemento: { ID: 0, Category: "" }
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
                    <CategoriesForm key="main"
                        isAdd={this.state.modo === "add"}
                        elemento={this.state.elemento}
                        onCancel={e => this.cancel()}
                        onSend={e => this.send(e)}
                    />
                )
                break
            case "view":
                result.push(
                    <CategoryView key="main"
                        elemento={this.state.elemento}
                        onCancel={e => this.cancel()}
                    />
                )
                break
            default:
                if (this.state.listado) result.push(
                    <CategoryList key="main"
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

function CategoryList(props) {
    return (
        <>
            <table className="table table-hover table-striped">
                <thead className="table-info">
                    <tr>
                        <th>Lista de Categorias</th>
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
                        <tr key={item.ID}>
                            <td>
                                {titleCase(item.Category)}
                            </td>
                            <td className="text-end">
                                <div className="btn-group text-end" role="group">
                                    <input type="button" className="btn btn-primary"
                                        value="Ver" onClick={e => props.onView(item.ID)}
                                    />
                                    <input type="button" className="btn btn-primary"
                                        value="Editar"
                                        onClick={e => props.onEdit(item.ID)}
                                    />
                                    <input type="button" className="btn btn-danger"
                                        value="Borrar"
                                        onClick={e => props.onDelete(item.ID)}
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

function CategoryView({ elemento, onCancel }) {
    return (
        <div>
            <p>
                <b>Identificador:</b> {elemento.ID}
                <br />
                <b>Categoria:</b> {elemento.Category}
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

class CategoriesForm extends Component {
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
                        value={this.state.elemento.idOriginal}
                        onChange={this.handleChange}
                        required readOnly={!this.props.isAdd}
                    />
                    <ValidationMessage msg={this.state.msgErr.id} />
                </div>
                <div className="form-group">
                    <label htmlFor="categoria">Categoria</label>
                    <input type="text" className="form-control"
                        id="categoria" name="categoria"
                        value={this.state.elemento.Category}
                        onChange={this.handleChange}
                        required minLength="2" maxLength="45"
                    />
                    <ValidationMessage msg={this.state.msgErr.Category} />
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