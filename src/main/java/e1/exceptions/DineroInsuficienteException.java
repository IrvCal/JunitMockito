package e1.exceptions;

//En spring boot este deberia de de tener @ControllerAdvice
//depende de que procedimiento se desee hacer y de eso depende el extends
public class DineroInsuficienteException extends RuntimeException{

    public DineroInsuficienteException(String message) {
        super(message);
    }
}
