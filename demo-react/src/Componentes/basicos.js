export function Header(props){
    return(
        <header>
            <nav className="navbar navbar-expand-lg">
                <a className="navbar-brand" href="/">Pruebas de frontend</a>
                <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon" />
                </button>
                <div className="collapse navbar-collapse" aria-labelledby="navbarSupportedContent">
                    <Menu {...props}/>
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