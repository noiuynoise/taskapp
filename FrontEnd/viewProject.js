function handleViewProjectClick(projectName) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", project_view_url + projectName, true);
    xhr.send();

    console.log("sent");

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr);
            processProjectViewResponse(xhr);
        } else {
            console.log("400")
        }
    };
}

function processProjectViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var id = js["projectName"];

    if (response.status == 200) {
        location.href="projectview.html#" + id
    } else {
        console.log("400")
    }
}

function loadProjectView() {
    var hash = location.hash.substring(1);
    document.getElementById("projectNameTitle").innerHTML = hash
    document.title = hash
    handleViewProjectClick(hash)

}