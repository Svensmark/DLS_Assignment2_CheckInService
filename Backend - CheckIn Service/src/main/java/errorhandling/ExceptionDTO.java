package errorhandling;

public class ExceptionDTO extends Exception{

  public ExceptionDTO(int code, String description){
      this.code = code;
      this.message = description;
  }
  private int code;
  private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
  
}
