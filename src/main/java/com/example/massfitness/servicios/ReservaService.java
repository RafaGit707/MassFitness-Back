package com.example.massfitness.servicios;

import com.example.massfitness.entidades.DatosPersonales;
import com.example.massfitness.entidades.Espacio;
import com.example.massfitness.entidades.Reserva;
import com.example.massfitness.entidades.Usuario;
import com.example.massfitness.servicios.impl.IReservaService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {
    private final AccesoBD accesoBD;

    @Autowired
    public ReservaService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    @Override
    public List<Reserva> getReservas() {
        List<Reserva> reservas = new ArrayList<>();
        String selectSQL = "SELECT * FROM reservas";
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int idReserva = resultSet.getInt("id_reserva");
                int idUsuario = resultSet.getInt("usuario_id");
                int idEspacio = resultSet.getInt("espacio_id");
                String tipoReserva = resultSet.getString("tipo_reserva");
                Timestamp horarioReserva = resultSet.getTimestamp("horario_reserva");
                String estadoReserva = resultSet.getString("estado_reserva");

                Reserva reserva = new Reserva(idReserva, new Usuario(idUsuario), new Espacio(idEspacio), tipoReserva, horarioReserva, estadoReserva);
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }
    public int addReserva(Reserva reserva) {
        String insertSQLEspacios = "INSERT INTO espacios (capacidad_maxima, nombre, entrenador_id) VALUES (?, ?, ?) RETURNING id_espacio";
        String selectCapacitySQL = "SELECT capacidad_actual, capacidad_maxima FROM espacio_horario eh INNER JOIN espacios e ON eh.espacio_id = e.id_espacio WHERE eh.espacio_id = ? AND eh.horario_reserva = ?";
        String insertEspacioHorarioSQL = "INSERT INTO espacio_horario (espacio_id, horario_reserva, capacidad_actual) VALUES (?, ?, ?)";
        String updateCapacitySQL = "UPDATE espacio_horario SET capacidad_actual = capacidad_actual + 1 WHERE espacio_id = ? AND horario_reserva = ?";
        String insertSQL = "INSERT INTO reservas (usuario_id, espacio_id, tipo_reserva, horario_reserva, estado_reserva) VALUES (?, ?, ?, ?, ?) RETURNING id_reserva";

        try (Connection connection = accesoBD.conectarPostgreSQL()) {

            int espacioId;
            try (PreparedStatement preparedStatementEspacios = connection.prepareStatement(insertSQLEspacios);) {
                preparedStatementEspacios.setInt(1, 1);
                preparedStatementEspacios.setString(2, reserva.getTipoReserva());
                preparedStatementEspacios.setInt(3, 0);
                ResultSet rs = preparedStatementEspacios.executeQuery();

                if (rs.next()) {
                    espacioId = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de Espacios.");
                }
            }

            // Verificar capacidad actual
            try (PreparedStatement selectCapacityStmt = connection.prepareStatement(selectCapacitySQL)) {
                Timestamp timestamp = Timestamp.valueOf(reserva.getHorarioReserva().getTime() + ":00"); // Asegura el formato correcto
                selectCapacityStmt.setInt(1, 1);
                selectCapacityStmt.setTimestamp(2, timestamp);
                ResultSet rs = selectCapacityStmt.executeQuery();
                if (rs.next()) {
                    int capacidadActual = rs.getInt("capacidad_actual");
                    int capacidadMaxima = rs.getInt("capacidad_maxima");
                    if (capacidadActual >= capacidadMaxima) {
                        throw new RuntimeException("La capacidad máxima del espacio en ese horario se ha alcanzado.");
                    }
                } else {
                    // Insertar nuevo registro de capacidad para el espacio en el horario específico
                    try (PreparedStatement insertEspacioHorarioStmt = connection.prepareStatement(insertEspacioHorarioSQL)) {
                        insertEspacioHorarioStmt.setInt(1, espacioId);
                        insertEspacioHorarioStmt.setTimestamp(2, timestamp);
                        insertEspacioHorarioStmt.setInt(3, 0); // Capacidad actual inicial
                        insertEspacioHorarioStmt.executeUpdate();
                    }
                }
            }
            // Verificar capacidad actual
            int capacidadActual = 0;
            int capacidadMaxima = 0;
            boolean espacioHorarioExistente = false;

            try (PreparedStatement selectCapacityStmt = connection.prepareStatement(selectCapacitySQL)) {
                selectCapacityStmt.setInt(1, espacioId);
                selectCapacityStmt.setTimestamp(2, Timestamp.valueOf(reserva.getHorarioReserva().getTime() + ":00"));
                ResultSet rs = selectCapacityStmt.executeQuery();
                if (rs.next()) {
                    capacidadActual = rs.getInt("capacidad_actual");
                    capacidadMaxima = rs.getInt("capacidad_maxima");
                    espacioHorarioExistente = true;
                }
            }

            if (!espacioHorarioExistente) {
                // Insertar nuevo registro de espacio_horario
                try (PreparedStatement insertEspacioHorarioStmt = connection.prepareStatement(insertEspacioHorarioSQL)) {
                    insertEspacioHorarioStmt.setInt(1, espacioId);
                    insertEspacioHorarioStmt.setTimestamp(2, Timestamp.valueOf(reserva.getHorarioReserva().getTime() + ":00"));
                    insertEspacioHorarioStmt.setInt(3, 0); // Capacidad actual inicial
                    insertEspacioHorarioStmt.executeUpdate();
                }
            } else if (capacidadActual >= capacidadMaxima) {
                throw new RuntimeException("La capacidad máxima del espacio en ese horario se ha alcanzado.");
            }

            // Insertar reserva
            int idReserva;
            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                insertStmt.setInt(1, 1);
                insertStmt.setInt(2, espacioId);
                insertStmt.setString(3, reserva.getTipoReserva());
                insertStmt.setTimestamp(4, Timestamp.valueOf(reserva.getHorarioReserva().getTime() + ":00"));
                insertStmt.setString(5, reserva.getEstadoReserva());
                ResultSet rs = insertStmt.executeQuery();
                if (rs.next()) {
                    idReserva = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la Reserva.");
                }
            }

            // Actualizar capacidad actual
            try (PreparedStatement updateCapacityStmt = connection.prepareStatement(updateCapacitySQL)) {
                updateCapacityStmt.setInt(1, espacioId);
                updateCapacityStmt.setTimestamp(2, Timestamp.valueOf(reserva.getHorarioReserva().getTime() + ":00"));
                updateCapacityStmt.executeUpdate();
            }

            connection.commit();
            return idReserva;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar Reserva", e);
        }
    }

