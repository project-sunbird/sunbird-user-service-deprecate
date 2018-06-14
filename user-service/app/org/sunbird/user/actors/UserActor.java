package org.sunbird.user.actors;

import org.sunbird.actor.core.BaseActor;
import org.sunbird.actor.router.ActorConfig;
import org.sunbird.common.request.Request;
import org.sunbird.extension.user.UserExtension;
import org.sunbird.user.extension.impl.UserProviderSunbirdImpl;
import org.sunbird.user.utils.Constant;

/**
 * UserActor handles User API requests.
 *
 * @author Amit Kumar
 */
@ActorConfig(
  tasks = {Constant.CREATE_USER, Constant.UPDATE_USER},
  asyncTasks = {}
)
public class UserActor extends BaseActor {

  @Override
  public void onReceive(Request request) throws Throwable {

    String operation = request.getOperation();

    switch (operation) {
      case "createUser":
        createUser(request);
        break;
      case "updateUser":
        updateUser(request);
        break;
      default:
        onReceiveUnsupportedOperation("UserActor");
    }
  }

  private void updateUser(Request request) {}

  private void createUser(Request request) {
    UserExtension sunbirdExtension = new UserProviderSunbirdImpl();
    sunbirdExtension.preCreate(request.getRequest());
    sunbirdExtension.create(request.getRequest());
  }
}
