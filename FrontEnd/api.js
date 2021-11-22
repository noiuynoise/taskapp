// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://7mcj3z9i48.execute-api.us-east-1.amazonaws.com/Beta1/";

var add_url    = base_url + "createproject";   // POST
var project_view_url    = base_url + "projectview/";   // GET