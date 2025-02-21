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
    private LinkedList<Proceso> colaQ1;
    private LinkedList<Proceso> colaQ2;
    private LinkedList<Proceso> colaQ3;
    private Kernel kernel;

    public MemoriaPrincipal(Kernel kernel) {
        this.colaListos = new LinkedList<Proceso>();
        this.colaBloqueados = new LinkedList<Proceso>();
        this.colaTerminados = new LinkedList<Proceso>();
        this.colaQ1 = new LinkedList<Proceso>();
        this.colaQ2 = new LinkedList<Proceso>();
        this.colaQ3 = new LinkedList<Proceso>();
        this.kernel = kernel;
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
    
    public void agregarAColaQ1(Proceso proceso) {
        if (proceso != null) {
            removerProceso(proceso);
            proceso.setEstado(PCB.Estado.LISTO);
            colaQ1.add(proceso);
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " listo Q1.");
        }
    }
    
    public void agregarAColaQ2(Proceso proceso) {
        if (proceso != null) {
            removerProceso(proceso);
            proceso.setEstado(PCB.Estado.LISTO);
            colaQ2.add(proceso);
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " listo Q2.");
        }
    }
    
    public void agregarAColaQ3(Proceso proceso) {
        if (proceso != null) {
            removerProceso(proceso);
            proceso.setEstado(PCB.Estado.LISTO);
            colaQ3.add(proceso);
            System.out.println("✔ Proceso " + proceso.getPCB().getNombre() + " listo Q3.");
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
            kernel.prepararProceso(proceso);
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
        if (colaListos.isEmpty() && colaBloqueados.isEmpty() && colaQ1.isEmpty() && colaQ2.isEmpty() && colaQ3.isEmpty()) {
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
    
    public int getCantidadQ1() { 
        return colaQ1.getSize(); 
    }
    
    public int getCantidadQ2() { 
        return colaQ2.getSize(); 
    }
    
    public int getCantidadQ3() { 
        return colaQ3.getSize(); 
    }

    public LinkedList<Proceso> getColaQ1() {
        return colaQ1;
    }

    public void setColaQ1(LinkedList<Proceso> colaQ1) {
        this.colaQ1 = colaQ1;
    }

    public LinkedList<Proceso> getColaQ2() {
        return colaQ2;
    }

    public void setColaQ2(LinkedList<Proceso> colaQ2) {
        this.colaQ2 = colaQ2;
    }

    public LinkedList<Proceso> getColaQ3() {
        return colaQ3;
    }

    public void setColaQ3(LinkedList<Proceso> colaQ3) {
        this.colaQ3 = colaQ3;
    }
    
    
}
