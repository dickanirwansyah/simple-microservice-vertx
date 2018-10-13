package io.vertx.book.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HelloMicroservice extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/").handler(this::getHello);
        router.get("/:name").handler(this::getHello);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

    private void getHello(RoutingContext rc){
        String message = "Hallo";
        if (rc.pathParam("name") != null){
            message += " "+rc.pathParam("name");
        }
        JsonObject json = new JsonObject().put("message", message);
        rc.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .end(json.encode());
    }
}
