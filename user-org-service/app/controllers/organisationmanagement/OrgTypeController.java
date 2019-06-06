package controllers.organisationmanagement;

import org.sunbird.common.models.util.LoggerEnum;
import org.sunbird.common.models.util.ProjectLogger;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class OrgTypeController extends Controller {

  public Result createOrgType() {
    ProjectLogger.log("Start Org type Creation Contoller",LoggerEnum.INFO);
    return Results.ok("200");
  }

  public Result updateOrgType() {
    ProjectLogger.log("Start Org update Contoller",LoggerEnum.INFO);
    return Results.ok("200");
  }

  public Result listOrgType() {
    ProjectLogger.log("Start Org list Contoller",LoggerEnum.INFO);
    return Results.ok("200");
  }
}
