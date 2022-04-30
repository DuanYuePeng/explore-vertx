package com.github.duanyuepeng.explore_vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);

    router.route(HttpMethod.GET, "/hello").handler(context -> {
      MultiMap queryParams = context.queryParams();
      String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
      context.json(
        new JsonObject()
          .put("message", "Hello " + name + " on " + ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME))
      );
    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888)
      .onSuccess(server ->
        System.out.println(
          "HTTP server started on port " + server.actualPort()
        )
      );
  }
}
