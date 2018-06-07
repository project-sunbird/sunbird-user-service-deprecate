package org.sunbird.user.utils;

import java.lang.reflect.Method;
import org.sunbird.global.BaseGlobal;
import play.mvc.Http.Request;

/**
 * Perform specific tasks to perform on application start, request, error etc.
 *
 * @author Manzarul
 */
public class Global extends BaseGlobal {

  @SuppressWarnings("rawtypes")
  @Override
  public play.mvc.Action onRequest(Request request, Method actionMethod) {
    return super.onRequest(request, actionMethod);
  }
}
