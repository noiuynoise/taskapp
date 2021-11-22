// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://xqagxkd8b7.execute-api.us-east-1.amazonaws.com/beta/";

var add_url    = base_url + "calculator";   // POST
var list_url   = base_url + "constants";    // GET
var create_url = base_url + "constants";    // POST

var delete_url = base_url + "constants";    // POST with {name}   [challenge in getting DELETE to work]