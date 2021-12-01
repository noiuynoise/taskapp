function processProjectViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var id = js["projectName"];

    if (response.status == 200) {
        document.getElementById("projectNameTitle").innerHTML = id
        document.title = id
        document.getElementById("responseBody").innerHTML = response.response
    } else {
        console.log("400")
        location.href = "index.html"
    }
}

function loadProjectView() {
    var xhr = new XMLHttpRequest();
    var projectNameFromHash = location.hash.substring(1);
    xhr.open("GET", project_view_url + projectNameFromHash, true);
    xhr.send();

    console.log("sent");

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr);
            processProjectViewResponse(xhr);
        } else {
        }
    };
}