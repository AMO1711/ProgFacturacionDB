package modelo;

import persistencia.*;

public class Model {
    private final ClienteDAO clienteDAO;
    private final ArticuloDAO articuloDAO;
    private final FacturaDAO facturaDAO;

    public Model() {
        this.clienteDAO = new ClienteDAO();
        this.articuloDAO = new ArticuloDAO();
        this.facturaDAO = new FacturaDAO();
    }

    public ClienteDAO getClienteDAO() { return clienteDAO; }
    public ArticuloDAO getArticuloDAO() { return articuloDAO; }
    public FacturaDAO getFacturaDAO() { return facturaDAO; }
}