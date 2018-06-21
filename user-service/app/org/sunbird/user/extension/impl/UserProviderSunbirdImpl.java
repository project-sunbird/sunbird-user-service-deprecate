package org.sunbird.user.extension.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.sunbird.common.exception.ProjectCommonException;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.models.util.ProjectUtil;
import org.sunbird.common.responsecode.ResponseCode;
import org.sunbird.common.responsecode.ResponseMessage;
import org.sunbird.extension.user.UserExtension;
import org.sunbird.services.sso.SSOManager;
import org.sunbird.services.sso.SSOServiceFactory;
import org.sunbird.user.models.entity.User;
import org.sunbird.user.services.AddressService;
import org.sunbird.user.services.EducationService;
import org.sunbird.user.services.JobProfileService;
import org.sunbird.user.services.OrgClient;
import org.sunbird.user.services.UserService;
import org.sunbird.user.services.dao.impl.UserDaoImpl;
import org.sunbird.user.services.impl.AddressServiceImpl;
import org.sunbird.user.services.impl.EducationServiceImpl;
import org.sunbird.user.services.impl.JobProfileServiceImpl;
import org.sunbird.user.services.impl.UserServiceImpl;
import org.sunbird.user.utils.Constant;
import org.sunbird.user.utils.SocialMediaWebUrlValidator;
import org.sunbird.user.validators.UserRequestValidator;

/**
 * This class implements UserExtension interface for sunbird.
 *
 * @author Amit Kumar
 */
public class UserProviderSunbirdImpl implements UserExtension {

  private UserRequestValidator userRequestValidator = new UserRequestValidator();
  private UserService userService = UserServiceImpl.getInstance();
  private AddressService addressService = AddressServiceImpl.getInstance();
  private EducationService educationService = EducationServiceImpl.getInstance();
  private JobProfileService jobProfileService = JobProfileServiceImpl.getInstance();
  private SSOManager ssoManager = SSOServiceFactory.getInstance();
  private ObjectMapper mapper = new ObjectMapper();

