import Controller.Controller;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Value.IValue;
import Repository.Repository;
import View.UI;

public class Main {
    public static void main(String[] args) {
        IStatement ex = new NoOperationStatement();
        ProgramState prg = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                new MyList<IValue>(), ex);

        Repository repo = new Repository(prg);
        Controller controller = new Controller(repo);
        UI ui = new UI(controller);
        ui.run();
    }
}