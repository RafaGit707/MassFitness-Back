package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Espacio;
import com.example.massfitness.servicios.impl.IEspacioService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EspacioService implements IEspacioService {

    private final AccesoBD accesoBD;
    @Autowired
    public EspacioService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    public void addEspacio(Espacio espacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Espacios (nombre, descripcion, capacidad_Maxima, capacidad_Actual, horario_Reserva) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, espacio.getNombre());
            preparedStatement.setString(2, espacio.getDescripcion());
            preparedStatement.setInt(3, espacio.getCapacidadMaxima());
            preparedStatement.setInt(4, espacio.getCapacidadActual());
            preparedStatement.setTimestamp(5, new Timestamp(espacio.getHorarioReserva().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEspacio(Espacio espacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Espacios SET nombre = ?, descripcion = ?, capacidad_Maxima = ?, capacidad_Actual = ?, horario_Reserva = ? WHERE id_Espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, espacio.getNombre());
            preparedStatement.setString(2, espacio.getDescripcion());
            preparedStatement.setInt(3, espacio.getCapacidadMaxima());
            preparedStatement.setInt(4, espacio.getCapacidadActual());
            preparedStatement.setTimestamp(5, new Timestamp(espacio.getHorarioReserva().getTime()));
            preparedStatement.setInt(6, espacio.getIdEspacio());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEspacio(int idEspacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Espacios WHERE id_Espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idEspacio);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Espacio> getEspacios() {
        List<Espacio> espacios = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Espacios";
            ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);
            while (resultSet.next()) {
                int id_Espacio = resultSet.getInt("id_Espacio");
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int capacidadMaxima = resultSet.getInt("capacidad_Maxima");
                int capacidadActual = resultSet.getInt("capacidad_Actual");
                Timestamp horarioReserva = resultSet.getTimestamp("horario_Reserva");
                Espacio espacio = new Espacio(id_Espacio, nombre, descripcion, capacidadMaxima, capacidadActual, horarioReserva);
                espacios.add(espacio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacios;
    }

    public Espacio buscarEspacioPorId(int idEspacio) {
        Espacio espacio = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Espacios WHERE id_Espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idEspacio);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String descripcion = resultSet.getString("descripcion");
                int capacidadMaxima = resultSet.getInt("capacidad_Maxima");
                int capacidadActual = resultSet.getInt("capacidad_Actual");
                java.util.Date horarioReserva = resultSet.getTimestamp("horario_Reserva");
                espacio = new Espacio(nombre, descripcion, capacidadMaxima, capacidadActual, horarioReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacio;
    }
}
