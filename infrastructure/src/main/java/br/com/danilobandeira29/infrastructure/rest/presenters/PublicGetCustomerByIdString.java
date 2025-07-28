package br.com.danilobandeira29.infrastructure.rest.presenters;

import br.com.danilobandeira29.application.Presenter;
import br.com.danilobandeira29.application.customer.GetCustomerByIdUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PublicGetCustomerByIdString implements Presenter<Optional<GetCustomerByIdUseCase.Output>, Object> {

    private static final Logger LOG = LoggerFactory.getLogger(PublicGetCustomerByIdString.class);

    @Override
    public String present(Optional<GetCustomerByIdUseCase.Output> out) {
        return out
                .map(o -> o.id())
                .orElseGet(() -> "not found");
    }

    @Override
    public String present(Throwable error) {
        LOG.error("An error was observer at PublicGetCustomerByIdString", error);
        return error.getMessage();
    }
}
