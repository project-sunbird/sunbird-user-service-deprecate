package org.sunbird.user.actors;

import org.sunbird.actor.core.BaseActor;
import org.sunbird.actor.router.ActorConfig;
import org.sunbird.common.request.Request;
import org.sunbird.extension.user.UserExtension;
import org.sunbird.user.extension.impl.UserProviderSunbirdImpl;
import org.sunbird.user.utils.Constant;

/**
 * CreateUserActor handles create user API request.
 *
 * @author Amit Kumar
 */
@ActorConfig(
  tasks = {Constant.CREATE_USER},
  asyncTasks = {}
)
public class CreateUserActor extends BaseActor {

  @Override
  public void onReceive(Request request) throws Throwable {
    if (Constant.CREATE_USER.equals(request.getOperation())) {
      createUser(request);
    } else {
      onReceiveUnsupportedOperation("CreateUserActor");
    }
  }

  private void createUser(Request request) {
    UserExtension extension = new UserProviderSunbirdImpl();
    extension.preCreate(request.getRequest());
    extension.create(request.getRequest());
  }
}
