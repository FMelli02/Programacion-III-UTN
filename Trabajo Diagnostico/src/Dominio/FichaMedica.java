package Dominio;

import java.util.List;

public class FichaMedica {
    private List<Diagnostico> diagnosticos;
    private List<TratamientoRealizado> historial;

    public FichaMedica(List<Diagnostico> diagnosticos, List<TratamientoRealizado> historial) {
        this.diagnosticos = diagnosticos;
        this.historial = historial;
    }

    public void agregarDiagnostico(Diagnostico d) {
        diagnosticos.add(d);
    }

    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public List<TratamientoRealizado> getHistorialDeTratamientos() {
        return historial;
    }
}
