package br.com.pht.dao;

import br.com.pht.model.Marca;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class MarcaDaoImpl extends BaseDaoImpl<Marca, Long> implements MarcaDao {

    @Override
    public Marca pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        Marca marca = (Marca) session.get(Marca.class, id);
        return marca;
    }

    @Override
    public List<Marca> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Marca").list();
    }

    @Override
    public List<Marca> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Marca m where m.nome like :nome");
        consulta.setParameter("nome", nome + "%");
        return consulta.list();
    }

}
