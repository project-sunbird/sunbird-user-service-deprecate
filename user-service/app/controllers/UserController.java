package controllers;

import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.request.Request;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.controllers.BaseController;
import org.sunbird.user.utils.Constant;
import org.sunbird.user.utils.Message;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Http.RequestBody;
import play.mvc.Result;

/**
 * User controller handles User APIs.
 *
 * @author Amit Kumar
 */
public class UserController extends BaseController {
  /**
   * Create user API. Request body with content type application/json.
   *
   * @return Return a promise for create user API result.
   */
  public F.Promise<Result> create() {
    try {
      Request request = new Request();
      request.setOperation(Constant.CREATE_USER);
      RequestBody reqBody = request().body();
      if (reqBody == null) {
        // throwing invalid data exception.
        throw createClientExceptionWithInvalidData(Message.INVALID_REQ_BODY_MSG_ERROR);
      }
      return actorResponseHandler(getActorRef(), request, timeout, "", request());
    } catch (Exception e) {
      ProjectLogger.log(e.getMessage(), e);
      return Promise.<Result>pure(
          createCommonExceptionResult(request().path(), e, request().method()));
    }
  }

  /**
   * Update user API. Request body with content type application/json.
   *
   * @return Return a promise for update user API result.
   */
  public F.Promise<Result> update() {
    try {
      Request request = new Request();
      request.setOperation(Constant.UPDATE_USER);
      RequestBody reqBody = request().body();
      if (reqBody == null) {
        // throwing invalid data exception.
        throw createClientExceptionWithInvalidData(Message.INVALID_REQ_BODY_MSG_ERROR);
      }
      return actorResponseHandler(getActorRef(), request, timeout, "", request());
    } catch (Exception e) {
      ProjectLogger.log(e.getMessage(), e);
      return Promise.<Result>pure(
          createCommonExceptionResult(request().path(), e, request().method()));
    }
  }

  private ProjectCommonException createClientExceptionWithInvalidData(String message) {
    String exceptionMessage = message;
    if (StringUtils.isBlank(exceptionMessage)) {
      exceptionMessage = Message.DEFAULT_MSG_ERROR;
    }
    return new ProjectCommonException(
        ResponseCode.invalidRequestData.getErrorCode(),
        exceptionMessage,
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }
}
