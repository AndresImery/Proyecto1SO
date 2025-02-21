/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import main.PCB.Estado;

/**
 *
 * @author andresimery
 */
public class Proceso {
    private PCB pcb;

    public Proceso(int id, String nombre, int instruccionesTotales, boolean esCPUBound, int ciclosPorExcepcion, int ciclosParaDesbloqueo, int tiempoLlegada) {
        this.pcb = new PCB(id, nombre, instruccionesTotales, esCPUBound, ciclosPorExcepcion, ciclosParaDesbloqueo, tiempoLlegada, "Q1");
    }
    
    /** TO-DO
     * public void ejecutarInstruccion();
     * public boolean haFinalizado();
     * public boolean generaExcepcion();
     * public void incrementarTiempoEspera();
     */

    // Obtener el PCB del proceso
    public PCB getPCB() {
        return pcb;
    }

    // Ejecutar una instrucción en el proceso
    public void ejecutarInstruccion() {
        pcb.ejecutarInstruccion();
    }

    // Verificar si el proceso ha finalizado
    public boolean haFinalizado() {
        return pcb.haFinalizado();
    }

    // Verificar si genera una excepción (I/O bound)
    public boolean generaExcepcion() {
        return pcb.generaExcepcion();
    }

    // Cambiar el estado del proceso
    public void setEstado(PCB.Estado estado) {
        pcb.setEstado(estado);
    }

    @Override
    public String toString() {
        return pcb.toString();
    }
}
