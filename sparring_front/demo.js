function MiClase(elId, elNombre) {
    this.id = elId;
    this.nombre = elNombre;
    this.muestraId = function () {
        alert("El ID del objeto es " + this.id);
    }
    this.ponNombre = function (nom) {
        this.nombre = nom.toUpperCase();
    }
    return 'Nada'
}

MiClase.prototype.cotilla = () => console.log("Ejecutando desde el prototipo")

//declaración de la clase sin el new
let sinNew = MiClase("0","sin new")

//declaración de la clase con el new (se queda con el objeto creado,  no con el return)
let o1 = new MiClase("1", "Objeto 1")

let o2 = new MiClase("2", "Objeto 2")
o1.cotilla = () => console.log("Ejecutando desde el objeto creado")

o1.apellido = "Nuevo atributo añadido"

console.log(sinNew)
console.log(o1, o2)

o1.cotilla()
o2.cotilla()
