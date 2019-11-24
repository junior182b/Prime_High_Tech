package br.com.pht.dao;

import br.com.pht.model.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class UsuarioDaoImpl extends BaseDaoImpl<Usuario, Long> implements UsuarioDao, Serializable {

    @Override
    public Usuario pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Usuario) session.get(Usuario.class, id);
    }

    @Override
    public List<Usuario> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Usuario u").list();
    }
    
   
    @Override
    public List<Usuario> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Usuario u where u.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public Usuario pesquisaPorLogin(String login, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Usuario u where u.login like :login");
        consulta.setParameter("login", "%" + login + "%");
        return (Usuario) consulta.uniqueResult();
    }

    @Override
    public List<String> pesquisarPorLoginAutoComplete(String login, Session session) throws HibernateException {
        Query consulta = session.createQuery("Select u.login from Usuario u where u.login like :login");
        consulta.setParameter("login", "%" + login + "%");
        return consulta.list();
    }

    @Override
    public Usuario lerPorId(Long id, Session session) throws HibernateException {
         return (Usuario) session.get(Usuario.class, id);
    }

   

}
