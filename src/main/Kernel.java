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
public class Kernel extends Thread {
    
    private int numCPUs;
    private LinkedList<CPU> cpus;
    private MemoriaPrincipal memoria;
    private Planificador planificador;
    private int cicloReloj;
    private GestorConfiguracion gestor;
//    private boolean quantum;
    private boolean enEjecucion; // Bandera para detener el kernel
        
    public Kernel(GestorConfiguracion gestor) {
        this.numCPUs = gestor.getNumCPUs();
        this.cpus = new LinkedList<CPU>();
        this.memoria = new MemoriaPrincipal();
        this.planificador = new Planificador(memoria);
        this.cicloReloj = 0;
        this.gestor = gestor;
//        this.quantum = false;
        this.enEjecucion = true;

        // Crear y asociar CPUs
        for (int i = 1; i <= numCPUs; i++) {
            CPU cpu = new CPU(i, this, gestor.getQuantum());
            this.cpus.add(cpu);
            cpu.start(); // Iniciar cada CPU una sola vez
        }
    }
    
    @Override    
    public void run() {
        System.out.println("Cargando " + gestor.getProcesosCargados().getSize() + " procesos.");
        memoria.setColaDeListos(gestor.getProcesosCargados());
        
        System.out.println("Simulación iniciada con " + numCPUs + " CPU(s).");
        while (enEjecucion) {
            
            cicloReloj++;
            System.out.println("\n[Ciclo de reloj: " + cicloReloj + "]");
            System.out.println("Total en listos: " + memoria.getCantidadListos());
            System.out.println("Total en bloqueados: " + memoria.getCantidadBloqueados());
            System.out.println("Total en terminados: " + memoria.getCantidadTerminados());
            
            Node<CPU> aux = cpus.getHead();
            while (aux != null) {
                CPU cpu = aux.getData();
                if (!cpu.estaLibre()) {
                    System.out.println("CPU " + cpu.getCpuId() + " ejecuta: " + cpu.getProcesoActual().getPCB().getNombre());
                } else {
                    System.out.println("CPU " + cpu.getCpuId() + " ejecuta: [ ]");
                }
                
                aux = aux.getNext();
            }
            
            manejarProcesos();
            manejarInterrupciones();
//            ejecutarCPUs();
            
            
            try {
                Thread.sleep(gestor.getDuracionCiclo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            

            // Detener la simulación si no hay procesos pendientes
            if (memoria.todosLosProcesosFinalizados(cpus)) {
                System.out.println("Todos los procesos han terminado. Finalizando simulación.");
                detenerKernel();
            }
        }
        
    }
    
    // Asignar procesos a CPUs
    private void manejarProcesos() {
        Node<CPU> aux = cpus.getHead();
        while (aux != null) {
            CPU cpu = aux.getData();
            if (cpu.estaLibre()) {
                Proceso proceso = planificador.seleccionarSiguienteProceso();
                if (proceso != null) {
                    cpu.asignarProceso(proceso);
                } else {
//                    System.out.println("⚠ No hay procesos en la cola de listos.");
                }
            }
            aux = aux.getNext();
        }
    }
    
    // Manejar interrupciones de procesos bloqueados
    private void manejarInterrupciones() {
        memoria.desbloquearProcesos();
    }
    
    // Finaliza la ejecución del Kernel
    public void detenerKernel() {
        enEjecucion = false;
        System.out.println("🛑 Deteniendo todas las CPU...");

        Node<CPU> aux = cpus.getHead();
        while (aux != null) {
            aux.getData().detenerCPU(); // ✅ Llama a detenerCPU() en cada CPU
            aux = aux.getNext();
        }

        System.out.println("✅ Simulación finalizada.");
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

    public boolean isQuantum() {
        return (planificador.getAlgoritmoActual().equals("RR"));
    }

//    public void setUseQuantum(boolean quantum) {
//        this.quantum = quantum;
//    }

    public GestorConfiguracion getGestor() {
        return gestor;
    }

    public void setGestor(GestorConfiguracion gestor) {
        this.gestor = gestor;
    }
    
    
    
}
