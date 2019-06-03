package controllers;

import play.mvc.Controller;

/**
 * This controller we can use for writing some common method to handel api request.
 *
 * @author Anmol
 */
public class BaseController extends Controller {

  public String getDummyResponse(){
    String dummyResponse = "{\"id\":\"api.private.user.migrate\",\"ver\":\"v1\",\"ts\":\"2019-01-17 16:53:26:286+0530\",\"params\":{\"resmsgid\":null,\"msgid\":\"8e27cbf5-e299-43b0-bca7-8347f7ejk5abcf\",\"err\":null,\"status\":\"success\",\"errmsg\":null},\"responseCode\":\"OK\",\"result\":{\"response\":{\"response\":\"SUCCESS\",\"errors\":[]}}}";
    return dummyResponse;
  }










}
