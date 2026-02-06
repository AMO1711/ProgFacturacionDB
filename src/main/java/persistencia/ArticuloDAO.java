package persistencia;

import modelo.Articulo;

public class ArticuloDAO extends GenericDAO<Articulo> {

    public ArticuloDAO(){
        super(Articulo.class);
    }
}
