package br.com.pht.dao;

import br.com.pht.model.ItemPedido;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Pedrão Master
 */
public interface ItemPedidoDao extends BaseDao<ItemPedido, Long> {

    public List<ItemPedido> pesquisaProdutoDataInicioFim(String nomeProduto, Date inicio, Date fim, Session session) throws HibernateException;

    public List<ItemPedido> listarTodosProdutosDataInicioFim(Date inicio, Date fim, Session session) throws HibernateException;


}
