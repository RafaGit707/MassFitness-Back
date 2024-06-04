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
                Timestamp timestamp = resultSet.getTimestamp("horario_reserva");
                Date horarioReserva = new Date(timestamp.getTime());
                String estadoReserva = resultSet.getString("estado_reserva");

                Reserva reserva = new Reserva(idReserva, new Usuario(idUsuario), new Espacio(idEspacio), tipoReserva, horarioReserva, estadoReserva);
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    @Override
    public int addReserva(Reserva reserva) {
        String insertSQL = "INSERT INTO reservas (usuario_id, espacio_id, tipo_reserva, horario_reserva, estado_reserva) VALUES (?, ?, ?, ?, ?) RETURNING id_reserva";
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            int idReserva;
            Usuario usuario = new Usuario();
            Espacio espacio = new Espacio();
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setInt(1, usuario.getIdUsuario());
                preparedStatement.setInt(2, espacio.getIdEspacio());
                preparedStatement.setString(3, reserva.getTipoReserva());
                preparedStatement.setTimestamp(4, new Timestamp(reserva.getHorarioReserva().getTime()));
                preparedStatement.setString(5, reserva.getEstadoReserva());
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    idReserva = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la Reserva.");
                }
            }
            return idReserva;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar Reserva", e);
        }
    }


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
                Timestamp timestamp = resultSet.getTimestamp("horario_reserva");
                Date horarioReserva = new Date(timestamp.getTime());
                String estadoReserva = resultSet.getString("estado_reserva");
                reserva = new Reserva(idReserva, new Usuario(idUsuario), new Espacio(idEspacio), tipoReserva, horarioReserva, estadoReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }
}
