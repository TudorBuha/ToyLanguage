package Repository;

import Model.ProgramState.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgramState();
    void addProgramState(ProgramState newProgramState);
}
