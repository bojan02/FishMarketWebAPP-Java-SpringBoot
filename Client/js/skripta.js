var host = "http://localhost:";
var port = "8080/";
var ribaEndPoint = "api/fishes";
var loginEndpoint = "api/auth/login";
var registerEndpoint = "api/auth/register";
var pretragaEndpoint = "api/search";
var formAction = "Create";
var editingId;
var jwt_token;


function showLogin(){
    document.getElementById("data").style.display = "block";
    document.getElementById("pretragaFormDiv").style.display = "none";
    document.getElementById("formDiv").style.display = "none";
    document.getElementById("loginFormDiv").style.display = "flex";
    document.getElementById("registerFormDiv").style.display = "none";
    document.getElementById("logout").style.display = "none";
    document.getElementById("logRegButtons").style.display = "none";
}

function setLogRegButtonsPage(){
	document.getElementById("logRegButtons").style.display = "flex";
	document.getElementById("logout").style.display = "none";
	document.getElementById("loginFormDiv").style.display = "none";
	document.getElementById("registerFormDiv").style.display = "none";		
	document.getElementById("data").style.display = "block";
    document.getElementById("pretragaFormDiv").style.display = "none";
	document.getElementById("formDiv").style.display = "none";
}

function setRegistrationPage(){
	document.getElementById("logRegButtons").style.display = "none";
	document.getElementById("logout").style.display = "none";
	document.getElementById("loginFormDiv").style.display = "none";
	document.getElementById("registerFormDiv").style.display = "flex";		
    document.getElementById("pretragaFormDiv").style.display = "none";
}

function validateRegisterForm(username, email, password, confirmPassword) {
    if(username.length === 0) {
        alert("Username field can not be empty.");
        return false;
    } else if(email.length === 0) {
        alert("Email field can not be empty.");
        return false;
    } else if(password.length === 0) {
        alert("Password field can not be empty.");
        return false;
    } else if(confirmPassword.length === 0) {
        alert("Confirm password field can not be empty.");
        return false;
    } else if(password !== confirmPassword) {
        alert("Password value and confirm password value should match.");
        return false;
    }
    return true;
}

function registerUser() {
    var username = document.getElementById("usernameRegister").value;
    var email = document.getElementById("emailRegister").value;
    var password = document.getElementById("passwordRegister").value;
    var confirmPassword = document.getElementById("confirmPasswordRegister").value;

    if(validateRegisterForm(username, email, password, confirmPassword)) {
        var url = host + port + registerEndpoint;
        var sendData = { "username": username, "email": email, "password": password };
        fetch(url, { method: "POST", headers: { 'Content-Type': 'application/json'}, body: JSON.stringify(sendData) })
            .then((response) => {
                if(response.status === 201) {
                    console.log("Succesful registration");
                    alert("Successful registration");
                    document.getElementById("emailRegister").value = "";
                    document.getElementById("usernameRegister").value = "";
                    document.getElementById("passwordRegister").value = "";
                    document.getElementById("confirmPasswordRegister").value = "";

                    showLogin();

                } else {
                    console.log("Error occured with code " + response.status);
                    console.log(response);
                    alert("Greska prilikom registracije!");
                }   
        })
        .catch(error => console.log(error));
    }
    return false;
}

function validateLoginForm(username , password) {
    if(username.length === 0) {
        alert("Username field can not be empty.");
        return false;
    } else if (password.length === 0) {
        alert("Password field can not be empty.");
        return false;
    }
    return true;
}