//    public int addReserva(Reserva reserva) {
//        if (reserva == null) {
//            throw new IllegalArgumentException("Reserva cannot be null");
//        }
//        if (reserva.getUsuario() == null) {
//            throw new IllegalArgumentException("Usuario cannot be null");
//        }
//
//        String insertSQLEspacios = "INSERT INTO espacios (capacidad_maxima, nombre) VALUES (?, ?) RETURNING id_espacio";
//        String selectCapacitySQL = "SELECT capacidad_actual FROM espacio_horario WHERE espacio_id = ? AND horario_reserva = ?";
//        String selectMaxCapacitySQL = "SELECT capacidad_maxima FROM espacios WHERE id_espacio = ?";
//        String insertEspacioHorarioSQL = "INSERT INTO espacio_horario (espacio_id, horario_reserva, capacidad_actual) VALUES (?, ?, ?)";
//        String updateCapacitySQL = "UPDATE espacio_horario SET capacidad_actual = capacidad_actual + 1 WHERE espacio_id = ? AND horario_reserva = ?";
//        String insertSQL = "INSERT INTO reservas (usuario_id, espacio_id, tipo_reserva, horario_reserva, estado_reserva) VALUES (?, ?, ?, ?, ?) RETURNING id_reserva";
//
//        try (Connection connection = accesoBD.conectarPostgreSQL()) {
//            connection.setAutoCommit(false);
//            int espacioId;
//
//            // Insertar o seleccionar espacio
//            try (PreparedStatement preparedStatementEspacios = connection.prepareStatement(insertSQLEspacios)) {
//                preparedStatementEspacios.setInt(1, 1);  // Capacidad máxima predeterminada
//                preparedStatementEspacios.setString(2, reserva.getTipoReserva());
//                ResultSet rs = preparedStatementEspacios.executeQuery();
//
//                if (rs.next()) {
//                    espacioId = rs.getInt(1);
//                } else {
//                    throw new SQLException("No se pudo obtener el ID de Espacios.");
//                }
//            }
//
//            // Verificar capacidad máxima
//            int capacidadMaxima;
//            try (PreparedStatement selectMaxCapacityStmt = connection.prepareStatement(selectMaxCapacitySQL)) {
//                selectMaxCapacityStmt.setInt(1, espacioId);
//                ResultSet rs = selectMaxCapacityStmt.executeQuery();
//                if (rs.next()) {
//                    capacidadMaxima = rs.getInt("capacidad_maxima");
//                } else {
//                    throw new SQLException("No se pudo obtener la capacidad máxima del espacio.");
//                }
//            }
//
//            // Verificar capacidad actual y agregar espacio_horario si no existe
//            int capacidadActual = 0;
//            boolean espacioHorarioExistente = false;
//            try (PreparedStatement selectCapacityStmt = connection.prepareStatement(selectCapacitySQL)) {
//                selectCapacityStmt.setInt(1, espacioId);
//                selectCapacityStmt.setTimestamp(2, new Timestamp(reserva.getHorarioReserva().getTime()));
//                ResultSet rs = selectCapacityStmt.executeQuery();
//                if (rs.next()) {
//                    capacidadActual = rs.getInt("capacidad_actual");
//                    espacioHorarioExistente = true;
//                }
//            }
//
//            if (!espacioHorarioExistente) {
//                // Insertar nuevo registro de espacio_horario
//                try (PreparedStatement insertEspacioHorarioStmt = connection.prepareStatement(insertEspacioHorarioSQL)) {
//                    insertEspacioHorarioStmt.setInt(1, espacioId);
//                    insertEspacioHorarioStmt.setTimestamp(2, new Timestamp(reserva.getHorarioReserva().getTime()));
//                    insertEspacioHorarioStmt.setInt(3, 0); // Capacidad actual inicial
//                    insertEspacioHorarioStmt.executeUpdate();
//                }
//            } else if (capacidadActual >= capacidadMaxima) {
//                throw new RuntimeException("La capacidad máxima del espacio en ese horario se ha alcanzado.");
//            }
//
//            // Insertar reserva
//            int idReserva;
//            try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
//                insertStmt.setInt(1, reserva.getUsuario().getIdUsuario());
//                insertStmt.setInt(2, espacioId);
//                insertStmt.setString(3, reserva.getTipoReserva());
//                insertStmt.setTimestamp(4, new Timestamp(reserva.getHorarioReserva().getTime()));
//                insertStmt.setString(5, reserva.getEstadoReserva());
//                ResultSet rs = insertStmt.executeQuery();
//                if (rs.next()) {
//                    idReserva = rs.getInt(1);
//                } else {
//                    throw new SQLException("No se pudo obtener el ID de la Reserva.");
//                }
//            }
//
//            // Actualizar capacidad actual
//            try (PreparedStatement updateCapacityStmt = connection.prepareStatement(updateCapacitySQL)) {
//                updateCapacityStmt.setInt(1, espacioId);
//                updateCapacityStmt.setTimestamp(2, new Timestamp(reserva.getHorarioReserva().getTime()));
//                updateCapacityStmt.executeUpdate();
//            }
//
//            connection.commit();
//            return idReserva;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error al agregar Reserva", e);
//        }
//    }

    public void actualizarReserva(Reserva reserva) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE reservas SET usuario_id = ?, espacio_id = ?, tipo_reserva = ?, horario_reserva = ?, estado_reserva = ? WHERE id_reserva = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, reserva.getUsuario().getIdUsuario());
            preparedStatement.setInt(2, reserva.getEspacio().getIdEspacio());
            preparedStatement.setString(3, reserva.getTipoReserva());
            preparedStatement.setTimestamp(4, new Timestamp(reserva.getHorarioReserva().getTime()));
            preparedStatement.setString(5, reserva.getEstadoReserva());
            preparedStatement.setInt(6, reserva.getIdReserva());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarReserva(int idReserva) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM reservas WHERE id_reserva = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idReserva);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reserva buscarReservaPorId(int idReserva) {
        Reserva reserva = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM reservas WHERE id_reserva = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idReserva);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idUsuario = resultSet.getInt("usuario_id");
                int idEspacio = resultSet.getInt("espacio_id");
                String tipoReserva = resultSet.getString("tipo_reserva");
                Timestamp horarioReserva = resultSet.getTimestamp("horario_reserva");
                String estadoReserva = resultSet.getString("estado_reserva");
                reserva = new Reserva(idReserva, new Usuario(idUsuario), new Espacio(idEspacio), tipoReserva, horarioReserva, estadoReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }
}
