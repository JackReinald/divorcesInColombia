package servicios;

import modelo.Divorcios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculoDeMediaAnual {
    private final FiltroDivorcios filtroDivorcios;

    public CalculoDeMediaAnual(FiltroDivorcios filtroDivorcios) {
        this.filtroDivorcios = filtroDivorcios;
    }

    public Map<String, Double> calcularMediaAnualDelDepartamento(List<Divorcios> divorcios, String departamento) {
        Map<String, Double> mediasAnualesDelDepartamento = new HashMap<>();

        // Filtrar los divorcios por departamento
        List<Divorcios> divorciosAnualesDelDepartamento =  filtroDivorcios.filtrarPorDepartamento(departamento, divorcios);

        // Agrupar los divorcios por año
        Map<String, List<Divorcios>> divorciosPorAnho = divorciosAnualesDelDepartamento.stream()
                        .collect(Collectors.groupingBy(d -> d.getFecha().substring(0,4)));

        for (Map.Entry< String, List<Divorcios> > datos : divorciosPorAnho.entrySet()) {
            String anho = datos.getKey();   // Extrae un año distinto por cada ciclo de las llaves del mapa
            List<Divorcios> divorciosEseAnho = datos.getValue();    // Extrae los divorcios del año seleccionado durante el ciclo

            // Calcular la media de divorcios para cada año
            double media = divorciosEseAnho.stream()
                    .mapToInt(d -> Integer.parseInt(d.getNumeroDeDivorcios()))
                    .average()
                    .orElse(0); // Si no hay datos, devolver 0 como media
            mediasAnualesDelDepartamento.put(anho, media);
        }

        return mediasAnualesDelDepartamento;
    }
}
