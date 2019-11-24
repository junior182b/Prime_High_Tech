package br.com.pht.dao;

import br.com.pht.model.Endereco;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class EnderecoDaoImpl extends BaseDaoImpl<Endereco, Long> implements EnderecoDao, Serializable{

    @Override
    public Endereco pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Endereco) session.get(Endereco.class, id);
    }

    @Override
    public List<Endereco> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Endereco").list();
    }

    @Override
    public List<Endereco> pesquisaPorNome(String logradouro, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Endereco e where e.logradouro like :logradouro");
        consulta.setParameter("logradouro", "&" + logradouro + "%");
        return consulta.list();
    }

}
