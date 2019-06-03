package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;

public class UserProfileUpdateController extends BaseController {

    /**
     * Updates current login time for given user in Keycloak.
     *
     * @return Return a promise for update login time API result.
     */

    public CompletionStage<Result> updateLoginTime() {
        return handelRequest();
    }

    /**
     * This action method is uses to assign Roles to the user
     *
     * @return success response
     */
    public CompletionStage<Result> assignRoles() {
        return handelRequest();
    }

    /**
     * This method is used to update user details
     *
     * @return Return a promise of updated userResponse
     */
    public CompletionStage<Result> updateUser() {
        return handelRequest();

    }
}
