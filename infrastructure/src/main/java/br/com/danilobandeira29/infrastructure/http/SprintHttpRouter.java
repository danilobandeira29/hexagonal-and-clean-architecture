package br.com.danilobandeira29.infrastructure.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Optional;

public class SprintHttpRouter implements HttpRouter {

    private final Logger LOG = LoggerFactory.getLogger(SprintHttpRouter.class);
    private final RouterFunctions.Builder router;

    public SprintHttpRouter() {
        this.router = RouterFunctions.route();
    }

    public RouterFunctions.Builder router() {
        return this.router;
    }

    @Override
    public <T> HttpRouter POST(String pattern, HttpHandler<T> handler) {
        this.router.POST(pattern, wrapHandler(pattern, handler));
        return this;
    }

    private <T> HandlerFunction<ServerResponse> wrapHandler(String pattern, HttpHandler<T> handler) {
        return req -> {
            try {
                var res = handler.handle(new SprintHttpRequest(req));
                return ServerResponse
                        .status(res.statusCode())
                        .headers(headers -> res.headers().forEach(headers::add))
                        .body(res.body());
            } catch (Throwable t) {
                LOG.error("Unexpected error was observed at %s".formatted(pattern), t);
                return ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unexpected error was observed.");
            }
        };
    }

    @Override
    public <T> HttpRouter GET(String pattern, HttpHandler<T> handler) {
        this.router.GET(pattern, wrapHandler(pattern, handler));
        return this;
    }

    public record SprintHttpRequest(ServerRequest request) implements HttpRequest {

        @Override
        public <T> T body(final Class<T> tClass) {
            try {
                return this.request.body(tClass);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        }

        @Override
        public String pathParam(String params) {
            return request.pathVariable(params);
        }

        @Override
        public Optional<String> queryParam(String params) {
            return request.param(params);
        }
    }
}
