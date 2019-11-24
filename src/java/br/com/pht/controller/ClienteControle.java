package br.com.pht.controller;

import br.com.pht.dao.ClienteDao;
import br.com.pht.dao.ClienteDaoImpl;
import br.com.pht.dao.HibernateUtil;
import br.com.pht.model.Cliente;
import br.com.pht.model.Contato;
import br.com.pht.model.Endereco;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author Leo
 */
@ManagedBean(name = "clienteC")
@ViewScoped
public class ClienteControle implements Serializable {

    private Session session;
    private boolean mostrar_toolbar;

    private ClienteDao clienteDao;

    private Endereco endereco;
    private Cliente cliente;
    private Contato contato;

    private List<Cliente> clientes;
    private DataModel<Cliente> modelClientes;

    private boolean skip;

    public ClienteControle() {
        clienteDao = new ClienteDaoImpl();
    }

    private void abreSessao() {
        if (session == null) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
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
        cliente = modelClientes.getRowData();
        endereco = cliente.getEndereco();
        contato = endereco.getContato();
    }

    public boolean isSkip() {
        return skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void pesquisar() {
        clienteDao = new ClienteDaoImpl();
        try {
            abreSessao();

            if (!cliente.getNome().equals("")) {
                clientes = clienteDao.pesquisaPorNome(cliente.getNome(), session);
            } else {
                Mensagem.campoVazio("O campo Cliente");
            }

            modelClientes = new ListDataModel(clientes);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Cliente:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao pesquisar cliente");
        } finally {
            session.close();
        }
    }

    public void limpar() {
        cliente = new Cliente();
        contato = new Contato();
        endereco = new Endereco();
    }

    public void excluir() {
        cliente = modelClientes.getRowData();
        abreSessao();
        try {
            clienteDao.remover(cliente, session);
            clientes.remove(cliente);
            modelClientes = new ListDataModel(clientes);
            Mensagem.excluir("Cliente " + cliente.getNome());
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
            Mensagem.mensagemError("Erro ao excluir cliente " + cliente.getNome());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        try {
            abreSessao();

            if (cliente.getId() == null) {
                cliente.setDtCadastro(new Date());
            }
            endereco.setCliente(cliente);
            cliente.setEndereco(endereco);

            contato.setEndereco(endereco);
            endereco.setContato(contato);

            clienteDao.salvarOuAlterar(cliente, session);
            Mensagem.salvar("Cliente: " + cliente.getNome());

        } catch (HibernateException ex) {
            System.err.println("Erro ao Salvar Cliente:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao salvar cliente");
        } catch (Exception e) {
            System.out.println("Erro no salvar clienteDao Controle "
                    + e.getMessage());
        } finally {
            session.close();
        }
        limpar();
    }

    public void limparTela() {
        limpar();
    }

    //    
    //Getters e Setters
    //
    public Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public DataModel<Cliente> getModelClientes() {
        return modelClientes;
    }

    public void setModelClientes(DataModel<Cliente> modelClientes) {
        this.modelClientes = modelClientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setFuncionarios(List<Cliente> clientes) {
        this.clientes = clientes;
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
