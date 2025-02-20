/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import structures.LinkedList;

/**
 *
 * @author andresimery
 */
public class Planificador {
    private MemoriaPrincipal memoria;
    private String algoritmoActual;
//    private int quantum;  // Para Round Robin

    public Planificador(MemoriaPrincipal memoria) {
        this.memoria = memoria;
        this.algoritmoActual = "FCFS";
//        this.quantum = 5;
    }
    
    // Seleccionar un proceso de la cola de listos según el algoritmo
    public Proceso seleccionarSiguienteProceso() {
        System.out.println("⏳ Intentando seleccionar un proceso. Total en listos: " + memoria.getCantidadListos());

        if (memoria.getCantidadListos() == 0) {
            System.out.println("⚠ No hay procesos en la cola de listos.");
            return null;
        }

        Proceso procesoSeleccionado = memoria.obtenerDeListos();
        System.out.println("✔ Proceso seleccionado: " + procesoSeleccionado.getPCB().getNombre());

        return procesoSeleccionado;
    }
    
    // Bloquear un proceso (cuando genera una interrupción de I/O)
    public void bloquearProceso(Proceso proceso) {
        if (proceso != null) {
            memoria.agregarABloqueados(proceso);
        }
    }
    
    public void prepararProceso(Proceso proceso) {
        if (proceso != null) {
            memoria.agregarAListos(proceso);
        }
    }
    
    // Finalizar un proceso y pasarlo a la cola de terminados
    public void finalizarProceso(Proceso proceso) {
        if (proceso != null) {
            memoria.agregarATerminados(proceso);
        }
    }

    public MemoriaPrincipal getMemoria() {
        return memoria;
    }

    public void setMemoria(MemoriaPrincipal memoria) {
        this.memoria = memoria;
    }

    public String getAlgoritmoActual() {
        return algoritmoActual;
    }

    public void setAlgoritmoActual(String algoritmoActual) {
        this.algoritmoActual = algoritmoActual;
    }

//    public int getQuantum() {
//        return quantum;
//    }

//    public void setQuantum(int quantum) {
//        this.quantum = quantum;
//    }
    
    
    
    
    /** TO-DO
     * public void agregarProceso(Proceso proceso);
     * public Proceso seleccionarSiguienteProceso();
     * public void bloquearProceso(Proceso proceso);
     * public void desbloquearProceso(Proceso proceso);
     */
}
