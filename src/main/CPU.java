/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.concurrent.Semaphore;

/**
 *
 * @author andresimery
 */
public class CPU extends Thread {
    private int cpuId;
    private Proceso procesoActual;
    private Kernel kernel;
    private Semaphore mutex;
    private int quantum;
    private int ciclosEjecutados;
    private boolean enEjecucion;
    private boolean interrumpir;

    // Constructor
    public CPU(int cpuId, Kernel kernel, int quantum) {
        this.cpuId = cpuId;
        this.kernel = kernel;
        this.quantum = quantum;
        this.mutex = new Semaphore(0); // Mutex para simular acceso exclusivo a la CPU
        this.ciclosEjecutados = 0;
        this.enEjecucion = true;
        this.interrumpir = false;
    }
    
    // Método principal de ejecución del hilo
    @Override
    public void run() {
        while (enEjecucion) {  // Simulación infinita hasta que el sistema termine
            try {
                if (procesoActual == null) {
                    mutex.acquire(); // Bloquea la CPU hasta que haya un proceso asignado
                }
                if (procesoActual != null) {
                    ejecutarProceso();
//                    mutex.release();
                } else {
//                    mutex.release();
                    Thread.sleep(kernel.getGestor().getDuracionCiclo());
                }
                 
            } catch (InterruptedException e) {
                System.out.println("🔴 CPU " + cpuId + " apagada.");
            }
        }
//        System.out.println("🔴 CPU " + cpuId + " apagada.");
    }
    
    // Método para asignar un proceso a la CPU
    public void asignarProceso(Proceso proceso) {
        this.procesoActual = proceso;
        this.ciclosEjecutados = 0;
        mutex.release(); // Permite la ejecución
    }
    
    // Método para ejecutar instrucciones del proceso
    private void ejecutarProceso() {
        
        if (procesoActual == null) {
//            System.out.println("⚠ CPU " + cpuId + " no tiene proceso asignado.");
            return;
        }
        
        procesoActual.getPCB().setTiempoEspera(0);
        if (kernel.isFeedback()) {
            String prioridad = procesoActual.getPCB().getPrioridad();
            if (prioridad.equals("Q1")) {
                quantum = 2;
            } else if (prioridad.equals("Q2")) {
                quantum = 4;
            }
        }
        
            
//        System.out.println("✅ CPU " + cpuId + " ejecutando: " + procesoActual.getPCB().getNombre());

//        int ciclosEjecutados = 0;
        while (procesoActual.getPCB().getInstruccionesRestantes() > 0 && !interrumpir) {
            if (kernel.getPlanificador().getAlgoritmoActual().equals("RR")) {
                if (getQuantum() <= 0) {
                    kernel.prepararProceso(procesoActual);
                    procesoActual = null;
                    this.ciclosEjecutados = 0;
                    return;
                }
            }
            
            if (kernel.isQuantum() && ciclosEjecutados >= quantum) {
                if (!procesoActual.getPCB().getPrioridad().equals("Q3")) {
                    System.out.println("Quantum agotado en CPU " + cpuId);
                    kernel.decrementarPrioridad(procesoActual);
                    kernel.prepararProceso(procesoActual);
                    procesoActual = null;
                    quantum = 5;
                    return;
                }
            }

            procesoActual.ejecutarInstruccion();
            ciclosEjecutados++;

            // Verificar si el proceso genera una interrupción (I/O bound)
            if (procesoActual.getPCB().esIOBound() && procesoActual.getPCB().generaExcepcion()) {
                System.out.println("🔴 Proceso " + procesoActual.getPCB().getNombre() + " generó una interrupción en CPU " + cpuId);
                kernel.bloquearProceso(procesoActual);
                procesoActual = null;
                quantum = 5;
                return;
            }

            // Simular tiempo de ejecución
            try {
                Thread.sleep(kernel.getGestor().getDuracionCiclo()); // Simula el tiempo de ciclo de instrucción
            } catch (InterruptedException e) {
                System.out.println("⚠ CPU " + cpuId + " interrumpida.");
            }
        }
        if (interrumpir) {
            kernel.prepararProceso(procesoActual);
            interrumpir = false;
            procesoActual = null;
            return;
        }
        quantum = 5;
        
        
        System.out.println("✅ Proceso " + procesoActual.getPCB().getNombre() + " finalizado en CPU " + cpuId);
        kernel.finalizarProceso(procesoActual);
        procesoActual = null;
    }
    
    public void generarInterrupcion() {
        interrumpir = true;
    }
    
    public void detenerCPU() {
        enEjecucion = false;
        this.interrupt(); // Interrumpe el `Thread.sleep()` si está esperando
    }
    
    public boolean estaLibre() {
        return procesoActual == null;
    }
    
    public int getCpuId() {
        return cpuId;
    }

    public Proceso getProcesoActual() {
        return procesoActual;
    }

    public int getQuantum() {
        if (kernel.getPlanificador().getAlgoritmoActual().equals("RR")) {
            return quantum - ciclosEjecutados;
        } 
        return 0;
    }
    
    
    
    /** TO-DO
     * public void run(); // Hilo que ejecuta procesos
     * public void asignarProceso(Proceso proceso);
     * public void liberarCPU();
     */
}
