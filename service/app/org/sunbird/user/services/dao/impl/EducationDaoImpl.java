package org.sunbird.user.services.dao.impl;

import java.util.Map;
import org.sunbird.user.models.Education;
import org.sunbird.user.services.dao.EducationDao;

/** @author Amit Kumar */
public class EducationDaoImpl implements EducationDao {

  public static final String TABLE_NAME = "education";

  @Override
  public void create(Education education) {

    create(KEY_SPACE, TABLE_NAME, mapper.convertValue(education, Map.class));
  }

  @Override
  public void update(Education education) {

    update(KEY_SPACE, TABLE_NAME, mapper.convertValue(education, Map.class));
  }

  @Override
  public void delete(String educationId) {

    delete(KEY_SPACE, TABLE_NAME, educationId);
  }
}
