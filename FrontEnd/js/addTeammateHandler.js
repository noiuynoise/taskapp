function handleAddTeammateClick() {
    var projectID = document.getElementById("invisiblePIDBox").innerHTML
    var teammateName = document.getElementById("addTeammateText").value
    var xhr = makeTwoFieldAPICall(teammateName, projectID, "name", "projectid", add_teammate_url)

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processAddTeammateResponse(teammateName, xhr);
        } else {

        }
    };
}

function processAddTeammateResponse(teammateName, response) {
    if (response.status == 200) {
        location.reload()
    } else {
        console.log(400)
    }
}