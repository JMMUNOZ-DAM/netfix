/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmmunoz.netfix;

/**
 *
 * @author Juanma Muñoz
 */
import java.util.Random;

public class SimuladorDiagnostico {

    private final Random random = new Random();

    // =============================================================
    // MODELO DE APARATO
    // =============================================================
    public static class Aparato {

        private String tipoAparato;
        private String marca;
        private String modelo;
        private String numeroSerie;
        private String mac;
        private int idContrato;

        public Aparato(String tipoAparato, String marca, String modelo, String numeroSerie, String mac, int idContrato) {
            this.tipoAparato = tipoAparato;
            this.marca = marca;
            this.modelo = modelo;
            this.numeroSerie = numeroSerie;
            this.mac = mac;
            this.idContrato = idContrato;
        }

        public String getTipoAparato() {
            return tipoAparato;
        }
    }

    // =============================================================
    // MODELO DE DIAGNOSTICO
    // =============================================================
    public static class Diagnostico {

        public String estadoGeneral;
        public double velocidadInternet;
        public String nivelesOpticos;
        public String cobertura;
        public double ping;
        public String observaciones;

        @Override
        public String toString() {
            return "Estado: " + estadoGeneral
                    + "\nVelocidad: " + velocidadInternet + " Mbps"
                    + "\nNiveles ópticos: " + nivelesOpticos
                    + "\nCobertura: " + cobertura
                    + "\nPing: " + ping + " ms"
                    + "\nObs: " + observaciones;
        }
    }

    // =============================================================
    // GENERADOR DE DIAGNÓSTICO SEGÚN EL APARATO
    // =============================================================
    public Diagnostico generarDiagnostico(Aparato aparato) {
        Diagnostico diag = new Diagnostico();

        switch (aparato.getTipoAparato()) {
            case "FTTH":
                simularFTTH(diag);
                break;
            case "5G":
                simular5G(diag);
                break;
            default:
                simularGenerico(diag);
        }

        return diag;
    }

    // =============================================================
    // SIMULACIÓN PARA APARATOS FTTH
    // =============================================================
    private void simularFTTH(Diagnostico d) {

        double velocidad = 500 + random.nextDouble() * 500; // 500–1000 Mbps
        double ping = 5 + random.nextDouble() * 10;         // 5–15 ms
        double nivelOptico = -28 + random.nextDouble() * 10; // -28 a -18 dBm

        d.velocidadInternet = velocidad;
        d.ping = ping;
        d.nivelesOpticos = String.format("%.1f dBm", nivelOptico);
        d.cobertura = "Excelente";

        if (nivelOptico < -25) {
            d.estadoGeneral = "advertencia";
            d.observaciones = "Nivel óptico bajo. Posible problema en fibra.";
        } else if (velocidad < 400) {
            d.estadoGeneral = "advertencia";
            d.observaciones = "Rendimiento inferior al esperado.";
        } else {
            d.estadoGeneral = "correcto";
            d.observaciones = "Funcionamiento óptimo.";
        }
    }

    // =============================================================
    // SIMULACIÓN PARA APARATOS 5G
    // =============================================================
    private void simular5G(Diagnostico d) {

        double velocidad = random.nextDouble() * 600;   // 0–600 Mbps
        double ping = 20 + random.nextDouble() * 200;   // 20–220 ms
        String[] coberturas = {"Excelente", "Buena", "Irregular", "Sin señal"};
        String cobertura = coberturas[random.nextInt(coberturas.length)];

        d.velocidadInternet = velocidad;
        d.ping = ping;
        d.cobertura = cobertura;
        d.nivelesOpticos = "N/A"; // No aplica en 5G

        if (null == cobertura) {
            d.estadoGeneral = "correcto";
            d.observaciones = "Sin incidencias detectadas.";
        } else switch (cobertura) {
            case "Sin señal" -> {
                d.estadoGeneral = "fallo";
                d.observaciones = "No hay conexión 5G. El dispositivo no responde.";
            }
            case "Irregular" -> {
                d.estadoGeneral = "advertencia";
                d.observaciones = "Cobertura 5G inestable.";
            }
            default -> {
                d.estadoGeneral = "correcto";
                d.observaciones = "Sin incidencias detectadas.";
            }
        }
    }

    // =============================================================
    // SIMULACIÓN GENÉRICA (por si aparecen nuevos tipos)
    // =============================================================
    private void simularGenerico(Diagnostico d) {
        d.estadoGeneral = "desconocido";
        d.velocidadInternet = 0;
        d.nivelesOpticos = "N/A";
        d.cobertura = "N/A";
        d.ping = 999;
        d.observaciones = "Tipo de aparato no soportado.";
    }

    // =============================================================
    // MÉTODO DE PRUEBA
    // =============================================================
    public static void main(String[] args) {
        SimuladorDiagnostico s = new SimuladorDiagnostico();
        Aparato a = new Aparato("FTTH", "Huawei", "HG8145V5", "SNFTTH001", "ABC123", 1);
        Diagnostico d = s.generarDiagnostico(a);
        System.out.println(d);
        Aparato b = new Aparato("5G", "ESIM", "ESIMBLANCA", "8434000001234564", "OEOE", 1);
        d = s.generarDiagnostico(b);
        System.out.println(d);
    }
}
