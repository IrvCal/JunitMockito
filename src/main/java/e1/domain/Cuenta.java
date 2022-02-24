package e1.domain;

import e1.exceptions.DineroInsuficienteException;
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
    private Banco banco;
    //Para hacer este equals primero se hizo el test para entender el comportamiento que debiera arrojar
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Cuenta)) return false; //valida también dentro de aquí que no sea nulo el obj
        Cuenta c = (Cuenta) obj;
        Predicate<Cuenta> equals = cuenta -> (this.persona != null && this.saldo != null) && (this.persona.equals(cuenta.persona) && this.saldo.equals(cuenta.getSaldo()));
        return equals.test(c);
    }

    /**
     * Metodos de logica de negocio
     * Debieran ir en Service
     */
    public void gasto(BigDecimal monto){
        BigDecimal nuevoSaldo=//se tiene que igualar porque el .substract() devuelve una nueva instancia de un BigDecimal y el valor original permanece intacto
        this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO)<0)
            throw new DineroInsuficienteException("Dinero insuficiente");
        this.saldo= nuevoSaldo;
    }
    public void abono(BigDecimal monto){
        this.saldo=//se tiene que igualar porque el .substract() devuelve una nueva instancia de un BigDecimal y el valor original permanece intacto
                this.saldo.add(monto);
    }

}
