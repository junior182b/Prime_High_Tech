package br.com.pht.controller;

import br.com.pht.dao.HibernateUtil;
import br.com.pht.dao.ProdutoDao;
import br.com.pht.dao.ProdutoDaoImpl;
import br.com.pht.model.Produto;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Alunos
 */
@ManagedBean(name = "estoqueC")
@ViewScoped
public class EstoqueControle implements Serializable {

    private Session session;
    private Produto produto;

    private ProdutoDao produtoDao;

    private DataModel<Produto> modelProdutos;
    private List<Produto> produtos;

    public EstoqueControle() {
        produtoDao = new ProdutoDaoImpl();
    }

    private void abreSessao() {
        if (session == null) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void carregarParaAlterar() {
        produto = modelProdutos.getRowData();

        System.out.println("id: " + produto.getId());
    }

    public void pesquisar() {
        produtoDao = new ProdutoDaoImpl();
        try {
            abreSessao();

            if (!produto.getNome().isEmpty()) {
                produtos = produtoDao.pesquisaPorNome(produto.getNome(), session);
            } else {
                produtos = produtoDao.listaTodos(session);
            }
            modelProdutos = new ListDataModel(produtos);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Produto:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao pesquisar produto");
        } finally {
            session.close();
        }
    }

    public void alterar() {

        try {
            abreSessao();
            produtoDao.salvarOuAlterar(produto, session);
            Mensagem.sucesso("Produto " + getProduto().getNome() + " alterado com sucesso");

        } catch (Exception e) {
            session.close();
            Mensagem.mensagemError("Erro ao alterar o Produto " + getProduto().getNome());
        }

    }

    //Gettes e Setters
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public DataModel<Produto> getModelProdutos() {
        return modelProdutos;
    }

    public void setModelProdutos(DataModel<Produto> modelProdutos) {
        this.modelProdutos = modelProdutos;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
