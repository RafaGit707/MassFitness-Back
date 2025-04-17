package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Clases;
import com.example.massfitness.entidades.Entrenador;
import com.example.massfitness.servicios.impl.IClaseService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class ClaseService implements IClaseService {

    private final AccesoBD accesoBD;

    @Autowired
    public ClaseService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    public Clases addClase(Clases clase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Clases (nombre, capacidad_maxima, entrenador_id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setString(1, clase.getNombre());
            ps.setInt(2, clase.getCapacidadMaxima());
            ps.setInt(3, clase.getEntrenador().getIdEntrenador());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }

    public void actualizarClase(Clases clase) {
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
    }

    public void eliminarClase(int idClase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Clases WHERE id_clase = ?";
            PreparedStatement ps = connection.prepareStatement(deleteSQL);
            ps.setInt(1, idClase);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Clases> getClases() {
        List<Clases> clases = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            /*String selectSQL = "SELECT c.*, e.nombre_entrenador, e.especializacion FROM Clases c JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador";*/
            String selectSQL = """
            SELECT 
                c.id_clase,
                c.nombre,
                c.capacidad_maxima,
                c.entrenador_id AS id_entrenador,
                e.nombre_entrenador,
                e.especializacion
            FROM Clases c 
            JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador
        """;
            ResultSet rs = connection.createStatement().executeQuery(selectSQL);
            while (rs.next()) {
                int idClase = rs.getInt("id_clase");
                String nombre = rs.getString("nombre");
                int capacidad = rs.getInt("capacidad_maxima");

                Entrenador entrenador = new Entrenador(
                        rs.getInt("id_entrenador"),
                        rs.getString("nombre_entrenador"),
                        rs.getString("especializacion")
                );

                Clases clase = new Clases(idClase, nombre, capacidad, entrenador);
                clases.add(clase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }

    public Clases buscarClasePorId(int idClase) {
        Clases clase = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT c.*, e.nombre_entrenador, e.especializacion FROM Clases c JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador WHERE id_clase = ?";
            PreparedStatement ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, idClase);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int capacidad = rs.getInt("capacidad_maxima");
                Entrenador entrenador = new Entrenador(
                        rs.getInt("entrenador_id"),
                        rs.getString("nombre_entrenador"),
                        rs.getString("especializacion")
                );
                clase = new Clases(idClase, nombre, capacidad, entrenador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }
}