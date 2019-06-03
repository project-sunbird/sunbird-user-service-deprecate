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


    /**
     * This action method we used to retrive user details on the basis of login Id
     *
     * @return CompletionStage of get User by login Id api result
     */
    public CompletionStage<Result> getUserByLoginId() {
        return handelRequest();

    }

    /**
     * This action method we used to retrive user details on the basis of object.
     *
     * @return CompletionStage of get User by Key api result
     */

    public CompletionStage<Result> getUserByKey(String idType, String id) {
        return handelRequest();


    }

    /**
     * This method is used to search user on the basis of filters
     *
     * @return Return a promise of user response
     */

    public CompletionStage<Result> searchUser() {
        return handelRequest();

    }

}