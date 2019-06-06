package controllers.organisationmanagement;

import org.sunbird.common.models.util.LoggerEnum;
import org.sunbird.common.models.util.ProjectLogger;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

public class OrgController extends Controller {

  public Result createOrg() {
    ProjectLogger.log("Start Org Creation Contoller", LoggerEnum.INFO);
    return Results.ok("200");
  }

  public Result updateOrg() {
    ProjectLogger.log("Start Org update Contoller", LoggerEnum.INFO);
    return Results.ok("200");
  }

  public Result updateOrgStatus() {
    ProjectLogger.log("Start Org status update Contoller", LoggerEnum.INFO);
    return Results.ok("200");
  }

  public Result getOrgDetails() {
    ProjectLogger.log("Start Org details Contoller", LoggerEnum.INFO);
    return Results.ok("200");
  }

  public Result search() {
    ProjectLogger.log("Start Org search Contoller", LoggerEnum.INFO);
    return Results.ok("200");
  }

}
