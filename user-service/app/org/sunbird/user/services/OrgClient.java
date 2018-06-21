package org.sunbird.user.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.ElasticSearchUtil;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.models.util.ProjectUtil.EsIndex;
import org.sunbird.common.models.util.ProjectUtil.EsType;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.dto.SearchDTO;

public class OrgClient {

  private OrgClient() {}

  /**
   * This method will validate channel and return the id of organization associated with this
   * channel.
   *
   * @param channel value of channel of an organization
   * @return Id of Root organization.
   */
  public static String getRootOrgIdFromChannel(String channel) {
    Map<String, Object> filters = new HashMap<>();
    filters.put(JsonKey.IS_ROOT_ORG, true);
    filters.put(JsonKey.CHANNEL, channel);
    List<Map<String, Object>> esContent = getContentByFiltersFromES(filters);
    if (CollectionUtils.isNotEmpty(esContent)) {
      return (String) esContent.get(0).get(JsonKey.ID);
    } else {
      if (StringUtils.isNotBlank(channel)) {
        throwInvalidParamValueException(JsonKey.CHANNEL, channel);
      } else {
        throwMandatoryParamMissingException(JsonKey.CHANNEL);
      }
    }
    return channel;
  }

  private static List<Map<String, Object>> getContentByFiltersFromES(Map<String, Object> filters) {
    SearchDTO searchDTO = new SearchDTO();
    searchDTO.getAdditionalProperties().put(JsonKey.FILTERS, filters);
    Map<String, Object> esResult =
        ElasticSearchUtil.complexSearch(
            searchDTO, EsIndex.sunbird.getIndexName(), EsType.organisation.getTypeName());
    if (MapUtils.isNotEmpty(esResult)
        && CollectionUtils.isNotEmpty((List) esResult.get(JsonKey.CONTENT))) {
      return ((List<Map<String, Object>>) esResult.get(JsonKey.CONTENT));
    }
    return Collections.emptyList();
  }

  private static void throwMandatoryParamMissingException(String param) {
    throw new ProjectCommonException(
        ResponseCode.mandatoryParamsMissing.getErrorCode(),
        ProjectUtil.formatMessage(ResponseCode.mandatoryParamsMissing.getErrorMessage(), param),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }

  private static void throwInvalidParamValueException(String param, String paramValue) {
    throw new ProjectCommonException(
        ResponseCode.invalidParameterValue.getErrorCode(),
        ProjectUtil.formatMessage(
            ResponseCode.invalidParameterValue.getErrorMessage(), paramValue, param),
        ResponseCode.CLIENT_ERROR.getResponseCode());
  }
}
