function handleTeamViewClick() {
    var hash = location.hash
    location.href = "teamview.html" + hash
}

function handleTeamViewReturnClick() {
    var hash = location.hash
    location.href = "projectview.html" + hash
}

function loadTeamView() {
    var xhr = new XMLHttpRequest();
    var projectNameFromHash = location.hash.substring(1);
    document.getElementById("projectNameTitle").innerHTML = projectNameFromHash
    xhr.open("GET", team_view_url + projectNameFromHash, true);
    xhr.send();

    console.log("sent");

    // This will process results and update HTML as appropriate.
    xhr.onloadend = function () {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            console.log("XHR:" + xhr);
            processTeamViewResponse(xhr);
        } else {
        }
    };
}

function processTeamViewResponse(response) {
    console.log("response is: " + response)
    var js = JSON.parse(response.response);

    var teammateList = js["teammates"];

    console.log(teammateList)

    if (response.status == 200) {
        teammateList.forEach(function(x) {
            var table = document.getElementById("teamTable")
            var newRow = table.insertRow(-1);
            var newNameCell = newRow.insertCell(0);
            var newTasksCell = newRow.insertCell(1);

            newNameCell.innerHTML = x['name']

            var taskNames = ''

            console.log(x['tasks'])
            x['tasks'].forEach(function(x) {
                taskNames = taskNames + (x + ', ')
            })
            console.log(taskNames)

            newTasksCell.innerHTML = taskNames


        })

    }
}