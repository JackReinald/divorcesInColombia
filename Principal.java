import graficos.BarChartExample;
import modelo.*;
import servicios.CalculoDeMediaAnual;
import servicios.ConsumoAPI;
import servicios.FiltroDivorcios;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Principal{
    private static final String API = "https://www.datos.gov.co/resource/6mwg-ezc2.json";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ConsumoAPI consumoAPI = new ConsumoAPI();
        FiltroDivorcios filtroDivorcios = new FiltroDivorcios();
        CalculoDeMediaAnual calculoDeMediaAnual = new CalculoDeMediaAnual(filtroDivorcios);

        int limite = 10000;

        System.out.println("Este programa muestra la tasa de divorcio de Colombia" +
                " y genera la gráfica de su comportamiento con los años");
        System.out.println("Elija el tipo de filtro que desea aplicar: ");

        List<DatosDivorcios> datosDivorcios = consumoAPI.obtenerDatos(API + "?$limit=" + limite);
        List<Divorcios> divorcios = datosDivorcios.stream()
                .map(Divorcios::new)
                .collect(Collectors.toList());

        ResultadosDivorcios<?> divorciosFiltrados = null;
        int opcion = -1;
        try {
            opcion = teclado.nextInt();
            divorciosFiltrados = null;
            switch (opcion) {
                case 1:     //Filtrar por un año en todos los departamentos: "En el año 2016 los divorcios en Cauca fueron X, en Caquetá fueron Y..."
                    System.out.println("Ingrese el año para el cual desea averiguar los divorcios de todos los departamentos: ");
                    String anho = teclado.next();
                    List<Divorcios> divorciosPorAnho = filtroDivorcios.filtrarPorAnho(anho, divorcios);
                    divorciosFiltrados = new ResultadoComoLista(divorciosPorAnho);

                    if (divorciosPorAnho.stream().anyMatch(Predicate.isEqual(anho))) {
                        System.out.println("Para el año " + anho + ", los divorsios de todos los departamentos son: " + divorciosFiltrados);
                    } else {
                        System.out.println("No se encontraron datos de divorcios para el año " + anho);
                    }

                    break;
                case 0:     //Filtrar por departamento a lo largo de los años
                    System.out.println("Ingrese el departamento para el cual desea averiguar el historial de divorcios en el tiempo: ");
                    String departamento = teclado.next();
                    //List<Divorcios> divorciosPorDepartamento = filtroDivorcios.filtrarPorDepartamento(departamento,divorcios);
                    Map<String, Double> mediaAnualDelDepartamento = calculoDeMediaAnual.calcularMediaAnualDelDepartamento(divorcios, departamento);
                    System.out.println("media anual por departamento: " + mediaAnualDelDepartamento);
                    divorciosFiltrados = new ResultadoComoMapa(mediaAnualDelDepartamento);  // Hasta aquí BIEN
                    // Verificar si el departamento existe
                    if (!mediaAnualDelDepartamento.containsKey(departamento)) {
                        System.out.println("El departamento que ingresó no existe.");
                    } else {
                        divorciosFiltrados = new ResultadoComoMapa(mediaAnualDelDepartamento);
                        System.out.println("Para el departamento " + departamento + " los divorcios son: " + divorciosFiltrados);
                    }
                    break;
                default:
                    System.out.println("Opción NO VÁLIDA");
            }
        } catch (InputMismatchException exc) {
            System.out.println("Error de tipo de dato. " + exc.getMessage());
        } catch (Exception exc) {
            System.out.println("Error inesperado de tipo: " + exc.getMessage());
        }
        // Extraer Fecha, Departamento, No. divorcios
        ArrayList<String> fecha = new ArrayList<>();
        ArrayList<String> departamento = new ArrayList<>();
        ArrayList<String> numeroDeDivorcios = new ArrayList<>();

        if (divorciosFiltrados instanceof ResultadoComoLista) {
            List<Divorcios> listaDeDivorcios = ((ResultadoComoLista) divorciosFiltrados).getResultados();
            for (Divorcios divorciosFiltrado : listaDeDivorcios) {
                fecha.add(divorciosFiltrado.getFecha());
                departamento.add(divorciosFiltrado.getDepartamento());
                numeroDeDivorcios.add(divorciosFiltrado.getNumeroDeDivorcios());
            }
            System.out.println(divorciosFiltrados);
        } else if (divorciosFiltrados instanceof ResultadoComoMapa) {
            Map<String, Double> mapaDeDivorcios = ((ResultadoComoMapa) divorciosFiltrados).getResultados();
            for (Map.Entry<String, Double> infoDeDivorcios : mapaDeDivorcios.entrySet()) {
                fecha.add(infoDeDivorcios.getKey());
                departamento.add("Todos los departamentos");
                numeroDeDivorcios.add(String.valueOf(infoDeDivorcios.getValue()));
            }
            System.out.println(divorciosFiltrados);
        }

        // Unir fecha y departamento para seleccionar una u otra según la necesidad
        ArrayList<ArrayList<String>> fechaYDepartamento = new ArrayList<>();
        fechaYDepartamento.add(fecha);
        fechaYDepartamento.add(departamento);

        // Grafica los datos
        int opcionFinal = opcion;
        EventQueue.invokeLater(() -> {
            BarChartExample ex = new BarChartExample(fechaYDepartamento, numeroDeDivorcios, opcionFinal);
            ex.setVisible(true);
        });

    }
}
