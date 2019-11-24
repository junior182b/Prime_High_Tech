package br.com.pht.dao;

import br.com.pht.model.Contato;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;


/**
 *
 * @author Dhyego Pedroso
 */
public class ContatoDaoImpl extends BaseDaoImpl<Contato, Long> implements ContatoDao{

     @Override
    public Contato pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Contato) session.get(Contato.class, id);
    }

    @Override
    public List<Contato> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Contato").list();
    }

    @Override
    public List<Contato> pesquisaPorNome(String nome, Session session) throws HibernateException {
        return null;
    }
    
}