function loginUser() {
    var username = document.getElementById("usernameLogin").value;
    var password = document.getElementById("passwordLogin").value;

    if (validateLoginForm(username, password)) {
        var url = host + port + loginEndpoint;
        var sendData = { "usernameOrEmail": username, "password": password };
        fetch(url, { method: "POST", headers: { 'Content-Type': 'application/json'}, body: JSON.stringify(sendData) })
            .then((response) => {
                if (response.status === 200) {
                    console.log("Succesful login");
                    alert("Succesful login");
                    response.json().then(function(data) {
                        console.log(data);
                        document.getElementById("info").innerHTML = "<br>Prijavljeni korisnik: <b>" + username + "<b/>.";
                        document.getElementById("logout").style.display = "block";
                        document.getElementById("usernameLogin").value = "";
                        document.getElementById("passwordLogin").value = "";
                        document.getElementById("loginFormDiv").style.display = "none";
						//document.getElementById("info").innerHTML = "Prijavljeni korisnik: <b>" + username + "<b/>";
                        document.getElementById("formDiv").style.display = "flex";
                        document.getElementById("pretragaFormDiv").style.display = "flex";

                        jwt_token = data.accessToken;
                        loadRiba();
                    });
                } else {
                    console.log("Error occured with code " + response.status);
                    console.log(response);
                    alert("Greska prilikom prijave!"); 
                }
            })
            .catch(error => console.log(error));
    }
    return false;
}

function loadRiba() {
    document.getElementById("data").style.display = "block"; 
    document.getElementById("registerFormDiv").style.display = "none";

    var requestUrl = host + port + ribaEndPoint;
    console.log("URL zahteva:" + requestUrl);
    var headers = { };
    if (jwt_token) {
        headers.Authorization = 'Bearer ' + jwt_token;
    }
    console.log(headers);
    fetch(requestUrl, { headers: headers})
        .then((response) => {
            if(response.status === 200) {
                response.json().then(setRiba);
                loadRibarnice();
            } else {
                console.log("Error occured with code " + response.status);
                showError();
            }
        })
        .catch(error => console.log(error));
};

function loadRibarnice() {
    var ribarniceUrl = host + port + "api/fishmarkets"; 

    fetch(ribarniceUrl)
        .then((response) => {
            if (response.status === 200) {
                response.json().then(setRibarnica);
            } else {
                console.log("Error occured with code " + response.status);
                showError();
            }
        })
        .catch(error => console.log(error));
}

function createHeader() {
    var thead = document.createElement("thead");
    thead.style.backgroundColor = "lightblue";
    thead.style.borderTop = "none";
    thead.style.borderRight = "none";
    thead.style.borderLeft = "none";
    var row = document.createElement("tr");
   
    if(jwt_token){
            row.appendChild(createTableCellTh("Sort"));
            row.appendChild(createTableCellTh("Price (din/kg)"));
            row.appendChild(createTableCellTh("Fish market"));
            row.appendChild(createTableCellTh("Quantity (kg)"));
            row.appendChild(createTableCellTh("Place of catch"));
            row.appendChild(createTableCellTh("Action"));
        } else {
            row.appendChild(createTableCellTh("Sort"));
            row.appendChild(createTableCellTh("Price (din/kg)"));
            row.appendChild(createTableCellTh("Fish market"));
            row.appendChild(createTableCellTh("Quantity (kg)"));
        }

    thead.appendChild(row);
    return thead;
}

function setRiba(data) {
    var container = document.getElementById("data");
    container.innerHTML = "";

    console.log(data);

    var div = document.createElement("div");
    var h1 = document.createElement("h1");
    var headingText = document.createTextNode("Fish for sale");
    h1.appendChild(headingText);
    div.appendChild(h1);

    var table = document.createElement("table");
    table.style.width = "100%";
    table.style.textAlign = "center";
  
    var header = createHeader();
    table.append(header);

    var tableBody = document.createElement("tbody");
    
    for(var i = 0; i < data.length; i++)
    {
        var row = document.createElement("tr");

        if(jwt_token){
            row.appendChild(createTableCell(data[i].sort));
            row.appendChild(createTableCell(data[i].price));
            row.appendChild(createTableCell(data[i].fishMarketName));
            row.appendChild(createTableCell(data[i].availableQuantity));
            row.appendChild(createTableCell(data[i].placeOfCatch));

            var stringId = data[i].id.toString();

            var buttonDelete = document.createElement("button");
            buttonDelete.name = stringId;
            buttonDelete.addEventListener("click", deleteRiba);
            buttonDelete.className = "btn btn-danger"
            var buttonDeleteText = document.createTextNode("Delete");
            buttonDelete.appendChild(buttonDeleteText);
            var buttonDeleteCell = document.createElement("td");
            buttonDeleteCell.style.border = "1px solid black";
            buttonDeleteCell.style.textAlign = "center";
            buttonDeleteCell.appendChild(buttonDelete);
            row.appendChild(buttonDeleteCell);

        } else {
            row.appendChild(createTableCell(data[i].sort));
            row.appendChild(createTableCell(data[i].price));
            row.appendChild(createTableCell(data[i].fishMarketName));
            row.appendChild(createTableCell(data[i].availableQuantity));
        }

        tableBody.appendChild(row);
    }

    table.appendChild(tableBody);
    div.appendChild(table);


    container.appendChild(div);
};

