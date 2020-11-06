/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.Vista;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.PlainDocument;

/**
 *
 * @author vicec
 */
public class Modelo {

    private int id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String password;

    public Modelo(String nombre, String apellido, String usuario, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.password = password;
    }

    public Modelo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Connection cc;
    Vista v = new Vista();

    public Connection Conexion() {
        try {
            cc = DriverManager.getConnection("jdbc:mysql://192.168.0.115/SistemaMVC", "root", "basededatos2020");
            // JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return cc;
    }

    public void insertarUsuario() {
        setNombre(v.txtNombre.getText());
        setApellido(v.txtApellido.getText());
        setUsuario(v.txtUsuario.getText());
        setPassword(v.txtPass.getText());

        Connection cn = Conexion();

        PreparedStatement ps = null;

        String sql = "INSERT INTO usuarios (nombre, apellido, usuario, password) VALUES (?, ?, ?, ?)";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getApellido());
            ps.setString(3, getUsuario());
            ps.setString(4, getPassword());

            ps.execute();

        } catch (SQLException ex) {
            System.out.println("Error al registrar usuario " + ex.toString());
        }
    }

    public void desbloquearCampos() {
        v.txtNombre.setEnabled(true);
        v.txtApellido.setEnabled(true);
        v.txtUsuario.setEnabled(true);
        v.txtPass.setEnabled(true);
        v.btnCancelar.setEnabled(true);
        v.btnActualizar.setEnabled(true);
        v.btnRegistrar.setEnabled(true);

    }

    public void BloquearCampos() {
        v.txtNombre.setEnabled(false);
        v.txtApellido.setEnabled(false);
        v.txtUsuario.setEnabled(false);
        v.txtPass.setEnabled(false);
        v.btnCancelar.setEnabled(false);
        v.btnRegistrar.setEnabled(false);
        v.btnActualizar.setEnabled(false);
    }

    /*
    
    public static void main(String[] args) {
        Modelo m = new Modelo();
        m.Conexion();
    }
     */
    public void mostrarUsusarios() {
        DefaultTableModel dtm = new DefaultTableModel();
        Connection cn = null;
        String[] datos = new String[5];
        dtm.addColumn("ID");
        dtm.addColumn("Nombre");
        dtm.addColumn("Apellido");
        dtm.addColumn("Usuario");
        dtm.addColumn("Password");
        v.tabla.setModel(dtm);

        cn = Conexion();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from usuarios");

            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt(1));
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                dtm.addRow(datos);
            }
            v.tabla.setModel(dtm);

        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }

    }

    public void mostrarUsusarios(String id) {
        DefaultTableModel dtm = new DefaultTableModel();
        Connection cn = null;
        String[] datos = new String[5];
        dtm.addColumn("ID");
        dtm.addColumn("Nombre");
        dtm.addColumn("Apellido");
        dtm.addColumn("Usuario");
        dtm.addColumn("Password");
        v.tabla.setModel(dtm);

        cn = Conexion();
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("select * from usuarios where id='" + id + "'");

            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt(1));
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                dtm.addRow(datos);
            }
            v.tabla.setModel(dtm);

        } catch (SQLException ex) {
            System.out.println("Error " + ex.toString());
        }

    }

    public void Limpiar() {
        v.txtBuscar.setText("");
        v.txtNombre.setText("");
        v.txtApellido.setText("");
        v.txtUsuario.setText("");
        v.txtPass.setText("");
        v.btnCancelar.setText("");
        v.btnRegistrar.setText("");
        v.btnActualizar.setText("");

    }

    public void seleccionarUsusrio() {
        desbloquearCampos();
        v.btnRegistrar.setEnabled(false);

        int fila = v.tabla.getSelectedRow();

        if (fila >= 0) {
            v.txtBuscar.setText(v.tabla.getValueAt(fila, 0).toString());
            v.txtNombre.setText(v.tabla.getValueAt(fila, 1).toString());
            v.txtApellido.setText(v.tabla.getValueAt(fila, 2).toString());
            v.txtUsuario.setText(v.tabla.getValueAt(fila, 3).toString());
            v.txtPass.setText(v.tabla.getValueAt(fila, 4).toString());

        } else {
            System.out.println("no se selecciono fila");
        }

    }

    public void Actualizar() {
        setNombre(v.txtNombre.getText());
        setApellido(v.txtApellido.getText());
        setUsuario(v.txtUsuario.getText());
        setPassword(v.txtPass.getText());
        setId(Integer.parseInt(v.txtBuscar.getText()));

        Connection cn = Conexion();

        PreparedStatement ps = null;
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, usuario=?, password=? WHERE id=?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, getNombre());
            ps.setString(2, getApellido());
            ps.setString(3, getUsuario());
            ps.setString(4, getPassword());
            ps.setInt(5, getId());

            ps.executeUpdate();

            mostrarUsusarios();

        } catch (SQLException ex) {
            System.out.println("Error al registrar usuario " + ex.toString());
        }
    }

    public void Eliminar() {
        desbloquearCampos();
        Connection cn = Conexion();
        v.btnRegistrar.setEnabled(false);
        PreparedStatement ps = null;

        int fila = v.tabla.getSelectedRow();

        if (fila >= 0) {
            String id = v.tabla.getValueAt(fila, 0).toString();
            try {
                String sql = "DELETE FROM usuarios WHERE id = ?";
                ps = cn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(id));
                ps.execute();
                mostrarUsusarios();
            } catch (SQLException ex) {

            }

        } else {
            System.out.println("no se selecciono fila");
        }
    }

}
