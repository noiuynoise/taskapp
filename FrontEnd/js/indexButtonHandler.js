function handleViewProjectClick() {
    var projectName = document.getElementById("projectName").value
    console.log(projectName)
    location.href = "projectview.html#" + projectName
}
