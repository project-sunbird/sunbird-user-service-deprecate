package controllers.usermanagement;

import controllers.BaseController;
import play.mvc.Result;

import java.util.concurrent.CompletionStage;


/**
 * This controller is used to handle master roles. it will have get all roles, create new roles,remove roles api's .
 * Currently it's having only get all roles api.
 */
public class RolesController extends BaseController {


    /**
     * This method will provide all available roles inside sunbird system.
     *
     * @return CompletionStage of get User api  result
     */

    public CompletionStage<Result> getRoles() {
        return handelRequest();
    }


}
