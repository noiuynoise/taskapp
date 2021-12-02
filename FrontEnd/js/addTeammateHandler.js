function handleAddTeammateClick() {
    var projectID = document.getElementById("invisiblePIDBox").innerHTML
    var teammateName = document.getElementById("addTeammateText").value
    var data = {};
    console.log(projectID)
    data["name"] = teammateName;
    data["projectid"] = projectID;
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", add_teammate_url, true);


    // send the collected data as JSON
    xhr.send(js);

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

}