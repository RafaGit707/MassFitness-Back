package com.example.massfitness.servicios.impl;

import com.example.massfitness.entidades.Clases;

import java.sql.SQLException;
import java.util.List;

public interface IClaseService {
    Clases addClase(Clases clase);
    /*void actualizarClase(Clases clase);*/

    /*    public void actualizarClase(Clases clase) {
            try (Connection connection = accesoBD.conectarPostgreSQL()) {
                String updateSQL = "UPDATE Clases SET nombre = ?, capacidad_maxima = ?, entrenador_id = ? WHERE id_clase = ?";
                PreparedStatement ps = connection.prepareStatement(updateSQL);
                ps.setString(1, clase.getNombre());
                ps.setInt(2, clase.getCapacidadMaxima());
                ps.setInt(3, clase.getEntrenador().getIdEntrenador());
                ps.setInt(4, clase.getIdClase());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
/*    boolean actualizarClase(int idClase, String nombre, int capacidadMaxima, String entrenadorIdStr) throws SQLException;*/
    Clases actualizarClase(Clases clase);
    void eliminarClase(int idClase);
    List<Clases> getClases();
    Clases buscarClasePorId(int idClase);
}
