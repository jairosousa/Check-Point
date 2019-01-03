package br.com.jns.checkpoint.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Controle entity.
 */
public class ControleDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate data;

    @NotNull
    private String hrEntrada;

    private String hrAlmocoSaida;

    private String hrAlmocoRetorno;

    private String hrSaida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getHrEntrada() {
        return hrEntrada;
    }

    public void setHrEntrada(String hrEntrada) {
        this.hrEntrada = hrEntrada;
    }

    public String getHrAlmocoSaida() {
        return hrAlmocoSaida;
    }

    public void setHrAlmocoSaida(String hrAlmocoSaida) {
        this.hrAlmocoSaida = hrAlmocoSaida;
    }

    public String getHrAlmocoRetorno() {
        return hrAlmocoRetorno;
    }

    public void setHrAlmocoRetorno(String hrAlmocoRetorno) {
        this.hrAlmocoRetorno = hrAlmocoRetorno;
    }

    public String getHrSaida() {
        return hrSaida;
    }

    public void setHrSaida(String hrSaida) {
        this.hrSaida = hrSaida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ControleDTO controleDTO = (ControleDTO) o;
        if (controleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), controleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ControleDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", hrEntrada='" + getHrEntrada() + "'" +
            ", hrAlmocoSaida='" + getHrAlmocoSaida() + "'" +
            ", hrAlmocoRetorno='" + getHrAlmocoRetorno() + "'" +
            ", hrSaida='" + getHrSaida() + "'" +
            "}";
    }
}
