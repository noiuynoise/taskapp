function handleAddTaskClick() {
    var projectID = document.getElementById("invisiblePIDBox").innerHTML
    var taskName = document.getElementById("addTaskText").value
    var taskArray = [];
    taskArray.push(taskName)
    var data = {};
    console.log(projectID)
    data["tasks"] = taskArray;
    data["projectid"] = projectID;
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", add_task_url, true);


    // send the collected data as JSON
    xhr.send(js);

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.request);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr.responseText);
            processAddTaskResponse(taskName, xhr);
        } else {

        }
    };
}

function processAddTaskResponse(teammateName, response) {
    if (response.status == 200) {
        location.reload()
    } else {
        console.log(400)
    }

}