function setRibarnica(data) {
    var selectElement = document.getElementById("ribaRibarnicaId");

    selectElement.innerHTML = "";

    // Create and add new options
    for (var i = 0; i < data.length; i++) {
        var option = document.createElement("option");
        option.value = data[i].id;
        option.textContent = data[i].name; 
        selectElement.appendChild(option);
    }
}

function deleteRiba(){
    var deleteId = this.name;

    var url = host + port + ribaEndPoint + "/" + deleteId.toString();
    var headers = { 'Content-Type': 'application/json' };
    if(jwt_token){
        headers.Authorization = 'Bearer ' + jwt_token;
    }
    fetch(url, { method: "DELETE", headers: headers})
        .then((response) => {
            if(response.status === 204 || response.status === 200){
                console.log("Successful action");
                refreshTable();
            } else {
                console.log("Error occured with code " + response.status);
                alert("Desila se greska!");
            }
        })
        .catch(error => console.log(error));
}

function odustaniUredjivanje(){
    document.getElementById("ribaSorta").value = "";
    document.getElementById("ribaMestoUlova").value = "";
    document.getElementById("ribaCena").value = "";
    document.getElementById("ribaUkupnaKolicina").value = "";
    document.getElementById("ribaRibarnicaId").value = "";
}


function submitRibaForm(){
    var ribaSorta = document.getElementById("ribaSorta").value;
    var ribaMestoUlova = document.getElementById("ribaMestoUlova").value;
    var ribaCena = document.getElementById("ribaCena").value;
    var ribaUkupnaKolicina = document.getElementById("ribaUkupnaKolicina").value;
    var ribaRibarnicaId = document.getElementById("ribaRibarnicaId").value;
    var httpAction;
    var sendData;
    var url;

    
        httpAction = "POST";
        url = host + port + ribaEndPoint;
        sendData = {
            "sort": ribaSorta,
            "placeOfCatch": ribaMestoUlova,
            "price": ribaCena,
            "availableQuantity": ribaUkupnaKolicina,
            "fishMarketId": ribaRibarnicaId
        };

   

    console.log("Objekat za slanje");
    console.log(sendData);
    var headers = { 'Content-Type': 'application/json' };
    if(jwt_token){
        headers.Authorization = 'Bearer ' + jwt_token;
    } else {
        alert("Niste prijavljeni !")
        return false;
    }
    if (validacijaUnosa(ribaSorta, ribaMestoUlova, ribaCena, ribaUkupnaKolicina, ribaRibarnicaId)){
    fetch(url, { method: httpAction, headers: headers, body: JSON.stringify(sendData) })
        .then((response) => {
            if(response.status === 200 || response.status === 201) {
                console.log("Successful action");
                formAction = "Create";
                refreshTable();
            } else {
                console.log("Error occured with code " + response.status);
                alert("Greska prilikom dodavanja !");
            }
        })
        .catch(error => console.log(error));
    }
    return false;
}

function validacijaUnosa(ribaSorta, ribaMestoUlova, ribaCena, ribaUkupnaKolicina, ribaRibarnicaId){
	
	if(ribaSorta.length < 2 || ribaSorta.length > 30){
		alert("Dozvoljen broj karaktera za sorte je izmedju 2 i 30");
		return false;
	}else if(ribaMestoUlova.length < 3 || ribaMestoUlova.length > 120){
        alert("Dozvoljen broj karaktera za mesto ulova je izmedju 3 i 120");
		return false;
    }else if(ribaCena < 100 || ribaCena > 10000){
		alert("Cena mora biti u opsegu 100 - 10000")
		return false;
	}else if(ribaUkupnaKolicina < 1 || ribaUkupnaKolicina > 1000){
		alert("Ukupna kolicina mora biti u opsegu 1 - 1000");
		return false;
	}else if(ribaRibarnicaId === 0 || ribaRibarnicaId === '') {
        alert("Ribranica mora biti odabrana !");
        return false;
    }
	return true;
};

