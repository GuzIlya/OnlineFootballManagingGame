var myTeamBtn = document.getElementById('myteam-btn'),
    statsBtn = document.getElementById('stats-btn'),
    transfersBtn = document.getElementById('transfers-btn'),
    page = document.getElementById('page'),
    resultBtn = document.getElementById('results-btn'),
    stadium = document.getElementById('stadium'),
    stats = document.getElementById('stats'),
    results = document.getElementById('result'),
    transfers = document.getElementById('transfers'),
    circles = document.querySelectorAll('.oblack'),
    choosePlayer = document.getElementById('choose-player'),
    table = document.getElementById('courttable'),
    findPl = document.getElementById('find-pl');

function changeClassList(divDisplay, divNoneDisplay1, divNoneDisplay2, divNoneDisplay3){
    divDisplay.classList.add('display');
    divDisplay.classList.remove('nonedisplay');
    divNoneDisplay1.classList.add('nonedisplay');
    divNoneDisplay1.classList.remove('display');
    divNoneDisplay2.classList.add('nonedisplay');
    divNoneDisplay2.classList.remove('display');
    divNoneDisplay3.classList.add('nonedisplay');
    divNoneDisplay3.classList.remove('display');
}

var stadiumWindow = function(){
    changeClassList(table, choosePlayer, results, transfers);
}

var statsWindow = function(){
    changeClassList(stats, stadium, results, transfers);
    getUsersRate();
}

var myTeamWindow = function(){
    changeClassList(stadium, stats, results, transfers);
}

var resultWindow = function(){
    changeClassList(results, stadium, stats, transfers);
    getResults();
};

function getUsersRate() {
    delAll("users-rate-table");
    $.ajax({
        url: 'http://localhost:8080/getUsersRate',
        type: 'get',
        success: function (data, textStatus, request) {
            let table = document.getElementById("users-rate-table");
            for (let i = 0; i < data.length; i++) {
                let str = "user" + i;
                let row = table.insertRow(i + 1);
                row.id = str;
                const cellName = row.insertCell(0);
                cellName.innerHTML = data[i]["login"];

                const cellPoints = row.insertCell(1);
                cellPoints.innerHTML = data[i]["points"];
            }
        }
    })
}

var transfersWindow = function(){
    changeClassList(transfers, stadium, results, stats);
};


statsBtn.addEventListener('click', statsWindow);
myTeamBtn.addEventListener('click', myTeamWindow);
resultBtn.addEventListener('click', resultWindow);
transfersBtn.addEventListener('click', transfersWindow);
findPl.addEventListener('click', function (e){
    let table = document.getElementById('player-table');
    table.classList.remove('nonedisplay');
    table.classList.add('display');
});


let choosePlDivWindow = function(circle){
    changeClassList(choosePlayer, table, results, transfers);

    if(circle === "def1"){
        getAvailablePlayers("Защитник", "def1-name", "def1-b");
    }else if(circle === "def2"){
        getAvailablePlayers("Защитник", "def2-name", "def2-b");
    }else if(circle === "def3"){
        getAvailablePlayers("Защитник", "def3-name", "def3-b");
    }else if(circle === "def4"){
        getAvailablePlayers("Защитник", "def4-name", "def4-b");
    }else if(circle === "middlefielder1"){
        getAvailablePlayers("Полузащитник", "middlefielder1-name", "middlefielder1-b");
    }else if(circle === "middlefielder2"){
        getAvailablePlayers("Полузащитник", "middlefielder2-name", "middlefielder2-b");
    }else if(circle === "keeper"){
        getAvailablePlayers("Вратарь", "keeper-name", "keeper-b");
    }else if(circle === "middlefielder4"){
        getAvailablePlayers("Полузащитник", "middlefielder4-name", "middlefielder4-b");
    }else if(circle === "forward1"){
        getAvailablePlayers("Нападающий", "forward1-name", "forward1-b");
    }else if(circle === "forward2"){
        getAvailablePlayers("Нападающий", "forward2-name", "forward2-b");
    }
    else if(circle === "middlefielder3"){
        getAvailablePlayers("Полузащитник", "middlefielder3-name", "middlefielder3-b");
    }
};


for(var i = 0; i < circles.length; i++){
    let k = circles[i].id;
    circles[i].addEventListener('click', function(){
        choosePlDivWindow(k);
    })
}

/*function setPl(circle) {

    let pl = document.getElementById("pl");
    let val = pl.innerText;
    let plList = document.getElementById("def1-name");
    let plB = document.getElementById("def1-b");
    pl.addEventListener('click', function() {
        plList.innerHTML = val;
        plB.innerHTML = val[0];
        changeClassList(table, choosePlayer);
    })
}*/

