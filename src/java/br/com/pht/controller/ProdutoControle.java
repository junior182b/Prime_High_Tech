package br.com.pht.controller;

import br.com.pht.dao.CategoriaDao;
import br.com.pht.dao.CategoriaDaoImpl;
import br.com.pht.dao.HibernateUtil;
import br.com.pht.dao.MarcaDao;
import br.com.pht.dao.MarcaDaoImpl;
import br.com.pht.dao.PerfilDao;
import br.com.pht.dao.PerfilDaoImpl;
import br.com.pht.dao.ProdutoDao;
import br.com.pht.dao.ProdutoDaoImpl;
import br.com.pht.model.Categoria;
import br.com.pht.model.Marca;
import br.com.pht.model.Perfil;
import br.com.pht.model.Produto;
import java.io.Serializable;
import java.util.ArrayList;
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
@ManagedBean(name = "produtoC")
@ViewScoped
public class ProdutoControle implements Serializable {

    private Session session;
    private boolean mostrar_toolbar;

    private Produto produto;
    private Marca marca;
    private Categoria categoria;

    private ProdutoDao produtoDao;

    private DataModel<Produto> modelProdutos;
    private List<Produto> produtos;
    private List<SelectItem> marcas;
    private List<SelectItem> categorias;

    public ProdutoControle() {
        produtoDao = new ProdutoDaoImpl();
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
        carregarCategoria();
        carregarMarca();
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
        produto = modelProdutos.getRowData();
    }

    public void pesquisar() {
        produtoDao = new ProdutoDaoImpl();
        try {
            abreSessao();

            if (!produto.getNome().equals("")) {
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

    public void limpar() {
        produto = new Produto();
    }

    public void excluir() {
        produto = modelProdutos.getRowData();
        abreSessao();
        try {
            produtoDao.remover(produto, session);
            produtos.remove(produto);
            modelProdutos = new ListDataModel(produtos);
            Mensagem.excluir("Produto " + produto.getNome());
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
            Mensagem.mensagemError("Erro ao excluir produto " + produto.getNome());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        try {
            abreSessao();
            if (produto.getId() == null) {
                produto.setQuantidade(0);
            }
            produto.setCategoria(categoria);
            produto.setMarca(marca);

            produtoDao.salvarOuAlterar(produto, session);
            limpar();
            Mensagem.salvar("Produto ");

        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            Mensagem.mensagemError("Erro ao salvar produto");
        } finally {
            produto = new Produto();
            session.close();
        }
    }

    public void salvarCategoria() {
        try {
            abreSessao();
            categoria.setAtivo(true);
            CategoriaDao categoriaDao = new CategoriaDaoImpl();
            categoriaDao.salvarOuAlterar(categoria, session);
            Mensagem.salvar("Categoria ");

        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            Mensagem.mensagemError("Erro ao salvar categoria");
        } finally {
            categoria = new Categoria();
            session.close();
            carregarCategoria();
        }
    }

    public void salvarMarca() {
        try {
            abreSessao();
            marca.setAtivo(true);
            MarcaDao marcaDao = new MarcaDaoImpl();
            marcaDao.salvarOuAlterar(marca, session);
            Mensagem.salvar("Marca ");

        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            Mensagem.mensagemError("Erro ao salvar marca");
        } finally {
            marca = new Marca();
            session.close();
            carregarMarca();
        }
    }

    private void carregarCategoria() {
        List<Categoria> todasCategorias;
        try {
            abreSessao();
            categorias = new ArrayList();

            CategoriaDao categoriaDao = new CategoriaDaoImpl();
            todasCategorias = categoriaDao.listaTodos(session);
            todasCategorias.stream().forEach((categ) -> {
                categorias.add(new SelectItem(categ.getId(), categ.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar as categorias " + hi.getMessage());
        } finally {
            session.close();
        }
    }

    private void carregarMarca() {
        List<Marca> todasMarcas;
        try {
            abreSessao();
            marcas = new ArrayList();

            MarcaDao marcaDao = new MarcaDaoImpl();
            todasMarcas = marcaDao.listaTodos(session);
            todasMarcas.stream().forEach((marc) -> {
                marcas.add(new SelectItem(marc.getId(), marc.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar as marcas " + hi.getMessage());
        } finally {
            session.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    //Gettes e Setters
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Marca getMarca() {
        if (marca == null) {
            marca = new Marca();
        }
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        if (categoria == null) {
            categoria = new Categoria();
        }
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<SelectItem> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<SelectItem> marcas) {
        this.marcas = marcas;
    }

    public List<SelectItem> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SelectItem> categorias) {
        this.categorias = categorias;
    }

}
