var main = function () {

    "use strict";

    $(".button-login button").on("click", ()=>{
        login();
    });

    // Delegated event handlers have the advantage that they can process events from descendant elements
    //  that are added to the document at a later time
    $("main .container").on("click", ".button-add button", () =>{
        insert_Trainer();
    });



    $("main .container").on("click", ".button-lesson button", () =>{
        insert_lesson();
    });




}


function check_login_values(username, password){
    if(!username || !password){
        return true;
    }
}

function login(){

    let username_text = $(".input-username input").val();
    let password_text = $(".input-password input").val();
    let check = check_login_values(username_text, password_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{

        if(username_text === "admin" && password_text === "admin"){
            show_operations();
        }   
        else{
            Swal.fire({
                title: 'Credenziali Errate',
                icon: 'error'
            })
        }


    }


}


function add_field(field, type){

    var $div = $("<div>"); 
    $div.addClass("input-" + field);

    var capitalized = field.charAt(0).toUpperCase() + field.slice(1); 
    // console.log(capitalized);

    var $label = $("<label>").text(capitalized); 
    var $input = $("<input>"); 
    $input.attr("type", type); 
    $input.attr("name", field);
    
    $div.append($label); 
    $div.append($input); 
    return $div; 
}


function add_button(text,func){

    var $div = $("<div>"); 
    $div.addClass("button-" + func);

    var $button = $("<button>"); 
    $button.attr("type", "button"); 
    $button.text(text); 

    $div.append($button); 
    return $div; 

}


function show_insert_trainer(){

    var $div_insert_add = $("<div>");
    $div_insert_add.addClass("insert_add");



    $div_insert_add.append($("<h3>").text("INSERIMENTO ADDESTRATORI")); 
    
    var $div_nome = add_field("nome", "text"); 
    var $div_cognome = add_field("cognome", "text"); 
    var $div_telefono = add_field("telefono", "text"); 
    var $div_tesserino = add_field("tesserino", "text");
    var $div_password = add_field("password", "password");
    var $button = add_button("Inserisci", "add"); 

    $div_insert_add.append($div_nome); 
    $div_insert_add.append($div_cognome); 
    $div_insert_add.append($div_telefono); 
    $div_insert_add.append($div_tesserino); 
    $div_insert_add.append($div_password);
    $div_insert_add.append($button); 

    return $div_insert_add; 
}



function show_insert_lesson(){

    var $div_insert_lesson = $("<div>");
    $div_insert_lesson.addClass("insert_lesson");

    $div_insert_lesson.append($("<h3>").text("PROGRAMMAZIONE LEZIONE")); 
    
    var $div_data = add_field("data", "datetime-local"); 
    var $div_luogo = add_field("luogo", "text"); 
    var $div_addestratore = add_field("addestratore", "text"); 

    var $div_classe = $("<div>");
    $div_classe.addClass("input-classe"); 

    var $label = $("<label>").text("Classe"); 
    var $select = $("<select>"); 
    var $option1 = $("<option>").text("L1"); 
    var $option2 = $("<option>").text("L2"); 
    var $option3 = $("<option>").text("L3"); 

    $select.append($option1); 
    $select.append($option2); 
    $select.append($option3); 
    
    $div_classe.append($label); 
    $div_classe.append($select); 


    var $button = add_button("Programma", "lesson"); 

    $div_insert_lesson.append($div_data); 
    $div_insert_lesson.append($div_luogo); 
    $div_insert_lesson.append($div_addestratore); 
    $div_insert_lesson.append($div_classe);
    $div_insert_lesson.append($button); 

    return $div_insert_lesson; 


}

function show_operations(){

    $(".form-area").remove();

    

    var $div_insert_add = show_insert_trainer(); 
    var $div_insert_lesson = show_insert_lesson(); 

    $("main .container").append($div_insert_add); 
    $("main .container").append($div_insert_lesson); 
   
    $("main .container").removeClass("container").addClass("container2");
   
}



function check_trainer_values(nome, cognome, telefono, tesserino, password){

    if(!nome || !cognome || !telefono || !tesserino || !password){
        return true; 
    }

}


function insert_Trainer(){

    let nome_text = $(".input-nome input").val();
    let cognome_text = $(".input-cognome input").val(); 
    let telefono_text = $(".input-telefono input").val(); 
    let tesserino_text = $(".input-tesserino input").val(); 
    let password_text = $(".input-password input").val();

    console.log(nome_text); 
    console.log(cognome_text); 
    console.log(telefono_text); 
    console.log(tesserino_text); 
    console.log(password_text); 

    let check = check_trainer_values(nome_text, cognome_text, telefono_text, tesserino_text, password_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{

        let addestratore = {
            nome: nome_text, 
            cognome: cognome_text,
            telefono: telefono_text, 
            tesserino: tesserino_text, 
            password: password_text
        }

        $.post("insertTrainer", addestratore)
            .done((result) =>{
                
                Swal.fire({
                    title: result, 
                    icon: "succes"
                });

                nome_text = $(".input-nome input").val("");
                cognome_text = $(".input-cognome input").val(""); 
                telefono_text = $(".input-telefono input").val(""); 
                tesserino_text = $(".input-tesserino input").val(""); 
                password_text = $(".input-password input").val("");
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





function check_lesson_values(date, luogo, addestratore, classe){

    if(!date || !luogo || !addestratore || !classe){
        return true;
    }

}

function insert_lesson(){


    let date_text = $(".input-data input").val(); 
    let luogo_text = $(".input-luogo input").val(); 
    let addestratore_text = $(".input-addestratore input").val();
    let classe_text = $(".input-classe select").val(); 


    

    // console.log(date_text);
   
    // console.log(luogo_text); 
    // console.log(addestratore_text); 
    // console.log(classe_text); 

    let check = check_lesson_values(date_text, luogo_text, addestratore_text, classe_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{


        let lezione = {
            data: date_text,  
            luogo: luogo_text, 
            addestratore: addestratore_text,
            classe: classe_text
        }

        $.post("insertLesson", lezione)
            .done((result) =>{
                Swal.fire({
                    title: result,
                    icon: 'success',
                });
                date_text = $(".input-data input").val(""); 
                luogo_text = $(".input-luogo input").val(""); 
                addestratore_text = $(".input-addestratore input").val("");
                classe_text = $(".input-classe select").val(""); 
            
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



$(document).ready(main); 