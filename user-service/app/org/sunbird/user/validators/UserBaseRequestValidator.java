package org.sunbird.user.validators;

import java.util.Map;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.request.BaseRequestValidator;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

public class UserBaseRequestValidator extends BaseRequestValidator {

  protected final int ERROR_CODE = ResponseCode.CLIENT_ERROR.getResponseCode();

  /**
   * Validates if ID is present for element (e.g. address, education, job profile) marked for
   * deletion in case of update request. If validation fails, then a custom exception is thrown.
   *
   * @param element Element details
   * @param operation Type of operation
   * @param type Element type (e.g. address)
   */
  public void validateDeletion(Map<String, Object> element, String operation, String type) {
    // For create user API ignore isDeleted
    if (JsonKey.UPDATE.equalsIgnoreCase(operation)
        && element.containsKey(JsonKey.IS_DELETED)
        && null != element.get(JsonKey.IS_DELETED)
        && ((boolean) element.get(JsonKey.IS_DELETED))) {
      checkMandatoryParamsPresent(
          element,
          ProjectUtil.formatMessage(ResponseMessage.Message.DOT_FORMAT, type, JsonKey.ID),
          (String) element.get(JsonKey.ID));
    }
  }
}
