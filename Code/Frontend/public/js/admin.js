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



alert("!!!");

function getRealTeamSquad(teamA, teamB) {
    $.ajax({
        url: 'http://localhost:8080/getRealTeamSquad?teamA=' + teamA + '&teamB=' + teamB,
        type: 'get',
        success: function (data, textStatus, request) {
            const table1 = document.getElementById("teamA");
            const table2 = document.getElementById("teamB");
            for (let i = 0; i < 1; i++) {
                for (let j = 0; j < data[i].length; j++) {
                    let row = table.insertRow(i + 1);
                    const cellPName = row.insertCell(0);
                    const cellPoints = row.insertCell(1);
                    cellPName.innerHTML = data[i][j]["name"];
                    cellPoints.innerHTML = '<input value="0">';
                }
            }
        }
    })
}
