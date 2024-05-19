package com.example.massfitness.servicios;

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

    public List<Reserva> getReservas() {
        List<Reserva> reservas = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Reservas";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int idReserva = resultSet.getInt("id_Reserva");
                int idUsuario = resultSet.getInt("usuario_id");
                String tipoReserva = resultSet.getString("tipo_Reserva");
                Timestamp timestamp = resultSet.getTimestamp("horario_Reserva");
                Date horarioReserva = new Date(timestamp.getTime());
                String estadoReserva = resultSet.getString("estado_Reserva");
                Usuario usuario = new Usuario(idUsuario);
                Reserva reserva = new Reserva(idReserva, usuario, tipoReserva, horarioReserva, estadoReserva);
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public void addReserva(Reserva reserva) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Reservas (usuario_id, tipo_Reserva, horario_Reserva, estado_Reserva) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, reserva.getUsuario().getIdUsuario());
            preparedStatement.setString(2, reserva.getTipoReserva());
            preparedStatement.setTimestamp(3, new Timestamp(reserva.getHorarioReserva().getTime()));
            preparedStatement.setString(4, reserva.getEstadoReserva());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarReserva(Reserva reserva) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Reservas SET usuario_id = ?, tipo_Reserva = ?, horario_Reserva = ?, estado_Reserva = ? WHERE id_Reserva = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, reserva.getUsuario().getIdUsuario());
            preparedStatement.setString(2, reserva.getTipoReserva());
            preparedStatement.setTimestamp(3, new Timestamp(reserva.getHorarioReserva().getTime()));
            preparedStatement.setString(4, reserva.getEstadoReserva());
            preparedStatement.setInt(5, reserva.getIdReserva());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarReserva(int idReserva) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Reservas WHERE id_Reserva = ?";
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
            String selectSQL = "SELECT * FROM Reservas WHERE id_Reserva = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idReserva);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idUsuario = resultSet.getInt("usuario_id");
                String tipoReserva = resultSet.getString("tipo_Reserva");
                Timestamp timestamp = resultSet.getTimestamp("horario_Reserva");
                Date horarioReserva = new Date(timestamp.getTime());
                String estadoReserva = resultSet.getString("estado_Reserva");
                reserva = new Reserva(idReserva, new Usuario(idUsuario), tipoReserva, horarioReserva, estadoReserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reserva;
    }
}
