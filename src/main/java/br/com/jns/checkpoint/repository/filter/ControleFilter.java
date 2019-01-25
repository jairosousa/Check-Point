package br.com.jns.checkpoint.repository.filter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ControleFilter {

    private LocalDate dataVencimentoDe;

    private LocalDate dataVencimentoAte;

    public LocalDate getDataVencimentoDe() {
        return dataVencimentoDe;
    }

    public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
        this.dataVencimentoDe = dataVencimentoDe;
    }

    public LocalDate getDataVencimentoAte() {
        return dataVencimentoAte;
    }

    public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
        this.dataVencimentoAte = dataVencimentoAte;
    }

    @Override
    public String toString() {
        return "ControleFilter{" +
            "dataVencimentoDe=" + dataVencimentoDe +
            ", dataVencimentoAte=" + dataVencimentoAte +
            '}';
    }
}
