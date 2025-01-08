package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.*;
import Model.ProgramState.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args) {

        TextMenu menu = new TextMenu();

        // example 1: int v; v=2; Print(v)

        try {
            IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                            new PrintStatement(new VariableExpression("v"))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex1.typeCheck(typeEnv);
            ProgramState prg1 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex1);
            IRepository repo1 = new Repository(prg1, "ex1.txt");
            Controller controller1 = new Controller(repo1);
            menu.addCommand(new RunExample("1", "int v; v = 2; print(v)", controller1));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 2: int a;int b; a=2+3*5;b=a+1;Print(b)
        try {
            IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                    new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                            new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                    new ArithmeticExpression(new ValueExpression(new IntValue(3)),
                                            new ValueExpression(new IntValue(5)), "*"), "+")),
                                    new CompoundStatement(new AssignStatement("b",
                                            new ArithmeticExpression(new VariableExpression("a"),
                                                    new ValueExpression(new IntValue(1)), "+")),
                                            new PrintStatement(new VariableExpression("b"))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex2.typeCheck(typeEnv);
            ProgramState prg2 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex2);
            IRepository repo2 = new Repository(prg2, "ex2.txt");
            Controller controller2 = new Controller(repo2);
            menu.addCommand(new RunExample("2", "int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)", controller2));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        try {
            IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                    new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                            new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                    new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                            new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                            new PrintStatement(new VariableExpression("v"))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex3.typeCheck(typeEnv);
            ProgramState prg3 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex3);
            IRepository repo3 = new Repository(prg3, "ex3.txt");
            Controller controller3 = new Controller(repo3);
            menu.addCommand(new RunExample("3", "bool a; int v; a = true; IF (a) THEN (v = 2) ELSE (v = 3); print(v)", controller3));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)
        try {
            IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                    new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                            new CompoundStatement(new openRFile(new VariableExpression("varf")),
                                    new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                            new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                            new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new closeRFile(new VariableExpression("varf"))))))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex4.typeCheck(typeEnv);
            ProgramState prg4 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex4);
            IRepository repo4 = new Repository(prg4, "ex4.txt");
            Controller controller4 = new Controller(repo4);
            menu.addCommand(new RunExample("4", "string varf; varf = \"test.in\"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)", controller4));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 5: string varf; varf="test_empty.in"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)
        try {
            IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                    new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test_empty.in"))),
                            new CompoundStatement(new openRFile(new VariableExpression("varf")),
                                    new CompoundStatement(new VariableDeclarationStatement("varc", new IntType()),
                                            new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                            new CompoundStatement(new readFile(new VariableExpression("varf"), "varc"),
                                                                    new CompoundStatement(new PrintStatement(new VariableExpression("varc")), new closeRFile(new VariableExpression("varf"))))))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex5.typeCheck(typeEnv);
            ProgramState prg5 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex5);
            IRepository repo5 = new Repository(prg5, "ex5.txt");
            Controller controller5 = new Controller(repo5);
            menu.addCommand(new RunExample("5", "string varf; varf = \"test_empty.in\"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)", controller5));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 6: print(2 + 3 < 1); (false)
        try {
            IStatement ex6 = new PrintStatement(new RelationalExpression(
                    new ArithmeticExpression(new ValueExpression(new IntValue(2)), new ValueExpression(new IntValue(3)), "+"),
                    new ValueExpression(new IntValue(1)), "<"));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex6.typeCheck(typeEnv);
            ProgramState prg6 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex6);
            IRepository repo6 = new Repository(prg6, "ex6.txt");
            Controller controller6 = new Controller(repo6);
            menu.addCommand(new RunExample("6", "print(2 + 3 < 1);", controller6));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 7: print(51 / 3 == 15 + 2); (true)
        try {
            IStatement ex7 = new PrintStatement(new RelationalExpression(new ArithmeticExpression(
                    new ValueExpression(new IntValue(51)), new ValueExpression(new IntValue(3)), "/"),
                    new ArithmeticExpression(new ValueExpression(new IntValue(15)), new ValueExpression(new IntValue(2)), "+"),
                    "=="));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex7.typeCheck(typeEnv);
            ProgramState prg7 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex7);
            IRepository repo7 = new Repository(prg7, "ex7.txt");
            Controller controller7 = new Controller(repo7);
            menu.addCommand(new RunExample("7", "print(51 / 3 == 15 + 2);", controller7));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 8: Ref int v;new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)
        try {
            IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new New("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompoundStatement(new New("a", new VariableExpression("v")),
                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new VariableExpression("a")))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex8.typeCheck(typeEnv);
            ProgramState prg8 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex8);
            IRepository repo8 = new Repository(prg8, "ex8.txt");
            Controller controller8 = new Controller(repo8);
            menu.addCommand(new RunExample("8", "Ref int v;new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)", controller8));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 9: Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
        try {
            IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new New("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompoundStatement(new New("a", new VariableExpression("v")),
                                            new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v"))),
                                                    new PrintStatement(new ArithmeticExpression(new readFromHeap(new readFromHeap(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), "+")))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex9.typeCheck(typeEnv);
            ProgramState prg9 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex9);
            IRepository repo9 = new Repository(prg9, "ex9.txt");
            Controller controller9 = new Controller(repo9);
            menu.addCommand(new RunExample("9", "Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)", controller9));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 10: Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5);
        try {
            IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new New("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new PrintStatement(new readFromHeap(new VariableExpression("v"))),
                                    new CompoundStatement(new writeToHeap("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new ArithmeticExpression(new readFromHeap(new VariableExpression("v")), new ValueExpression(new IntValue(5)), "+"))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex10.typeCheck(typeEnv);
            ProgramState prg10 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex10);
            IRepository repo10 = new Repository(prg10, "ex10.txt");
            Controller controller10 = new Controller(repo10);
            menu.addCommand(new RunExample("10", "Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5);", controller10));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 11: Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
        try {
            IStatement ex11 = new CompoundStatement(new VariableDeclarationStatement("v", new RefType(new IntType())),
                    new CompoundStatement(new New("v", new ValueExpression(new IntValue(20))),
                            new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new RefType(new IntType()))),
                                    new CompoundStatement(new New("a", new VariableExpression("v")),
                                            new CompoundStatement(new New("v", new ValueExpression(new IntValue(30))),
                                                    new PrintStatement(new readFromHeap(new readFromHeap(new VariableExpression("a")))))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex11.typeCheck(typeEnv);
            ProgramState prg11 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex11);
            IRepository repo11 = new Repository(prg11, "ex11.txt");
            Controller controller11 = new Controller(repo11);
            menu.addCommand(new RunExample("11", "Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))", controller11));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 12: int v; v=4; (while (v>0) print(v); v=v-1); print(v)
        try {
            IStatement ex12 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                            new CompoundStatement(new WhileStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                            new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), "-")))),
                                    new PrintStatement(new VariableExpression("v")))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex12.typeCheck(typeEnv);
            ProgramState prg12 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex12);
            IRepository repo12 = new Repository(prg12, "ex12.txt");
            Controller controller12 = new Controller(repo12);
            menu.addCommand(new RunExample("12", "int v; v=4; (while (v>0) print(v); v=v-1); print(v)", controller12));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 13: int v; Ref int a; v = 10; new(a,22);
        //             fork(wH(a,30); v = 32; print(v); print(rH(a)));
        //             print(v); print(rH(a))
        try {
            IStatement ex13 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new VariableDeclarationStatement("a", new RefType(new IntType())),
                            new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                    new CompoundStatement(new New("a", new ValueExpression(new IntValue(22))),
                                            new CompoundStatement(new forkStatement(new CompoundStatement(new writeToHeap("a", new ValueExpression(new IntValue(30))),
                                                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                            new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new readFromHeap(new VariableExpression("a"))))))),
                                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new readFromHeap(new VariableExpression("a")))))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex13.typeCheck(typeEnv);
            ProgramState prg13 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex13);
            IRepository repo13 = new Repository(prg13, "ex13.txt");
            Controller controller13 = new Controller(repo13);
            menu.addCommand(new RunExample("13", "int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v = 32; print(v); print(rH(a))); print(v); print(rH(a))", controller13));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 14: int count; fork(fork(print(count);count=count+1;));
        try {
            IStatement ex14 = new CompoundStatement(new VariableDeclarationStatement("count", new IntType()),
                    new forkStatement(new forkStatement(
                            new CompoundStatement(new PrintStatement(new VariableExpression("count")),
                                    new AssignStatement("count", new ArithmeticExpression(new VariableExpression("count"), new ValueExpression(new IntValue(1)), "+"))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex14.typeCheck(typeEnv);
            ProgramState prg14 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex14);
            IRepository repo14 = new Repository(prg14, "ex14.txt");
            Controller controller14 = new Controller(repo14);
            menu.addCommand(new RunExample("14", "int count; fork(fork(print(count);count=count+1;));", controller14));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // example 15: int v; v=10; fork(print(v)); fork(print(v)); v=8; print(v)
        try {
            IStatement ex15 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignStatement("v", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(new forkStatement(new PrintStatement(new VariableExpression("v"))),
                                    new CompoundStatement(new forkStatement(new PrintStatement(new VariableExpression("v"))),
                                            new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(8))),
                                                    new PrintStatement(new VariableExpression("v")))))));
            IDictionary<String, IType> typeEnv = new MyDictionary<>();
            ex15.typeCheck(typeEnv);
            ProgramState prg15 = new ProgramState(new MyStack<IStatement>(), new MyDictionary<String, IValue>(),
                    new MyList<IValue>(), new MyFileTable<StringValue, BufferedReader>(), new MyHeapTable<IValue>(), ex15);
            IRepository repo15 = new Repository(prg15, "ex15.txt");
            Controller controller15 = new Controller(repo15);
            menu.addCommand(new RunExample("15", "int v; v=10; fork(print(v)); fork(print(v)); v=8; print(v)", controller15));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

       /* TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExample("1", "int v; v = 2; print(v)", controller1));
        menu.addCommand(new RunExample("2", "int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)", controller2));
        menu.addCommand(new RunExample("3", "bool a; int v; a = true; IF (a) THEN (v = 2) ELSE (v = 3); print(v)", controller3));
        menu.addCommand(new RunExample("4", "string varf; varf = \"test.in\"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)", controller4));
        menu.addCommand(new RunExample("5", "string varf; varf = \"test_empty.in\"; openRFile(varf); int varc; readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)", controller5));
        menu.addCommand(new RunExample("6", "print(2 + 3 < 1);", controller6));
        menu.addCommand(new RunExample("7", "print(51 / 3 == 15 + 2);", controller7));
        menu.addCommand(new RunExample("8", "Ref int v;new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)", controller8));
        menu.addCommand(new RunExample("9", "Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)", controller9));
        menu.addCommand(new RunExample("10", "Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5);", controller10));
        menu.addCommand(new RunExample("11", "Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))", controller11));
        menu.addCommand(new RunExample("12", "int v; v=4; (while (v>0) print(v); v=v-1); print(v)", controller12));
        menu.addCommand(new RunExample("13", "int v; Ref int a; v = 10; new(a,22); fork(wH(a,30); v = 32; print(v); print(rH(a))); print(v); print(rH(a))", controller13));
        menu.addCommand(new RunExample("14", "int count; fork(fork(print(count);count=count+1;));", controller14));
        menu.addCommand(new RunExample("15", "int v; v=10; fork(print(v)); fork(print(v)); v=8; print(v)", controller15));*/

        menu.show();
    }
}
