package e1.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class E5RepeatedTest {
    String saludo = "Hola";
    @DisplayName("Nombre de todo el test")
    @RepeatedTest(value = 5,name = "{displayName} -Nombre por repeticion")
    void test1(RepetitionInfo repetitionInfo){
        if (repetitionInfo.getCurrentRepetition() == 3)
            saludo = saludo.concat(" Por ultima vez");
        System.out.println(saludo);
    }
}
