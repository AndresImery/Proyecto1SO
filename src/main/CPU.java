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

    // Constructor
    public CPU(int cpuId, Kernel kernel, int quantum) {
        this.cpuId = cpuId;
        this.kernel = kernel;
        this.quantum = quantum;
        this.mutex = new Semaphore(0); // Mutex para simular acceso exclusivo a la CPU
        this.ciclosEjecutados = 0;
        this.enEjecucion = true;
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
            
//        System.out.println("✅ CPU " + cpuId + " ejecutando: " + procesoActual.getPCB().getNombre());

//        int ciclosEjecutados = 0;
        while (procesoActual.getPCB().getInstruccionesRestantes() > 0) {
            if (kernel.isQuantum() && ciclosEjecutados >= quantum) {
                System.out.println("Quantum agotado en CPU " + cpuId);
                kernel.prepararProceso(procesoActual);
                procesoActual = null;
                return;
            }

            procesoActual.ejecutarInstruccion();
            ciclosEjecutados++;

            // Verificar si el proceso genera una interrupción (I/O bound)
            if (procesoActual.getPCB().esIOBound() && procesoActual.getPCB().generaExcepcion()) {
                System.out.println("🔴 Proceso " + procesoActual.getPCB().getNombre() + " generó una interrupción en CPU " + cpuId);
                kernel.bloquearProceso(procesoActual);
                procesoActual = null;
                return;
            }

            // Simular tiempo de ejecución
            try {
                Thread.sleep(kernel.getGestor().getDuracionCiclo()); // Simula el tiempo de ciclo de instrucción
            } catch (InterruptedException e) {
                System.out.println("⚠ CPU " + cpuId + " interrumpida.");
            }
        }
        

        
        
        System.out.println("✅ Proceso " + procesoActual.getPCB().getNombre() + " finalizado en CPU " + cpuId);
        kernel.finalizarProceso(procesoActual);
        procesoActual = null;
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
    
    
    
    /** TO-DO
     * public void run(); // Hilo que ejecuta procesos
     * public void asignarProceso(Proceso proceso);
     * public void liberarCPU();
     */
}
