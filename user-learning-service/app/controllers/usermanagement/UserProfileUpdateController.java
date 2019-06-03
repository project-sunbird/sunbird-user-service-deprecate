package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UserProfileUpdateController extends BaseController {

    /**
     * Updates current login time for given user in Keycloak.
     *
     * @return Return a promise for update login time API result.
     */

    public CompletionStage<Result> updateLoginTime() {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);
    }

    /**
     * This action method is uses to assign Roles to the user
     * @return success response
     */
    public CompletionStage<Result> assignRoles() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);
    }
    /**
     * This method is used to update user details
     * @return Return a promise of updated userResponse
     */
    public CompletionStage<Result> updateUser() {
        Http.RequestBody requestBody = request().body();
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(getDummyResponse());
        return cf.thenApplyAsync(Results::ok);

    }
}
