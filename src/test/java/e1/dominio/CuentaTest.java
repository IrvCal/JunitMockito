package e1.dominio;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    /**
     * Test para probar si el nombre se esta seteando correctamente
     */
    @Test
    void testNombreCuenta(){
        Cuenta cuenta = Cuenta.builder().saldo(new BigDecimal("1000.20323456")).build();
        cuenta.setPersona("Irving");
        String valorEsperado="Irving";
        String valorReal = cuenta.getPersona();
        //Va a hacer la validation
        assertEquals(valorEsperado,valorReal);
    }
    @Test
    void test_nombre_cuenta(){}
    @Test
    void testSaldoCuenta(){

        Cuenta cuenta = Cuenta.builder().saldo(new BigDecimal("1000.20323")).build();
        assertEquals(
                1000.20323,
                cuenta.getSaldo().doubleValue() // lo pasas a un valor double
        );
//        Para verificar que el saldo no sea un valor negativo se utiliza
        assertFalse(
                cuenta.getSaldo().compareTo(BigDecimal.ZERO) <0
        );
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = Cuenta.builder()
                .persona("Irving")
                .saldo(new BigDecimal("10"))
                .build();
        Cuenta cuenta2= Cuenta.builder()
                .persona("Irving")
                .saldo(new BigDecimal("10"))
                .build();

//        Se compara dos instancias con los mismos valores, pero no son los mismos
//        assertNotEquals(cuenta2,cuenta);
//        Pero que pasaria si se quiere tomar que estos son el mismo, por ejemplo en una lista de productos
        assertEquals(cuenta2,cuenta);//se tiene que sobreescribir el metodo Equals de la clase Cuenta
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = Cuenta.builder().persona("Irving").saldo(new BigDecimal("1000.12354")).build();
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900,cuenta.getSaldo().intValue());
        assertEquals("900.12354",cuenta.getSaldo().toPlainString());
    }
    @Test
    void testCreditoCuenta() {
        Cuenta cuenta = Cuenta.builder().persona("Irving").saldo(new BigDecimal("1000.12354")).build();
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100,cuenta.getSaldo().intValue());
        assertEquals("1100.12354",cuenta.getSaldo().toPlainString());
    }
}