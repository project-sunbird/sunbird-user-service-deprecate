package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * This controller is used to handel the user socialMedia accounts information
 */
public class UserProfileReadController extends BaseController {

    /**
     *This method is used get user SocialMedia type as list
     * @return a CompletionStage of user Medial Type info Api Result.
     */

    public CompletionStage<Result> getProfileSupportedSocialMediaTypes() {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(getDummyResponse());
      return cf.thenApplyAsync(Results::ok);
  }

    /**
     * This method is used to set the profile visibility of user
     * @return Return a CompletionsTAGE OF set user profile visibility Api Result
     */
  public CompletionStage<Result> setProfileVisibility() {
      Http.RequestBody requestBody = request().body();
      CompletableFuture<String> cf = new CompletableFuture<>();
      cf.complete(getDummyResponse());
      return cf.thenApplyAsync(Results::ok);
  }


    /**
     * This method is used to get the user with their respective types
     * @return Return a CompletionsTAGE OF get user by type  Api Result
     */
    public CompletionStage<Result> getUserTypes() {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);
    }

    /**
     * This method is used to get user by Id
     * @return Return a CompletionsTAGE OF get user by id Api Result
     */
    public CompletionStage<Result> getUserById(String userId) {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);

    }
    /**
     * This method is used to get user by id but some extra fields appear in the response like skills etc.
     * @return Return a promise of user response
     */
    public CompletionStage<Result> getUserByIdV2(String userId) {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);
    }
}
