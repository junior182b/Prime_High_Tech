package br.com.pht.controller;

import br.com.pht.dao.HibernateUtil;
import br.com.pht.dao.MarcaDao;
import br.com.pht.dao.MarcaDaoImpl;
import br.com.pht.model.Marca;
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
@ManagedBean(name = "marcaC")
@ViewScoped
public class MarcaControle implements Serializable {

    private Session session;
    private Marca marca;

    private MarcaDao marcaDao;

    private DataModel<Marca> modelMarcas;
    private List<Marca> marcas;

    public MarcaControle() {
        marcaDao = new MarcaDaoImpl();
    }

    private void abreSessao() {
        if (session == null) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void carregarParaAlterar() {
        marca = modelMarcas.getRowData();

        System.out.println("id: " + marca.getId());
    }

    public void pesquisar() {
        marcaDao = new MarcaDaoImpl();
        try {
            abreSessao();

            if (!marca.getNome().isEmpty()) {
                marcas = marcaDao.pesquisaPorNome(marca.getNome(), session);
            } else {
                marcas = marcaDao.listaTodos(session);
            }
            modelMarcas = new ListDataModel(marcas);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa Marca:\n" + ex.getMessage());
            Mensagem.mensagemError("Erro ao pesquisar marca");
        } finally {
            session.close();
        }
    }

    public void alterar() {

        try {
            abreSessao();
            marcaDao.salvarOuAlterar(marca, session);
            Mensagem.sucesso("Marca " + getMarca().getNome() + " alterado com sucesso");

        } catch (Exception e) {
            session.close();
            Mensagem.mensagemError("Erro ao alterar o Marca " + getMarca().getNome());
        }

    }

    //Gettes e Setters
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
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

    public MarcaDao getMarcaDao() {
        return marcaDao;
    }

    public void setMarcaDao(MarcaDao marcaDao) {
        this.marcaDao = marcaDao;
    }

    public DataModel<Marca> getModelMarcas() {
        return modelMarcas;
    }

    public void setModelMarcas(DataModel<Marca> modelMarcas) {
        this.modelMarcas = modelMarcas;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

}
