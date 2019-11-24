package br.com.pht.dao;

import br.com.pht.model.Categoria;
import br.com.pht.model.Cliente;
import br.com.pht.model.Contato;
import br.com.pht.model.Endereco;
import br.com.pht.model.Funcionario;
import br.com.pht.model.ItemPedido;
import br.com.pht.model.Marca;
import br.com.pht.model.Pedido;
import br.com.pht.model.Perfil;
import br.com.pht.model.Pessoa;
import br.com.pht.model.Produto;
import br.com.pht.model.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Dhyego Pedroso
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Pessoa.class);
            cfg.addAnnotatedClass(Perfil.class);
            cfg.addAnnotatedClass(Usuario.class);
            cfg.addAnnotatedClass(Funcionario.class);
            cfg.addAnnotatedClass(Cliente.class);
            cfg.addAnnotatedClass(Endereco.class);
            cfg.addAnnotatedClass(Contato.class);
            cfg.addAnnotatedClass(Produto.class);
            cfg.addAnnotatedClass(Pedido.class);
            cfg.addAnnotatedClass(ItemPedido.class);
            cfg.addAnnotatedClass(Marca.class);
            cfg.addAnnotatedClass(Categoria.class);

            cfg.configure("/br/com/pht/dao/hibernate.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();

            sessionFactory = cfg.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Erro ao construir session factory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session abreSessao() {
        return sessionFactory.openSession();

    }
}
