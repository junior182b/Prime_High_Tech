package br.com.pht.util;

import br.com.pht.dao.FuncionarioDao;
import br.com.pht.dao.FuncionarioDaoImpl;
import br.com.pht.dao.HibernateUtil;
import br.com.pht.dao.PerfilDao;
import br.com.pht.dao.PerfilDaoImpl;
import br.com.pht.model.Contato;
import br.com.pht.model.Endereco;
import br.com.pht.model.Funcionario;
import br.com.pht.model.Perfil;
import br.com.pht.model.Usuario;
import java.util.Date;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class InicializarSistema {

    Session sessao;

    public void iniciarSistema() {

        sessao = HibernateUtil.abreSessao();

        PerfilDao perfilDao = new PerfilDaoImpl();
        FuncionarioDao funcionarioDao = new FuncionarioDaoImpl();

        //Cadastrar Funcionario Administrador
        Perfil perfilAdmin = new Perfil("ROLE_ADMIN", "usuario como permissão de administrador", "Administrador");
        Usuario usuarioAdmin = new Usuario("admin", "admin", true, perfilAdmin);

        Funcionario funcionarioAdmin = new Funcionario();
        funcionarioAdmin.setNome("Admin");
        funcionarioAdmin.setCpf("053.362.321.84");
        funcionarioAdmin.setDtCadastro(new Date());
        funcionarioAdmin.setMatricula("0123456789");
        funcionarioAdmin.setRg("3.962.625");
        funcionarioAdmin.setUsuario(usuarioAdmin);

        Contato contato = new Contato("(48) 3033-8547", "(48) 95487-9865", "admin@adin.com", true);
        Endereco endereco = new Endereco("admin", 132, "admin", "admin", "88122-400", "admin", "SC", "Brasil", contato, funcionarioAdmin);

        funcionarioAdmin.setEndereco(endereco);
        endereco.setFuncionario(funcionarioAdmin);
        contato.setEndereco(endereco);
        endereco.setContato(contato);

        usuarioAdmin.setFuncionario(funcionarioAdmin);
        perfilDao.salvarOuAlterar(perfilAdmin, sessao);
        funcionarioDao.salvarOuAlterar(funcionarioAdmin, sessao);

//        //Cadastrar Funcionario Conferente
        Perfil perfilConferente = new Perfil("ROLE_VENDEDOR", "usuario logado com perfil vendedor", "Vendedor");
        Usuario usuarioConferente = new Usuario("vendedor", "vendedor", true, perfilConferente);

        Funcionario funcionarioConferente = new Funcionario();
        funcionarioConferente.setNome("Conferente");
        funcionarioConferente.setCpf("053.362.321.84");
        funcionarioConferente.setDtCadastro(new Date());
        funcionarioConferente.setMatricula("0123456789");
        funcionarioConferente.setRg("3.962.625");
        funcionarioConferente.setUsuario(usuarioConferente);

        Contato contatoConferente = new Contato("(48) 3033-8547", "(48) 95487-9865", "admin@adin.com", true);
        Endereco enderecoConferente = new Endereco("admin", 132, "admin", "admin", "88122-400", "admin", "SC", "Brasil", contatoConferente, funcionarioConferente);

        funcionarioConferente.setEndereco(enderecoConferente);
        enderecoConferente.setFuncionario(funcionarioConferente);

        contatoConferente.setEndereco(enderecoConferente);
        enderecoConferente.setContato(contatoConferente);

        usuarioConferente.setFuncionario(funcionarioConferente);
        perfilDao.salvarOuAlterar(perfilConferente, sessao);
        funcionarioDao.salvarOuAlterar(funcionarioConferente, sessao);
        sessao.close();
    }

}
