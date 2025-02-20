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
    private int id;
    private Proceso procesoActual;
    private boolean activo;
    private Semaphore semaphore;
    private int quantumRestante;

    public CPU(int id, Proceso procesoActual, boolean activo, Semaphore semaphore, int quantumRestante) {
        this.id = id;
        this.procesoActual = procesoActual;
        this.activo = activo;
        this.semaphore = semaphore;
        this.quantumRestante = quantumRestante;
    }
    
    @Override
    public void run() {
        
    }
    
    /** TO-DO
     * public void run(); // Hilo que ejecuta procesos
     * public void asignarProceso(Proceso proceso);
     * public void liberarCPU();
     */
}
