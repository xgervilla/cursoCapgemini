import {titleCase} from './formateadores'

describe.only('Pruebas de los formateadores', () => {
    describe.only('titleCase', () => {
        [['todas en minúscula', 'Todas En Minúscula'], ['EL SALVADOR', 'El Salvador'], ['ÍÑIGO MontoYA', 'Íñigo Montoya'], ['',''], [null, null], ['CASA123', 'Casa123'], [12345,12345], [' espacio al inicio', ' Espacio Al Inicio']].forEach(([original, resultado]) => {
            it(`${original} => ${resultado}`, function() {
                expect(titleCase(original)).toBe(resultado)
            })
        })
    })
})
