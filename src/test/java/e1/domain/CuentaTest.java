package e1.domain;

import e1.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CuentaTest {

    private Cuenta cuenta;
    /**
     * Tienen que ser static de lo contrario va a ingnorar todos los test
     */
    @AfterAll
    static void despues(){
        System.out.println("Trmino TODO el test");
    }
    @BeforeAll
    static void antesDeTodos(){
        System.out.println("Iniciando tests");
    }

    /**
     * Este ya no es STATIC
     */
    @AfterEach
     void despsCada(){
        System.out.println("Termino este test");
    }

    @BeforeEach
    void dataInit(){
        cuenta= Cuenta.builder().persona("Irving").saldo(new BigDecimal("1000.12354")).build();
    }

    /**
     * Test para probar si el nombre se esta seteando correctamente
     */
    @Test
    @DisplayName("Primer test de prueba sobre la cuenta")
    void testNombreCuenta(){
        Cuenta cuenta = Cuenta.builder().saldo(new BigDecimal("1000.20323456")).build();
        cuenta.setPersona("Irving");
        String valorEsperado="Irving";
        String valorReal = cuenta.getPersona();
        //Va a hacer la validation
        assertEquals(valorEsperado,valorReal);
    }
    @Test
    @Disabled
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
        cuenta.gasto(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(900,cuenta.getSaldo().intValue());
        assertEquals("900.12354",cuenta.getSaldo().toPlainString());
    }
    @Test
    void testCreditoCuenta() {
        cuenta.abono(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100,cuenta.getSaldo().intValue());
        assertEquals("1100.12354",cuenta.getSaldo().toPlainString());
    }
    @Test
    void dineroInsuficienteExceptionTest(){
//        se utiliza para checar precisamente el manejo de excepciones
        Exception exception = //se cacha el obj que devuelve el assertThrows para despues mirar si el msj de error es el deseado
        assertThrows(DineroInsuficienteException.class,() -> {
            cuenta.gasto(new BigDecimal(1500));
        });
        assertEquals("Dinero insuficiente",exception.getMessage());
    }

    @Test
    void transferirDinero(){
        Cuenta origen = Cuenta.builder().persona("Irving").saldo(new BigDecimal("2500.12354")).build();
        Cuenta destino = Cuenta.builder().persona("Juan").saldo(new BigDecimal("1500.12354")).build();
        Banco banco = Banco.builder().nombre("Banamex").build();
        banco.transferirDinero(origen,destino,new BigDecimal(500));
        assertEquals("2000.12354",origen.getSaldo().toPlainString());
        assertEquals("2000.12354",destino.getSaldo().toPlainString());
    }
    @Test
    void relacionCuentas(){
        Cuenta cuenta1 = Cuenta.builder().persona("Irving").saldo(new BigDecimal("2500.12354")).build();
        Cuenta cuenta2 = Cuenta.builder().persona("Juan").saldo(new BigDecimal("1500.12354")).build();

        Banco banco = new Banco();
        /**
         * Nota
         */
//        Banco banco = Banco.builder().build(); // 1. como en este builder da error porque la lista NO ESTA INICIALIZADA pero se puede colocar un constructor con todos los args y con @Builder donde se inicializa la lista
//        Banco banco = Banco.builder().cuentas(new ArrayList<>()).build();// se pasa por parameter en su construction a manera de constructor un nuevo array (estos dos builder es para quitar el constructor sin parametros del banco donde se INICIALIZA la lista) de esta forma ya se pueden poner las anotaciones de constructores
        banco.setNombre("Banamex");
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.transferirDinero(cuenta1,cuenta2,new BigDecimal(500));
        assertEquals("2000.12354",cuenta1.getSaldo().toPlainString());
        assertEquals("2000.12354",cuenta2.getSaldo().toPlainString());

        assertEquals(2,banco.getCuentas().size());

        assertNotNull(cuenta1.getBanco());
        assertNotNull(cuenta2.getBanco());
        assertEquals("Banamex",cuenta1.getBanco().getNombre());

        //Asertar si se encontro al menos una cuenta a nombre de irving, se hace de dos formas
        Predicate<Cuenta> propietarioIrving = cuenta -> cuenta.getPersona().equals("Irving");
//        esta es la primera forma
        assertEquals("Irving",banco.getCuentas()
                .stream()
                .filter(propietarioIrving)
                .findFirst()
                .get().getPersona()
        );

//        esta es la segunda forma
        //pero tiene dos formas de hacerla, una que es usando muchos elementos
        // y la otra haciendo el correcto uso del API Streams

        assertTrue(banco.getCuentas().stream().filter(propietarioIrving).findFirst().isPresent());

        assertTrue(banco.getCuentas().stream().anyMatch(propietarioIrving));

    }
    /**
     * Hasta este punto los test tienen que ser satisfactorios en cada una de sus aserciones
     * de lo contrario se parara en cualquiera que haya fallado, esto a su manera esta bien
     * PERO QUE PASARIA si se desea que se hagan todos los casos, y no se pare hasta el que
     * no cumpla,
     * ASSERTALL es la asercion que hace que se ejecuten mediante lambdas todos los casos
     * que se coloquendentro de la misma y al final detalla cuales fueron los casos error
     */
    @Test
    void assertVarios(){
        Cuenta cuenta1 = Cuenta.builder().persona("Irving").saldo(new BigDecimal("2500.12354")).build();
        Cuenta cuenta2 = Cuenta.builder().persona("Juan").saldo(new BigDecimal("1500.12354")).build();
        Banco banco = Banco.builder().nombre("Banamex").cuentas(new ArrayList<>()).build();// se pasa por parameter en su construction a manera de constructor un nuevo array (estos dos builder es para quitar el constructor sin parametros del banco donde se INICIALIZA la lista) de esta forma ya se pueden poner las anotaciones de constructores

        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.transferirDinero(cuenta1,cuenta2,new BigDecimal(500));
//      De esta forma se ejecutan todos los assert y al final dice cuales fueron los que fallaron
        assertAll(
                () -> assertEquals("200.12",cuenta1.getSaldo().toPlainString()),//ERROR
                () -> assertEquals("2000.12354",cuenta2.getSaldo().toPlainString()),
                () -> assertEquals(6,banco.getCuentas().size()),//ERROR
                () -> assertNotNull(cuenta1.getBanco()),
                () -> assertNotNull(cuenta2.getBanco()),
                () -> assertEquals("Banamex el banco fuerte de mexico",cuenta1.getBanco().getNombre()),//ERROR
                () -> assertTrue(banco.getCuentas().stream().anyMatch(cuenta -> cuenta.getPersona().equals("Irving")))
        );
    }
    /**
     * MENSAJES PERSONALIZADOS
     * basicamente es un parametro mas a cada assert y se pasa como string
     */
    @Test
    void mensajesPersonalizados(){
        Cuenta cuenta1 = Cuenta.builder().persona("Irving").saldo(new BigDecimal("2500.12354")).build();
        Cuenta cuenta2 = Cuenta.builder().persona("Juan").saldo(new BigDecimal("1500.12354")).build();
        Banco banco = Banco.builder().nombre("Banamex").cuentas(new ArrayList<>()).build();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.transferirDinero(cuenta1,cuenta2,new BigDecimal(500));
        assertAll(
                () -> assertEquals("200.12",cuenta1.getSaldo().toPlainString(), ()-> "El valor esperado era: 200.12 "),//ERROR
                () -> assertEquals("2000.12354",cuenta2.getSaldo().toPlainString(),()->"No se retorno la cantidad esperada"),
                () -> assertEquals(6,banco.getCuentas().size(),()-> "Solo se han agregado 2 cuentas no puede haber mas"),//ERROR
                () -> assertNotNull(cuenta1.getBanco(),()-> "El banco de esta cuenta esta en nulo"),
                () -> assertNotNull(cuenta2.getBanco(),()-> "El banco de esta cuenta esta en nulo"),
                () -> assertEquals("Banamex el banco fuerte de mexico",cuenta1.getBanco().getNombre(),()-> "El nombre correcto del banco es otro"),//ERROR
                () -> assertTrue(banco.getCuentas().stream().anyMatch(cuenta -> cuenta.getPersona().equals("Irving")))
        );
    }

}