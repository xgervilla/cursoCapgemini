import './basicos.css'

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