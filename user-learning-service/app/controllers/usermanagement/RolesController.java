package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


/**
 * This Controller is used to handles roles od Entities
 */
public class RolesController extends BaseController {


    /**
     * This action method is used to get the roles of the entity
     * @return CompletionStage of get User api  result
     */

    public CompletionStage<Result> getRoles() {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(getDummyResponse());
      return cf.thenApplyAsync(Results::ok);
  }


}
