/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;
import model.Modelo;
import view.Vista;
import controller.Controlador;
import javax.swing.UIManager;
/**
 *
 * @author vicec
 */
public class Principal {
    public static void main(String[] args) {
        
       Modelo m = new Modelo();
       Vista v = new Vista();
       Controlador c = new Controlador(m, v);
       c.Iniciar();
        
    }
}
