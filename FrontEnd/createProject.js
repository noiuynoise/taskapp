/**
 * Respond to server JSON object.
 *
 */
function processCreateResponse(projectName, response) {
    if (response.status == 200) {
        location.href="projectview.html"
    } else {
        document.getElementById("errorText").innerHTML = "Please try a different project name"
    }
}

function handleCreateClick(e) {
    var projectName = document.getElementById("projectName").value
    console.log("project name is " + projectName)

    var js = JSON.stringify(projectName);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", add_url, true);

    // send the collected data as JSON
    xhr.send(js);

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.response);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processCreateResponse(projectName, xhr);
            console.log("xhr response is " + xhr.response)
            console.log(xhr)
        } else {
            console.log("else condition")
            processCreateResponse(projectName, "N/A");
        }
    };
}

