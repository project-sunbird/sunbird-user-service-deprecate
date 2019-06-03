package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;


/**
 * This controller is used to encrypt the user sensitive fields link email.
 */
public class UserDataEncryptionController extends BaseController {


    /**
     * This action method is used to encrypt the user data
     *
     * @return Return a CompletableFuture of success response
     */
    public CompletionStage<Result> encrypt() {
        return handelRequest();
    }

    /**
     * This action method is used to decrypt the user data
     *
     * @return Return a CompletableFuture of success response
     */

    public CompletionStage<Result> decrypt() {
        return handelRequest();
    }
}
