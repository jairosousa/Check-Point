package br.com.jns.checkpoint.service.util;

import org.hibernate.jdbc.Work;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class ExecutorRelatorio implements Work {
    private String caminhoRelatorio;
    private HttpServletResponse response;
    private String nomeArquivoSaida;

    public ExecutorRelatorio(String caminhoRelatorio, HttpServletResponse response, String nomeArquivoSaida) {
        this.caminhoRelatorio = caminhoRelatorio;
        this.response = response;
        this.nomeArquivoSaida = nomeArquivoSaida;
    }

    @Override
    public void execute(Connection connection) throws SQLException {

    }
}
