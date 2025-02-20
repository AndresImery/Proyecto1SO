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
    private int quantum;  // Para Round Robin

    public Planificador(MemoriaPrincipal memoria, String algoritmoActual, int quantum) {
        this.memoria = memoria;
        this.algoritmoActual = algoritmoActual;
        this.quantum = quantum;
    }
    
    
    
    /** TO-DO
     * public void agregarProceso(Proceso proceso);
     * public Proceso seleccionarSiguienteProceso();
     * public void bloquearProceso(Proceso proceso);
     * public void desbloquearProceso(Proceso proceso);
     */
}
