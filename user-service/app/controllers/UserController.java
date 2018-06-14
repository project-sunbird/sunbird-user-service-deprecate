package controllers;

import java.util.Arrays;
import java.util.List;
import org.sunbird.common.models.util.ActorOperations;
import org.sunbird.common.models.util.CustomMapUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.LoggerEnum;
import org.sunbird.common.models.util.ProjectLogger;
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
  List<String> keyList =
      Arrays.asList(JsonKey.USERNAME, JsonKey.LOGIN_ID, JsonKey.SOURCE, JsonKey.PROVIDER);

  /**
   * Create user API.
   *
   * @return Return a promise for create user API result.
   */
  public F.Promise<Result> create() {
    try {
      ProjectLogger.log("UserController: create called", LoggerEnum.DEBUG.name());
      Request reqObj =
          createAndInitRequest(ActorOperations.CREATE_USER.getValue(), request().body().asJson());
      reqObj.getRequest().put(JsonKey.CREATED_BY, ctx().flash().get(JsonKey.USER_ID));
      CustomMapUtils.convertValuesToLower(reqObj.getRequest(), keyList);
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
      ProjectLogger.log("UserController: update called", LoggerEnum.DEBUG.name());
      Request reqObj =
          createAndInitRequest(ActorOperations.UPDATE_USER.getValue(), request().body().asJson());
      reqObj.getRequest().put(JsonKey.UPDATED_BY, ctx().flash().get(JsonKey.USER_ID));
      CustomMapUtils.convertValuesToLower(reqObj.getRequest(), keyList);
      return actorResponseHandler(getActorRef(), reqObj, timeout, null, request());
    } catch (Exception e) {
      return Promise.<Result>pure(
          createCommonExceptionResult(request().path(), e, request().method()));
    }
  }
}
