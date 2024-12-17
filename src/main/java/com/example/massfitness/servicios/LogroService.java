package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.entidades.UsuarioLogro;
import com.example.massfitness.servicios.impl.ILogroService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogroService implements ILogroService {

    private final AccesoBD accesoBD;
    @Autowired
    public LogroService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }
    public void addLogro(Logro logro) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Logros (nombre_logro, descripcion, requisitos_puntos, recompensa) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, logro.getNombreLogro());
            preparedStatement.setString(2, logro.getDescripcion());
            preparedStatement.setInt(3, logro.getRequisitosPuntos());
            preparedStatement.setString(4, logro.getRecompensa());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarLogro(Logro logro) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Logros SET nombre_logro = ?, descripcion = ?, requisitos_puntos = ?, recompensa = ? WHERE id_Logro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, logro.getNombreLogro());
            preparedStatement.setString(2, logro.getDescripcion());
            preparedStatement.setInt(3, logro.getRequisitosPuntos());
            preparedStatement.setString(4, logro.getRecompensa());
            preparedStatement.setInt(5, logro.getIdLogro());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarLogro(int idLogro) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Logros WHERE id_Logro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idLogro);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Logro> getLogros() {
        List<Logro> logros = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Logros")) {

            while (resultSet.next()) {
                int idLogro = resultSet.getInt("id_Logro");
                String nombre = resultSet.getString("nombre_logro");
                String descripcion = resultSet.getString("descripcion");
                int requisitosPuntos = resultSet.getInt("requisitos_puntos");
                String recompensa = resultSet.getString("recompensa");

                Logro logro = new Logro(idLogro, nombre, descripcion, requisitosPuntos, recompensa);
                logros.add(logro);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los logros: " + e.getMessage());
            e.printStackTrace();
        }
        return logros;
    }

/*    public Logro buscarLogroPorId(int idLogro) {
        Logro logro = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Logros WHERE id_Logro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idLogro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre_logro");
                String descripcion = resultSet.getString("descripcion");
                int requisitosPuntos = resultSet.getInt("requisitos_puntos");
                String recompensa = resultSet.getString("recompensa");
                logro = new Logro(idLogro, nombre, descripcion, requisitosPuntos, recompensa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logro;
    }*/

    public List<UsuarioLogro> getLogrosByUserId(int idUsuario) {
        List<UsuarioLogro> logros = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            // Consulta para obtener los logros del usuario específico
            String selectSQL = "SELECT id_usuario_logro, logro_id, fecha_obtenido " +
                    "FROM usuario_logro WHERE usuario_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idUsuarioLogro = resultSet.getInt("id_usuario_logro");
                int logro_id = resultSet.getInt("logro_id");
                Timestamp fecha_obtenido = Timestamp.valueOf(resultSet.getString("fecha_obtenido"));
                UsuarioLogro usuarioLogro = new UsuarioLogro(idUsuarioLogro, logro_id, fecha_obtenido);
                logros.add(usuarioLogro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logros;
    }
}
