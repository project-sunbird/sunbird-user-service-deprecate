package org.sunbird.user.services.dao.impl;

import org.sunbird.user.models.entity.Address;
import org.sunbird.user.services.dao.AddressDao;

/**
 * DAO implementation class for address entity.
 *
 * @author Amit Kumar
 */
public class AddressDaoImpl implements AddressDao {

  public static final String TABLE_NAME = "address";

  private AddressDaoImpl() {}

  private static AddressDaoImpl addressDaoImpl = null;

  public static synchronized AddressDaoImpl getInstance() {
    if (null == addressDaoImpl) {
      addressDaoImpl = new AddressDaoImpl();
    }
    return addressDaoImpl;
  }

  @Override
  public void create(Address address) {
    create(KEY_SPACE, TABLE_NAME, address);
  }

  @Override
  public void update(Address address) {
    update(KEY_SPACE, TABLE_NAME, address);
  }
}
