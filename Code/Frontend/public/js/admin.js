alert("!!!");

let tables = document.getElementById("add-result"),
    chooseTeam = document.getElementById("team-tables"),
    butSend = document.getElementById("send");

let sendScoreBtn = document.getElementById("send-score");

function changeClassList(divDisplay, divNoneDisplay1){
    divDisplay.classList.add('display');
    divDisplay.classList.remove('nonedisplay');
    divNoneDisplay1.classList.add('nonedisplay');
    divNoneDisplay1.classList.remove('display');
}


//getRealTeamSquad(
//     document.getElementBId("select-team1").value,
//     document.getElementById("select-team2").value
// )

var changeTable = function(){
    changeClassList(chooseTeam, tables);
    getRealTeamSquad(
        document.getElementById("select-team1").value,
        document.getElementById("select-team2").value)
};

var calculateResultFunction = function(){
    calculateResult(document.getElementById("select-team1").value,
                    document.getElementById("select-team2").value);
};

sendScoreBtn.addEventListener('click', calculateResultFunction);
butSend.addEventListener('click', changeTable);

function getRealTeamSquad(teamA, teamB) {
    $.ajax({
        url: 'http://localhost:8080/getRealTeamSquad?teamA=' + teamA + '&teamB=' + teamB,
        type: 'get',
        success: function (data, textStatus, request) {
            const table1 = document.getElementById("teamA");
            const table2 = document.getElementById("teamB");
            for (let i = 0; i < 2; i++) {
                for (let j = 0; j < data[i].length; j++) {
                    let row;
                    if (i == 0)
                        row = table1.insertRow(j);
                    else
                        row = table2.insertRow(j);

                    const cellPName = row.insertCell(0);
                    const cellPoints = row.insertCell(1);
                    cellPName.innerHTML = data[i][j];
                    cellPoints.innerHTML = '<input value="0">';
                }
            }
        }
    })
}

function calculateResult(teamA, teamB) {
    let arrTeamA = [],
        arrTeamB = [],
        arrScoreA = [],
        arrScoreB = [];
    const table1 = document.getElementById("teamA");
    const table2 = document.getElementById("teamB");
    for(let i = 0; i < table1.rows.length; i++){
        arrTeamA[i] = table1.rows[i].cells[0].innerText;
    }

    for(let i = 0; i < table1.rows.length; i++){
        arrScoreA[i] = +table1.rows[i].cells[1].children[0].value;
    }

    for(let i = 0; i < table2.rows.length; i++){
        arrTeamB[i] = table2.rows[i].cells[0].innerText;
    }

    for(let i = 0; i < table2.rows.length; i++){
        arrScoreB[i] = +table2.rows[i].cells[1].children[0].value;
    }

    let teamAScore = +document.getElementById("goalsOfA").value,
        teamBScore = +document.getElementById("goalsOfB").value;

    let json = '{ "teamA": "' + teamA + '","teamB": "' + teamB + '" ,"teamAScore":' + teamAScore + ' ,"teamBScore":' + teamBScore +
        ' ,"teamAPlayers":' + JSON.stringify(arrTeamA) + ' ,"teamAResults":' + JSON.stringify(arrScoreA) + ' ,"teamBPlayers":' + JSON.stringify(arrTeamB) + ' ,"teamBResults":' + JSON.stringify(arrScoreB) + '}';
    $.ajax({
        url: 'http://localhost:8080/calculateResult',
        type: 'post',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: json,
        statusCode: {
            500: function() {
                alert("Херово");
            },
            200: function () {
                alert("Ok");
                window.location = '/index.html';
            }
        }
    })
}


