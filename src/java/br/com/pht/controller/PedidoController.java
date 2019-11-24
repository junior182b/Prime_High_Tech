package br.com.pht.controller;

import br.com.pht.dao.ClienteDao;
import br.com.pht.dao.ClienteDaoImpl;
import br.com.pht.dao.HibernateUtil;
import br.com.pht.dao.PedidoDao;
import br.com.pht.dao.PedidoDaoImpl;
import br.com.pht.dao.ProdutoDao;
import br.com.pht.dao.ProdutoDaoImpl;
import br.com.pht.model.Cliente;
import br.com.pht.model.ItemPedido;
import br.com.pht.model.Pedido;
import br.com.pht.model.Produto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
@ManagedBean(name = "pedidoC")
@ViewScoped
public class PedidoController implements Serializable {

    private boolean mostrar_Toolbar;
    private Session session;

    private PedidoDao pedidoDao;
    private ClienteDao clienteDao;

    private List<Pedido> pedidos;
    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<ItemPedido> itemProdutos;

    private DataModel<Pedido> modelPedido;
    private DataModel<ItemPedido> modelItemProdutos;

    private ItemPedido itemPedido;
    private Pedido pedido;
    private Produto produto;
    private Cliente cliente;

    private String nomePesquisa;

    public PedidoController() {
        pedidoDao = new PedidoDaoImpl();
    }

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void novo() {
        mostrar_Toolbar = !mostrar_Toolbar;
        limpar();
    }

    public void novaPesquisa() {
        mostrar_Toolbar = !mostrar_Toolbar;
        limpar();
    }

    public void preparaAlterar() {
        mostrar_Toolbar = !mostrar_Toolbar;
        limpar();
    }

    public void carregarParaAlterar() {
        mostrar_Toolbar = !mostrar_Toolbar;
        pedido = modelPedido.getRowData();
        cliente = pedido.getCliente();

        itemProdutos = pedido.getItemPedidos();
        modelItemProdutos = new ListDataModel<>(pedido.getItemPedidos());
    }

    public void pesquisar() {
        pedidoDao = new PedidoDaoImpl();
        try {
            abreSessao();

            if (!getNomePesquisa().equals("")) {
                pedidos = pedidoDao.pesquisaPedidoNomeSocial(getNomePesquisa(), session);
            }
            modelPedido = new ListDataModel(pedidos);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Pedido:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao pesquisar pedido");
        } finally {
            session.close();
        }
    }

    public void salvar() {
        try {

            abreSessao();

            if (pedido.getId() == null) {
                pedido.setCadastro(new Date());
            }
            pedido.setCliente(cliente);
            pedido.setItemPedidos(itemProdutos);

            updateEstoque();

            pedidoDao.salvarOuAlterar(pedido, session);

            limpar();
            Mensagem.salvar("Pedido ");
        } catch (HibernateException ex) {
            System.err.println("Erro ao Salvar pedido:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao tentar salvar o pedido");
        } finally {
            session.close();
        }

    }

    public void updateEstoque() {
    }

    public List<String> completeCliente(String query) {
        return null;
    }

    public Cliente carregarDadosCliente() {
        return null;
    }

    public List<String> completeProduto(String query) {
        return null;
    }

    public Produto carregarDadosProduto() {
        return null;
    }

    public void addItemProduto() {

    }

    public void limpar() {
        pedido = new Pedido();
        cliente = new Cliente();
        produto = new Produto();
        itemProdutos = new ArrayList<>();
        modelItemProdutos = new ListDataModel<>(itemProdutos);
    }

//    Gett e Settes
    public Pedido getPedido() {
        if (pedido == null) {
            pedido = new Pedido();
        }
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public ItemPedido getItemPedido() {
        if (itemPedido == null) {
            itemPedido = new ItemPedido();
        }
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public Cliente getCliente() {
        if (cliente == null) {
            cliente = new Cliente();
        }
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        if (produto == null) {
            produto = new Produto();
        }
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public boolean isMostrar_Toolbar() {
        return mostrar_Toolbar;
    }

    public void setMostrar_Toolbar(boolean mostrar_Toolbar) {
        this.mostrar_Toolbar = mostrar_Toolbar;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public PedidoDao getPedidoDao() {
        return pedidoDao;
    }

    public void setPedidoDao(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public DataModel<Pedido> getModelPedido() {
        return modelPedido;
    }

    public void setModelPedido(DataModel<Pedido> modelPedido) {
        this.modelPedido = modelPedido;
    }

    public List<ItemPedido> getItemProdutos() {
        return itemProdutos;
    }

    public void setItemProdutos(List<ItemPedido> itemProdutos) {
        this.itemProdutos = itemProdutos;
    }

    public DataModel<ItemPedido> getModelItemProdutos() {
        return modelItemProdutos;
    }

    public void setModelItemProdutos(DataModel<ItemPedido> modelItemProdutos) {
        this.modelItemProdutos = modelItemProdutos;
    }

    public String getNomePesquisa() {
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa) {
        this.nomePesquisa = nomePesquisa;
    }

}
