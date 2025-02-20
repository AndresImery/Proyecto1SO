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
public class Simulador {
    private Planificador planificador;
    private LinkedList<CPU> cpus;
    private int duracionCiclo;

    public Simulador(Planificador planificador, LinkedList<CPU> cpus, int duracionCiclo) {
        this.planificador = planificador;
        this.cpus = cpus;
        this.duracionCiclo = duracionCiclo;
    }
    
    /** TO-DO
     * public void iniciarSimulacion();
     * public void verificarProcesosBloqueados();
     * public void actualizarInterfaz();
     */

}
