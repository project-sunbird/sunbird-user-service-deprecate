package controllers.usermanagement;

import play.mvc.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class UserController extends Controller {


    String dummyResponse = "{\"id\":\"api.private.user.migrate\",\"ver\":\"v1\",\"ts\":\"2019-01-17 16:53:26:286+0530\",\"params\":{\"resmsgid\":null,\"msgid\":\"8e27cbf5-e299-43b0-bca7-8347f7ejk5abcf\",\"err\":null,\"status\":\"success\",\"errmsg\":null},\"responseCode\":\"OK\",\"result\":{\"response\":{\"response\":\"SUCCESS\",\"errors\":[]}}}";

    public CompletionStage<Result> createUser() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);

    }

    public CompletionStage<Result> createUserV2() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);
    }

    public CompletionStage<Result> updateUser() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);

    }

    public CompletionStage<Result> getUserById(String userId) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);

    }


    public CompletionStage<Result> getUserByIdV2(String userId) {

        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);
    }

    public CompletionStage<Result> getUserByLoginId() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);

    }

    public CompletionStage<Result> getUserByKey(String idType, String id) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);


    }

    public CompletionStage<Result> searchUser() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);

    }

    private CompletionStage<Result> handleGetUserProfile(String operation, String userId) {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete(dummyResponse);
        return cf.thenApplyAsync(Results::ok);


    }
}