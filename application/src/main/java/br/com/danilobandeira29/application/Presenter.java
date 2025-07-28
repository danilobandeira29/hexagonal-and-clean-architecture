package br.com.danilobandeira29.application;

public interface Presenter<IN, OUT> {
    OUT present(IN input);
    OUT present(Throwable error);
}
