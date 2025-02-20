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
public class MemoriaPrincipal {
    private LinkedList<Proceso> colaListos;
    private LinkedList<Proceso> colaBloqueados;
    private LinkedList<Proceso> colaTerminados;

    public MemoriaPrincipal() {
        this.colaListos = new LinkedList<Proceso>();
        this.colaBloqueados = new LinkedList<Proceso>();
        this.colaTerminados = new LinkedList<Proceso>();
    }
    
    // 📌 Agregar un proceso a la cola de listos
    public void agregarAListos(Proceso proceso) {
        if (proceso != null) {
            proceso.setEstado(PCB.Estado.LISTO);
            colaListos.add(proceso);
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " agregado a la cola de listos.");
        }
    }
    
    // 📌 Agregar un proceso a la cola de bloqueados
    public void agregarABloqueados(Proceso proceso) {
        if (proceso != null) {
            proceso.setEstado(PCB.Estado.BLOQUEADO);
            colaBloqueados.add(proceso);
            System.out.println("🔴 Proceso " + proceso.getPCB().getNombre() + " bloqueado.");
        }
    }
}
