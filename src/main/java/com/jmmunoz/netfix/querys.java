/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author juanm
 */
public class querys {

    static String incidencias = """
                        select *
                        from netfix.incidencias t1
                        inner join netfix.usuarios t2 on t1.id_usuario = t2.id_usuario  
                        where estado in ('abierta', 'en_proceso')""";

    static String inciContra = """
                                   select *
                                   from netfix.incidencias as t1
                                   inner join netfix.contratos as t2 on t1.id_contrato = t2.id_contrato
                                   where t1.id_contrato = ?""";
    static String contadorIncidencias
            = """
              SELECT estado, COUNT(*) TOTAL 
              FROM netfix.incidencias 
              GROUP BY estado""";

    static String incidenciasResueltasPorTecnico
            = """
              SELECT t1.id_usuario ID, t2.nombre NOMBRE, COUNT(*) total 
              FROM netfix.incidencias t1 
              INNER JOIN netfix.usuarios t2 ON t1.id_usuario = t2.id_usuario 
              WHERE t1.estado = 'resuelta' 
              GROUP BY t1.id_usuario, t2.nombre""";

    static String diagnosticoAparato
            = """
              SELECT * 
              FROM netfix.aparatos t1 
              INNER JOIN netfix.diagnostico t2 ON t1.id_aparato = t2.id_aparato 
              WHERE t1.id_aparato = ?""";

    static String contadorAparatos
            = """
              SELECT tipo_aparato, COUNT(*) total 
              FROM netfix.aparatos 
              GROUP BY tipo_aparato""";

    static String login = """
    SELECT password_hash
    FROM netfix.usuarios
    WHERE id_usuario = ?
    """;

    static String diagnostico = """
        INSERT INTO netfix.diagnostico (
            id_aparato,
            fecha_actualizacion,
            estado_general,
            velocidad_internet,
            niveles_opticos,
            cobertura,
            ping,
            observaciones
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        ON DUPLICATE KEY UPDATE
            fecha_actualizacion = VALUES(fecha_actualizacion),
            estado_general = VALUES(estado_general),
            velocidad_internet = VALUES(velocidad_internet),
            niveles_opticos = VALUES(niveles_opticos),
            cobertura = VALUES(cobertura),
            ping = VALUES(ping),
            observaciones = VALUES(observaciones)
        """;

}
