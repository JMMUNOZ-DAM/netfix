/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

import com.jmmunoz.netfix.SimuladorDiagnostico.Aparato;
import com.jmmunoz.netfix.SimuladorDiagnostico.Diagnostico;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author juanm
 */
public class utilities {

    public utilities() {
    }

    public boolean loggin(int idUsuario, String passwordIntroducida) {

        try {
            DatabaseManager db = DatabaseManager.getInstance();

            // 1️⃣ Obtener hash desde BD
            String hashBD = db.executePreparedString(
                    querys.login,
                    idUsuario
            );

            // Usuario no existe
            if (hashBD == null) {
                return false;
            }

            // 2️⃣ Comparar hash
            return PasswordUtils.checkPassword(
                    passwordIntroducida,
                    hashBD
            );

        } catch (SQLException ex) {
        }

        return false;
    }

    public enum TipoConsulta {
        INCIDENCIAS,
        INCI_CONTRATO,
        CONTADOR_INCIDENCIAS,
        INCIDENCIAS_RESUELTAS_TECNICO,
        DIAGNOSTICO_APARATO,
        CONTADOR_APARATOS
    }

    public ResultSet ejecutarConsulta(TipoConsulta tipo, Object... params) {
        try {
            DatabaseManager db = DatabaseManager.getInstance();

            switch (tipo) {
                case INCIDENCIAS -> {
                    return db.executeQuery(querys.incidencias);
                }

                case CONTADOR_INCIDENCIAS -> {
                    return db.executeQuery(querys.contadorIncidencias);
                }

                case INCIDENCIAS_RESUELTAS_TECNICO -> {
                    return db.executeQuery(querys.incidenciasResueltasPorTecnico);
                }

                case CONTADOR_APARATOS -> {
                    return db.executeQuery(querys.contadorAparatos);
                }
                case DIAGNOSTICO_APARATO -> {
                    return db.executePreparedQuery(
                            querys.diagnosticoAparato,
                            params
                    );
                }

                case INCI_CONTRATO -> {
                    return db.executePreparedQuery(
                            querys.inciContra,
                            params
                    );
                }
            }

        } catch (SQLException ex) {

        }
        return null;
    }

   

    public class PasswordUtils {

        // Crear hash al registrar usuario
        public static String hashPassword(String plainPassword) {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
        }

        // Comparar password con hash
        public static boolean checkPassword(String plainPassword, String hashedPassword) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }
    }
}
