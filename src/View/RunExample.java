package View;

import Controller.Controller;
import Model.Exceptions.*;
import Model.Statement.IStatement;
import Repository.IRepository;
import java.io.IOException;

public class RunExample extends Command{
    private Controller controller;

    public RunExample(String k, String desc, Controller c) {
        super(k, desc);
        this.controller = c;
    }

    @Override
    public void execute() {
        try {
            // reinitialize the program state to be able to run the example again
            this.controller.reinitializeProgramState();
            this.controller.allSteps();
        } catch (ControllerException | StatementException | ListException | StackException | ExpressionException | DictionaryException | FileException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
