package e1.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class E4ClasesAnidadas {

    @Test
    void imprimirPropuedadesDeSistema() {
        System.getProperties().forEach((k, v) -> System.out.println(k + " "+v));
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

    @Nested
    @DisplayName("Clase de propiedades del sistema")
    class PropiedadesDelSistema{
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
    @Nested
    class SoWindows{

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void noMac() {
            System.out.println("No acepta windows");
        }

        @Test
        @EnabledOnOs(OS.WINDOWS)
        void soloEnWIndows() {
            System.out.println("Estoy en windows");
        }
    }
    @Nested
    class SoMacOs{

        @Test
        @EnabledOnOs(OS.MAC)
        void soloEnMac() {
            System.out.println("Estoy en Mac");
        }
    }
}
