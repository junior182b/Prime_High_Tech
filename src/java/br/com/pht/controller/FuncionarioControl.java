package br.com.pht.controller;

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
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Leo
 */
@ManagedBean(name = "funcionarioC")
@ViewScoped
public class FuncionarioControl implements Serializable {

    private Session session;
    private boolean mostrar_toolbar;

    private Funcionario funcionario;
    private Usuario usuario;
    private Perfil perfil;
    private Contato contato;
    private Endereco endereco;

    private FuncionarioDao funcionarioDao;

    private DataModel<Funcionario> modelFuncionarios;
    private List<SelectItem> perfils;
    private List<Funcionario> funcionarios;

    public FuncionarioControl() {
        funcionarioDao = new FuncionarioDaoImpl();
    }

    private void abreSessao() {
        if (session == null) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    @PostConstruct
    public void inicializar() {
        carregaPerfil();
    }

    public void novo() {
        mostrar_toolbar = !mostrar_toolbar;
        limpar();
    }

    public void novaPesquisa() {
        mostrar_toolbar = !mostrar_toolbar;
        limpar();
    }

    public void preparaAlterar() {
        mostrar_toolbar = !mostrar_toolbar;
        limpar();
    }

    public void carregarParaAlterar() {
        mostrar_toolbar = !mostrar_toolbar;
        funcionario = modelFuncionarios.getRowData();
        endereco = funcionario.getEndereco();
        contato = endereco.getContato();
        usuario = funcionario.getUsuario();
        perfil = usuario.getPerfil();

    }

    public void pesquisar() {
        funcionarioDao = new FuncionarioDaoImpl();
        try {
            abreSessao();
            if (!funcionario.getNome().equals("")) {
                funcionarios = funcionarioDao.pesquisaPorNome(funcionario.getNome(), session);
            } else {
                funcionarios = funcionarioDao.listaTodos(session);
            }
            modelFuncionarios = new ListDataModel(funcionarios);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Funcionario:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao pesquisar funcionário");
        } finally {
            session.close();
        }
    }

    private void carregaPerfil() {
        List<Perfil> todosPerfis;
        try {
            abreSessao();
            perfils = new ArrayList();

            PerfilDao perfilDao = new PerfilDaoImpl();
            todosPerfis = perfilDao.listaTodos(session);
            todosPerfis.stream().forEach((perf) -> {
                perfils.add(new SelectItem(perf.getId(), perf.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar os perfil " + hi.getMessage());
        } finally {
            session.close();
        }
    }

    public void limpar() {
        funcionario = new Funcionario();
        perfil = new Perfil();
        usuario = new Usuario();
        endereco = new Endereco();
        contato = new Contato();
    }

    public void excluir() {
        funcionario = modelFuncionarios.getRowData();
        abreSessao();
        try {
            funcionarioDao.remover(funcionario, session);
            funcionarios.remove(funcionario);
            modelFuncionarios = new ListDataModel(funcionarios);
            Mensagem.excluir("Funcionario " + funcionario.getNome());
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
            Mensagem.mensagemError("Erro ao excluir funcionário" + funcionario.getNome());
        } finally {
            session.close();
        }
    }

    public void salvar() throws NoSuchAlgorithmException {

        try {
            abreSessao();

            funcionario.setUsuario(usuario);
            usuario.setFuncionario(funcionario);

            usuario.setEnable(true);
            usuario.setPerfil(perfil);

            funcionario.setEndereco(endereco);
            endereco.setFuncionario(funcionario);

            contato.setEndereco(endereco);
            endereco.setContato(contato);

            if (funcionario.getId() == null) {
                funcionario.setDtCadastro(new Date());
            }

            funcionarioDao.salvarOuAlterar(funcionario, session);
            Mensagem.salvar("Funcionario: " + funcionario.getNome());
            funcionario = null;
            usuario = null;

        } catch (HibernateException ex) {
            System.err.println("Erro ao Salvar funcionario:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao tentar salvar funcionário");
        } catch (Exception e) {
            System.out.println("Erro no salvar funcionarioDao Controle "
                    + e.getMessage());
        } finally {
            session.close();
        }
        limpar();

    }

    public void limparTela() {
        limpar();
    }

    public Funcionario getFuncionario() {
        if (funcionario == null) {
            funcionario = new Funcionario();
        }
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public DataModel<Funcionario> getModelFuncionarios() {
        return modelFuncionarios;
    }

    public void setModelFuncionarios(DataModel<Funcionario> modelFuncionarios) {
        this.modelFuncionarios = modelFuncionarios;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<SelectItem> getPerfils() {
        return perfils;
    }

    public void setPerfils(List<SelectItem> perfils) {
        this.perfils = perfils;
    }

    public boolean isMostrar_toolbar() {
        return mostrar_toolbar;
    }

    public void setMostrar_toolbar(boolean mostrar_toolbar) {
        this.mostrar_toolbar = mostrar_toolbar;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public FuncionarioDao getFuncionarioDao() {
        return funcionarioDao;
    }

    public void setFuncionarioDao(FuncionarioDao funcionarioDao) {
        this.funcionarioDao = funcionarioDao;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        if (contato == null) {
            contato = new Contato();
        }
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}
