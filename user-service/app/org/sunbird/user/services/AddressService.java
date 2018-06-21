package org.sunbird.user.services;

import java.util.Map;
import org.sunbird.user.models.entity.Address;

/** @author Amit Kumar */
public interface AddressService {

  /**
   * This method will save address of given user.
   *
   * @param userMap User details
   */
  void saveUserAddress(Map<String, Object> userMap);

  /**
   * This method use to save address inside DB.
   *
   * @param address Address details
   * @return address id
   */
  String saveAddress(Address address);
}
