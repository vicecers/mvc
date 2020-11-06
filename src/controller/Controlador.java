/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import view.Vista;
import model.Modelo;

/**
 *
 * @author vicec
 */
public class Controlador implements ActionListener {

    private Modelo m;
    private Vista v;

    public Controlador(Modelo m, Vista v) {
        this.m = m;
        this.v = v;
        v.btnNuevo.addActionListener(this);
        v.btnCancelar.addActionListener(this);
        v.btnRegistrar.addActionListener(this);
        v.btnBuscar.addActionListener(this);
        v.btnRefrescar.addActionListener(this);
        v.btnModificar.addActionListener(this);
        v.btnActualizar.addActionListener(this);
        v.btnEliminar.addActionListener(this);
    }

    public void Iniciar() {
        v.setTitle("Sistema con MVC");
        v.pack();
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        v.setLocationRelativeTo(null);
        v.setVisible(true);
        m.BloquearCampos();
        m.mostrarUsusarios();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (v.btnNuevo == e.getSource()) {
            m.desbloquearCampos();
        } else if (v.btnCancelar == e.getSource()) {
            m.BloquearCampos();
        } else if (v.btnRegistrar == e.getSource()) {
            m.insertarUsuario();
        } else if (v.btnBuscar == e.getSource()) {
            m.mostrarUsusarios(v.txtBuscar.getText());
        } else if (v.btnRefrescar == e.getSource()) {
            m.mostrarUsusarios();
        }else if (v.btnModificar == e.getSource()) {
            m.seleccionarUsusrio();
        }else if (v.btnActualizar == e.getSource()) {
            m.Actualizar();
        }
        else if (v.btnEliminar == e.getSource()) {
            m.Eliminar();
            System.out.println("eliminado");
        }
    }

}
