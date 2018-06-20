package org.sunbird.user.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.user.models.entity.Address;
import org.sunbird.user.models.entity.Education;
import org.sunbird.user.services.dao.impl.EducationDaoImpl;

public class EducationService {

  private static ObjectMapper mapper = new ObjectMapper();

  private EducationService() {}

  @SuppressWarnings("unchecked")
  public static void saveEducationDetails(Map<String, Object> userMap) {
    List<Map<String, Object>> educationList =
        (List<Map<String, Object>>) userMap.get(JsonKey.EDUCATION);
    for (Map<String, Object> education : educationList) {
      // primary key for education entity
      education.put(JsonKey.ID, ProjectUtil.getUniqueIdFromTimestamp(1));
      String addressId = "";
      if (MapUtils.isNotEmpty((Map<String, Object>) education.get(JsonKey.ADDRESS))) {
        Map<String, Object> address = (Map<String, Object>) education.get(JsonKey.ADDRESS);
        address.put(JsonKey.CREATED_BY, userMap.get(JsonKey.ID));
        addressId = AddressService.saveAddress(mapper.convertValue(address, Address.class));
        if (StringUtils.isNotBlank(addressId)) {
          education.put(JsonKey.ADDRESS_ID, addressId);
        }
      }
      parseYearOfPassing(education);
      parsePercentage(education);
      education.put(JsonKey.CREATED_DATE, ProjectUtil.getFormattedDate());
      education.put(JsonKey.CREATED_BY, userMap.get(JsonKey.ID));
      education.put(JsonKey.USER_ID, userMap.get(JsonKey.ID));
      Education userEduDetails = mapper.convertValue(education, Education.class);
      try {
        EducationDaoImpl.getInstance().create(userEduDetails);
      } catch (Exception e) {
        ProjectLogger.log(e.getMessage(), e);
      }
    }
  }

  private static void parsePercentage(Map<String, Object> education) {
    try {
      if (null != education.get(JsonKey.PERCENTAGE)) {
        education.put(
            JsonKey.PERCENTAGE,
            Double.parseDouble(String.valueOf(education.get(JsonKey.PERCENTAGE))));
      } else {
        education.put(JsonKey.PERCENTAGE, Double.parseDouble("0"));
      }
    } catch (Exception ex) {
      education.put(JsonKey.PERCENTAGE, Double.parseDouble("0"));
      ProjectLogger.log(ex.getMessage(), ex);
    }
  }

  private static void parseYearOfPassing(Map<String, Object> education) {
    try {
      if (null != education.get(JsonKey.YEAR_OF_PASSING)) {
        education.put(
            JsonKey.YEAR_OF_PASSING,
            ((BigInteger) education.get(JsonKey.YEAR_OF_PASSING)).intValue());
      } else {
        education.put(JsonKey.YEAR_OF_PASSING, 0);
      }
    } catch (Exception ex) {
      ProjectLogger.log(ex.getMessage(), ex);
      education.put(JsonKey.YEAR_OF_PASSING, 0);
    }
  }
}
