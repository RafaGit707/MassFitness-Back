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
            String insertSQL = "INSERT INTO Espacios (tipo_Espacio, capacidad_Maxima, capacidad_Actual, horario_Reserva_Espacio) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, espacio.getTipoEspacio());
            preparedStatement.setInt(2, espacio.getCapacidadMaxima());
            preparedStatement.setInt(3, espacio.getCapacidadActual());
            preparedStatement.setTimestamp(4, new Timestamp(espacio.getHorarioReserva().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEspacio(Espacio espacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Espacios SET tipo_Espacio = ?, capacidad_Maxima = ?, capacidad_Actual = ?, horario_Reserva_Espacio = ? WHERE id_Espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, espacio.getTipoEspacio());
            preparedStatement.setInt(2, espacio.getCapacidadMaxima());
            preparedStatement.setInt(3, espacio.getCapacidadActual());
            preparedStatement.setTimestamp(4, new Timestamp(espacio.getHorarioReserva().getTime()));
            preparedStatement.setInt(5, espacio.getIdEspacio());
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
                int idEspacio = resultSet.getInt("id_Espacio");
                String tipoEspacio = resultSet.getString("tipo_Espacio");
                int capacidadMaxima = resultSet.getInt("capacidad_Maxima");
                int capacidadActual = resultSet.getInt("capacidad_Actual");
                Timestamp horarioReserva = resultSet.getTimestamp("horario_Reserva_Espacio");
                Espacio espacio = new Espacio(idEspacio, tipoEspacio, capacidadMaxima, capacidadActual, horarioReserva);
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
                String tipoEspacio = resultSet.getString("tipo_Espacio");
                int capacidadMaxima = resultSet.getInt("capacidad_Maxima");
                int capacidadActual = resultSet.getInt("capacidad_Actual");
                java.util.Date horarioReserva = resultSet.getTimestamp("horario_Reserva_Espacio");
                espacio = new Espacio(idEspacio, tipoEspacio, capacidadMaxima, capacidadActual, horarioReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacio;
    }
}
