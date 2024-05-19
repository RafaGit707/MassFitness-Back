package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Clase;
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
    public void addClase(Clase clase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Clases (id_Entrenador, nombre_Clase, descripcion, horario_Clase, capacidad_Maxima, capacidad_Actual) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, clase.getEntrenador().getIdEntrenador());
            preparedStatement.setString(2, clase.getNombre());
            preparedStatement.setString(3, clase.getDescripcion());
            preparedStatement.setTimestamp(4, new Timestamp(clase.getHorarioClase().getTime()));
            preparedStatement.setInt(5, clase.getCapacidadMaxima());
            preparedStatement.setInt(6, clase.getCapacidadActual());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Clase> getClases() {
        List<Clase> clases = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Clases";
            ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);
            while (resultSet.next()) {
                int idClase = resultSet.getInt("id_Clase");
                int idEntrenador = resultSet.getInt("id_Entrenador");
                String nombre = resultSet.getString("nombre_Clase");
                String descripcion = resultSet.getString("descripcion");
                Timestamp timestamp = resultSet.getTimestamp("horario_Clase");
                Date horarioClase = new Date(timestamp.getTime());
                int capacidadMaxima = resultSet.getInt("capacidad_Maxima");
                int capacidadActual = resultSet.getInt("capacidad_Actual");
                Clase clase = new Clase(idClase, new Entrenador(idEntrenador), nombre, descripcion, horarioClase, capacidadMaxima, capacidadActual);
                clases.add(clase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }

    public Clase buscarClasePorId(int idClase) {
        Clase clase = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Clases WHERE id_Clase = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idClase);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idEntrenador = resultSet.getInt("id_Entrenador");
                String nombre = resultSet.getString("nombre_Clase");
                String descripcion = resultSet.getString("descripcion");
                Timestamp timestamp = resultSet.getTimestamp("horario_Clase");
                Date horarioClase = new Date(timestamp.getTime());
                int capacidadMaxima = resultSet.getInt("capacidad_Maxima");
                int capacidadActual = resultSet.getInt("capacidad_Actual");
                clase = new Clase(idClase, new Entrenador(idEntrenador), nombre, descripcion, horarioClase, capacidadMaxima, capacidadActual);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }

    public void actualizarClase(Clase clase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Clases SET id_Entrenador = ?, nombre_Clase = ?, descripcion = ?, horario_Clase = ?, capacidad_Maxima = ?, capacidad_Actual = ? WHERE id_Clase = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, clase.getEntrenador().getIdEntrenador());
            preparedStatement.setString(2, clase.getNombre());
            preparedStatement.setString(3, clase.getDescripcion());
            preparedStatement.setTimestamp(4, new Timestamp(clase.getHorarioClase().getTime()));
            preparedStatement.setInt(5, clase.getCapacidadMaxima());
            preparedStatement.setInt(6, clase.getCapacidadActual());
            preparedStatement.setInt(7, clase.getIdClase());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarClase(int idClase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Clases WHERE id_Clase = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idClase);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
