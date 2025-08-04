package br.com.danilobandeira29.infrastructure.rest;

import br.com.danilobandeira29.application.partner.CreatePartnerUseCase;
import br.com.danilobandeira29.application.partner.GetPartnerByIdUseCase;
import br.com.danilobandeira29.domain.exceptions.ValidationException;
import br.com.danilobandeira29.infrastructure.dtos.NewPartnerDTO;
import br.com.danilobandeira29.infrastructure.http.HttpRouter;

import java.net.URI;
import java.util.Objects;

import static br.com.danilobandeira29.infrastructure.http.HttpRouter.*;

// Para Java e Spring não vale essa abstração
// Driving Adapter
public class PartnerFnController {

    private final CreatePartnerUseCase createPartnerUseCase;
    private final GetPartnerByIdUseCase getPartnerByIdUseCase;

    public PartnerFnController(final CreatePartnerUseCase createPartnerUseCase, final GetPartnerByIdUseCase getPartnerByIdUseCase) {
        this.createPartnerUseCase = Objects.requireNonNull(createPartnerUseCase);
        this.getPartnerByIdUseCase = Objects.requireNonNull(getPartnerByIdUseCase);
    }

    public HttpRouter bind(final HttpRouter router) {
        router.GET("/partners/{id}", this::get);
        router.POST("/partners", this::create);
        return router;
    }

    private HttpRouter.HttpResponse<?> create(final HttpRequest req) {
        try {
            final var dto = req.body(NewPartnerDTO.class);
            final var output = createPartnerUseCase.execute(new CreatePartnerUseCase.Input(dto.cnpj(), dto.email(), dto.name()));
            return HttpResponse.created(URI.create("/partners/"+output.id())).body(output);
        } catch (ValidationException ex) {
            return HttpResponse.unprocessableEntity().body(ex.getMessage());
        }
    }

    private HttpRouter.HttpResponse<GetPartnerByIdUseCase.Output> get(final HttpRequest req) {
        return getPartnerByIdUseCase.execute(new GetPartnerByIdUseCase.Input(req.pathParam("id")))
                .map(HttpResponse::ok)
                .orElseGet(HttpResponse.notFound()::build);
    }

}
