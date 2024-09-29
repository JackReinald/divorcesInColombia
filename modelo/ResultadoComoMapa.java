package modelo;

import java.util.Map;

public class ResultadoComoMapa implements ResultadosDivorcios< Map<String,Double> >{
    Map<String,Double> resultados;

    public ResultadoComoMapa(Map<String, Double> resultados) {
        this.resultados = resultados;
    }

    @Override
    public Map<String, Double> getResultados() {
        return resultados;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("El mapa de divorcios es: \n");
        getResultados().forEach((anho, media) -> {
            stringBuilder.append(String.format("Año %s = %.2f \n",anho,media));
        });
        return stringBuilder.toString();
    }
}
