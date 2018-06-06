package org.sunbird.user.validators;

import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;

public class UserEducationValidator {

  private static final int ERROR_CODE = ResponseCode.CLIENT_ERROR.getResponseCode();

  private UserEducationValidator() {}

  /**
   * Method to validate educational details of the user
   *
   * @param userRequest
   */
  @SuppressWarnings("unchecked")
  public static void educationValidation(Map<String, Object> userRequest) {
    Map<String, Object> reqMap;
    if (userRequest.containsKey(JsonKey.EDUCATION) && null != userRequest.get(JsonKey.EDUCATION)) {
      if (!(userRequest.get(JsonKey.EDUCATION) instanceof List)) {
        throw new ProjectCommonException(
            ResponseCode.dataTypeError.getErrorCode(),
            ProjectUtil.formatMessage(
                ResponseCode.dataTypeError.getErrorMessage(), JsonKey.EDUCATION, JsonKey.LIST),
            ERROR_CODE);
      } else if (userRequest.get(JsonKey.EDUCATION) instanceof List) {
        List<Map<String, Object>> reqList =
            (List<Map<String, Object>>) userRequest.get(JsonKey.EDUCATION);
        for (int i = 0; i < reqList.size(); i++) {
          reqMap = reqList.get(i);
          if (StringUtils.isBlank((String) reqMap.get(JsonKey.NAME))) {
            throwMandatoryParamMissingException(
                ProjectUtil.formatMessage(
                    ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.NAME));
          }
          if (StringUtils.isBlank((String) reqMap.get(JsonKey.DEGREE))) {
            throwMandatoryParamMissingException(
                ProjectUtil.formatMessage(
                    ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.DEGREE));
          }
          if (reqMap.containsKey(JsonKey.ADDRESS) && null != reqMap.get(JsonKey.ADDRESS)) {
            UserAddressValidator.validateAddress(
                (Map<String, Object>) reqMap.get(JsonKey.ADDRESS), JsonKey.EDUCATION);
          }
        }
      }
    }
  }

  private static void throwMandatoryParamMissingException(String paramName) {
    throw new ProjectCommonException(
        ResponseCode.mandatoryParamsMissing.getErrorCode(),
        ProjectUtil.formatMessage(ResponseCode.mandatoryParamsMissing.getErrorMessage(), paramName),
        ERROR_CODE);
  }

  public static void validateUpdateUserEducation(Map<String, Object> userRequest) {
    List<Map<String, Object>> reqList =
        (List<Map<String, Object>>) userRequest.get(JsonKey.EDUCATION);
    if (CollectionUtils.isNotEmpty(reqList))
      for (int i = 0; i < reqList.size(); i++) {
        Map<String, Object> reqMap = reqList.get(i);
        if (reqMap.containsKey(JsonKey.IS_DELETED)
            && null != reqMap.get(JsonKey.IS_DELETED)
            && ((boolean) reqMap.get(JsonKey.IS_DELETED))
            && StringUtils.isBlank((String) reqMap.get(JsonKey.ID))) {
          throwMandatoryParamMissingException(
              ProjectUtil.formatMessage(
                  ResponseMessage.Message.DOT_FORMAT, JsonKey.EDUCATION, JsonKey.ID));
        }
        if (!reqMap.containsKey(JsonKey.IS_DELETED)
            || (reqMap.containsKey(JsonKey.IS_DELETED)
                && (null == reqMap.get(JsonKey.IS_DELETED)
                    || !(boolean) reqMap.get(JsonKey.IS_DELETED)))) {
          educationValidation(userRequest);
        }
      }
  }
}
