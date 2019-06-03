package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.*;

import java.util.concurrent.CompletionStage;


/**
 * This controller is used to handel request related to user based updation and creation.
 */
public class UserController extends BaseController {

    /**
     * This method is used to create user
     *
     * @return Return a promise of created userResponse
     */
    public CompletionStage<Result> createUser() {
        return handelRequest();

    }

    /**
     * This method is used to create user in this method userName is not mandatory
     *
     * @return Return a promise of created userResponse
     */
    public CompletionStage<Result> createUserV2() {
        return handelRequest();
    }


}