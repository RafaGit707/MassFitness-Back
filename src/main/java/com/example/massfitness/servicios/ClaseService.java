package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Clases;
import com.example.massfitness.entidades.Entrenador;
import com.example.massfitness.servicios.impl.IClaseService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class ClaseService implements IClaseService {

    private final AccesoBD accesoBD;

    @Autowired
    public ClaseService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    public Clases addClase(Clases clase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Clases (nombre, capacidad_maxima, entrenador_id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insertSQL);
            ps.setString(1, clase.getNombre());
            ps.setInt(2, clase.getCapacidadMaxima());
            ps.setInt(3, clase.getEntrenador().getIdEntrenador());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }

/*    public void actualizarClase(Clases clase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Clases SET nombre = ?, capacidad_maxima = ?, entrenador_id = ? WHERE id_clase = ?";
            PreparedStatement ps = connection.prepareStatement(updateSQL);
            ps.setString(1, clase.getNombre());
            ps.setInt(2, clase.getCapacidadMaxima());
            ps.setInt(3, clase.getEntrenador().getIdEntrenador());
            ps.setInt(4, clase.getIdClase());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
/*@Override
public boolean actualizarClase(int idClase, String nombre, int capacidadMaxima, String entrenadorIdStr) throws SQLException {
    String updateSQL = "UPDATE Clases SET nombre = ?, capacidad_maxima = ?, entrenador_id = ? WHERE id_clase = ?";
    Integer entrenadorIdParaDb = null; // Usamos Integer para poder representar NULL

    // Intentamos convertir el ID del entrenador si se proporcionó y no está vacío
    if (StringUtils.hasText(entrenadorIdStr)) { // Si no es null ni vacío ""
        try {
            entrenadorIdParaDb = Integer.parseInt(entrenadorIdStr);
            // Opcional: Si quieres que ID 0 signifique NULL en la BD
            // if (entrenadorIdParaDb == 0) {
            //    entrenadorIdParaDb = null;
            // }
        } catch (NumberFormatException e) {
            // El ID recibido no era un número válido. Lanza la excepción
            // para que el controlador devuelva BadRequest.
            System.err.println("Error: entrenador_id inválido: " + entrenadorIdStr);
            throw e; // Re-lanza para que el controlador la capture
        }
    }
    // Si entrenadorIdStr era null o vacío, entrenadorIdParaDb sigue siendo null.

    try (Connection connection = accesoBD.conectarPostgreSQL();
         PreparedStatement ps = connection.prepareStatement(updateSQL)) {

        ps.setString(1, nombre);
        ps.setInt(2, capacidadMaxima);

        // Usamos setObject para manejar correctamente el Integer null
        if (entrenadorIdParaDb != null) {
            ps.setInt(3, entrenadorIdParaDb);
        } else {
            ps.setNull(3, Types.INTEGER); // ¡Importante para enviar NULL a la BD!
        }

        ps.setInt(4, idClase); // El ID de la clase a actualizar

        int filasAfectadas = ps.executeUpdate();
        return filasAfectadas > 0; // Devuelve true si se actualizó al menos una fila

    } catch (SQLException e) {
        System.err.println("Error SQL en actualizarClaseDesdeParams: " + e.getMessage());
        e.printStackTrace(); // Loguea el error
        throw e; // Re-lanza para que el controlador sepa que hubo un error SQL
    }
}*/
@Override
public Clases actualizarClase(Clases clase) {
    String sql = "UPDATE Clases SET nombre = ?, capacidad_maxima = ?, entrenador_id = ? WHERE id_clase = ?";
    try (Connection conn = accesoBD.conectarPostgreSQL(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, clase.getNombre());
        ps.setInt(2, clase.getCapacidadMaxima());
        if (clase.getEntrenador() != null && clase.getEntrenador().getIdEntrenador() > 0) {
            ps.setInt(3, clase.getEntrenador().getIdEntrenador());
        } else {
            ps.setNull(3, java.sql.Types.INTEGER);
        }
        ps.setInt(4, clase.getIdClase());
        int filasAfectadas = ps.executeUpdate();
        return (filasAfectadas > 0) ? clase : null;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}
    public void eliminarClase(int idClase) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Clases WHERE id_clase = ?";
            PreparedStatement ps = connection.prepareStatement(deleteSQL);
            ps.setInt(1, idClase);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Clases> getClases() {
        List<Clases> clases = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            /*String selectSQL = "SELECT c.*, e.nombre_entrenador, e.especializacion FROM Clases c JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador";*/
            String selectSQL = """
            SELECT 
                c.id_clase,
                c.nombre,
                c.capacidad_maxima,
                c.entrenador_id AS id_entrenador,
                e.nombre_entrenador,
                e.especializacion
            FROM Clases c 
            JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador
        """;
            ResultSet rs = connection.createStatement().executeQuery(selectSQL);
            while (rs.next()) {
                int idClase = rs.getInt("id_clase");
                String nombre = rs.getString("nombre");
                int capacidad = rs.getInt("capacidad_maxima");

                Entrenador entrenador = new Entrenador(
                        rs.getInt("id_entrenador"),
                        rs.getString("nombre_entrenador"),
                        rs.getString("especializacion")
                );

                Clases clase = new Clases(idClase, nombre, capacidad, entrenador);
                clases.add(clase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }

   /* @Override
    public List<Clases> getClases() {
        List<Clases> clases = new ArrayList<>();
        // Usamos LEFT JOIN para incluir clases aunque no tengan entrenador asignado
        String selectSQL = """
        SELECT
            c.id_clase, c.nombre, c.capacidad_maxima,
            e.id_entrenador, e.nombre_entrenador, e.especializacion
        FROM Clases c
        LEFT JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador
        ORDER BY c.nombre
    """;
        try (Connection connection = accesoBD.conectarPostgreSQL();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                Clases clase = new Clases();
                clase.setIdClase(rs.getInt("id_clase"));
                clase.setNombre(rs.getString("nombre"));
                clase.setCapacidadMaxima(rs.getInt("capacidad_maxima"));

                int entrenadorId = rs.getInt("id_entrenador");
                // Si el ID del entrenador no es NULL (es decir, el JOIN encontró una coincidencia)
                if (!rs.wasNull()) {
                    Entrenador entrenador = new Entrenador(
                            entrenadorId,
                            rs.getString("nombre_entrenador"),
                            rs.getString("especializacion")
                    );
                    clase.setEntrenador(entrenador);
                }
                clases.add(clase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clases;
    }*/

    public Clases buscarClasePorId(int idClase) {
        Clases clase = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT c.*, e.nombre_entrenador, e.especializacion FROM Clases c JOIN Entrenadores e ON c.entrenador_id = e.id_entrenador WHERE id_clase = ?";
            PreparedStatement ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, idClase);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                int capacidad = rs.getInt("capacidad_maxima");
                Entrenador entrenador = new Entrenador(
                        rs.getInt("entrenador_id"),
                        rs.getString("nombre_entrenador"),
                        rs.getString("especializacion")
                );
                clase = new Clases(idClase, nombre, capacidad, entrenador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clase;
    }
}