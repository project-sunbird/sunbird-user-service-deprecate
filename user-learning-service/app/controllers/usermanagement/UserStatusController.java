package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * This controller is used to change status of user
 */



    public class UserStatusController extends BaseController {


    /**
     * This action method is used to block the user
     * @return CompletionStage of block user api result
     */

    public CompletionStage<Result> blockUser() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);
    }
    /**
     * This action method is used to unblock the user
     * @return CompletionStage of unblock user api result
     */

  public CompletionStage<Result> unblockUser() {
      CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(getDummyResponse());
      return cf.thenApplyAsync(Results::ok);
  }
  
}
