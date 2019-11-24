package br.com.pht.dao;

import br.com.pht.model.Pedido;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Pedrão Master
 */
public class PedidoDaoImpl extends BaseDaoImpl<Pedido, Long> implements PedidoDao {

    @Override
    public Pedido pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Pedido) session.get(Pedido.class, id);
    }

    @Override
    public List<Pedido> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Pedido").list();
    }

    @Override
    public List<Pedido> pesquisaPorNome(String nomeSocial, Session session) throws HibernateException {
        return null;
    }

    @Override
    public List<Pedido> pesquisaPedidoNomeSocial(String nomeSocial, Session session) throws HibernateException {
        Query consulta = session.createQuery("select p from Pedido p join p.cliente c where c.nomeSocial like :nomeSocial");
        consulta.setParameter("nomeSocial", "%" + nomeSocial + "%");
        return consulta.list();
    }

    @Override
    public List<Pedido> pesquisaPedidoClienteDataInicioFim(String nomeSocial, Date inicio, Date fim, Session session) throws HibernateException {
        Query consulta = session.createQuery(
                "select p from Pedido p join p.cliente c "
                + "where c.nomeSocial like :nomeSocial "
                + "and p.cadastro between :dataInicio and :dataFinal "
        );
        consulta.setParameter("dataFinal", fim);
        consulta.setParameter("dataInicio", inicio);
        consulta.setParameter("nomeSocial", "%" + nomeSocial + "%");
        return consulta.list();
    }

    @Override
    public List<Pedido> listarTodosPedidoDataInicioFim(Date inicio, Date fim, Session session) throws HibernateException {
        Query consulta = session.createQuery(
                "select p from Pedido p join p.cliente c "
                + "where p.cadastro between :dataInicio and :dataFinal "
        );
        consulta.setParameter("dataFinal", fim);
        consulta.setParameter("dataInicio", inicio);
        return consulta.list();
    }

}
