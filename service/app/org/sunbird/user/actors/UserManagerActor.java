package org.sunbird.user.actors;

import com.typesafe.config.Config;
import org.sunbird.actor.core.BaseActor;
import org.sunbird.actor.router.ActorConfig;
import org.sunbird.common.models.response.Response;
import org.sunbird.common.models.util.JsonKey;
import org.sunbird.common.request.Request;
import org.sunbird.user.utils.Constant;
import org.sunbird.util.ConfigUtil;

/**
 * UserManagerActor handles User api requests.
 *
 * @author Amit Kumar
 */
@ActorConfig(
  tasks = {Constant.CREATE_USER, Constant.UPDATE_USER},
  asyncTasks = {}
)
public class UserManagerActor extends BaseActor {

  private static Config config = ConfigUtil.getConfig();

  @Override
  public void onReceive(Request request) throws Throwable {

    String operation = request.getOperation();
    if (Constant.CREATE_USER.equals(operation)) {

      Response response = new Response();
      response.put(JsonKey.RESPONSE, JsonKey.SUCCESS);
      sender().tell(response, self());
    } else {
      onReceiveUnsupportedOperation(operation);
    }
  }
}
