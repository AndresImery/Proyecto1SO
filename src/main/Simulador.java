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
    private MemoriaPrincipal memoria;
    private Planificador planificador;
    private LinkedList<CPU> cpus;
    private int duracionCiclo;

    public Simulador(Planificador planificador, LinkedList<CPU> cpus, int duracionCiclo) {
        this.memoria = new MemoriaPrincipal();
        this.planificador = new Planificador(memoria);
        this.cpus = new LinkedList<CPU>();
        this.duracionCiclo = duracionCiclo;
    }

    public Planificador getPlanificador() {
        return planificador;
    }

    public void setPlanificador(Planificador planificador) {
        this.planificador = planificador;
    }

    public LinkedList<CPU> getCpus() {
        return cpus;
    }

    public void setCpus(LinkedList<CPU> cpus) {
        this.cpus = cpus;
    }

    public int getDuracionCiclo() {
        return duracionCiclo;
    }

    public void setDuracionCiclo(int duracionCiclo) {
        this.duracionCiclo = duracionCiclo;
    }
    
    
    
    /** TO-DO
     * public void iniciarSimulacion();
     * public void verificarProcesosBloqueados();
     * public void actualizarInterfaz();
     */

}
