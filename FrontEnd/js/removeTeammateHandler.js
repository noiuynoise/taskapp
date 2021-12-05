function handleRemoveTeammateClick() {
    var projectID = document.getElementById("invisiblePIDBox").innerHTML
    var teammateName = document.getElementById("removeTeammateText").value

    var xhr = makeTwoFieldAPICall(teammateName, projectID, "name", "projectid", remove_teammmate_url)

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processRemoveTeammateResponse(teammateName, xhr);
        }
    };
}

function processRemoveTeammateResponse(teammateName, response) {
    if (response.status == 200) {
        console.log(200)
        location.reload()
    } else {
        document.getElementById("teammateDoesNotExistErrorText").innerHTML = "Teammate does not exist"
    }

}