function processProjectViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var projectName = js["projectName"];
    var projectID = js["projectTUUID"]
    var taskList = js["tasks"]
    var teammateList = js["teammates"]

    if (response.status == 200) {
        document.getElementById("projectNameTitle").innerHTML = projectName + " - Project View"
        document.getElementById("invisiblePIDBox").innerHTML = projectID
        console.log(projectID)
        document.title = projectName
        document.getElementById("responseBody").innerHTML = response.response

        if (response.status == 200) {
            displayTasks(taskList, teammateList, projectID, "")
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

function processGenericResponse(response) {
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
            processGenericResponse(xhr);
        }
    }
}

function editTaskTeammatesRequest(teammate, projectID, taskID, call) {
    var xhr = makeThreeFieldAPICall(teammate, projectID, taskID, "teammate", "projectid", "taskid", call)

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.response);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log("XHR:" + xhr.responseText);
            processGenericResponse(xhr);
        }
    }
}

function decomposeClick(projectID, taskID) {
    document.getElementById("decomposeTaskDiv").style.visibility = 'visible'
    document.getElementById("submitDecomposeTask").onclick = function(){handleDecomposeTaskRequest(projectID, taskID)}
}

function handleDecomposeTaskRequest(projectID, taskID) {
    document.getElementById("decomposeTaskDiv").style.visibility = 'hidden'
    var decomposeText = document.getElementById("decomposeTaskName").value
    console.log(decomposeText)
    var tasks = []
    tasks.push(decomposeText)
    console.log(tasks)

    var xhr = makeThreeFieldAPICall(projectID, taskID, tasks, "projectid", "taskid", "tasks", decompose_task_url)

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        console.log(xhr);
        console.log(xhr.response);

        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log("XHR:" + xhr.responseText);
            processGenericResponse(xhr);
        }
    }

}
function displayTasks(taskList, teammateList, projectID, parentID) {
    taskList.forEach(function(x) {
        var table = document.getElementById("taskTable")
        var newRow = table.insertRow(-1);
        var newNameCell = newRow.insertCell(0);
        var newIDCell = newRow.insertCell(1);
        var newCompleteCell = newRow.insertCell(2);
        var newRenameCell = newRow.insertCell(3);
        var newDecomposeCell = newRow.insertCell(4);
        var newEditTeammatesForTaskCell = newRow.insertCell(5)
        var newAssignedTeammatesCell = newRow.insertCell(6);

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

        var editTaskTeammatesText = document.createElement('input');
        editTaskTeammatesText.type = "text";
        editTaskTeammatesText.id = "editTaskTeammatesText";
        editTaskTeammatesText.placeholder = "Teammate"
        newEditTeammatesForTaskCell.appendChild(editTaskTeammatesText);

        var assignButton = document.createElement('input');
        assignButton.type = "button";
        assignButton.id = "assignButton";
        assignButton.value = "Assign";
        assignButton.onclick = function()
        {editTaskTeammatesRequest(editTaskTeammatesText.value, projectID, x['ID'], assign_teammate_url)};
        newEditTeammatesForTaskCell.appendChild(assignButton);

        var unassignButton = document.createElement('input');
        unassignButton.type = "button";
        unassignButton.id = "unassignButton";
        unassignButton.value = "Unassign";
        unassignButton.onclick = function()
        {editTaskTeammatesRequest(editTaskTeammatesText.value, projectID, x['ID'], unassign_teammate_url)};
        newEditTeammatesForTaskCell.appendChild(unassignButton);

        var decomposeButton = document.createElement('input');
        decomposeButton.type = "button";
        decomposeButton.id = "decomposeButton";
        decomposeButton.value = "Decompose"
        decomposeButton.onclick = function() {decomposeClick(projectID, x['ID'])}

        newDecomposeCell.appendChild(decomposeButton);

        newNameCell.innerHTML = x['name']
        newNameCell.style.fontFamily = "Comic Sans MS"
        newIDCell.innerHTML = parentID + x['IDNum']

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

        displayTasks(x['subtasks'], teammateList, projectID, parentID + x["IDNum"] + ".")
    })
}
