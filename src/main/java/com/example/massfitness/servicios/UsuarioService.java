package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Rol;
import com.example.massfitness.repositories.UsuarioRepository;
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
        String insertUsuarioSQL = "INSERT INTO usuarios (nombre, correo_electronico, contrasena,  cantidad_puntos, rol) VALUES (?, ?, ?, ?, ?) RETURNING id_usuario";

        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            int usuarioId;
            try (PreparedStatement preparedStatementUsuario = connection.prepareStatement(insertUsuarioSQL)) {
                preparedStatementUsuario.setString(1, usuario.getNombre());
                preparedStatementUsuario.setString(2, usuario.getCorreo_electronico());
                preparedStatementUsuario.setString(3, usuario.getContrasena());
                preparedStatementUsuario.setInt(4, usuario.getCantidadPuntos());
                preparedStatementUsuario.setString(5, usuario.getRol().name() != null ? usuario.getRol().name() : "USUARIO");

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
                int cantidadPuntos = resultSet.getInt("cantidad_puntos");
                Rol rol = Rol.valueOf(resultSet.getString("rol"));

                Usuario usuario = new Usuario(id, nombre, correo_electronico, contrasena, cantidadPuntos, rol, new HashSet<>(), new HashSet<>());
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
            connection.setAutoCommit(false);

            String updateUsuariosSQL = "UPDATE Usuarios SET nombre = ?, correo_electronico = ?, contrasena = ?, cantidad_puntos = ?, rol = ? WHERE id_usuario = ?";

            try (PreparedStatement preparedStatementUsuarios = connection.prepareStatement(updateUsuariosSQL)) {
                preparedStatementUsuarios.setString(1, usuario.getNombre());
                preparedStatementUsuarios.setString(2, usuario.getCorreo_electronico());
                preparedStatementUsuarios.setString(3, usuario.getContrasena());
                preparedStatementUsuarios.setInt(4, 0);
                preparedStatementUsuarios.setString(5, usuario.getRol().name());
                preparedStatementUsuarios.setInt(6, usuario.getIdUsuario());
                preparedStatementUsuarios.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarUsuario(int idUsuario) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            connection.setAutoCommit(false);

            String deleteReservasSQL = "DELETE FROM Reservas WHERE usuario_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteReservasSQL)) {
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.executeUpdate();
            }

            String deleteUsuarioSQL = "DELETE FROM Usuarios WHERE id_usuario = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteUsuarioSQL)) {
                preparedStatement.setInt(1, idUsuario);
                preparedStatement.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar usuario", e);
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
                int cantidadPuntos = resultSet.getInt("cantidad_puntos");
                Rol rol = Rol.valueOf(resultSet.getString("rol"));

                usuario = new Usuario(nombre, correo_electronico, contrasena, cantidadPuntos, rol, new HashSet<>(), new HashSet<>());            }
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
                int cantidadPuntos = resultSet.getInt("cantidad_puntos");
                Rol rol = Rol.valueOf(resultSet.getString("rol"));

                usuario = new Usuario(idUsuario, nombre, correo_electronico, contrasena, cantidadPuntos, rol, new HashSet<>(), new HashSet<>());            }
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
        logger.info("Verificando usuario: {} - {}", correo_electronico, existe);
        return existe;
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
