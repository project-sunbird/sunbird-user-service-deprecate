package org.sunbird.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.user.models.entity.Address;
import org.sunbird.user.models.entity.JobProfile;
import org.sunbird.user.services.dao.impl.JobProfileDaoImpl;

public class JobProfileService {

  private static ObjectMapper mapper = new ObjectMapper();

  private JobProfileService() {}

  @SuppressWarnings("unchecked")
  public static void saveJobProfileDetails(Map<String, Object> userMap) {
    List<Map<String, Object>> jobProfileList =
        (List<Map<String, Object>>) userMap.get(JsonKey.JOB_PROFILE);
    for (Map<String, Object> jobProfile : jobProfileList) {
      // primary key for education entity
      jobProfile.put(JsonKey.ID, ProjectUtil.getUniqueIdFromTimestamp(1));
      String addressId = "";
      if (MapUtils.isNotEmpty((Map<String, Object>) jobProfile.get(JsonKey.ADDRESS))) {
        Map<String, Object> address = (Map<String, Object>) jobProfile.get(JsonKey.ADDRESS);
        address.put(JsonKey.CREATED_BY, userMap.get(JsonKey.ID));
        addressId = AddressService.saveAddress(mapper.convertValue(address, Address.class));
        if (StringUtils.isNotBlank(addressId)) {
          jobProfile.put(JsonKey.ADDRESS_ID, addressId);
        }
      }
      jobProfile.put(JsonKey.CREATED_DATE, ProjectUtil.getFormattedDate());
      jobProfile.put(JsonKey.CREATED_BY, userMap.get(JsonKey.ID));
      jobProfile.put(JsonKey.USER_ID, userMap.get(JsonKey.ID));
      JobProfile userJobDetails = mapper.convertValue(jobProfile, JobProfile.class);
      try {
        JobProfileDaoImpl.getInstance().create(userJobDetails);
      } catch (Exception e) {
        ProjectLogger.log(e.getMessage(), e);
      }
    }
  }
}
