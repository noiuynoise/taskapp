function processProjectViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var projectName = js["projectName"];
    var projectID = js["projectTUUID"]
    var taskList = js["tasks"]
    var teammateList = js["teammates"]

    if (response.status == 200) {
        document.getElementById("projectNameTitle").innerHTML = projectName
        document.getElementById("invisiblePIDBox").innerHTML = projectID
        console.log(projectID)
        document.title = projectName
        document.getElementById("responseBody").innerHTML = response.response

        if (response.status == 200) {
            taskList.forEach(function(x) {
                var table = document.getElementById("taskTable")
                var newRow = table.insertRow(-1);
                var newNameCell = newRow.insertCell(0);
                var newIDCell = newRow.insertCell(1);
                var newCompleteCell = newRow.insertCell(2);
                var newRenameCell = newRow.insertCell(3);
                var newDecomposeCell = newRow.insertCell(4);
                var newAssignedTeammatesCell = newRow.insertCell(5);

                var renameText = document.createElement('input');
                renameText.type = "text";
                renameText.id = "renameText";
                renameText.placeholder = "New Name"
                newRenameCell.appendChild(renameText);

                var renameButton = document.createElement('input');
                renameButton.type = "button";
                renameButton.id = "renameButton";
                renameButton.value = "Rename";
                renameButton.onclick = function() {renameClick(x['ID'], projectID, renameText.value)};
                newRenameCell.appendChild(renameButton);

                var decomposeButton = document.createElement('input');
                decomposeButton.type = "button";
                decomposeButton.id = "decomposeButton";
                decomposeButton.value = "Decompose"

                newDecomposeCell.appendChild(decomposeButton);

                newNameCell.innerHTML = x['name']
                newNameCell.style.fontFamily = "Comic Sans MS"
                newIDCell.innerHTML = x['IDNum']

                var imgButton = document.createElement('input');
                imgButton.type = "image";
                imgButton.id = "completedButton";
                if (x['complete'] == true) {
                    imgButton.src = "imgsources/greenCheck.png";
                } else {
                    imgButton.src = "imgsources/redX.png";
                }
                imgButton.onclick = function() {completedClick(x['ID'])};
                newCompleteCell.appendChild(imgButton);
                newAssignedTeammatesCell.innerHTML = getAssignedTeammatesFromTask(x['ID'], teammateList)
            })
        } else {
            console.log("400")
        }

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

function processRenameTaskResponse(response) {
    if (response.status == 200) {
        console.log(response.status)
        location.reload()
    }
}

function renameClick(taskID, projectID, newName) {
    var xhr = makeThreeFieldAPICall(taskID, projectID, newName, "taskid", "projectid", "name", rename_task_url)

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.response);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log("XHR:" + xhr.responseText);
            processRenameTaskResponse(xhr);
        }
    }
}

function getAssignedTeammatesFromTask(taskID, teammateList) {
    var validTeammateArray = "";
    teammateList.forEach(function(x) {
        if (x['tasks'].includes(taskID)) {
            validTeammateArray= validTeammateArray + (x['name']) + ", "
        }
    })
    return validTeammateArray;
}

function processMarkTaskResponse(response) {
    if (response.status == 200) {
        console.log(response.status)
        location.reload()
    }
}

function completedClick(taskID) {
    var xhr = makeOneFieldAPICall(taskID, mark_task_url)

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.response);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log("XHR:" + xhr.responseText);
            processMarkTaskResponse(xhr);
        }
    }
}
