alert("!!!");

let tables = document.getElementById("add-result"),
    chooseTeam = document.getElementById("team-tables"),
    butSend = document.getElementById("send");

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
