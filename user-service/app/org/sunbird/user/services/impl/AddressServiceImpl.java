package org.sunbird.user.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectLogger;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.user.models.entity.Address;
import org.sunbird.user.services.AddressService;
import org.sunbird.user.services.UserService;
import org.sunbird.user.services.dao.impl.AddressDaoImpl;

/**
 * This class provides implementation of AddressService interface.
 *
 * @author Amit Kumar
 */
public class AddressServiceImpl implements AddressService {

  private UserService userService = UserServiceImpl.getInstance();
  private ObjectMapper mapper = new ObjectMapper();

  private AddressServiceImpl() {}

  private static AddressService addressService = null;

  public static synchronized AddressService getInstance() {
    if (null == addressService) {
      addressService = new AddressServiceImpl();
    }
    return addressService;
  }

  @Override
  public void saveUserAddress(Map<String, Object> userMap) {
    List<Map<String, Object>> addressList =
        (List<Map<String, Object>>) userMap.get(JsonKey.ADDRESS);
    for (Map<String, Object> address : addressList) {
      Address userAddress = mapper.convertValue(address, Address.class);
      userAddress.setCreatedBy((String) userMap.get(JsonKey.CREATED_BY));
      try {
        userAddress.setUserId(userService.getEncryptedData((String) userMap.get(JsonKey.USER_ID)));
        saveAddress(userAddress);
      } catch (Exception e) {
        ProjectLogger.log(
            e.getMessage()
                + ",exception occurred while saving address for userId "
                + userMap.get(JsonKey.USER_ID),
            e);
      }
    }
  }

  @Override
  public String saveAddress(Address address) {
    try {
      String addressId = ProjectUtil.getUniqueIdFromTimestamp(1);
      address.setId(addressId);
      address.setCreatedDate(ProjectUtil.getFormattedDate());
      address.setDeleted(false);
      AddressDaoImpl.getInstance().create(address);
      return addressId;
    } catch (Exception ex) {
      ProjectLogger.log(ex.getMessage(), ex);
    }
    return null;
  }
}
