package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Reserva;
import com.example.massfitness.servicios.impl.IEspacioHorarioService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

@Service
public class EspacioHorarioService implements IEspacioHorarioService {

    private final AccesoBD accesoBD;

    @Autowired
    public EspacioHorarioService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    @Override
    public int obtenerCapacidadActual(String salaNombre, String horarioReserva) {
        String query = "SELECT eh.capacidad_actual FROM espacio_horario eh INNER JOIN espacios e ON eh.espacio_id = e.id_espacio WHERE e.nombre = ? AND eh.horario_reserva = ?";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, salaNombre);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(horarioReserva));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("capacidad_actual");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int obtenerCapacidadMaxima(String salaNombre) {
        String query = "SELECT capacidad_maxima FROM espacios WHERE nombre = ?";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, salaNombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("capacidad_maxima");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}