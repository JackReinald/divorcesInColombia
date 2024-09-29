package graficos;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BarChartExample extends JFrame {

    public BarChartExample(ArrayList<ArrayList<String>> fechaYDepartamento, ArrayList<String> numeroDeDivorcios, int opcion) {
        initUI(fechaYDepartamento, numeroDeDivorcios, opcion);
    }

    private void initUI(ArrayList<ArrayList<String>> fechaYDepartamento, ArrayList<String> numeroDeDivorcios, int opcion) {

        CategoryDataset dataset = createDataset(fechaYDepartamento, numeroDeDivorcios, opcion);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Bar chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public CategoryDataset createDataset(ArrayList<ArrayList<String>> fechaYDepartamento, ArrayList<String> numeroDeDivorcios, int opcion) {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        /*dataset.setValue(46, "Gold medals", "USA");
        dataset.setValue(38, "Gold medals", "China");
        dataset.setValue(29, "Gold medals", "UK");
        dataset.setValue(22, "Gold medals", "Russia");
        dataset.setValue(13, "Gold medals", "South Korea");
        dataset.setValue(11, "Gold medals", "Germany"); */

        if (opcion == 0) {  //Graficar por departamento a lo largo de los años
            var eleccion = fechaYDepartamento.get(opcion);
            for (int i = 0; i < eleccion.size(); i++) {
                dataset.setValue(Double.parseDouble(numeroDeDivorcios.get(i)),"Divorces", eleccion.get(i));
            }
        } else if (opcion == 1) {
            var eleccion = fechaYDepartamento.get(opcion);
            for (int i = 0; i < eleccion.size(); i++) {
                dataset.setValue(Double.parseDouble(numeroDeDivorcios.get(i)),"Divorces", eleccion.get(i));
            }
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Divorces in Colombia by department",
                "",
                "Divorces",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, false, false);

        // Rotación de etiquetas en el eje Y (fechas o departamentos)
        CategoryPlot plot = barChart.getCategoryPlot();
        CategoryAxis axis = plot.getDomainAxis();

        // Ajustar tamaño de la fuente de las etiquetas
        axis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 11)); // Tamaño de la fuente reducido

        // Aumentar la distancia entre etiquetas (margen entre categorías)
        axis.setCategoryMargin(0.3); // Ajusta el valor para aumentar la distancia. 0.3 es un buen valor de prueba.

        return barChart;
    }
}
