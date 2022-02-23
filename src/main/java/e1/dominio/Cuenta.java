package e1.dominio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {
    private String persona;
    private BigDecimal saldo;//Es mas preciso

    //Para hacer este equals primero se hizo el test para entender el comportamiento que debiera arrojar
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Cuenta)) return false; //valida también dentro de aquí que no sea nulo el obj
        Cuenta c = (Cuenta) obj;
        Predicate<Cuenta> equals = cuenta -> (this.persona != null && this.saldo != null) && (this.persona.equals(cuenta.persona) && this.saldo.equals(cuenta.getSaldo()));
        return equals.test(c);
    }

}
