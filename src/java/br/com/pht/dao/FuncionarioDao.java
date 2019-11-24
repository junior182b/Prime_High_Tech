package br.com.pht.dao;

import br.com.pht.model.Funcionario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;


/**
 *
 * @author Alunos
 */
public interface FuncionarioDao extends BaseDao<Funcionario, Long>{
    
    List<Funcionario> listarPorUnidade(String unidade, Session session) throws HibernateException;
    
    Funcionario buscarPorCpf(String cpf, Session session) throws HibernateException;
    
    Funcionario buscarPorMatricula(String matricula, Session session) throws HibernateException;
    
    List<Funcionario> listarPorFuncao(String funcao, Session session) throws HibernateException;
}
