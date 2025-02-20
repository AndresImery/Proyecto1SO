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

/**
 *
 * @author andresimery
 */
public class GestorConfiguracion {
    private int duracionCiclo;
    private int numCPUs;
    private LinkedList<Proceso> procesosCargados;
//    private int indiceProcesos = 0;

    // Constructor
    public GestorConfiguracion() {
        this.duracionCiclo = 0;
        this.numCPUs = 0;
        this.procesosCargados = new LinkedList<Proceso>();
    }
    
    
    
    
}
