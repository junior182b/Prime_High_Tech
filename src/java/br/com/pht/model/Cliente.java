package br.com.pht.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author rossi
 */
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "idPessoa")
public class Cliente extends Pessoa implements Serializable {

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cliente", cascade = CascadeType.ALL)
    private Endereco endereco;

    @Column(nullable = false)
    private String cpf;

    public Cliente() {
    }

    public Cliente(Endereco endereco, String cpf) {
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
