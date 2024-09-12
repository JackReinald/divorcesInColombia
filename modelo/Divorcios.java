package modelo;

public class Divorcios {
    private String fecha;
    private String departamento;
    private String numeroDeDivorcios;

    public Divorcios(DatosDivorcios datosDivorcios){
        this.fecha = datosDivorcios.periodo();
        this.departamento = datosDivorcios.departamento();
        this.numeroDeDivorcios = datosDivorcios.divorcios();
    }

    public String getFecha() {
        return fecha;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getNumeroDeDivorcios() {
        return numeroDeDivorcios;
    }

    @Override
    public String toString() {
        return "fecha = '" + fecha + '\'' +
                ", departamento = '" + departamento + '\'' +
                ", numeroDeDivorcios = '" + numeroDeDivorcios;
    }
}
