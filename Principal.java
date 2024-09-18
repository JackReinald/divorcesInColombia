import graficos.BarChartExample;
import modelo.Divorcios;
import servicios.ConsumoAPI;
import modelo.DatosDivorcios;
import servicios.FiltroDivorcios;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal{
    private static final String API = "https://www.datos.gov.co/resource/6mwg-ezc2.json";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        ConsumoAPI consumoAPI = new ConsumoAPI();
        FiltroDivorcios filtroDivorcios = new FiltroDivorcios();

        int limite = 10000;

        System.out.println("Este programa muestra la tasa de divorcio de Colombia" +
                " y genera la gráfica de su comportamiento con los años");
        List<DatosDivorcios> datosDivorcios = consumoAPI.obtenerDatos(API + "?$limit=" + limite);
        List<Divorcios> divorcios = datosDivorcios.stream()
                .map(Divorcios::new)
                .collect(Collectors.toList());

        System.out.println("Elija el tipo de filtro que desea aplicar: ");
        int opcion = teclado.nextInt();
        List<Divorcios> divorciosFiltrados = Collections.emptyList();
        switch (opcion){
            case 1:     //Filtrar por año en todos los departamentos
                System.out.println("Ingrese el año para el cual desea averiguar los divorcios: ");
                String anho = teclado.next();
                divorciosFiltrados = filtroDivorcios.filtrarPorAnho(anho,divorcios);
                System.out.println("Para el año " + anho + " los divorcios son: " + divorciosFiltrados);
                break;
            case 0: //Filtrar por departamento a lo largo de los años
                System.out.println("Ingrese el departamento para el cual desea averiguar el historial de divorcios en el tiempo: ");
                String departamento = teclado.next();
                //teclado.nextLine();
                divorciosFiltrados = filtroDivorcios.filtrarPorDepartamento(departamento, divorcios);
                System.out.println("Para el departamento " + departamento + " los divorcios son: " + divorciosFiltrados);
                break;
            default:
                System.out.println("Opción NO VÁLIDA");
        }

        // Extraer Fecha, Departamento, No. divorcios
        ArrayList<String> fecha = new ArrayList<>();
        ArrayList<String> departamento = new ArrayList<>();
        ArrayList<String> numeroDeDivorcios = new ArrayList<>();
        for (Divorcios divorciosFiltrado : divorciosFiltrados){
            fecha.add(divorciosFiltrado.getFecha());
            departamento.add(divorciosFiltrado.getDepartamento());
            numeroDeDivorcios.add(divorciosFiltrado.getNumeroDeDivorcios());
        }

        // Unir fecha y departamento para seleccionar una u otra según la necesidad
        ArrayList<ArrayList<String>> fechaYDepartamento = new ArrayList<>();
        fechaYDepartamento.add(fecha);
        fechaYDepartamento.add(departamento);
        System.out.println("Elemento 0 de fechaYDepartamento: " + fechaYDepartamento.get(0));
        System.out.println("Elemento 1 de fechaYDepartamento: " + fechaYDepartamento.get(1));

        EventQueue.invokeLater(() -> {
            BarChartExample ex = new BarChartExample(fechaYDepartamento, numeroDeDivorcios, opcion);
            ex.setVisible(true);
        });
    }
}
