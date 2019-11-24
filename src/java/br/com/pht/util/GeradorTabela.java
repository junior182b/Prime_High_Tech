package br.com.pht.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.text.ParseException;

/**
 *
 * @author Pedrão, Dhyego
 */
public class GeradorTabela {

    public static void main(String[] args) throws ParseException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("phtPU");
        emf.close();
        
        InicializarSistema sistema = new InicializarSistema();
        sistema.iniciarSistema();

    }
}
