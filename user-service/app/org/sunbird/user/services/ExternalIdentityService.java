package org.sunbird.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.user.models.entity.UserExternalIdentity;
import org.sunbird.user.services.dao.impl.UserExternalIdentityDaoImpl;
import org.sunbird.user.services.impl.UserServiceImpl;
import org.sunbird.user.utils.Constant;

public class ExternalIdentityService {

  private static UserService userService = UserServiceImpl.getInstance();
  private static ObjectMapper mapper = new ObjectMapper();

  private ExternalIdentityService() {}

  public static void saveUserExternalIdentity(Map<String, Object> userMap) {
    try {
      List<Map<String, String>> externalIds =
          (List<Map<String, String>>) userMap.get(Constant.EXTERNAL_IDS);
      if (CollectionUtils.isNotEmpty(externalIds)) {
        for (Map<String, String> extIdsMap : externalIds) {
          if (StringUtils.isBlank(extIdsMap.get(JsonKey.OPERATION))
              || Constant.ADD.equalsIgnoreCase(extIdsMap.get(JsonKey.OPERATION))) {
            createUserExternalIdentityData(extIdsMap, userMap);
          }
        }
      }
    } catch (Exception ex) {
      ProjectLogger.log("Exception occurred while creating user_ext_table", ex);
    }
  }

  private static void createUserExternalIdentityData(
      Map<String, String> extIdMap, Map<String, Object> userMap) {
    Map<String, Object> map = new HashMap<>();
    map.put(JsonKey.EXTERNAL_ID, userService.getEncryptedData(extIdMap.get(JsonKey.ID)));
    map.put(JsonKey.PROVIDER, extIdMap.get(JsonKey.PROVIDER));
    map.put(Constant.ID_TYPE, extIdMap.get(Constant.ID_TYPE));
    map.put(JsonKey.USER_ID, userMap.get(JsonKey.USER_ID));
    map.put(JsonKey.CREATED_BY, userMap.get(JsonKey.CREATED_BY));
    map.put(JsonKey.CREATED_ON, new Timestamp(Calendar.getInstance().getTime().getTime()));
    UserExternalIdentity extId = mapper.convertValue(map, UserExternalIdentity.class);
    UserExternalIdentityDaoImpl.getInstance().create(extId);
  }
}
