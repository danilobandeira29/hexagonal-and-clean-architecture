package br.com.danilobandeira29.application;

public abstract class UnitUseCase<INPUT> {
    public abstract void execute(INPUT input);
}