function getUserInfo() {
    $.ajax({
        url: 'http://localhost:8080' + '/userInfo?token=' + getCookie("Auth-Token"),
        type: 'get',
        success: function (data, textStatus, request) {
            const loginView = document.getElementById("loginName");
            const balanceView = document.getElementById("balance");
            loginView.innerHTML = data["login"];
            balanceView.innerHTML = data["balance"] + ' $';
        }
    })
}

function setPlayers() {
    let list = document.getElementsByClassName("plName");
    let str = '';
    for(let i = 0; i < list.length; i++){
        if(i === 10){
            str += '"' + list[i].innerText;
        }else{
            str += '"' + list[i].innerText + '",';
        }
    }
    console.log(list);
    let json = '{ "token": "' + getCookie("Auth-Token") + '","playersList": ['+ str + '"]}';
    $.ajax({
        url: 'http://localhost:8080/addTeam',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: json,
        statusCode: {
            200: function () {
                alert("Состав отправлен");
                window.location = '/index.html';
            }
        }
    })
}

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function logout() {
    $.ajax({
        url: 'http://localhost:8080' + '/loginOut?token=' + getCookie("Auth-Token"),
        type: 'get',
        success: function (data, textStatus, request) {
            window.location = '/login.html';
        }
    })
    document.cookie = 'Auth-Token=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

function getPlayers(position, maxCost) {
    let json = '{ "token": "' + getCookie("Auth-Token") + '","position": "' + position + '" ,"maxCost":"' + maxCost + '"}';
    delAll("player-table");
    $.ajax({
        url: 'http://localhost:8080/getPlayersToBuy',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: json,
        success: function (data, textStatus, request) {
            const table = document.getElementById("player-table");
            for (let i = 0; i < data.length; i++) {
                let str = "player" + i;
                let row = table.insertRow(i + 1);
                row.id = str;

                const cellPosition = row.insertCell(0);
                const cellCost = row.insertCell(1);

                row.onclick = function(){
                    return buyPlayer(str)
                };

                cellPosition.innerHTML = data[i]["name"];
                cellCost.innerHTML = data[i]["cost"];
            }
        }
    })
}

function buyPlayer(row_id) {
    var row = document.getElementById(row_id);
    var playerName = row.cells[0].innerHTML;
    var json = '{ "token": "' + getCookie("Auth-Token") + '" ,"name":"' + playerName + '"}';
    $.ajax({
        url: 'http://localhost:8080/buyPlayer?token=' + getCookie("Auth-Token") + '&name=' + playerName,
        type: 'get',
        statusCode: {
            500: function() {
                alert("Недостаточно средств");
            },
            200: function () {
                alert("Игрок куплен");
                window.location = '/index.html';
            }
        }
    })
}

function checkForDuplicate(player) {
    let table = document.getElementById("playersTable");
    for (let r = 0; r < table.rows.length; r++) {
        if (player === table.rows[r].cells[0].innerHTML){
            return true;
        }
    }
    return false;
}

function getAvailablePlayers(position, listElementId, buttonId) {
    var json = '{ "token": "' + getCookie("Auth-Token") + '" ,"position":"' + position + '"}';
    delAll("available-player-table");
    $.ajax({
        url: 'http://localhost:8080/getAvailablePlayers?token=' + getCookie("Auth-Token") + '&position=' + position,
        type: 'get',
        success: function (data, textStatus, request) {
            let table = document.getElementById("available-player-table");
            let y = 0;
            for (let i = 0; i < data.length; i++) {
                if (!checkForDuplicate(data[i])){
                    let str = "playerAv" + y;
                    let row = table.insertRow(y + 1);
                    y++;
                    row.id = str;
                    const cellName = row.insertCell(0);

                    row.onclick = function() {
                        document.getElementById(listElementId).textContent = row.cells[0].innerHTML;
                        document.getElementById(buttonId).textContent = row.cells[0].innerHTML[0];
                        changeClassList(document.getElementById('courttable'), choosePlayer, transfers, results);
                    };

                    cellName.innerHTML = data[i];
                }
            }
        }
    })
}

function deleteRow(r, tableId) {
    let i = r.parentNode.parentNode.rowIndex;
    document.getElementById(tableId).deleteRow(i);
}

function delAll(tableId) {
    let table = document.getElementById(tableId);
    for(let i = table.rows.length - 1; i > 0; i--){
        table.deleteRow(i, tableId);
    }
}

function getResults() {
    delAll("result-table");
    $.ajax({
        url: 'http://localhost:8080/getResults',
        type: 'get',
        success: function (data, textStatus, request) {
            const resultTable = document.getElementById("result-table");
            let str;
            for (let i = 0; i < data.length; i++) {
                str = data[i]["teamAName"] + " " + data[i]["teamAScore"] + " : " + data[i]["teamBScore"] + " " + data[i]["teamBName"];
                let row = resultTable.insertRow(i + 1);
                const cellResult = row.insertCell(0);
                cellResult.innerHTML = str;
            }
        }
    })
}
