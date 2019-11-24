package br.com.pht.dao;

import br.com.pht.model.Perfil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class PerfilDaoImpl extends BaseDaoImpl<Perfil, Long> implements PerfilDao{

   @Override
    public Perfil pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Perfil) session.get(Perfil.class, id);
    }

    @Override
    public List<Perfil> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Perfil").list();
    }

    @Override
    public List<Perfil> pesquisaPorNome(String nome, Session session) throws HibernateException {
        return null;
    }
    
}
