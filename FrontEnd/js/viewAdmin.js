function handleAdminViewRequest() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", admin_view_url, true);
    xhr.send();

    console.log("sent");

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log ("XHR:" + xhr);
            processAdminViewResponse(xhr);
        } else {
            console.log("400")
        }
    };
}

function processAdminViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var projectList = js["projects"];

    console.log(projectList)

    if (response.status == 200) {
        projectList.forEach(function(x) {
            var table = document.getElementById("projectTable")
            var newRow = table.insertRow(-1);
            var newProjectCell = newRow.insertCell(0);
            var newProjectIDCell = newRow.insertCell(1);
            var newCompletionCell = newRow.insertCell(2);
            var newArchivedCell = newRow.insertCell(3);
            var newDeleteCell = newRow.insertCell(4);

            var deleteButton = document.createElement('input');
            deleteButton.type = "button";
            deleteButton.id = "deleteButton";
            deleteButton.value = "Delete";
            //TODO : make this call the delete project request from the api
            deleteButton.onclick = function() {handleDeleteClick(x['projectID'])};
            newDeleteCell.appendChild(deleteButton);

            var archiveButton = document.createElement('input');
            archiveButton.type = "button";
            archiveButton.id = "archiveButton";
            archiveButton.value = "Yes"

            if (x['archived'] == true) {
                archiveButton.value = "Yes";
            } else if (x['archived'] == false) {
                archiveButton.value = "No";
            }

            //TODO: make this call the archive project request from the api
            //archiveButton.onclick = function();

            newArchivedCell.appendChild(archiveButton);

            newProjectCell.innerHTML = x['projectName']
            newProjectIDCell.innerHTML = x['projectID'].slice(0,8)
            newCompletionCell.innerHTML = x['completion'] + "%"
        })
    } else {
        console.log("400")
    }
}

