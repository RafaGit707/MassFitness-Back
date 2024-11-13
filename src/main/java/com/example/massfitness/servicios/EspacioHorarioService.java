package com.example.massfitness.servicios;

import com.example.massfitness.servicios.impl.IEspacioHorarioService;
import com.example.massfitness.util.AccesoBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class EspacioHorarioService implements IEspacioHorarioService {

    private final AccesoBD accesoBD;
    private static final Logger logger = LoggerFactory.getLogger(EspacioHorarioService.class);

    @Autowired
    public EspacioHorarioService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    @Override
    public int obtenerCapacidadActual(String salaNombre, Timestamp horarioReserva) {
        String query = "SELECT eh.capacidad_actual FROM espacio_horario eh INNER JOIN espacios e ON eh.espacio_id = e.id_espacio WHERE e.nombre = ? AND eh.horario_reserva = ?";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, salaNombre);
            preparedStatement.setTimestamp(2, horarioReserva);
            ResultSet resultSet = preparedStatement.executeQuery();

            logger.info("Query executed: {}, Parameters: [salaNombre={}, horarioReserva={}]", query, salaNombre, horarioReserva);

            if (resultSet.next()) {
                int capacidadActual = resultSet.getInt("capacidad_actual");
                logger.info("Capacidad Actual found: {}", capacidadActual);
                return capacidadActual;
            } else {
                logger.info("No records found for salaNombre={} and horarioReserva={}", salaNombre, horarioReserva);
                insertDefaultRecord(connection, salaNombre, horarioReserva);
                return 0;
            }
        } catch (SQLException e) {
            logger.error("Error executing query: {}", e.getMessage());
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

            logger.info("Query executed: {}, Parameters: [salaNombre={}]", query, salaNombre);

            if (resultSet.next()) {
                int capacidadMaxima = resultSet.getInt("capacidad_maxima");
                logger.info("Capacidad Maxima found: {}", capacidadMaxima);
                return capacidadMaxima;
            } else {
                logger.info("No records found for salaNombre={}", salaNombre);
                return getDefaultCapacidadMaxima(salaNombre);
            }
        } catch (SQLException e) {
            logger.error("Error executing query: {}", e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    private void insertDefaultRecord(Connection connection, String salaNombre, Timestamp horarioReserva) {
        int espacioId = obtenerCapacidad(salaNombre);
        int capacidadMaxima = getDefaultCapacidadMaxima(salaNombre);

        if (espacioId <= 0) {
            throw new IllegalArgumentException("El espacio con el nombre " + salaNombre + " no existe.");
        }

        String insertQuery = "INSERT INTO espacio_horario (espacio_id, horario_reserva, capacidad_actual) VALUES (?, ?, 0)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, espacioId);
            preparedStatement.setTimestamp(2, horarioReserva);
            preparedStatement.executeUpdate();
            logger.info("Inserted default record for salaNombre={} and horarioReserva={}", salaNombre, horarioReserva);
        } catch (SQLException e) {
            logger.error("Error inserting default record: {}", e.getMessage());
            e.printStackTrace();
        }
    }
    private int getDefaultCapacidadMaxima(String salaNombre) {
        switch (salaNombre) {
            case "Sala de Musculación":
                return 50;
            case "Sala de Abdominales":
                return 15;
            default:
                return 0;
        }
    }
    private int obtenerCapacidad(String tipoReserva) {
        switch (tipoReserva) {
            case "Sala de Musculación":
                return 3;
            case "Sala de Abdominales":
                return 4;
            default:
                return 0;
        }
    }
}
