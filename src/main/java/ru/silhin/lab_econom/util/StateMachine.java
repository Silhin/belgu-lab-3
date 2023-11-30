package ru.silhin.lab_econom.util;

public class StateMachine {
    private static final StateMachine INSTANCE = new StateMachine();
    public static StateMachine getInstance() {
        return INSTANCE;
    }

    public State next(State state) {
        return state != State.SCREEN14 ? State.values()[state.ordinal() + 1] : State.SCREEN14;
    }

    public State back(State state) {
        return state != State.INIT ? State.values()[state.ordinal() - 1] : State.INIT;
    }
}
