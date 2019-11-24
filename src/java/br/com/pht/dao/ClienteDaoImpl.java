package br.com.pht.dao;

import br.com.pht.model.Cliente;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author rossi
 */
public class ClienteDaoImpl extends BaseDaoImpl<Cliente, Long> implements ClienteDao, Serializable {

    @Override
    public Cliente pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Cliente) session.get(Cliente.class, id);
    }

    @Override
    public List<Cliente> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Cliente c").list();
    }

    @Override
    public List<Cliente> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Cliente c where c.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public Cliente pesquisarCNPJ(String cnpj, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Cliente c where cnpj = :cnpj");
        consulta.setParameter("cnpj", cnpj);
        return (Cliente) consulta.uniqueResult();
    }

    @Override
    public List<Cliente> pesquisarNomeSocial(String nomeSocial, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Cliente where nomeSocial like :nomeSocial");
        consulta.setParameter("nomeSocial", "%" + nomeSocial + "%");
        return consulta.list();
    }

}
