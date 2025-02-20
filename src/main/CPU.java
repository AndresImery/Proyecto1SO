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
//    private int quantumAsignado;

    // Constructor
    public CPU(Kernel kernel, int quantum) {
        this.cpuId = cpuId++;
        this.kernel = kernel;
        this.quantum = quantum;
        this.mutex = new Semaphore(1); // Mutex para simular acceso exclusivo a la CPU
    }
    
    // Método principal de ejecución del hilo
    @Override
    public void run() {
        while (true) {  // Simulación infinita hasta que el sistema termine
            try {
                mutex.acquire(); // Bloquea la CPU hasta que haya un proceso asignado
                if (procesoActual != null) {
                    ejecutarProceso();
                }
                mutex.release();
            } catch (InterruptedException e) {
                System.out.println("⚠ CPU " + cpuId + " interrumpida.");
            }
        }
    }
    
    // Método para asignar un proceso a la CPU
    public void asignarProceso(Proceso proceso) {
        this.procesoActual = proceso;
        mutex.release(); // Permite la ejecución
    }
    
    // Método para ejecutar instrucciones del proceso
    private void ejecutarProceso() {
        System.out.println("✅ CPU " + cpuId + " ejecutando: " + procesoActual.getPCB().getNombre());

        int ciclosEjecutados = 0;
        while (ciclosEjecutados < quantum && procesoActual.getPCB().getInstruccionesRestantes() > 0) {
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
                Thread.sleep(100); // Simula el tiempo de ciclo de instrucción
            } catch (InterruptedException e) {
                System.out.println("⚠ CPU " + cpuId + " interrumpida.");
            }
        }

        // Si el proceso terminó todas sus instrucciones
        if (procesoActual.getPCB().getInstruccionesRestantes() == 0) {
            System.out.println("✅ Proceso " + procesoActual.getPCB().getNombre() + " finalizado en CPU " + cpuId);
            kernel.finalizarProceso(procesoActual);
        } else {
            // Si es Round Robin, debe volver a la cola de listos
            System.out.println("⏳ Quantum agotado para " + procesoActual.getPCB().getNombre() + " en CPU " + cpuId);
            kernel.prepararProceso(procesoActual);
        }

        procesoActual = null;
    }
    
    public boolean estaLibre() {
        return procesoActual == null;
    }
    
    public int getCpuId() {
        return cpuId;
    }
    
    /** TO-DO
     * public void run(); // Hilo que ejecuta procesos
     * public void asignarProceso(Proceso proceso);
     * public void liberarCPU();
     */
}
