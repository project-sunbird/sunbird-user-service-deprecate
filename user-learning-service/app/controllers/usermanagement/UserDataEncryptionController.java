package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;


/**
 * This controller is used to encrypt the user sensitive fields link email.
 */
public class UserDataEncryptionController extends BaseController {


    /**
     * This method will encrypt user all PI/private data
     *
     * @return Return a CompletableFuture of success response
     */
    public CompletionStage<Result> encrypt() {
        return handelRequest();
    }

    /**
     * This method will decrypt user private data.
     *
     * @return Return a CompletableFuture of success response
     */

    public CompletionStage<Result> decrypt() {
        return handelRequest();
    }
}
