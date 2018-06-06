package org.sunbird.user.services.dao;

import org.sunbird.user.services.dao.impl.EducationDaoImpl;
import org.sunbird.user.services.dao.impl.JobProfileDaoImpl;
import org.sunbird.user.services.dao.impl.UserDaoImpl;
import org.sunbird.user.services.dao.impl.UserExternalIdentityDaoImpl;
import org.sunbird.user.services.dao.impl.UserOrgDaoImpl;

/**
 * Factory class for DaoImpl. This class will provide specific daoImpl obj based on dao type.
 *
 * @author Amit Kumar
 */
public class DaoFactory {

  private DaoFactory() {}

  public static BaseDao getDao(String type) {

    switch (type) {
      case "user":
        return new UserDaoImpl();

      case "eduaction":
        return new EducationDaoImpl();

      case "jobProfile":
        return new JobProfileDaoImpl();

      case "userExternalIdentity":
        return new UserExternalIdentityDaoImpl();

      case "userOrg":
        return new UserOrgDaoImpl();

      default:
        return null;
    }
  }
}
