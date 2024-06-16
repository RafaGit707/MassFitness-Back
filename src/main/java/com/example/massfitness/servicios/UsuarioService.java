package com.example.massfitness.servicios;

import com.example.massfitness.repositories.UsuarioRepository;
import com.example.massfitness.entidades.DatosPersonales;
import com.example.massfitness.entidades.Usuario;
import com.example.massfitness.servicios.impl.IUsuarioService;
import com.example.massfitness.util.AccesoBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    private final AccesoBD accesoBD;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    @Autowired
    public UsuarioService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }
    @Override
    public int addUsuario(Usuario usuario) {
        logger.info("Agregando nuevo usuario a la base de datos: {}", usuario);
        String insertDatosPersonalesSQL = "INSERT INTO datos_personales (edad, genero) VALUES (?, ?) RETURNING id_datos_personales";
        String insertUsuarioSQL = "INSERT INTO usuarios (nombre, correo_electronico, contrasena, datos_personales_id, progreso_fitness, cantidad_puntos) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";

        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            int datosPersonalesId;
            try (PreparedStatement preparedStatementDatosPersonales = connection.prepareStatement(insertDatosPersonalesSQL)) {
                preparedStatementDatosPersonales.setInt(1, 0);
                preparedStatementDatosPersonales.setString(2, "");
                ResultSet rs = preparedStatementDatosPersonales.executeQuery();

                if (rs.next()) {
                    datosPersonalesId = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de DatosPersonales.");
                }
            }

            int usuarioId;
            try (PreparedStatement preparedStatementUsuario = connection.prepareStatement(insertUsuarioSQL)) {
                preparedStatementUsuario.setString(1, usuario.getNombre());
                preparedStatementUsuario.setString(2, usuario.getCorreo_electronico());
                preparedStatementUsuario.setString(3, usuario.getContrasena());
                preparedStatementUsuario.setInt(4, datosPersonalesId);
                preparedStatementUsuario.setInt(5, usuario.getProgresoFitness());
                preparedStatementUsuario.setInt(6, usuario.getCantidadPuntos());
                ResultSet rs = preparedStatementUsuario.executeQuery();

                if (rs.next()) {
                    usuarioId = rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del Usuario.");
                }
            }
            return usuarioId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar usuario", e);
        }
    }

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String selectSQL = "SELECT * FROM Usuarios";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id_usuario");
                String nombre = resultSet.getString("nombre");
                String correo_electronico = resultSet.getString("correo_electronico");
                String contrasena = resultSet.getString("contrasena");
                int datos_personales_id = resultSet.getInt("datos_personales_id");
                int progresoFitness = resultSet.getInt("progreso_fitness");
                int cantidadPuntos = resultSet.getInt("cantidad_puntos");

                Usuario usuario = new Usuario(id, nombre, correo_electronico, contrasena, progresoFitness, cantidadPuntos, new DatosPersonales(datos_personales_id), new HashSet<>(), new ArrayList<>());
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    @Override
    public void actualizarUsuario(Usuario usuario) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Usuarios SET nombre = ?, correo_electronico = ?, contrasena = ?, edad = ?, genero = ?, progreso_fitness = ?, cantidad_puntos = ? WHERE id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreo_electronico());
            preparedStatement.setString(3, usuario.getContrasena());
            preparedStatement.setInt(4, usuario.getDatosPersonales().getEdad());
            preparedStatement.setString(5, usuario.getDatosPersonales().getGenero());
            preparedStatement.setInt(6, usuario.getProgresoFitness());
            preparedStatement.setInt(7, usuario.getCantidadPuntos());
            preparedStatement.setInt(8, usuario.getIdUsuario());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void eliminarUsuario(int idUsuario) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Usuario buscarUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String correo_electronico = resultSet.getString("correo_electronico");
                String contrasena = resultSet.getString("contrasena");
                int datos_personales_id = resultSet.getInt("datos_personales_id");
                int progresoFitness = resultSet.getInt("progreso_fitness");
                int cantidadPuntos = resultSet.getInt("cantidad_puntos");
                usuario = new Usuario(nombre, correo_electronico, contrasena, progresoFitness, cantidadPuntos, new DatosPersonales(datos_personales_id), new HashSet<>(), new ArrayList<>());            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    @Override
    public Usuario buscarUsuarioPorCorreo(String correo_electronico) {
        Usuario usuario = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Usuarios WHERE correo_electronico = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, correo_electronico);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idUsuario = resultSet.getInt("id_usuario");
                String nombre = resultSet.getString("nombre");
                String contrasena = resultSet.getString("contrasena");
                int datos_personales_id = resultSet.getInt("datos_personales_id");
                int progresoFitness = resultSet.getInt("progreso_fitness");
                int cantidadPuntos = resultSet.getInt("cantidad_puntos");
                usuario = new Usuario(idUsuario, nombre, correo_electronico, contrasena, progresoFitness, cantidadPuntos, new DatosPersonales(datos_personales_id), new HashSet<>(), new ArrayList<>());            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
    @Override
    public boolean usuarioExiste(String correo_electronico, String contrasena) {
        boolean existe = false;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT COUNT(*) AS count FROM Usuarios WHERE correo_electronico = ? AND contrasena = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, correo_electronico);
            preparedStatement.setString(2, contrasena);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                existe = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
    @Override
    public int getProgresoFitnessUsuario(int idUsuario) {
        int progresoFitness = 0;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT progreso_fitness FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                progresoFitness = resultSet.getInt("progreso_fitness");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progresoFitness;
    }
    @Override
    public void actualizarProgresoFitnessUsuario(int idUsuario, int nuevoProgresoFitness) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Usuarios SET progreso_fitness = ? WHERE id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, nuevoProgresoFitness);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getCantidadPuntosUsuario(int idUsuario) {
        int cantidadPuntos = 0;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT cantidad_puntos FROM Usuarios WHERE id_usuario = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cantidadPuntos = resultSet.getInt("cantidad_puntos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidadPuntos;
    }
    @Override
    public void actualizarCantidadPuntosUsuario(int idUsuario, int nuevaCantidadPuntos) {
        String updateSQL = "UPDATE Usuarios SET cantidad_puntos = ? WHERE id_usuario = ?";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, nuevaCantidadPuntos);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar cantidad de puntos de usuario", e);
        }
    }
}
