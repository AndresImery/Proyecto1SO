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
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " listo.");
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
    
    // Remover un proceso de cualquier cola en la que esté
    public boolean removerProceso(Proceso proceso) {
        boolean eliminado = colaListos.remove(proceso) != null || colaBloqueados.remove(proceso) != null || colaTerminados.remove(proceso) != null;

//        if (eliminado) {
//            System.out.println("✅ Proceso " + proceso.getPCB().getNombre() + " eliminado de su cola actual.");
//        } else {
//            System.out.println("⚠ No se encontró el proceso en ninguna cola.");
//        }
        return eliminado;
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

        // Recorrer la cola de bloqueados para identificar procesos listos para desbloqueo
        while (aux != null) {
            Proceso proceso = aux.getData();
            if (proceso.getPCB().listoParaDesbloqueo()) {
                proceso.getPCB().resetCicloBloqueo();
                procesosADesbloquear.add(proceso);
            } else {
                proceso.getPCB().incrementarCiclosEnBloqueo();
            }
            aux = aux.getNext();
        }
//        for (Proceso proceso : colaBloqueados) {
//            if (proceso.getPCB().listoParaDesbloqueo()) {
//                procesosADesbloquear.add(proceso);
//            }
//        }

        // Mover los procesos de bloqueados a listos
        aux = procesosADesbloquear.getHead();
        while (aux != null) {
            Proceso proceso = aux.getData();
            removerProceso(proceso);
            System.out.println("Proceso " + proceso.getPCB().getNombre() + " paso a listos");
            proceso.getPCB().setEstado(PCB.Estado.LISTO);
            agregarAListos(proceso);
//            proceso.getPCB().resetCicloBloqueo(); // Restablecer contador de espera
            aux = aux.getNext();
        }

//        for (Proceso proceso : procesosADesbloquear) {
//            colaBloqueados.remove(proceso);
//            agregarAListos(proceso);
//            proceso.getPCB().resetCicloBloqueo();  // Restablecer el contador de bloqueo
//        }
    }
    
    public boolean todosLosProcesosFinalizados(LinkedList<CPU> cpus) {
        if (colaListos.isEmpty() && colaBloqueados.isEmpty()) {
            Node<CPU> aux = cpus.getHead();
            while (aux != null) {
                CPU cpu = aux.getData();
                if (!cpu.estaLibre()) { return false; }
                aux = aux.getNext();
            }
            return true;
        }
        return false;
    }

    public LinkedList<Proceso> getColaListos() {
        return colaListos;
    }

    public LinkedList<Proceso> getColaBloqueados() {
        return colaBloqueados;
    }

    public LinkedList<Proceso> getColaTerminados() {
        return colaTerminados;
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
