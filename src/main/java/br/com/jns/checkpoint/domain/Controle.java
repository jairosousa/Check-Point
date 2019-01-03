package br.com.jns.checkpoint.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Controle.
 */
@Entity
@Table(name = "controle")
@Document(indexName = "controle")
public class Controle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @NotNull
    @Column(name = "hr_entrada", nullable = false)
    private String hrEntrada;

    @Column(name = "hr_almoco_saida")
    private String hrAlmocoSaida;

    @Column(name = "hr_almoco_retorno")
    private String hrAlmocoRetorno;

    @Column(name = "hr_saida")
    private String hrSaida;

    @Column(name = "cor_pulseira")
    private String corPulseira;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Controle data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHrEntrada() {
        return hrEntrada;
    }

    public Controle hrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
        return this;
    }

    public void setHrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
    }

    public String getHrAlmocoSaida() {
        return hrAlmocoSaida;
    }

    public Controle hrAlmocoSaida(String hrAlmocoSaida) {
        this.hrAlmocoSaida = hrAlmocoSaida;
        return this;
    }

    public void setHrAlmocoSaida(String hrAlmocoSaida) {
        this.hrAlmocoSaida = hrAlmocoSaida;
    }

    public String getHrAlmocoRetorno() {
        return hrAlmocoRetorno;
    }

    public Controle hrAlmocoRetorno(String hrAlmocoRetorno) {
        this.hrAlmocoRetorno = hrAlmocoRetorno;
        return this;
    }

    public void setHrAlmocoRetorno(String hrAlmocoRetorno) {
        this.hrAlmocoRetorno = hrAlmocoRetorno;
    }

    public String getHrSaida() {
        return hrSaida;
    }

    public Controle hrSaida(String hrSaida) {
        this.hrSaida = hrSaida;
        return this;
    }

    public void setHrSaida(String hrSaida) {
        this.hrSaida = hrSaida;
    }

    public String getCorPulseira() {
        return corPulseira;
    }

    public Controle corPulseira(String corPulseira) {
        this.corPulseira = corPulseira;
        return this;
    }

    public void setCorPulseira(String corPulseira) {
        this.corPulseira = corPulseira;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Controle controle = (Controle) o;
        if (controle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), controle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Controle{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", hrEntrada='" + getHrEntrada() + "'" +
            ", hrAlmocoSaida='" + getHrAlmocoSaida() + "'" +
            ", hrAlmocoRetorno='" + getHrAlmocoRetorno() + "'" +
            ", hrSaida='" + getHrSaida() + "'" +
            ", corPulseira='" + getCorPulseira() + "'" +
            "}";
    }
}
