package com.example.massfitness.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class AccesoBD {

    @Autowired
    private Environment env;
    public Connection conectarPostgreSQL(){
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(
                    env.getProperty("spring.datasource.url"),
                    env.getProperty("spring.datasource.username"),
                    env.getProperty("spring.datasource.password")
            );
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conexion;
    }

    public void desconectar(Connection conexion){
        try {
            conexion.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @PostConstruct
    public void initializeDatabase() {
        try (Connection connection = conectarPostgreSQL()) {
            // Crear las tablas si no existen
            String createDatosPersonalesTableSQL = "CREATE TABLE IF NOT EXISTS datos_personales (" +
                    "id_datos_personales SERIAL PRIMARY KEY," +
                    "edad INTEGER," +
                    "genero TEXT)";
            connection.createStatement().executeUpdate(createDatosPersonalesTableSQL);

            String createUsuariosTableSQL = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id SERIAL PRIMARY KEY," +
                    "nombre TEXT NOT NULL," +
                    "correo_electronico TEXT NOT NULL," +
                    "contrasena TEXT NOT NULL," +
                    "datos_personales_id INTEGER," +
                    "progreso_fitness INTEGER DEFAULT 0," +
                    "cantidad_puntos INTEGER DEFAULT 0," +
                    "FOREIGN KEY (datos_personales_id) REFERENCES datos_personales(id_datos_personales)," +
                    "FOREIGN KEY (id) REFERENCES reservas(usuario_id))";
            connection.createStatement().executeUpdate(createUsuariosTableSQL);

            String createReservasTableSQL = "CREATE TABLE IF NOT EXISTS reservas (" +
                    "id_Reserva SERIAL PRIMARY KEY," +
                    "usuario_id INTEGER," +
                    "espacio_id INTEGER," +
                    "tipo_Reserva TEXT," +
                    "horario_Reserva TIMESTAMP," +
                    "estado_Reserva TEXT," +
                    "FOREIGN KEY (usuario_id) REFERENCES usuarios(id)," +
                    "FOREIGN KEY (espacio_id) REFERENCES espacios(id_Espacio))";
            connection.createStatement().executeUpdate(createReservasTableSQL);

            String createEspaciosTableSQL = "CREATE TABLE IF NOT EXISTS espacios (" +
                    "id_Espacio SERIAL PRIMARY KEY," +
                    "nombre TEXT," +
                    "descripcion TEXT," +
                    "horario TIMESTAMP," +
                    "capacidad_Maxima INTEGER," +
                    "capacidad_Actual INTEGER DEFAULT 0," +
                    "entrenador_id INTEGER," +
                    "tipo TEXT," +
                    "FOREIGN KEY (entrenador_id) REFERENCES entrenadores(id_Entrenador))";
            connection.createStatement().executeUpdate(createEspaciosTableSQL);

            String createEntrenadoresTableSQL = "CREATE TABLE IF NOT EXISTS entrenadores (" +
                    "id_Entrenador SERIAL PRIMARY KEY," +
                    "nombre_Entrenador TEXT," +
                    "especializacion TEXT)";
            connection.createStatement().executeUpdate(createEntrenadoresTableSQL);

            String createLogrosTableSQL = "CREATE TABLE IF NOT EXISTS logros (" +
                    "id_Logro SERIAL PRIMARY KEY," +
                    "nombre_Logro TEXT," +
                    "descripcion TEXT," +
                    "requisitos_Puntos INTEGER," +
                    "recompensa TEXT)";
            connection.createStatement().executeUpdate(createLogrosTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
