package br.com.pht.dao;

import br.com.pht.model.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public interface UsuarioDao extends BaseDao<Usuario, Long> {

    Usuario pesquisaPorLogin(String login, Session session) throws HibernateException;

     List<String> pesquisarPorLoginAutoComplete(String login, Session session) throws HibernateException;
     
    Usuario lerPorId(Long id, Session session) throws HibernateException;
            

}
