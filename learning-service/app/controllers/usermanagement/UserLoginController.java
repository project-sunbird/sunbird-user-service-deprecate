package controllers.usermanagement;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UserLoginController extends Controller {
  /**
   * Updates current login time for given user in Keycloak.
   *
   * @return Return a promise for update login time API result.
   */

  String dummyResponse = "{\"id\":\"api.private.user.migrate\",\"ver\":\"v1\",\"ts\":\"2019-01-17 16:53:26:286+0530\",\"params\":{\"resmsgid\":null,\"msgid\":\"8e27cbf5-e299-43b0-bca7-8347f7ejk5abcf\",\"err\":null,\"status\":\"success\",\"errmsg\":null},\"responseCode\":\"OK\",\"result\":{\"response\":{\"response\":\"SUCCESS\",\"errors\":[]}}}";
    public CompletionStage<Result> updateLoginTime() {
      CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(dummyResponse);
      return cf.thenApplyAsync(Results::ok);
  }
}
