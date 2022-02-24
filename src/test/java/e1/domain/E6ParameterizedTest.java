package e1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class E6ParameterizedTest {

    private TestInfo info;
    private TestReporter reporter;

    @BeforeEach
    void llenarInfo(TestInfo info, TestReporter reporter){
        this.info = info;
        this.reporter = reporter;
    }

    @ParameterizedTest(name = "Aparece una descripcion por cada caso: {index} tiene valor {argumentsWithNames}")
    @ValueSource(strings = {"100","200","300","200"})
    void test1(String monto) {
//        System.out.println(info+" \n "+reporter);
        System.out.println(monto);
        reporter.publishEntry("Ya se termino de ejecutar el test1");
    }
    //Tambein se pueden hacer con formato CSV para parametrizar los test donde 1,100 es: indice,valor
    @ParameterizedTest
    @CsvSource({"1,100","2,200","3,300","4,200"})
    @Tag("CSV")
    void test2(String indice, String valor) {
//        System.out.println(info+" \n "+reporter);
        System.out.println("El valor en "+indice+" tiene: "+valor );
    }
    @ParameterizedTest
    @Tag("CSV")
    @CsvSource({"1,100,Juan,Toluca","2,200,Mario,Jalisco","3,300,Oto,Toluca","4,200,Maria,Oaxaca"})
    void test2_1(String indice, String valor,String persona, String cuidad) {
//        System.out.println(info+" \n "+reporter);
        System.out.println("El valor en "+indice+" tiene: "+valor+" Pertenece a: "+persona+" que vive en: " +cuidad);
    }
    @ParameterizedTest
    @Tag("CSV")
    @Tag("Files")
    @CsvFileSource(resources = "/data.csv")//EL ARCHIVO TIENE QUE ESTAR DENTOR DE LA CARPETA RESOURCES
    void test3(String valor) {
        System.out.println(info+" \n "+reporter);System.out.println("Encontrado en file: "+valor );
    }
    @ParameterizedTest
    @MethodSource("listaMontos")
    public void test4(Integer montos) {
        System.out.println(montos);

    }

    static List<Integer> listaMontos(){
        List<Integer> montos = new ArrayList<>();
        IntStream.rangeClosed(0,10).forEach(montos::add);
        return montos;
    }
}
