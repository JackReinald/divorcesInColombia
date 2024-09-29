package modelo;

import java.util.List;

public class ResultadoComoLista implements ResultadosDivorcios< List<Divorcios> > {
    private final List<Divorcios> resultados;

    public ResultadoComoLista(List<Divorcios> resultado) {
        this.resultados = resultado;
    }

    @Override
    public List<Divorcios> getResultados() {
        return resultados;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Divorcios divorcio : resultados) {
            sb.append(divorcio.toString()).append("\n"); // Llama a Divorcios.toString()
        }
        return sb.toString();
    }
}
