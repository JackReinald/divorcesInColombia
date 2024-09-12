package servicios;

import modelo.DatosDivorcios;
import modelo.Divorcios;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroDivorcios {
    // Filtrar divorcios por año
    public List<Divorcios> filtrarPorAnho(String anho, List<Divorcios> datosDivorcios){
        return datosDivorcios.stream()
                .filter(d -> d.getFecha().startsWith(anho))
                .collect(Collectors.toList());
    }
    public List<Divorcios> filtrarPorDepartamento(String departamento, List<Divorcios> datosDivorcios){
        return datosDivorcios.stream()
                .filter(d -> d.getDepartamento().equalsIgnoreCase(departamento))
                .collect(Collectors.toList());
    }
}
