package org.sunbird.user.services.dao;

import org.sunbird.user.services.dao.impl.EducationDaoImpl;
import org.sunbird.user.services.dao.impl.JobProfileDaoImpl;
import org.sunbird.user.services.dao.impl.UserDaoImpl;
import org.sunbird.user.services.dao.impl.UserExternalIdentityDaoImpl;
import org.sunbird.user.services.dao.impl.UserOrgDaoImpl;

/**
 * Factory class for various user DAO implementation classes. Based on given type corresponding user
 * DAO implementation is instantiated and returned.
 *
 * @author Amit Kumar
 */
public class DaoFactory {

  private static final String USER = "user";
  private static final String EDUCATION = "education";
  private static final String JOB_PROFILE = "job_profile";
  private static final String USER_EXTERNAL_IDENTITY = "user_external_identity";
  private static final String USER_ORG = "user_org";

  private DaoFactory() {}

  public static BaseDao getDao(String type) {

    switch (type) {
      case USER:
        return UserDaoImpl.getInstance();

      case EDUCATION:
        return EducationDaoImpl.getInstance();

      case JOB_PROFILE:
        return JobProfileDaoImpl.getInstance();

      case USER_EXTERNAL_IDENTITY:
        return UserExternalIdentityDaoImpl.getInstance();

      case USER_ORG:
        return UserOrgDaoImpl.getInstance();

      default:
        return null;
    }
  }
}
