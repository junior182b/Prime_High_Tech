package br.com.pht.controller;

import br.com.pht.dao.CategoriaDao;
import br.com.pht.dao.CategoriaDaoImpl;
import br.com.pht.dao.HibernateUtil;
import br.com.pht.model.Categoria;
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
@ManagedBean(name = "categoriaC")
@ViewScoped
public class CategoriaControle implements Serializable {

    private Session session;
    private Categoria categoria;

    private CategoriaDao categoriaDao;

    private DataModel<Categoria> modelCategorias;
    private List<Categoria> categorias;

    public CategoriaControle() {
        categoriaDao = new CategoriaDaoImpl();
    }

    private void abreSessao() {
        if (session == null) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void carregarParaAlterar() {
        categoria = modelCategorias.getRowData();

        System.out.println("id: " + categoria.getId());
    }

    public void pesquisar() {
        categoriaDao = new CategoriaDaoImpl();
        try {
            abreSessao();

            if (!categoria.getNome().isEmpty()) {
                categorias = categoriaDao.pesquisaPorNome(categoria.getNome(), session);
            } else {
                categorias = categoriaDao.listaTodos(session);
            }
            modelCategorias = new ListDataModel(categorias);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Categoria:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao pesquisar Categoria");
        } finally {
            session.close();
        }
    }

    public void alterar() {

        try {
            abreSessao();
            categoriaDao.salvarOuAlterar(categoria, session);
            Mensagem.sucesso("Categoria " + getCategoria().getNome() + " alterado com sucesso");

        } catch (Exception e) {
            session.close();
            Mensagem.mensagemError("Erro ao alterar o Categoria " + getCategoria().getNome());
        }

    }
    
    //Gettes e Setters
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public CategoriaDao getCategoriaDao() {
        return categoriaDao;
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public DataModel<Categoria> getModelCategorias() {
        return modelCategorias;
    }

    public void setModelCategorias(DataModel<Categoria> modelCategorias) {
        this.modelCategorias = modelCategorias;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

}
