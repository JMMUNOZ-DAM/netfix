/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author juanm
 */
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilitiesTest {

    public static void main(String[] args) {

        utilities util = new utilities();

        // ===============================
        // ✅ 1️⃣ PROBAR LOGIN CON HASH
        // ===============================
        int idUsuario = 1;
        String password = "1234"; // la que escriba el usuario

        boolean loginOk = util.loggin(idUsuario, password);

        System.out.println("¿Login correcto? -> " + loginOk);

        // ===============================
        // ✅ 2️⃣ PROBAR CONSULTA SIN PARÁMETROS
        // ===============================
        System.out.println("\n--- CONTADOR APARATOS ---");

        try (ResultSet rs = util.ejecutarConsulta(
                utilities.TipoConsulta.CONTADOR_APARATOS)) {

            while (rs.next()) {
                System.out.println(
                        "Tipo: " + rs.getString("tipo_aparato")
                        + " | Total: " + rs.getInt("total")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ===============================
        // ✅ 3️⃣ PROBAR CONSULTA CON PARÁMETRO
        // ===============================
        System.out.println("\n--- DIAGNOSTICO APARATO ---");

        int idAparato = 8;

        try (ResultSet rs = util.ejecutarConsulta(
                utilities.TipoConsulta.DIAGNOSTICO_APARATO,
                idAparato)) {

            while (rs.next()) {
                System.out.println(
                        "id_aparato: " + rs.getInt("id_aparato")
                        + " | tipo_aparato: " + rs.getString("tipo_aparato")
                        + " | marca: " + rs.getString("marca")
                        + " | modelo: " + rs.getString("modelo")
                        + " | numero_serie: " + rs.getString("numero_serie")
                        + " | mac: " + rs.getString("mac")
                        + " | id_contrato: " + rs.getInt("id_contrato")
                        + " | id_diagnostico: " + rs.getInt("id_diagnostico")
                        + " | fecha_actualizacion: " + rs.getTimestamp("fecha_actualizacion")
                        + " | estado_general: " + rs.getString("estado_general")
                        + " | velocidad_internet: " + rs.getString("velocidad_internet")
                        + " | niveles_opticos: " + rs.getString("niveles_opticos")
                        + " | cobertura: " + rs.getString("cobertura")
                        + " | ping: " + rs.getString("ping")
                        + " | observaciones: " + rs.getString("observaciones")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ===============================
        // ✅ 4️⃣ PROBAR INCI_CONTRATO
        // ===============================
        System.out.println("\n--- INCIDENCIAS POR CONTRATO ---");

        int idContrato = 15;

        try (ResultSet rs = util.ejecutarConsulta(
                utilities.TipoConsulta.INCI_CONTRATO,
                idContrato)) {

            while (rs.next()) {
                System.out.println(
                        "Incidencia ID: " + rs.getInt("id_incidencia")
                        + " | Estado: " + rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\n✅ PRUEBAS FINALIZADAS");
    }
}
