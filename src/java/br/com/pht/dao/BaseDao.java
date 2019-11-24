package br.com.pht.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public interface BaseDao<T, ID> {

    void salvarOuAlterar(T entidade, Session session) throws HibernateException;

    void remover(T entidade, Session session) throws HibernateException;

    T pesquisaEntidadeId(Long id, Session session) throws HibernateException;

    List<T> listaTodos(Session session) throws HibernateException;

    List<T> pesquisaPorNome(String nome, Session session) throws HibernateException;
}
