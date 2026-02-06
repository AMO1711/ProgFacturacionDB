package persistencia;

import modelo.Cliente;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class ClienteDAO extends GenericDAO<Cliente> {
    public ClienteDAO() {
        super(Cliente.class);
    }

    public Cliente buscarPorNif(String nif) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Cliente> query = session.createQuery("from Cliente where nif = :nif", Cliente.class);
            query.setParameter("nif", nif);
            return query.uniqueResult();
        }
    }
}