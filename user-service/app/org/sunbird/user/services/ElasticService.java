package org.sunbird.user.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.sunbird.common.ElasticSearchUtil;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil.EsIndex;
import org.sunbird.common.models.util.ProjectUtil.EsType;
import org.sunbird.dto.SearchDTO;

/**
 * This class will provide elastic search helper methods for user APIs.
 *
 * @author Amit Kumar
 */
public class ElasticService {

  private ElasticService() {}

  public static List<Map<String, Object>> getContentByFiltersFromES(Map<String, Object> filters) {
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
}
