// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://5tcgfaucil.execute-api.us-east-2.amazonaws.com/Beta/";

var add_url    = base_url + "createproject";   // POST
var project_view_url    = base_url + "projectview/";   // GET
var admin_view_url = base_url + "listprojects" //GET
var delete_project_url = base_url + "deleteproject" //POST