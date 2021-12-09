function makeOneFieldAPICall(arg1, apicall) {
    var js = JSON.stringify(arg1);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", apicall, true);

    // send the collected data as JSON
    xhr.send(js);

    return xhr;
}

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

function makeThreeFieldAPICall(arg1, arg2, arg3, arg1dest, arg2dest, arg3dest, apicall) {
    var data = {};
    data[arg1dest] = arg1;
    data[arg2dest] = arg2;
    data[arg3dest] = arg3;
    var js = JSON.stringify(data);
    console.log("JS:" + js);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", apicall, true);

    // send the collected data as JSON
    xhr.send(js);

    return xhr;
}