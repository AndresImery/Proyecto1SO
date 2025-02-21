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
    private Kernel kernel;

    public Planificador(MemoriaPrincipal memoria, Kernel kernel) {
        this.memoria = memoria;
        this.algoritmoActual = "FCFS";
        this.quantum = 5;  // Valor por defecto para Round Robin
        this.quantumRestante = quantum;
        this.kernel = kernel;
    }
    
    // Seleccionar un proceso de la cola de listos según el algoritmo
    public Proceso seleccionarSiguienteProceso() {
//        System.out.println("⏳ Intentando seleccionar un proceso.");
//        System.out.println("Total en listos: " + memoria.getCantidadListos());
//        System.out.println("Total en bloqueados: " + memoria.getCantidadBloqueados());
//        System.out.println("Total en terminados: " + memoria.getCantidadTerminados());

        if (memoria.getCantidadListos() == 0 && !algoritmoActual.equals("Feedback")) {
//            System.out.println("⚠ No hay procesos en la cola de listos.");
            return null;
        }

        Proceso procesoSeleccionado = null;

        switch (algoritmoActual) {
            case "FCFS": // First Come First Serve (FIFO)
                procesoSeleccionado = memoria.obtenerDeListos();
                break;
            case "SPN": // 
                procesoSeleccionado = seleccionarProcesoSPN();
                break;
            case "SRT":
                procesoSeleccionado = seleccionarProcesoSRT(false);
                break;
            case "HRRN":
                procesoSeleccionado = seleccionarProcesoHRRN();
                break;
            case "RR":
                procesoSeleccionado = memoria.obtenerDeListos();
                quantumRestante = quantum;
                break;
            case "Feedback":
                procesoSeleccionado = seleccionarProcesoFeedback();
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
    
    // Implementación del algoritmo SPN (Shortest Process Next)
    private Proceso seleccionarProcesoSPN() {
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
    
    // Shortest Remaining Time
    public Proceso seleccionarProcesoSRT(boolean peek) {
        Node<Proceso> aux = memoria.getColaListos().getHead();
        Proceso procesoMenor = null;
        while (aux != null) {
            Proceso actual = aux.getData();
            if (procesoMenor == null || actual.getPCB().getInstruccionesRestantes() < procesoMenor.getPCB().getInstruccionesRestantes()) {
                procesoMenor = actual;
            }
            aux = aux.getNext();
        }
        if (procesoMenor != null && !peek) {
            memoria.removerProceso(procesoMenor);
        }
        return procesoMenor;
    }
    
    // Highest Response Ratio Next (RR = (w + s) / s)
    private Proceso seleccionarProcesoHRRN() {
        Node<Proceso> aux = memoria.getColaListos().getHead();
        Proceso mejorProceso = null;
        double mejorRatio = -1;
        while (aux != null) {
            Proceso actual = aux.getData();
            double w = actual.getPCB().getTiempoEspera();
            double s = actual.getPCB().getInstruccionesRestantes();
            double responseRatio = (w + s) / s;
            if (responseRatio > mejorRatio) {
                mejorRatio = responseRatio;
                mejorProceso = actual;
            }
            aux = aux.getNext();
        }
        if (mejorProceso != null) {
            memoria.removerProceso(mejorProceso);
        }
        return mejorProceso;
    }
    
    // Feedback (Prioridades y Quantum)
    private Proceso seleccionarProcesoFeedback() {
        Proceso proceso;
        // Prioridad: Primero Q1, luego Q2, luego Q3
        if (!memoria.getColaQ1().isEmpty()) {
            return memoria.getColaQ1().removeFirst();
        } else if (!memoria.getColaQ2().isEmpty()) {
            return memoria.getColaQ2().removeFirst();
        } else if (!memoria.getColaQ3().isEmpty()) {
            return memoria.getColaQ3().removeFirst();
        }
        return null; // No hay procesos en ninguna cola de Feedback
    }
    
    // Bloquear un proceso (cuando genera una interrupción de I/O)
    public void bloquearProceso(Proceso proceso) {
        if (proceso != null) {
            memoria.agregarABloqueados(proceso);
        }
    }
    
    public void prepararProceso(Proceso proceso) {
        if (proceso != null) {
            if (algoritmoActual.equals("Feedback")) {
                switch (proceso.getPCB().getPrioridad()) {
                    case "Q1":
                        memoria.agregarAColaQ1(proceso);
                        break;
                    case "Q2":
                        memoria.agregarAColaQ2(proceso);
                        break;
                    case "Q3":
                        memoria.agregarAColaQ3(proceso);
                        break;
                }
            } else {
                memoria.agregarAListos(proceso);
                if (algoritmoActual.equals("SRT")) {
                    kernel.forzarProceso();
                }
            }
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
