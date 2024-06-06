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
                    "id_usuario SERIAL PRIMARY KEY," +
                    "nombre TEXT NOT NULL," +
                    "correo_electronico TEXT NOT NULL," +
                    "contrasena TEXT NOT NULL," +
                    "datos_personales_id INTEGER," +
                    "progreso_fitness INTEGER DEFAULT 0," +
                    "cantidad_puntos INTEGER DEFAULT 0," +
                    "FOREIGN KEY (datos_personales_id) REFERENCES datos_personales(id_datos_personales))";
            connection.createStatement().executeUpdate(createUsuariosTableSQL);

            String createReservasTableSQL = "CREATE TABLE IF NOT EXISTS reservas (" +
                    "id_reserva SERIAL PRIMARY KEY," +
                    "usuario_id INTEGER," +
                    "espacio_id INTEGER," +
                    "tipo_reserva TEXT," +
                    "horario_reserva TIMESTAMP WITHOUT TIME ZONE," +
                    "estado_reserva TEXT," +
                    "FOREIGN KEY (usuario_id) REFERENCES usuarios(id_usuario)," +
                    "FOREIGN KEY (espacio_id) REFERENCES espacios(id_espacio))";
            connection.createStatement().executeUpdate(createReservasTableSQL);

            String createEspaciosTableSQL = "CREATE TABLE IF NOT EXISTS espacios (" +
                    "id_espacio SERIAL PRIMARY KEY," +
                    "nombre TEXT," +
                    "entrenador_id INTEGER," +
                    "FOREIGN KEY (entrenador_id) REFERENCES entrenadores(id_entrenador))";
            connection.createStatement().executeUpdate(createEspaciosTableSQL);

            String createEspacioHorarioTableSQL = "CREATE TABLE IF NOT EXISTS espacio_horario (" +
                    "id_espacio_horario SERIAL PRIMARY KEY," +
                    "espacio_id INTEGER," +
                    "horario_reserva TIMESTAMP WITHOUT TIME ZONE," +
                    "capacidad_actual INTEGER DEFAULT 0," +
                    "capacidad_maxima INTEGER," +
                    "FOREIGN KEY (espacio_id) REFERENCES espacios(id_espacio))";
            connection.createStatement().executeUpdate(createEspacioHorarioTableSQL);

            String createEntrenadoresTableSQL = "CREATE TABLE IF NOT EXISTS entrenadores (" +
                    "id_entrenador SERIAL PRIMARY KEY," +
                    "nombre_Entrenador TEXT," +
                    "especializacion TEXT)";
            connection.createStatement().executeUpdate(createEntrenadoresTableSQL);

            String createLogrosTableSQL = "CREATE TABLE IF NOT EXISTS logros (" +
                    "id_logro SERIAL PRIMARY KEY," +
                    "nombre_logro TEXT," +
                    "descripcion TEXT," +
                    "requisitos_puntos INTEGER," +
                    "recompensa TEXT)";
            connection.createStatement().executeUpdate(createLogrosTableSQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
