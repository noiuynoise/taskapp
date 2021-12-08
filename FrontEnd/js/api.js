// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://5tcgfaucil.execute-api.us-east-2.amazonaws.com/Beta/";

var add_url    = base_url + "createproject";   // POST
var project_view_url    = base_url + "projectview/";   // GET
var admin_view_url = base_url + "listprojects" //GET
var delete_project_url = base_url + "deleteproject" //POST
var add_teammate_url = base_url + "addteammate" //POST
var add_task_url = base_url + "addtask" //POST
var remove_teammmate_url = base_url + "removeteammate" //POST
var archive_project_url = base_url + "archiveproject" //POST
