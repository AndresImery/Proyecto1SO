/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import com.google.gson.Gson;

/**
 *
 * @author andresimery
 */
public class Proyecto1SO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        System.out.println("Iniciando Sistema Operativo...");

        // Cargar configuración
        GestorConfiguracion gestor = new GestorConfiguracion();
        gestor.cargarDesdeJSON();

        // Inicializar Kernel con la configuración
        Kernel kernel = new Kernel(gestor);

        // Iniciar la simulación
        kernel.run();
    }
    
}
