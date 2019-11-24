package br.com.pht.dao;

import br.com.pht.model.Funcionario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class FuncionarioDaoImpl extends BaseDaoImpl<Funcionario, Long> implements FuncionarioDao, Serializable {

    @Override
    public Funcionario pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Funcionario) session.get(Funcionario.class, id);
    }

    @Override
    public List<Funcionario> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Funcionario").list();
    }

    @Override
    public List<Funcionario> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcionario f where f.nome like :nome");
        consulta.setParameter("nome", nome + "%");
        return consulta.list();
    }

    @Override
    public List<Funcionario> listarPorUnidade(String unidade, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcionario f where f.unidade like :unidade");
        consulta.setParameter("unidade", unidade);
        return consulta.list();
    }

    @Override
    public Funcionario buscarPorCpf(String cpf, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcionario f where f.cpf like :cpf");
        consulta.setParameter("cpf", cpf);
        return (Funcionario) consulta;
    }

    @Override
    public Funcionario buscarPorMatricula(String matricula, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcionario f where f.matricula like :matricula");
        consulta.setParameter("matricula", matricula);
        return (Funcionario) consulta;
    }

    @Override
    public List<Funcionario> listarPorFuncao(String funcao, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcionario f where f.funcao like :funcao");
        consulta.setParameter("funcao", funcao);
        return consulta.list();
    }
    
}
