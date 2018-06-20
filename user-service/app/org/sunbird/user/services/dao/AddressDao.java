package org.sunbird.user.services.dao;

import org.sunbird.user.models.entity.Address;

/**
 * AddressDao is an interface for accessing address database entity.
 *
 * @author Amit Kumar
 */
public interface AddressDao extends BaseDao {

  /**
   * Creating a address entity in database.
   *
   * @param address Address details.
   */
  void create(Address address);

  /**
   * Updates address entity database.
   *
   * @param address Address details.
   */
  void update(Address address);
}
