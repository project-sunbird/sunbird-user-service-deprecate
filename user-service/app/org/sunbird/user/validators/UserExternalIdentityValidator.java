package org.sunbird.user.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;
import org.sunbird.user.utils.Constant;

/**
 * This class will provide helper methods to validate user external identity.
 *
 * @author Amit Kumar
 */
public class UserExternalIdentityValidator extends BaseValidator {

  // valid operation type for externalIds in user api.
  private List<String> operationTypeList =
      Arrays.asList(Constant.ADD, Constant.REMOVE, Constant.EDIT);

  public void externalIdsValidation(Map<String, Object> userRequest, String operation) {
    validateListParam(userRequest, Constant.EXTERNAL_IDS);
    List<Map<String, String>> externalIds =
        (List<Map<String, String>>) userRequest.get(Constant.EXTERNAL_IDS);
    validateIndividualExternalId(operation, externalIds);
  }

  private void validateIndividualExternalId(
      String operation, List<Map<String, String>> externalIds) {
    externalIds
        .stream()
        .forEach(
            s -> {
              validateExternalIdMandatoryParam(JsonKey.ID, s.get(JsonKey.ID));
              validateExternalIdMandatoryParam(JsonKey.PROVIDER, s.get(JsonKey.PROVIDER));
              validateExternalIdMandatoryParam(Constant.ID_TYPE, s.get(Constant.ID_TYPE));
              // check for invalid operation type
              if (StringUtils.isNotBlank(s.get(JsonKey.OPERATION))
                  && (!operationTypeList.contains((s.get(JsonKey.OPERATION)).toLowerCase()))) {
                throw new ProjectCommonException(
                    ResponseCode.invalidValue.getErrorCode(),
                    ProjectUtil.formatMessage(
                        ResponseCode.invalidValue.getErrorMessage(),
                        (Constant.EXTERNAL_IDS + "." + JsonKey.OPERATION),
                        s.get(JsonKey.OPERATION),
                        String.join(",", operationTypeList)),
                    ERROR_CODE);
              }
              // throw exception for invalid operation if requested operation type is
              // other than add or null for create user api
              if (JsonKey.CREATE.equalsIgnoreCase(operation)
                  && StringUtils.isNotBlank(s.get(JsonKey.OPERATION))
                  && (!Constant.ADD.equalsIgnoreCase(s.get(JsonKey.OPERATION)))) {
                throw new ProjectCommonException(
                    ResponseCode.invalidValue.getErrorCode(),
                    ProjectUtil.formatMessage(
                        ResponseCode.invalidValue.getErrorMessage(),
                        (Constant.EXTERNAL_IDS + "." + JsonKey.OPERATION),
                        s.get(JsonKey.OPERATION),
                        Constant.ADD),
                    ERROR_CODE);
              }
            });
  }

  private void validateExternalIdMandatoryParam(String param, String paramValue) {
    if (StringUtils.isBlank(paramValue)) {
      throw new ProjectCommonException(
          ResponseCode.mandatoryParamsMissing.getErrorCode(),
          ProjectUtil.formatMessage(
              ResponseCode.mandatoryParamsMissing.getErrorMessage(),
              ProjectUtil.formatMessage(
                  ResponseMessage.Message.DOT_FORMAT, Constant.EXTERNAL_IDS, param)),
          ERROR_CODE);
    }
  }
}
