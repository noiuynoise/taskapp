function makeTwoFieldAPICall(arg1, arg2, arg1dest, arg2dest, apicall) {
    var data = {};
    data[arg1dest] = arg1;
    data[arg2dest] = arg2;
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", apicall, true);

    // send the collected data as JSON
    xhr.send(js);

    return xhr;
}