package br.com.pht.dao;

import br.com.pht.model.Cliente;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author rossi
 */
public interface ClienteDao extends BaseDao<Cliente, Long> {
    
   Cliente pesquisarCNPJ( String cnpj, Session session)throws HibernateException;
    
   List<Cliente> pesquisarNomeSocial(String nomeSocial, Session session)throws HibernateException;
    


    
}
