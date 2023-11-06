var main = function(){

    "use strict"; 
    
    $(".button-login button").on("click", () => {
        login(); 
    }); 



    $(".button-signup button").on("click", ()=>{
        signup();
    });

    $("main .container" ).on("click", ".button-show button", () =>{  
        show_lessons();
    });

    $("main .container").on("click", ".button-ins button", ()=>{
        insert_dog();
    });

}



function check_login_values(email, password){
    if(!email || !password){
        return true;
    }
    return false;
}

function login(){

    let email_text = $(".left .input-email input").val();
    let password_text = $(".left .input-password input").val();


    let check = check_login_values(email_text, password_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{

        let credentials = {
            email: email_text, 
            password: password_text
        }


        
        $.post("login_owner", credentials)
            .done((result) =>{

                $("header .container p").text("Inserisci un Cane o Visualizza le lezioni programmate");
                console.log(result);
                sessionStorage.setItem("_id", result);
                show_operations();
            })
            .fail(err =>{
                if (err.status === 404) {
                    // Gestione di un errore 404 (Not Found)
                    Swal.fire({
                        title: 'Errore 404',
                        text: err.responseText,
                        icon: 'error'
                    });
                } else if (err.status === 401) {
                    // Gestione di un errore 401 
                    Swal.fire({
                        title: 'Errore 401',
                        text: err.responseText,
                        icon: 'error'
                    });

                } else if (err.status === 500) {
                    // Gestione di un errore 500 (Internal Server Error)
                    Swal.fire({
                        title: 'Errore 500',
                        text: err.responseText,
                        icon: 'error'
                    });
                } else {
                    // Altro tipo di risposta
                    Swal.fire({
                        title: 'Errore',
                        text: err.status,
                        icon: 'error'
                    });
                }
            });

    }


}


function check_signup_values(nome, cognome, email, telefono, password){

    if(!nome || !cognome || !email || !telefono || !password){
        return true;
    }
    return false;


}


function signup(){


    let nome_text = $(".right .input-nome input").val(); 
    let cognome_text = $(".right .input-cognome input").val();
    let email_text = $(".right .input-email input").val();
    let telefono_text = $(".right .input-telefono input").val(); 
    let password_text = $(".right .input-password input").val();

    let check = check_signup_values(nome_text, cognome_text, email_text, telefono_text, password_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{

        let padrone = {
            nome: nome_text, 
            cognome: cognome_text, 
            email: email_text, 
            telefono: telefono_text, 
            password: password_text, 
        }

        $.post("signup", padrone)
            .done((result) =>{
                
                Swal.fire({
                    title: result, 
                    icon: "succes"
                });

                nome_text = $(".right .input-nome input").val(""); 
                cognome_text = $(".right .input-cognome input").val("");
                email_text = $(".right .input-email input").val("");
                telefono_text = $(".right .input-telefono input").val(""); 
                password_text = $(".right .input-password input").val("");
            })
            .fail(err =>{
                if (err.status === 404) {
                    // Gestione di un errore 404 (Not Found)
                    Swal.fire({
                        title: 'Errore 404',
                        text: err.responseText,
                        icon: 'error'
                    });
                } else if (err.status === 500) {
                    // Gestione di un errore 500 (Internal Server Error)
                    Swal.fire({
                        title: 'Errore 500',
                        text: err.responseText,
                        icon: 'error'
                    });
                } else {
                    // Altro tipo di risposta
                    Swal.fire({
                        title: 'Errore',
                        text: err.status,
                        icon: 'error'
                    });
                }
            });
        
        

    }


}


function check_dog_values(codice, nome, razza, eta){

    if(!codice || !nome || !razza || !eta){
        return true;
    }
    return false;
}

function insert_dog(){


    
    let codice_text = $(".input-codice input").val(); 
    let nome_text = $(".input-nome input").val();
    let razza_text = $(".input-razza input").val();
    let eta_text = $(".input-età input").val(); 


    let check = check_dog_values(codice_text, nome_text, razza_text, eta_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{

        let cane = {
            codice: codice_text, 
            nome: nome_text, 
            razza: razza_text, 
            eta: eta_text,
            padrone: sessionStorage.getItem("_id")
        }


        $.post("insertDog", cane)
            .done(result =>{
                
                Swal.fire({
                    title: result, 
                    icon: 'succes'
                });

                codice_text = $(".input-codice input").val(""); 
                nome_text = $(".input-nome input").val("");
                razza_text = $(".input-razza input").val("");
                eta_text = $(".input-età input").val("");
            })
            .fail(err =>{
                if (err.status === 404) {
                    // Gestione di un errore 404 (Not Found)
                    Swal.fire({
                        title: 'Errore 404',
                        text: err.responseText,
                        icon: 'error'
                    });
                } else if (err.status === 500) {
                    // Gestione di un errore 500 (Internal Server Error)
                    Swal.fire({
                        title: 'Errore 500',
                        text: err.responseText,
                        icon: 'error'
                    });
                } else {
                    // Altro tipo di risposta
                    Swal.fire({
                        title: 'Errore',
                        text: err.status,
                        icon: 'error'
                    });
                }
            });
        
        

    }



}







function add_field(field, type){

    var $div = $("<div>"); 
    $div.addClass("input-" + field);

    var capitalized = field.charAt(0).toUpperCase() + field.slice(1); 

    var $label = $("<label>").text(capitalized); 
    var $input = $("<input>"); 
    $input.attr("type", type); 
    $input.attr("name", field);
    
    $div.append($label); 
    $div.append($input); 
    return $div; 
}


function add_button(text, func){

    var $div = $("<div>"); 
    $div.addClass("button-" + func);

    var $button = $("<button>"); 
    $button.attr("type", "button"); 
    $button.text(text); 

    $div.append($button); 
    return $div; 

}


function show_insert_cane(){

    var $div_insert_cane = $("<div>");
    $div_insert_cane.addClass("insert_cane");



    $div_insert_cane.append($("<h3>").text("INSERIMENTO CANE")); 
    


    var $div_codice = $("<div>"); 
    $div_codice.addClass("input-codice");

    var $label = $("<label>").text("Codice Chip"); 
    var $input = $("<input>"); 
    $input.attr("type", "text"); 
    $input.attr("name", "codice");
    
    $div_codice.append($label); 
    $div_codice.append($input); 


    var $div_nome = add_field("nome", "text"); 
    var $div_razza = add_field("razza", "text"); 
    var $div_eta = add_field("età", "text"); 
    var $button = add_button("Inserisci", "ins"); 

    $div_insert_cane.append($div_codice); 
    $div_insert_cane.append($div_nome); 
    $div_insert_cane.append($div_razza); 
    $div_insert_cane.append($div_eta); 
    $div_insert_cane.append($button); 

    return $div_insert_cane; 
}



function visualize_lesson(){

    var $div_insert_lesson = $("<div>");
    $div_insert_lesson.addClass("show_lesson");

    $div_insert_lesson.append($("<h3>").text("VISUALIZZA LEZIONI")); 
    

    var $button = add_button("Visualizza", "show"); 


    $div_insert_lesson.append($button); 

    return $div_insert_lesson; 


}



function show_operations(){

    console.log("ENTRATO");
    $(".left").remove();
    $(".right").remove();

    

    var $div_insert_cane = show_insert_cane(); 
    var $div_insert_lesson = visualize_lesson(); 

    $("main .container").append($div_insert_cane); 
    $("main .container").append($div_insert_lesson); 
   
   
}


function show_lessons(){


  

    $.getJSON("lessons_owner.json", {"_id": sessionStorage.getItem("_id")})
        .done(lessons =>{

            $(".show_lesson").remove(); 
            var $div_lessons = $("<div>");
            $div_lessons.addClass("lessons");
        
            var $h2 = $("<h2>").text("LEZIONI PROGRAMMATE");
        
            $div_lessons.append($h2); 
        
            $("main .container").append($div_lessons);
        

            lessons.forEach(element => {

                var lesson = {
                    data: element.data,
                    orario: element.orario, 
                    luogo: element.luogo, 
                    addestratore: element.addestratore.nome + " " + element.addestratore.cognome,
                    classe: element.classeCani
                } 

                add_lesson(lesson);
            });

        })
        .fail(err =>{
            if (err.status === 404) {
                // Gestione di un errore 404 (Not Found)
                Swal.fire({
                    title: 'Errore 404',
                    text: err.responseText,
                    icon: 'error'
                });
            } else if (err.status === 500) {
                // Gestione di un errore 500 (Internal Server Error)
                Swal.fire({
                    title: 'Errore 500',
                    text: err.responseText,
                    icon: 'error'
                });
            } else {
                // Altro tipo di risposta
                Swal.fire({
                    title: 'Errore',
                    text: err.status,
                    icon: 'error'
                });
            }
        });
    




}


function add_lesson(lesson){


    var $div_lesson = $("<div>"); 
    $div_lesson.addClass("lesson"); 

     
    $div_lesson.append(add_lesson_field("data", lesson["data"])); 
    $div_lesson.append(add_lesson_field("orario", lesson["orario"])); 
    $div_lesson.append(add_lesson_field("luogo", lesson["luogo"])); 
    $div_lesson.append(add_lesson_field("addestratore", lesson["addestratore"])); 
    $div_lesson.append(add_lesson_field("classe", lesson["classe"])); 


    $(".lessons").append($div_lesson);

}


function add_lesson_field(field, field_text){

    var $div = $("<div>"); 
    $div.addClass(field); 

    var $h3 = $("<h3>"); 
    var $span = $("<span>").text(field.toUpperCase()); 
    $h3.append($span); 

    var $h4 = $("<h4>").text(field_text);


    $div.append($h3); 
    $div.append($h4);

    return $div;
}


$(document).ready(main);