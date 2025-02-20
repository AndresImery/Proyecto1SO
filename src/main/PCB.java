/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author andresimery
 */
public class PCB {
    private int processId;  // ID único del proceso
    private String nombre;
    private Estado estado;
    private int programCounter; // Instrucción actual que está ejecutando
    private int instruccionesTotales;
    private int instruccionesEjecutadas;
    private boolean esCPUBound;
    private int ciclosPorExcepcion;
    private int ciclosParaDesbloqueo;
    private int ciclosEnBloqueo;
    private int tiempoEspera;
    private int tiempoServicio;
    private int tiempoLlegada;
    
    public enum Estado { LISTO, EJECUTANDO, BLOQUEADO, TERMINADO }

    // Constructor
    public PCB(String nombre, int instruccionesTotales, boolean esCPUBound, int ciclosPorExcepcion, int ciclosParaDesbloqueo, int tiempoLlegada) {
        this.processId = processId++;
        this.nombre = nombre;
        this.estado = Estado.LISTO;
        this.programCounter = 0;
        this.instruccionesTotales = instruccionesTotales;
        this.instruccionesEjecutadas = 0;
        this.esCPUBound = esCPUBound;
        this.ciclosPorExcepcion = ciclosPorExcepcion; // Cada cuántos ciclos el proceso genera una interrupción (si es I/O-bound)
        this.ciclosParaDesbloqueo = ciclosParaDesbloqueo; // Cuántos ciclos debe esperar en bloqueo antes de volver a listos
        this.ciclosEnBloqueo = 0; // Cuánto tiempo ha estado en la cola de bloqueados
        this.tiempoEspera = 0; // Tiempo total que ha esperado en la cola de listos
        this.tiempoServicio = 0; // Tiempo total de ejecución en la CPU
        this.tiempoLlegada = tiempoLlegada; // Ciclo de reloj en el que el proceso ingresó al sistema
    }
    
    

    // Métodos para cambiar el estado del proceso
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado() {
        return estado;
    }

    // Métodos para ejecutar instrucciones
    public void ejecutarInstruccion() {
        if (programCounter < instruccionesTotales) {
            programCounter++;
            instruccionesEjecutadas++;
        }
    }

    public boolean haFinalizado() {
        return instruccionesEjecutadas >= instruccionesTotales;
    }

    // Métodos para interrupciones (I/O bound)
    public boolean generaExcepcion() {
        return !esCPUBound && instruccionesEjecutadas >= ciclosPorExcepcion;
    }
    
    public boolean listoParaDesbloqueo() {
        return (ciclosParaDesbloqueo <= ciclosEnBloqueo);
    }
    
    public void resetCicloBloqueo() {
        this.ciclosEnBloqueo = 0;
    }

    // Métodos para manejar tiempos
    public void incrementarTiempoEspera() {
        this.tiempoEspera++;
    }

    public void incrementarTiempoServicio() {
        this.tiempoServicio++;
    }

    public int getProcessId() {
        return processId;
    }

    public String getNombre() {
        return nombre;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public int getInstruccionesEjecutadas() {
        return instruccionesEjecutadas;
    }

    public int getInstruccionesTotales() {
        return instruccionesTotales;
    }
    
    public int getInstruccionesRestantes() {
        return (instruccionesTotales - instruccionesEjecutadas);
    }

    public boolean esCPUBound() {
        return esCPUBound;
    }
    
    public boolean esIOBound() {
        return !esCPUBound;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public int getCiclosPorExcepcion() {
        return ciclosPorExcepcion;
    }

    public int getCiclosParaDesbloqueo() {
        return ciclosParaDesbloqueo;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "ID=" + processId +
                ", Nombre='" + nombre + '\'' +
                ", Estado=" + estado +
                ", PC=" + programCounter +
                ", Instrucciones=" + instruccionesEjecutadas + "/" + instruccionesTotales +
                ", Tipo=" + (esCPUBound ? "CPU-bound" : "I/O-bound") +
                '}';
    }
}
