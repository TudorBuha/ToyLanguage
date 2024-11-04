package View;

import Controller.Controller;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Exceptions.*;
import Model.Expression.ArithmeticExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.Scanner;

public class UI {
    private Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
        //this.controller.turnDisplayFlagOn();
    }

    private void runExample1() throws ControllerException, StatementException, StackException, ExpressionException, DictionaryException, ListException {
        // int v; v=2; Print(v)
        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        ProgramState prg = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                new MyList<IValue>(), ex1);
        this.controller.addProgramState(prg);
        this.controller.allSteps();
    }

    private String getExample1String() {
        return "int v; v=2; Print(v)";
    }

    private void runExample2() throws ControllerException, StatementException, StackException, ExpressionException, DictionaryException, ListException {
        // int a;int b; a=2+3*5;b=a+1;Print(b)
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                        new ValueExpression(new IntValue(5)), '*'), '+')),
                                new CompoundStatement(new AssignStatement("b",
                                        new ArithmeticExpression(new VariableExpression("a"),
                                                new ValueExpression(new IntValue(1)), '+')),
                                        new PrintStatement(new VariableExpression("b"))))));

        ProgramState prg = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                new MyList<IValue>(), ex2);
        this.controller.addProgramState(prg);
        this.controller.allSteps();
    }

    private String getExample2String() {
        return "int a;int b; a=2+3*5;b=a+1;Print(b)";
    }

    private void runExample3() throws ControllerException, StatementException, StackException, ExpressionException, DictionaryException, ListException {
        // bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                new PrintStatement(new VariableExpression("v"))))));
        ProgramState prg = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                new MyList<IValue>(), ex3);
        this.controller.addProgramState(prg);
        this.controller.allSteps();
    }

    private String getExample3String() {
        return "bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)";
    }

    private void printMenu() {
        System.out.print("Commands:\n" +
                "\t1. Run example 1: " + this.getExample1String() + "\n" +
                "\t2. Run example 2: " + this.getExample2String() + "\n" +
                "\t3. Run example 3: " + this.getExample3String() + "\n" +
                "\t4. Exit");
    }

    public void run() {
        Scanner keyboard = new Scanner(System.in);
        while (true) {
            this.printMenu();
            System.out.print("\nPlease enter a valid command: ");
            int cmd = keyboard.nextInt();
            try {
                switch (cmd) {
                    case 1 -> this.runExample1();
                    case 2 -> this.runExample2();
                    case 3 -> this.runExample3();
                    case 4 -> {
                        System.out.println("Exitting...");
                        return;
                    }
                    default -> System.out.println("Invalid command given");
                }
            }
            catch (ControllerException | StatementException | StackException | ExpressionException | DictionaryException | ListException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
