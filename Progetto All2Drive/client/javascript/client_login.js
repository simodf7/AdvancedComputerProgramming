var main = function(){

    $(".button-login button").on("click", function () {
        login(); 
    });


    $(".button-signup button").on("click", function () {
        signup(); 
    });


}


function check_signup_values(username, password, email, phone) {
    if(username === "" || password === "" || email === "" || phone === ""){
        return true; 
    }
}

function login(){
    let username_text = $(".left .input-username input").val();
    let password_text = $(".left .input-password input").val();

    let user = {
        username: username_text,
        password: password_text,
    }

    $.post("login", user).then((result) =>{
            console.log(result);
            sessionStorage.setItem("username", username_text);
            window.location = "ad.html";
        })
        .fail(err =>{
            window.alert(err);
        });
}

function signup(){
    
    let username_input = $(".right .input-username input").val();
    let password_input = $(".right .input-password input").val();
    let phone_input = $(".right .input-telefono input").val();
    let email_input = $(".right .input-email input").val();

    if(check_signup_values(username_input, password_input, phone_input, email_input)){
        window.alert("Inserisci valori in ogni campo");
    }
    else{   

        $.getJSON("users.json", users)

        let newUser = {
            username: username_input,
            password: password_input,
            phone: phone_input,
            email: email_input, 
            ads: []
        }


        $.post("signup", newUser)
            .then((result) => {
                console.log(result);
                // users = result;
            })
            .fail( err => console.log(err));
    }

}


$(document).ready(main);