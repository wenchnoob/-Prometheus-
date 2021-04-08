let usernameClicked = false;
let passwordClicked = false;
let confirmPasswordClicked = false;

window.onload = () => {
    usernameClicked = false;
    passwordClicked = false;
    confirmPasswordClicked = false;
}

window.addEventListener("click", listenToClicks, false);


/**
 * This function will send the expression to the server to be evaluated
 * then it will print out the answer.
 * */
function solve() {
    const expression = document.getElementById("expression_input").value.replace("+", "plusSign");
    const xhr = new XMLHttpRequest();
    const message = String.raw`calculator/solve?expression_input=${expression}`;
    xhr.open("GET", message,true);
    xhr.send();
    xhr.onload=function(){
        console.log(this.responseText)
        let answer = JSON.parse(this.responseText).val;
        document.getElementById("answer_output").innerText = answer;
    }
}

function  listenToClicks(e) {
    let target = e.target.id;
    if (target == "username") {
        usernameClicked = true;
    } else if (target == "password") {
        passwordClicked = true;
    } else if (target == "confirmPassword") {
        confirmPasswordClicked = true;
    }

    if (target != "username" && usernameClicked) validateUsername();
    if (target != "password" && passwordClicked) validatePassword();
    if (target != "confirmPassword" && confirmPasswordClicked) confirmPassword();
}

function validateUsername() {
    let username = document.getElementById("username");
    let notifier = document.getElementById("usernameNotifier");
    if (username.value.length >= 6) {
        notifier.style.display = "none";
        notifier.style.visibility = "hidden";
    } else {
        notifier.style.display = "block";
        notifier.style.visibility = "visible";
    }
}

function validatePassword() {
    let password = document.getElementById("password");
    let passwordNotifier = document.getElementById("passwordNotifier");
    if (password.value.match(/^[0-9a-z]+$/)) {
        passwordNotifier.style.display = "none";
        passwordNotifier.style.visibility = "hidden";
    } else {
        passwordNotifier.style.display = "block";
        passwordNotifier.style.visibility = "visible";
    }
}

function confirmPassword() {
    let password = document.getElementById("password");
    let confirmPassword = document.getElementById("confirmPassword");
    let confirmPasswordNotifier = document.getElementById("confirmPasswordNotifier");
    if (password.value != confirmPassword.value) {
        confirmPasswordNotifier.style.display = "block";
        confirmPasswordNotifier.style.visibility = "visible";
    } else {
        confirmPasswordNotifier.style.display = "none";
        confirmPasswordNotifier.style.visibility = "hidden";
    }
}