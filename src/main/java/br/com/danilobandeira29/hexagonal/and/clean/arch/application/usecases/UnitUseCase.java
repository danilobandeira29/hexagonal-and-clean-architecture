package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases;

public abstract class UnitUseCase<INPUT> {
    public abstract void execute(INPUT input);
}