  @SuppressWarnings("unchecked")
  @Override
  public void preCreate(Map<String, Object> userMap) {
    userRequestValidator.validateCreateUser(userMap);
    if (StringUtils.isBlank((String) userMap.get(JsonKey.CHANNEL))) {
      userMap.put(JsonKey.CHANNEL, userService.getDefaultChannel());
    }
    // validate channel and fetch rootOrgId corresponding to this channel value.
    userMap.put(
        JsonKey.ROOT_ORG_ID,
        OrgClient.getRootOrgIdFromChannel((String) userMap.get(JsonKey.CHANNEL)));

    userService.isUserExists(
        userService.getLoginId(
            (String) userMap.get(JsonKey.USERNAME), (String) userMap.get(JsonKey.CHANNEL)));

    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.PHONE))
        && CollectionUtils.isNotEmpty(
            userService.getUserDetailsByPhone((String) userMap.get(JsonKey.PHONE)))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              (String) userMap.get(JsonKey.PHONE)));
    }

    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.EMAIL))
        && CollectionUtils.isNotEmpty(
            userService.getUserDetailsByEmail((String) userMap.get(JsonKey.EMAIL)))) {
      ProjectCommonException.throwClientErrorException(
          ResponseCode.userAlreadyExists,
          MessageFormat.format(
              ResponseCode.userAlreadyExists.getErrorMessage(),
              (String) userMap.get(JsonKey.EMAIL)));
    }

    List<Map<String, Object>> externalIds =
        (List<Map<String, Object>>) userMap.get(Constant.EXTERNAL_IDS);
    if (CollectionUtils.isNotEmpty(externalIds)) {
      for (Map<String, Object> externalId : externalIds) {
        if (MapUtils.isNotEmpty(
            userService.getExternalIdDetails(
                (String) externalId.get(JsonKey.PROVIDER),
                (String) externalId.get(Constant.ID_TYPE),
                (String) externalId.get(JsonKey.ID)))) {
          ProjectCommonException.throwClientErrorException(
              ResponseCode.userAlreadyExists,
              MessageFormat.format(
                  ResponseMessage.Message.EXTERNAL_ID_FORMAT,
                  externalId.get(JsonKey.ID),
                  externalId.get(JsonKey.PROVIDER),
                  externalId.get(Constant.ID_TYPE)));
        }
      }
    }

    if (userMap.containsKey(JsonKey.WEB_PAGES)
        && CollectionUtils.isNotEmpty((List<Map<String, String>>) userMap.get(JsonKey.WEB_PAGES))) {
      SocialMediaWebUrlValidator.validateSocialMedia(
          (List<Map<String, String>>) userMap.get(JsonKey.WEB_PAGES));
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Response create(Map<String, Object> userMapRequest) {
    Map<String, Object> userMap = new HashMap<>(userMapRequest);
    prepareUserCreateRequest(userMap);
    saveUserToKeycloak(userMap);
    User user = mapper.convertValue(userMap, User.class);
    try {
      UserDaoImpl.getInstance().create(user);
    } catch (ProjectCommonException exception) {
      ssoManager.removeUser(userMap);
      throw exception;
    }
    if (CollectionUtils.isNotEmpty((List<Map<String, Object>>) userMap.get(JsonKey.ADDRESS))) {
      addressService.saveUserAddress(userMap);
    }
    if (CollectionUtils.isNotEmpty((List<Map<String, Object>>) userMap.get(JsonKey.EDUCATION))) {
      educationService.saveEducationDetails(userMap);
    }
    if (CollectionUtils.isNotEmpty((List<Map<String, Object>>) userMap.get(JsonKey.JOB_PROFILE))) {
      jobProfileService.saveJobProfileDetails(userMap);
    }
    Response response = new Response();
    response.put(JsonKey.USER, userMap);
    return response;
  }

  @Override
  public void postCreate(Map<String, Object> userMap) {}

  private void prepareUserCreateRequest(Map<String, Object> userMap) {
    /** will ignore roles coming from request, Only PUBLIC role is applicable for user by default */
    List<String> roles = new ArrayList<>();
    roles.add(ProjectUtil.UserRole.PUBLIC.getValue());
    userMap.put(JsonKey.ROLES, roles);
    userMap.put(JsonKey.CREATED_DATE, ProjectUtil.getFormattedDate());
    userMap.put(JsonKey.STATUS, ProjectUtil.Status.ACTIVE.getValue());
    userMap.put(JsonKey.EMAIL_VERIFIED, false);
    userMap.put(JsonKey.IS_DELETED, false);
    Map<String, String> profileVisbility = new HashMap<>();
    for (String field : ProjectUtil.defaultPrivateFields) {
      profileVisbility.put(field, JsonKey.PRIVATE);
    }
    userMap.put(JsonKey.PROFILE_VISIBILITY, profileVisbility);
    if (StringUtils.isNotBlank((String) userMap.get(JsonKey.COUNTRY_CODE))) {
      userMap.put(
          JsonKey.COUNTRY_CODE, ProjectUtil.getConfigValue(Constant.SUNBIRD_DEFAULT_COUNTRY_CODE));
    }
    if (StringUtils.isBlank((String) userMap.get(JsonKey.PASSWORD))) {
      userMap.put(JsonKey.PASSWORD, ProjectUtil.generateRandomPassword());
    }
    userMap.put(
        JsonKey.LOGIN_ID,
        userService.getLoginId(
            (String) userMap.get(JsonKey.USERNAME), (String) userMap.get(JsonKey.CHANNEL)));
  }

  private void saveUserToKeycloak(Map<String, Object> userMap) {
    Map<String, String> responseMap = ssoManager.createUser(userMap);
    String userId = responseMap.get(JsonKey.USER_ID);
    if (StringUtils.isNotBlank(userId)) {
      userMap.put(JsonKey.USER_ID, userId);
      userMap.put(JsonKey.ID, userId);
    } else {
      ProjectCommonException.throwClientErrorException(ResponseCode.userRegUnSuccessfull, null);
    }
  }

  @Override
  public void preUpdate(Map<String, Object> userMap) {}

  @Override
  public Response update(Map<String, Object> userMap) {
    return null;
  }
}
