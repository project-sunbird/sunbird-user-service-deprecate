package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;


/**
 * This Controller is used to handles roles od Entities
 */
public class RolesController extends BaseController {


    /**
     * This action method is used to get the roles of the entity
     *
     * @return CompletionStage of get User api  result
     */

    public CompletionStage<Result> getRoles() {
        return handelRequest();
    }


}
