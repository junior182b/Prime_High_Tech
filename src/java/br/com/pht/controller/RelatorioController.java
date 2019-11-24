package br.com.pht.controller;

import br.com.pht.dao.HibernateUtil;
import br.com.pht.dao.ItemPedidoDao;
import br.com.pht.dao.ItemPedidoDaoImpl;
import br.com.pht.dao.PedidoDao;
import br.com.pht.dao.PedidoDaoImpl;
import br.com.pht.model.ItemPedido;
import br.com.pht.model.Pedido;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
@ManagedBean(name = "relatorioC")
@ViewScoped
public class RelatorioController {

    private Session session;

    private PedidoDao pedidoDao;
    private ItemPedidoDao itemPedidoDao;

    private List<Pedido> pedidos;
    private List<ItemPedido> itemPedidos;

    private DataModel<Pedido> modelPedido;
    private DataModel<ItemPedido> modelItemPedido;

    private Pedido pedido;
    private ItemPedido itemPedido;

    private Date dataInicio;
    private Date dataFinal;
    private String nomeCliente;
    private String nomeProduto;

    private Boolean qualPesquisa;

    public RelatorioController() {
        pedidoDao = new PedidoDaoImpl();
        itemPedidoDao = new ItemPedidoDaoImpl();
    }

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void pesquisarPedido() {

        try {

            abreSessao();

            if (qualPesquisa) {
                if (!nomeCliente.equalsIgnoreCase("")) {
                    pedidos = pedidoDao.pesquisaPedidoClienteDataInicioFim(nomeCliente, dataInicio, dataFinal, session);
                } else {
                    pedidos = pedidoDao.listarTodosPedidoDataInicioFim(dataInicio, dataFinal, session);
                }

                modelPedido = new ListDataModel<>(pedidos);
            } else {
                if (!nomeProduto.equalsIgnoreCase("")) {
                    itemPedidos = itemPedidoDao.pesquisaProdutoDataInicioFim(nomeProduto, dataInicio, dataFinal, session);
                } else {
                    itemPedidos = itemPedidoDao.listarTodosProdutosDataInicioFim(dataInicio, dataFinal, session);
                }

                modelItemPedido = new ListDataModel<>(itemPedidos);
            }

        } catch (Exception e) {
            session.close();
        }

    }

//       
//      Gettes e Settes
//
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public PedidoDao getPedidoDao() {
        if (pedido == null) {
            pedido = new Pedido();
        }
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

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Boolean getQualPesquisa() {
        return qualPesquisa;
    }

    public void setQualPesquisa(Boolean qualPesquisa) {
        this.qualPesquisa = qualPesquisa;
    }

    public ItemPedidoDao getItemPedidoDao() {
        return itemPedidoDao;
    }

    public void setItemPedidoDao(ItemPedidoDao itemPedidoDao) {
        this.itemPedidoDao = itemPedidoDao;
    }

    public List<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<ItemPedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public DataModel<ItemPedido> getModelItemPedido() {
        return modelItemPedido;
    }

    public void setModelItemPedido(DataModel<ItemPedido> modelItemPedido) {
        this.modelItemPedido = modelItemPedido;
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

}
