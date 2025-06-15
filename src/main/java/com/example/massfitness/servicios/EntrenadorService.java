package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Entrenador;
import com.example.massfitness.servicios.impl.IEntrenadorService;
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
public class EntrenadorService implements IEntrenadorService {

    private final AccesoBD accesoBD;
    @Autowired
    public EntrenadorService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }
/*    public Entrenador addEntrenador(Entrenador entrenador) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Entrenadores (nombre_Entrenador, especializacion) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, entrenador.getNombreEntrenador());
            preparedStatement.setString(2, entrenador.getEspecializacion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entrenador;
    }*/

    @Override
    public Entrenador addEntrenador(Entrenador entrenador) {
        // La sentencia INSERT ahora pide que se devuelva el ID generado
        String insertSQL = "INSERT INTO Entrenadores (nombre_entrenador, especializacion) VALUES (?, ?) RETURNING id_entrenador";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {

            ps.setString(1, entrenador.getNombreEntrenador());
            ps.setString(2, entrenador.getEspecializacion());

            // Usamos executeQuery porque RETURNING devuelve un resultado
            ResultSet rs = ps.executeQuery();

            // Si la inserción fue exitosa y devolvió un ID...
            if (rs.next()) {
                // ...obtenemos ese ID y lo asignamos al objeto que vamos a devolver.
                int nuevoId = rs.getInt(1);
                entrenador.setIdEntrenador(nuevoId);
                return entrenador;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Si algo falla, devolvemos null
        return null;
    }

    @Override
    public Entrenador actualizarEntrenador(Entrenador entrenador) {
        String sql = "UPDATE Entrenadores SET nombre_entrenador = ?, especializacion = ? WHERE id_entrenador = ?";
        try (Connection conn = accesoBD.conectarPostgreSQL(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, entrenador.getNombreEntrenador());
            ps.setString(2, entrenador.getEspecializacion());
            ps.setInt(3, entrenador.getIdEntrenador());
            int filasAfectadas = ps.executeUpdate();
            return (filasAfectadas > 0) ? entrenador : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void eliminarEntrenador(int idEntrenador) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Entrenadores WHERE id_Entrenador = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idEntrenador);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Entrenador> getEntrenadores() {
        List<Entrenador> entrenadores = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Entrenadores";
            ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);
            while (resultSet.next()) {
                int idEntrenador = resultSet.getInt("id_Entrenador");
                String nombre = resultSet.getString("nombre_Entrenador");
                String especializacion = resultSet.getString("especializacion");
                Entrenador entrenador = new Entrenador(idEntrenador, nombre, especializacion);
                entrenadores.add(entrenador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entrenadores;
    }

    public Entrenador buscarEntrenadorPorId(int idEntrenador) {
        Entrenador entrenador = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Entrenadores WHERE id_Entrenador = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idEntrenador);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre_Entrenador");
                String especializacion = resultSet.getString("especializacion");
                entrenador = new Entrenador(idEntrenador, nombre, especializacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entrenador;
    }
}
