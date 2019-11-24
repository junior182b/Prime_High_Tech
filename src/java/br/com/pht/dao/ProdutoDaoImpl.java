package br.com.pht.dao;

import br.com.pht.model.Produto;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class ProdutoDaoImpl extends BaseDaoImpl<Produto, Long> implements ProdutoDao, Serializable {

    @Override
    public Produto pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        Produto produto = (Produto) session.get(Produto.class, id);
        return produto;
    }

    @Override
    public List<Produto> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Produto").list();
    }

    @Override
    public List<Produto> pesquisaPorNome(String nomeProduto, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Produto p where p.nome like :nome");
        consulta.setParameter("nome", nomeProduto + "%");
        return consulta.list();
    }

}
