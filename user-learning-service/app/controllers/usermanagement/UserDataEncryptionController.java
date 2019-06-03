package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


/**
 * This controller is used to encrypt the user sensitive fields link email.
 */
public class UserDataEncryptionController extends BaseController {


    /**
     * This action method is used to encrypt the user data
     * @return Return a CompletableFuture of success response
     */
  public CompletionStage<Result> encrypt() {
      Http.RequestBody requestBody = request().body();
      CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(getDummyResponse());
      return cf.thenApplyAsync(Results::ok);
  }
    /**
     * This action method is used to decrypt the user data
     * @return Return a CompletableFuture of success response
     */

  public CompletionStage<Result> decrypt() {
      Http.RequestBody requestBody = request().body();
      CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(getDummyResponse());
      return cf.thenApplyAsync(Results::ok);
  }
}
