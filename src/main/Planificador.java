/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import structures.LinkedList;
import structures.Node;

/**
 *
 * @author andresimery
 */
public class Planificador {
    private MemoriaPrincipal memoria;
    private String algoritmoActual;
    private int quantum;  // Para Round Robin
    private int quantumRestante;  // Control del Quantum por proceso

    public Planificador(MemoriaPrincipal memoria) {
        this.memoria = memoria;
        this.algoritmoActual = "FCFS";
        this.quantum = 5;  // Valor por defecto para Round Robin
        this.quantumRestante = quantum;
    }
    
    // Seleccionar un proceso de la cola de listos según el algoritmo
    public Proceso seleccionarSiguienteProceso() {
//        System.out.println("⏳ Intentando seleccionar un proceso.");
//        System.out.println("Total en listos: " + memoria.getCantidadListos());
//        System.out.println("Total en bloqueados: " + memoria.getCantidadBloqueados());
//        System.out.println("Total en terminados: " + memoria.getCantidadTerminados());

        if (memoria.getCantidadListos() == 0) {
//            System.out.println("⚠ No hay procesos en la cola de listos.");
            return null;
        }

        Proceso procesoSeleccionado = null;

        switch (algoritmoActual) {
            case "FCFS":
                procesoSeleccionado = memoria.obtenerDeListos();
                break;

            case "SJF": // Shortest Job First
                procesoSeleccionado = seleccionarProcesoSJF();
                break;

            case "RR": // Round Robin
                procesoSeleccionado = memoria.obtenerDeListos();
                quantumRestante = quantum; // Reiniciar el quantum
                break;

            default:
                System.out.println("⚠ Algoritmo no reconocido. Usando FCFS.");
                procesoSeleccionado = memoria.obtenerDeListos();
                break;
        }

//        if (procesoSeleccionado != null) {
//            System.out.println("✔ Proceso seleccionado: " + procesoSeleccionado.getPCB().getNombre());
//        }

        return procesoSeleccionado;
    }
    
    // Implementación del algoritmo SJF (Shortest Job First)
    private Proceso seleccionarProcesoSJF() {
        Node<Proceso> aux = memoria.getColaListos().getHead();
        Proceso procesoMenor = null;

        while (aux != null) {
            Proceso actual = aux.getData();
            if (procesoMenor == null || actual.getPCB().getInstruccionesRestantes() < procesoMenor.getPCB().getInstruccionesRestantes()) {
                procesoMenor = actual;
            }
            aux = aux.getNext();
        }

        if (procesoMenor != null) {
            memoria.removerProceso(procesoMenor);
        }

        return procesoMenor;
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
    
    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }
}
