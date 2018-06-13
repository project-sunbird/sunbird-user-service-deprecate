package org.sunbird.user.actors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sunbird.actor.core.BaseActor;
import org.sunbird.actor.router.ActorConfig;
import org.sunbird.common.request.Request;
import org.sunbird.user.utils.Constant;
import org.sunbird.user.validators.UserRequestValidator;

/**
 * UpdateUserActor handles update user API request.
 *
 * @author Amit Kumar
 */
@ActorConfig(
  tasks = {Constant.UPDATE_USER},
  asyncTasks = {}
)
public class UpdateUserActor extends BaseActor {

  private UserRequestValidator userRequestValidator = new UserRequestValidator();
  private ObjectMapper mapper = new ObjectMapper();

  @Override
  public void onReceive(Request request) throws Throwable {
    if (Constant.UPDATE_USER.equals(request.getOperation())) {
      userRequestValidator.validateUpdateUser(request.getRequest());
      updateUser(request);
    } else {
      onReceiveUnsupportedOperation("UpdateUserActor");
    }
  }

  private void updateUser(Request request) {}
}
