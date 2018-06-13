package controllers;

import org.sunbird.common.models.util.ActorOperations;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.LoggerEnum;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.request.Request;
import org.sunbird.controllers.BaseController;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Result;

/**
 * User controller handles User APIs.
 *
 * @author Amit Kumar
 */
public class UserController extends BaseController {
  /**
   * Create user API.
   *
   * @return Return a promise for create user API result.
   */
  public F.Promise<Result> create() {
    try {
      Request reqObj = process(ActorOperations.CREATE_USER.getValue(), JsonKey.CREATE);
      reqObj.getRequest().put(JsonKey.CREATED_BY, ctx().flash().get(JsonKey.USER_ID));
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(
          createCommonExceptionResult(request().path(), e, request().method()));
    }
  }

  /**
   * Update user API.
   *
   * @return Return a promise for update user API result.
   */
  public F.Promise<Result> update() {
    try {
      Request reqObj = process(ActorOperations.UPDATE_USER.getValue(), JsonKey.UPDATE);
      reqObj.getRequest().put(JsonKey.UPDATED_BY, ctx().flash().get(JsonKey.USER_ID));
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(
          createCommonExceptionResult(request().path(), e, request().method()));
    }
  }

  private Request process(String actorOperation, String operation) {
    ProjectLogger.log("UserController : " + operation + " : start ", LoggerEnum.INFO.name());
    Request reqObj = createAndInitRequest(actorOperation, request().body().asJson());
    ProjectUtil.updateMapSomeValueTOLowerCase(reqObj);
    reqObj.setRequest(reqObj.getRequest());
    return reqObj;
  }
}