function refreshTable(){
    document.getElementById("ribaSorta").value = "";
    document.getElementById("ribaMestoUlova").value = "";
    document.getElementById("ribaCena").value = "";
    document.getElementById("ribaUkupnaKolicina").value = "";
    document.getElementById("ribaRibarnicaId").value = "";

    loadRiba();
}

function showError(){
    var container = document.getElementById("data");
    container.innerHTML = "";

    var div = document.createElement("div");
    var h1 = document.createElement("h1");
    var errorText = document.createTextNode("Greska prilikom preuzimanja podataka !");

    h1.appendChild(errorText);
    div.appendChild(h1);
    container.append(div);
}


function createTableCell(text) {
    var cell = document.createElement("td");
    cell.style.border = "1px solid black";
    cell.style.height = "50px";
    var cellText = document.createTextNode(text);
    cell.appendChild(cellText);
    return cell;
}

function createTableCellTh(text) {
    var cell = document.createElement("th");
    cell.style.border = "3px solid black";
    cell.style.borderTop = "none";
    cell.style.borderLeft = "none";
    cell.style.borderRight = "none";
    cell.style.height = "50px";
    var cellText = document.createTextNode(text);
    cell.appendChild(cellText);
    return cell;
}

function logout(){
    jwt_token = undefined;
    document.getElementById("info").innerHTML = "";
    document.getElementById("data").style.display = "block";
    document.getElementById("pretragaFormDiv").style.display = "none";
    document.getElementById("formDiv").style.display = "none";
    document.getElementById("loginFormDiv").style.display = "none";
    document.getElementById("registerFormDiv").style.display = "none";
    document.getElementById("logout").style.display = "none";
    document.getElementById("logRegButtons").style.display = "flex";
    loadRiba();

}

function submitPretragaRibePoUkupnojKolicini() {
	var pretragaStart = document.getElementById("pretragaStart").value;
	var pretragaKraj = document.getElementById("pretragaKraj").value;

    if(validateSearch(pretragaStart, pretragaKraj)){
	var sendData = {
			"min": pretragaStart,
			"max": pretragaKraj,
		};
	var url = host + port + pretragaEndpoint;

	console.log("Objekat za slanje");
	console.log(sendData);
    var headers = { 'Content-Type': 'application/json' };
    if(jwt_token){
        headers.Authorization = 'Bearer ' + jwt_token;
    } else {
        alert("Niste prijavljeni !")
        return false;
    }

	fetch(url, { method: "POST", headers: headers, body: JSON.stringify(sendData) })
		.then((response) => {
			if (response.status === 200) {
				response.json().then(setRiba);
				document.getElementById("pretragaRibe").reset();
			} else {
				console.log("Error occured with code " + response.status);
				showError();
                alert("Greska prilikom pretrage !");
			}
		})
		.catch(error => console.log(error));
    }
	return false;
}

function validateSearch(pretragaStart, pretragaKraj){
	var najmanje = parseInt(pretragaStart, 10);
	var najvise = parseInt(pretragaKraj, 10);
    //Zbog izbegavanja uporedjivanja stringova

	if(najmanje < 1 || najmanje > 1000){
		alert("Najmanja vrednost mora biti u opsegu 1 - 1000");
		return false;
	}else if(najvise < 1 || najvise > 1000){
		alert("Najveca vrednost mora biti u opsegu 1 - 1000");
		return false;
	}else if(najmanje > najvise){
        alert("Najmanja vrednost ne sme biti veca od najvece vrednosti.");
		return false;
    }else if(isNaN(najmanje) || isNaN(najvise)) {
        alert("Najmanje i najvise moraju imati vrednosti !");
        return false;
    }
	return true;
}