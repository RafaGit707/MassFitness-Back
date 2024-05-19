package com.example.massfitness.servicios;

import com.example.massfitness.entidades.DatosPersonales;
import com.example.massfitness.entidades.Usuario;
import com.example.massfitness.servicios.impl.IUsuarioService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {
    private final AccesoBD accesoBD;
    @Autowired
    public UsuarioService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }
    public void addUsuario(Usuario usuario) {
        String insertSQL = "INSERT INTO Usuarios (nombre, correoElectronico, contrasena, edad, genero, progresoFitness, cantidadPuntos) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreoElectronico());
            preparedStatement.setString(3, usuario.getContrasena());
            preparedStatement.setInt(4, usuario.getDatosPersonales().getEdad());
            preparedStatement.setString(5, usuario.getDatosPersonales().getGenero());
            preparedStatement.setInt(6, usuario.getProgresoFitness());
            preparedStatement.setInt(7, usuario.getCantidadPuntos());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String selectSQL = "SELECT * FROM Usuarios";
        try (Connection connection = accesoBD.conectarPostgreSQL();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String correoElectronico = resultSet.getString("correoElectronico");
                String contrasena = resultSet.getString("contrasena");
                int edad = resultSet.getInt("edad");
                String genero = resultSet.getString("genero");
                int progresoFitness = resultSet.getInt("progresoFitness");
                int cantidadPuntos = resultSet.getInt("cantidadPuntos");

                Usuario usuario = new Usuario(id, nombre, correoElectronico, contrasena, new DatosPersonales(edad, genero), progresoFitness, cantidadPuntos, null);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public void actualizarUsuario(Usuario usuario) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Usuarios SET nombre = ?, correoElectronico = ?, contrasena = ?, edad = ?, genero = ?, progresoFitness = ?, cantidadPuntos = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getCorreoElectronico());
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

    public void eliminarUsuario(int idUsuario) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Usuarios WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Usuarios WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String correoElectronico = resultSet.getString("correoElectronico");
                String contrasena = resultSet.getString("contrasena");
                int edad = resultSet.getInt("edad");
                String genero = resultSet.getString("genero");
                int progresoFitness = resultSet.getInt("progresoFitness");
                int cantidadPuntos = resultSet.getInt("cantidadPuntos");
                usuario = new Usuario(idUsuario, nombre, correoElectronico, contrasena, new DatosPersonales(edad, genero), progresoFitness, cantidadPuntos, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorCorreo(String correoElectronico) {
        Usuario usuario = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Usuarios WHERE correoElectronico = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, correoElectronico);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String contrasena = resultSet.getString("contrasena");
                int edad = resultSet.getInt("edad");
                String genero = resultSet.getString("genero");
                int progresoFitness = resultSet.getInt("progresoFitness");
                int cantidadPuntos = resultSet.getInt("cantidadPuntos");
                usuario = new Usuario(id, nombre, correoElectronico, contrasena, new DatosPersonales(edad, genero), progresoFitness, cantidadPuntos, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public boolean usuarioExiste(String correoElectronico, String contrasena) {
        boolean existe = false;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT COUNT(*) AS count FROM Usuarios WHERE correoElectronico = ? AND contrasena = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, correoElectronico);
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

    public int getProgresoFitnessUsuario(int idUsuario) {
        int progresoFitness = 0;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT progresoFitness FROM Usuarios WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                progresoFitness = resultSet.getInt("progresoFitness");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return progresoFitness;
    }

    public void actualizarProgresoFitnessUsuario(int idUsuario, int nuevoProgresoFitness) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Usuarios SET progresoFitness = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, nuevoProgresoFitness);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCantidadPuntosUsuario(int idUsuario) {
        int cantidadPuntos = 0;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT cantidadPuntos FROM Usuarios WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idUsuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cantidadPuntos = resultSet.getInt("cantidadPuntos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidadPuntos;
    }

    public void actualizarCantidadPuntosUsuario(int idUsuario, int nuevaCantidadPuntos) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Usuarios SET cantidadPuntos = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setInt(1, nuevaCantidadPuntos);
            preparedStatement.setInt(2, idUsuario);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
