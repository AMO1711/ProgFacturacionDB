package modelo;

import controlador.Controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import persistencia.*;

/*
     Cliente
        nif: 9
        Nombre: 40 (9)
        direccion: 40 (49)
        poblacion: 20 (89)
        provincia: 20 (109)
        cp: 5 (129)
        telefono: 9 (134)
        TOTAL: 143

        Todo añadir:
- Id: sencer, clau primari, auto incremental.
- nom: text, màxim 50 caràcters, no null
- dni: text, màxim 9 caràcters, no null, únic, verificació lletra correcta.
- direcció: text, màxim 50 caràcters, no null
- Població: text, màxim 30 caràcters, no null
- Província: selecció de les províncies d’Espanya, no null
- Codi Postal: text, 5 dígits, validació amb els 2 primers dígits de la província
- Telèfon Fitxo: 9 dígits
- Telèfon Mòbil: 9 dígits
- Correu Electrònic: 80 caràcters
- Plana Web: 50 caràcters
- Forma de pagament del client: Crèdit o contat.
- Limit de crèdit: numèric, mínim 0,00 € màxim 1.000.000,00 € (crèdit consumit a data d’avui –camp preparat però pendent)
- Numero de conta bancari, per poder passar el rebut mensual si el client te forma de pagament a crèdit.
- Actiu : Boolean
- Observacions: Camp de text de màxim 500 caràcters
- Imatge: Logo del client (Resolució 300 dpi x 300 dpi)
- Llistat de clientes ordenat per numero, DNI, Nom
- Recerca de clientes, per nom, dni, telèfonon, plana web

     Articulo
        Nombre: 40
        Precio: 8 (40)
        iva: 6 (48)
        TOTAL: 54

Etiqueta (UI) | Nom del camp (model) | Tipus de component (Swing) | Longitud / Format / Validació

ID | id | JTextField (no editable) | Enter, auto incremental
Codi article | codi | JTextField | Màx. 20 caràcters, no null, únic
Nom article | nom | JTextField | Màx. 80 caràcters, no null
Descripció | descripcio | JTextArea | Màx. 500 caràcters, pot ser null
Família | familia | JComboBox<String> | No null (llistat de famílies)
Categoria | categoria | JComboBox<String> | No null
Unitat de venda | unitat | JComboBox<String> | No null
Proveïdor | proveedor | JComboBox<Proveedor> | No null (relació ManyToOne)
Preu de cost (€) | preuCost | JFormattedTextField | Decimal, ≥ 0,00, 2 decimals
Preu de venda (€) | preuVenda | JFormattedTextField | Decimal, ≥ preuCost, 2 decimals
IVA (%) | ivaPercent | JComboBox<Integer> | 0, 4, 10, 21
Stock actual | stockActual | JSpinner | Enter, ≥ 0
Stock mínim | stockMinim | JSpinner | Enter, ≥ 0
Codi de barres | codiBarres | JTextField | 13 dígits, opcional
Article actiu | actiu | JCheckBox | Boolean
Imatge article | imatge | JLabel + JButton | Imatge 300x300 px
Data d’alta | dataAlta | JTextField | (no editable) Data/hora automàtica
Observacions | observacions | JTextArea | Màx. 500 caràcters

     Factura
        numerofactura: 7
        fecha: 8 YYYYMMDD (7)
        nif: 9 (15)
        precioB: 8 (24)
        precioIva: 8 (32)
        precioT: 9 (40)
        cantidad + nombre: (4 + 40)*10 (49)
        TOTAL: 489
*/

public class Model {
    private final ClienteDAO clienteDAO;
    private final ArticuloDAO articuloDAO;
    private final FacturaDAO facturaDAO;

    public Model() {
        this.clienteDAO = new ClienteDAO();
        this.articuloDAO = new ArticuloDAO();
        this.facturaDAO = new FacturaDAO();
    }

    // Getters para que el Controller use los DAOs
    public ClienteDAO getClienteDAO() { return clienteDAO; }
    public ArticuloDAO getArticuloDAO() { return articuloDAO; }
    public FacturaDAO getFacturaDAO() { return facturaDAO; }
}