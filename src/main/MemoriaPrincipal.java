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
public class MemoriaPrincipal {
    private LinkedList<Proceso> colaListos;
    private LinkedList<Proceso> colaBloqueados;
    private LinkedList<Proceso> colaTerminados;

    public MemoriaPrincipal() {
        this.colaListos = new LinkedList<Proceso>();
        this.colaBloqueados = new LinkedList<Proceso>();
        this.colaTerminados = new LinkedList<Proceso>();
    }
    
    // Agregar un proceso a la cola de listos
    public void agregarAListos(Proceso proceso) {
        if (proceso != null) {
            removerProceso(proceso);
            proceso.setEstado(PCB.Estado.LISTO);
            colaListos.add(proceso);
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " agregado a la cola de listos.");
        }
    }
    
    // Agregar un proceso a la cola de bloqueados
    public void agregarABloqueados(Proceso proceso) {
        if (proceso != null) {
            removerProceso(proceso);
            proceso.setEstado(PCB.Estado.BLOQUEADO);
            colaBloqueados.add(proceso);
            System.out.println("🔴 Proceso " + proceso.getPCB().getNombre() + " bloqueado.");
        }
    }
    
    // Agregar un proceso a la cola de terminados
    public void agregarATerminados(Proceso proceso) {
        if (proceso != null) {
            removerProceso(proceso);
            proceso.setEstado(PCB.Estado.TERMINADO);
            colaTerminados.add(proceso);
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " terminado.");
        }
    }
    
    public boolean removerProceso(Proceso proceso) {
        if (colaListos.remove(proceso) != null) {
            System.out.println("✅ Proceso " + proceso.getPCB().getNombre() + " eliminado de la cola de listos.");
            return true;
        }
        if (colaBloqueados.remove(proceso) != null) {
            System.out.println("✅ Proceso " + proceso.getPCB().getNombre() + " eliminado de la cola de bloqueados.");
            return true;
        }
        if (colaTerminados.remove(proceso) != null) {
            System.out.println("✅ Proceso " + proceso.getPCB().getNombre() + " eliminado de la cola de terminados.");
            return true;
        }
        System.out.println("No se encontró el proceso en ninguna cola.");
        return false;
    }
    
    public Proceso obtenerDeListos() {
        return colaListos.isEmpty() ? null : colaListos.removeFirst();
    }
    
    public void setColaDeListos(LinkedList<Proceso> procesos) {
        this.colaListos = procesos;
    }
    
    // Mover un proceso de bloqueados a listos cuando cumpla su tiempo de I/O
    public void desbloquearProcesos() {
        LinkedList<Proceso> procesosADesbloquear = new LinkedList<>();

        Node<Proceso> aux = colaBloqueados.getHead();
        if (aux != null) {
            Proceso data = aux.getData();
            if (data.getPCB().listoParaDesbloqueo()) {
                procesosADesbloquear.add(data);
            }
            while (aux.getNext() != null) {
                data = aux.getNext().getData();
                if (data.getPCB().listoParaDesbloqueo()) {
                    procesosADesbloquear.add(data);
                }
                aux = aux.getNext();
            }
        }
//        for (Proceso proceso : colaBloqueados) {
//            if (proceso.getPCB().listoParaDesbloqueo()) {
//                procesosADesbloquear.add(proceso);
//            }
//        }

        aux = procesosADesbloquear.getHead();
        if (aux != null) {
            Proceso proceso = aux.getData();
            colaBloqueados.remove(proceso);
            agregarAListos(proceso);
            proceso.getPCB().resetCicloBloqueo();
            while (aux.getNext() != null) {
                proceso = aux.getNext().getData();
                colaBloqueados.remove(proceso);
                agregarAListos(proceso);
                proceso.getPCB().resetCicloBloqueo();
                
                aux = aux.getNext();
            }
        }

//        for (Proceso proceso : procesosADesbloquear) {
//            colaBloqueados.remove(proceso);
//            agregarAListos(proceso);
//            proceso.getPCB().resetCicloBloqueo();  // Restablecer el contador de bloqueo
//        }
    }
    
    public boolean todosLosProcesosFinalizados() {
        if (colaListos.isEmpty() && colaBloqueados.isEmpty()) {
            return true;
        }
        return false;
    }
    
    // Métodos auxiliares
    public int getCantidadListos() { 
        return colaListos.getSize(); 
    }
    
    public int getCantidadBloqueados() { 
        return colaBloqueados.getSize(); 
    }
    
    public int getCantidadTerminados() { 
        return colaTerminados.getSize(); 
    }
}
