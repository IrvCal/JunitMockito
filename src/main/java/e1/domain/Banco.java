package e1.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//No tiene AllArgsConstructor ni NoArgsConstructor porque se tenia que inicializar la lista Cuentas pero si se manda la inicializacion en el builder 2. ya se pueden colocar
@Data
public class Banco {

    private String nombre;
    private List<Cuenta> cuentas;

    public Banco (){
        cuentas = new ArrayList<>();
    }

    @Builder // tuve que poner el builder y un constructor con tdo aqui porque no me dejaba iniciar la listsa en un constructor vacio por el @Builder hasta arriba
    public Banco(String nombre, List<Cuenta> cuentas) {
//        cuentas = new ArrayList<>(); //pertenece a el builder 1.
        this.nombre = nombre;
        this.cuentas = cuentas;
    }

    public void addCuenta(Cuenta cuenta){
        cuentas.add(cuenta);
        cuenta.setBanco(this);//se hace la liga con este banco a la cuenta en cuestion
    }
    public void transferirDinero(Cuenta origen, Cuenta destino, BigDecimal monto){
        origen.gasto(monto);
        destino.abono(monto);
    }
}
