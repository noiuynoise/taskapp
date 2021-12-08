function handleArchiveClick(projectName) {
    console.log("project name is " + projectName)

    var js = JSON.stringify(projectName);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", archive_project_url, true);

    // send the collected data as JSON
    xhr.send(js);

    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.response);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.response);
            processArchiveResponse(projectName, xhr);
        }
    };
}

function processArchiveResponse(projectName, response) {
    if (response.status == 200) {
        location.reload()
    } else {
        console.log("delete returned 400")
    }
}