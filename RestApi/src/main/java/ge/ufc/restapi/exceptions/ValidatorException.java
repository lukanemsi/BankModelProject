package ge.ufc.restapi.exceptions;

public class ValidatorException extends Exception {
  public ValidatorException() {
  }

  public ValidatorException(String message) {
    super(message);
  }
}
