import java.util.List;
// context folosit pentru a executa strategia
public class Context {
    private Converter strategy;
    public Context(Converter strategy){
        this.strategy = strategy;
    }
    public void executeStrategyToConvert(List<String[]> Strategy){
        strategy.convertStringArrayToObjectList(Strategy);
    }
}
