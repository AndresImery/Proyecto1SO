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
public class Kernel {
    
    private int numCPUs;
    private LinkedList<CPU> cpus;
    private MemoriaPrincipal memoria;
    private Planificador planificador;
    private int cicloReloj;
    private GestorConfiguracion gestor;
        
    public Kernel(GestorConfiguracion gestor) {
        this.numCPUs = gestor.getNumCPUs();
        this.cpus = new LinkedList<CPU>();
        this.memoria = new MemoriaPrincipal();
        this.planificador = new Planificador(memoria);
        this.cicloReloj = 0;
        this.gestor = gestor;

        // Crear y asociar CPUs
        for (int i = 0; i < numCPUs; i++) {
            this.cpus.add(new CPU(this, gestor.getQuantum())); // Cada CPU recibe una referencia al Kernel
        }
    }
        
    public void iniciarSimulacion() {
        System.out.println("Cargando " + gestor.getProcesosCargados().getSize() + " procesos.");
        memoria.setColaDeListos(gestor.getProcesosCargados());
        
        System.out.println("Simulación iniciada con " + numCPUs + " CPU(s).");
        while (true) {
            cicloReloj++;
            System.out.println("\n[Ciclo de reloj: " + cicloReloj + "]");
            manejarProcesos();
            manejarInterrupciones();
            ejecutarCPUs();

            // Detener la simulación si no hay procesos pendientes
            if (memoria.todosLosProcesosFinalizados()) {
                System.out.println("Todos los procesos han terminado. Finalizando simulación.");
                break;
            }
        }
    }
    
    // Asignar procesos a CPUs
    private void manejarProcesos() {
        Node<CPU> aux = cpus.getHead();
        if (aux != null) {
            CPU cpu = aux.getData();
            if (cpu.estaLibre()) {
                Proceso proceso = planificador.seleccionarSiguienteProceso();
                if (proceso != null) {
                    cpu.asignarProceso(proceso);
                    cpu.run();
                } else {
                    System.out.println("⚠ No hay procesos en la cola de listos.");
                }
            }
            while (aux.getNext() != null) {
                cpu = aux.getNext().getData();
                if (cpu.estaLibre()) {
                    Proceso proceso = planificador.seleccionarSiguienteProceso();
                    if (proceso != null) {
                        cpu.asignarProceso(proceso);
                        cpu.run();
                    } else {
                        System.out.println("⚠ No hay procesos en la cola de listos.");
                    }
                }
                aux = aux.getNext();
            }
        }
//        for (CPU cpu : cpus) {
//            if (!cpu.estaOcupado()) {
//                Proceso proceso = planificador.seleccionarSiguienteProceso(memoria);
//                if (proceso != null) {
//                    cpu.ejecutarProceso(proceso);
//                } else {
//                    System.out.println("⚠ No hay procesos en la cola de listos.");
//                }
//            }
//        }
    }
    
    // Manejar interrupciones de procesos bloqueados
    private void manejarInterrupciones() {
        memoria.desbloquearProcesos();
    }
    
    // Ejecutar cada CPU
    private void ejecutarCPUs() {
        Node<CPU> aux = cpus.getHead();
        if (aux != null) {
            CPU data = aux.getData();
            
        }
        
//        for (CPU cpu : cpus) {
//            cpu.ejecutarCiclo();
//        }
    }
    
    // Obtener acceso a la memoria principal
    public MemoriaPrincipal getMemoria() {
        return memoria;
    }
    
    public void bloquearProceso(Proceso proceso) {
        planificador.bloquearProceso(proceso);
    }
    
    public void finalizarProceso(Proceso proceso) {
        planificador.finalizarProceso(proceso);
    }
    
    public void prepararProceso(Proceso proceso) {
        planificador.prepararProceso(proceso);
    }
}
