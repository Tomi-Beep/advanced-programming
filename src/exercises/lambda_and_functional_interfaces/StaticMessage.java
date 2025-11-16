package exercises.lambda_and_functional_interfaces;

public class StaticMessage implements MessageProvider{

    @Override
    public String getMessage() {
        return "Hello from a regular class!";
    }
}
