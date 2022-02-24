package e1.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/**
 * SOLO si esta levantando el servidor o algo por el estilo
 */
public class E3Assumptions {

    @Test
    @DisplayName("Probando cuentas con assumptions")
    void testSaldoCuenta(){
//        Se puede obtener el tipo de perfil que se esta corriendo por ejemplo
//        si es de enviroment o algo por el estilo (este perfil se configura en la
//        configuracion del proyecto, y luego en -build and run- colocar
//        -DENV=dev
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(isDev);//este es como un IF si esto se cumple el test continua, de lo contrario se deshabilita
        Cuenta cuenta = Cuenta.builder().saldo(new BigDecimal("1000.20323")).build();
        assertEquals(1000.20323, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) <0);
        System.out.println("Termiando test");
    }

    /**
     * Esta es una mejor manera de hacer el test de arriba
     * porque se implementan lambdas, la ventaja entre un o y otro es que
     * en el de arriba si no se cumple el assumeTrue se tunca y hasta ahi llega
     * el de abajo (este) solo se ejecuta o no lo que este dentro de la lambda
     * y continua su flujo
     */
    @Test
    @DisplayName("Probando cuentas con assumptions 2")
    void testSaldoCuenta2(){
        boolean isDev = "dev".equals(System.getProperty("ENV"));
        assumingThat(isDev, ()-> {
            Cuenta cuenta = Cuenta.builder().saldo(new BigDecimal("1000.20323")).build();
            assertEquals(1000.2, cuenta.getSaldo().doubleValue());
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        });
        System.out.println("Termiando test 2");
    }

}
