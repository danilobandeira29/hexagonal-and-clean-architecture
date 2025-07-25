package br.com.danilobandeira29.hexagonal.and.clean.arch.application.usecases;

public abstract class UseCase<INPUT, OUTPUT> {
    public abstract OUTPUT execute(INPUT input);
}
