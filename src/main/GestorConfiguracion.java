/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import structures.LinkedList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.*;
//import java.util.List;
import structures.Node;

/**
 *
 * @author andresimery
 */
public class GestorConfiguracion {
    private String rutaArchivo;
    private int duracionCiclo;
    private int numCPUs;
    private LinkedList<Proceso> procesosCargados;
    private ArrayList<ConfigProceso> auxProcesos;
    private int quantum;
    private String algoritmoActual;

    // Constructor
    public GestorConfiguracion() {
        this.rutaArchivo = "./src/Public/config.json";
        this.duracionCiclo = 0;
        this.numCPUs = 0;
        this.procesosCargados = new LinkedList<Proceso>();
        this.auxProcesos = new ArrayList<>();
        this.quantum = 5;
        this.algoritmoActual = "FCFS";
    }
    
    // Cargar configuración desde JSON con Gson
    public void cargarDesdeJSON() {
        procesosCargados = new LinkedList<Proceso>();
        auxProcesos = new ArrayList<>();
        try (Reader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            Configuracion config = gson.fromJson(reader, Configuracion.class);
            this.duracionCiclo = config.DuracionCiclo;
            this.numCPUs = config.NumCPUs;
//            List<ConfigProceso> procesos = config.Procesos;
            System.out.println("Duración del Ciclo: " + config.DuracionCiclo);
            System.out.println("Número de CPUs: " + config.NumCPUs);

            int counter = 0;
            for (ConfigProceso data : config.Procesos) {
                System.out.println("Cargando proceso: " + data.nombre);
                System.out.println("  - Instrucciones Totales: " + data.instruccionesTotales);
                System.out.println("  - Tipo: " + (data.esCPUBound ? "CPU Bound" : "I/O Bound"));
                System.out.println("  - Ciclos por Excepción: " + data.ciclosPorExcepcion);
                System.out.println("  - Ciclos para Desbloqueo: " + data.ciclosParaDesbloqueo);

                // Crear objeto Proceso y agregarlo a la lista de procesos cargados
                Proceso newProcess = new Proceso(
                    counter,
                    data.nombre, 
                    data.instruccionesTotales, 
                    data.esCPUBound, 
                    data.ciclosPorExcepcion, 
                    data.ciclosParaDesbloqueo, 
                    0  // Suponiendo que el ID se maneja automáticamente
                        
                );
                counter++;
                procesosCargados.add(newProcess);
            }

            System.out.println("Configuración cargada desde JSON con Gson.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
        }
    }
    
    // Guardar configuración en JSON con Gson
    public void guardarEnJSON() {
        try (Writer writer = new FileWriter(rutaArchivo)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            auxProcesos = new ArrayList<ConfigProceso>();
            
            
            Node<Proceso> aux = procesosCargados.getHead();
            PCB data = aux.getData().getPCB();
            auxProcesos.add(new ConfigProceso(data.getNombre(), data.getInstruccionesTotales(), data.esCPUBound(), data.getCiclosPorExcepcion(), data.getCiclosParaDesbloqueo()));
            while (aux.getNext() != null) {
                data = aux.getNext().getData().getPCB();
                auxProcesos.add(new ConfigProceso(data.getNombre(), data.getInstruccionesTotales(), data.esCPUBound(), data.getCiclosPorExcepcion(), data.getCiclosParaDesbloqueo()));
                
                aux = aux.getNext();
            }
            
            Configuracion config = new Configuracion(duracionCiclo, numCPUs, auxProcesos);
            gson.toJson(config, writer);
            System.out.println("Configuración guardada en JSON con Gson.");
        } catch (IOException e) {
            System.out.println("Error al guardar en JSON: " + e.getMessage());
        }
    }
    
    private static class Configuracion {
        int DuracionCiclo;
        int NumCPUs;
        ArrayList<ConfigProceso> Procesos;

        Configuracion(int duracionCiclo, int numCPUs, ArrayList<ConfigProceso> procesos) {
            this.DuracionCiclo = duracionCiclo;
            this.NumCPUs = numCPUs;
            this.Procesos = procesos;
        }
        
        
        
    }

    private static class ConfigProceso {
            String nombre;
            int instruccionesTotales;
            boolean esCPUBound;
            int ciclosPorExcepcion;
            int ciclosParaDesbloqueo;

            public ConfigProceso(String nombre, int instruccionesTotales, boolean esCPUBound, int ciclosPorExcepcion, int ciclosParaDesbloqueo) {
                this.nombre = nombre;
                this.instruccionesTotales = instruccionesTotales;
                this.esCPUBound = esCPUBound;
                this.ciclosPorExcepcion = ciclosPorExcepcion;
                this.ciclosParaDesbloqueo = ciclosParaDesbloqueo;
            }
            
            
            
        }
    
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public int getDuracionCiclo() {
        return duracionCiclo;
    }

    public void setDuracionCiclo(int duracionCiclo) {
        this.duracionCiclo = duracionCiclo;
    }

    public int getNumCPUs() {
        return numCPUs;
    }

    public void setNumCPUs(int numCPUs) {
        this.numCPUs = numCPUs;
    }

    public LinkedList<Proceso> getProcesosCargados() {
        return procesosCargados;
    }

    public void setProcesosCargados(LinkedList<Proceso> procesosCargados) {
        this.procesosCargados = procesosCargados;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public String getAlgoritmoActual() {
        return algoritmoActual;
    }

    public void setAlgoritmoActual(String algoritmoActual) {
        this.algoritmoActual = algoritmoActual;
    }
}
