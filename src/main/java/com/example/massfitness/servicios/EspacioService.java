package com.example.massfitness.servicios;

import com.example.massfitness.entidades.Espacio;
import com.example.massfitness.repositories.EspacioRepository;
import com.example.massfitness.servicios.impl.IEspacioService;
import com.example.massfitness.util.AccesoBD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EspacioService implements IEspacioService {

    private final AccesoBD accesoBD;
    @Autowired
    private EspacioRepository espacioRepository;

    public Espacio findById(int id) {
        return espacioRepository.findById(id).orElse(null);
    }
    @Autowired
    public EspacioService(AccesoBD accesoBD) {
        this.accesoBD = accesoBD;
    }

    public void addEspacio(Espacio espacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String insertSQL = "INSERT INTO Espacios (nombre, capacidad_maxima) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, espacio.getNombre());
            preparedStatement.setInt(2, espacio.getCapacidadMaxima());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEspacio(Espacio espacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String updateSQL = "UPDATE Espacios SET nombre = ?, capacidad_maxima = ? WHERE id_Espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
            preparedStatement.setString(1, espacio.getNombre());
            preparedStatement.setInt(2, espacio.getCapacidadMaxima());
            preparedStatement.setInt(3, espacio.getIdEspacio());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEspacio(int idEspacio) {
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String deleteSQL = "DELETE FROM Espacios WHERE id_Espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idEspacio);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Espacio> getEspacios() {
        List<Espacio> espacios = new ArrayList<>();
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Espacios";
            ResultSet resultSet = connection.createStatement().executeQuery(selectSQL);
            while (resultSet.next()) {
                int id_Espacio = resultSet.getInt("id_espacio");
                String nombre = resultSet.getString("nombre");
                int capacidadMaxima = resultSet.getInt("capacidad_maxima");
                Espacio espacio = new Espacio(id_Espacio, nombre, capacidadMaxima);
                espacios.add(espacio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacios;
    }

    public Espacio buscarEspacioPorId(int idEspacio) {
        Espacio espacio = null;
        try (Connection connection = accesoBD.conectarPostgreSQL()) {
            String selectSQL = "SELECT * FROM Espacios WHERE id_espacio = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idEspacio);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int capacidadMaxima = resultSet.getInt("capacidad_maxima");
                espacio = new Espacio(nombre, capacidadMaxima);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return espacio;
    }
}
