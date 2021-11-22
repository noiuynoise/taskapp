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
            processProjectViewResponse("N/A");
        }
    };
}

function processProjectViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var id = js["projectID"];

    if (response.status == 200) {
        location.href="projectview.html"
        document.getElementById("projectNameTitle").innerHTML = "Bob"
    } else {
        console.log("400")
    }
}