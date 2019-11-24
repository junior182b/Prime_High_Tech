package br.com.pht.dao;

import br.com.pht.model.Categoria;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class CategoriaDaoImpl extends BaseDaoImpl<Categoria, Long> implements CategoriaDao {

    @Override
    public Categoria pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        Categoria categoria = (Categoria) session.get(Categoria.class, id);
        return categoria;
    }

    @Override
    public List<Categoria> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Categoria").list();
    }

    @Override
    public List<Categoria> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Categoria c where c.nome like :nome");
        consulta.setParameter("nome", nome + "%");
        return consulta.list();
    }

}
