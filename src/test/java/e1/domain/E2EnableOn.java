package e1.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class E2EnableOn {

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void soloEnWIndows() {
        System.out.println("Estoy en windows");
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void soloEnMac() {
        System.out.println("Estoy en Mac");
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void noMac() {
        System.out.println("No acepta windows");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void soloJDK8() {
        System.out.println("Se esta usando JDK 8");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void soloJDK11() {
        System.out.println("Se esta usando JDK 11");
    }

    @Test
    void imprimirPropuedadesDeSistema() {
        System.getProperties().forEach((k, v) -> System.out.println(k + " "+v));
    }

    @Test
    @EnabledIfSystemProperty(named = "user.timezone",matches = "America/Mexico_City")
    void soloSiEsMexico() {
        System.out.println("\nEstas en mexico\n");
    }

    @Test
    @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void soloArquitectuar64 () {
        System.out.println("ESta pc es arquitectura 64");
    }

    @Test
    @DisabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
    void noArquitectuar64 () {
        System.out.println("ESta pc es arquitectura 64 y aqui no quiero que corra");
    }
}
