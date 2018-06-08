package org.sunbird.user.validators;

import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.request.BaseRequestValidator;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

public class BaseValidator extends BaseRequestValidator {

  protected final int ERROR_CODE = ResponseCode.CLIENT_ERROR.getResponseCode();

  public void validateDeletedEntity(Map<String, Object> entity, String operation, String type) {
    // For create user API ignore isDeleted
    if (JsonKey.UPDATE.equalsIgnoreCase(operation)
        && entity.containsKey(JsonKey.IS_DELETED)
        && null != entity.get(JsonKey.IS_DELETED)
        && ((boolean) entity.get(JsonKey.IS_DELETED))
        && StringUtils.isBlank((String) entity.get(JsonKey.ID))) {
      checkMandatoryParamsPresent(
          entity,
          ProjectUtil.formatMessage(ResponseMessage.Message.DOT_FORMAT, type, JsonKey.ID),
          (String) entity.get(JsonKey.ID));
    }
  }
}
