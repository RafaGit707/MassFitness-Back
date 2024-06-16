package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Logro;
import com.example.massfitness.servicios.impl.ILogroService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String insertSQL = "INSERT INTO Logros (nombre_Logro, descripcion, requisitos_Puntos, recompensa) VALUES (?, ?, ?, ?)";
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
            String updateSQL = "UPDATE Logros SET nombre_Logro = ?, descripcion = ?, requisitos_Puntos = ?, recompensa = ? WHERE id_Logro = ?";
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
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Logros";
            ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);
            while (resultSet.next()) {
                int idLogro = resultSet.getInt("id_Logro");
                String nombre = resultSet.getString("nombre_Logro");
                String descripcion = resultSet.getString("descripcion");
                int requisitosPuntos = resultSet.getInt("requisitos_Puntos");
                String recompensa = resultSet.getString("recompensa");
                Logro logro = new Logro(idLogro, nombre, descripcion, requisitosPuntos, recompensa);
                logros.add(logro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logros;
    }

    public Logro buscarLogroPorId(int idLogro) {
        Logro logro = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Logros WHERE id_Logro = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idLogro);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre_Logro");
                String descripcion = resultSet.getString("descripcion");
                int requisitosPuntos = resultSet.getInt("requisitos_Puntos");
                String recompensa = resultSet.getString("recompensa");
                logro = new Logro(idLogro, nombre, descripcion, requisitosPuntos, recompensa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logro;
    }
}
