package br.com.jns.checkpoint.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    private String corPulseira;

    private String observacao;

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

    public String getCorPulseira() {
        return corPulseira;
    }

    public void setCorPulseira(String corPulseira) {
        this.corPulseira = corPulseira;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getBancoHora() {
        long intervalo = ChronoUnit.MINUTES.between(getHora(hrAlmocoSaida), getHora(hrAlmocoRetorno));
        long periodo = ChronoUnit.MINUTES.between(getHora(hrEntrada), getHora(hrSaida));

        long minExcedida = periodo - intervalo - 480;
        long minutos = minExcedida % 60;
        long horas = ((minExcedida - minutos) / 60);

//        return entrada.format(DateTimeFormatter.ofPattern("HH:mm"));

        return (minExcedida > 15) ? horas + ":" + minutos : "";
    }

    private LocalTime getHora(String hora) {
        int hr = Integer.parseInt(hora.substring(0, 2));
        int min = Integer.parseInt(hora.substring(3, 5));
        return LocalTime.of(hr, min);
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
            ", corPulseira='" + getCorPulseira() + "'" +
            ", observacao='" + getObservacao() + "'" +
            "}";
    }
}
