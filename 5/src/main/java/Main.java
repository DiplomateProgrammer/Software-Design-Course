import application.AwtApplication;
import application.FxApplication;
import application.GraphDrawingApplication;
import application.ApplicationConfig;

public class Main {

    public static void main(String[] args ) {
        if (args.length != 2) {
            throw new IllegalArgumentException("There must be two arguments");
        }

        GraphDrawingApplication graphApplication = switch (args[0]) {
            case "fx" -> new FxApplication();
            case "awt" -> new AwtApplication();
            default -> throw new IllegalArgumentException("First argument must be \"fx\" or \"awt\"");
        };

        ApplicationConfig.graphType = switch (args[1]) {
            case "matrix" -> ApplicationConfig.GraphType.MATRIX;
            case "list" -> ApplicationConfig.GraphType.LIST;
            default -> throw new IllegalArgumentException("Second argument must be \"matrix\" or \"list\"");
        };

        graphApplication.startApplication();
    }
}
