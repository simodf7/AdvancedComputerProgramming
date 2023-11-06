var main = function(){

    "use strict";

    $(".button-login button").on("click", () => {
        // show_lessons();
        login();
    });

   
    

}


function check_login_values(tesserino, password){
    if(!tesserino || !password){
        return true;
    }
    return false;
}


function login(){

    let tesserino_text = $(".input-tesserino input").val();
    let password_text = $(".input-password input").val();


    let check = check_login_values(tesserino_text, password_text);

    if(check){
        Swal.fire({
            title: 'Inserisci tutti i valori correttamente',
            icon: 'warning'
        })
    }
    else{

        let credentials = {
            tesserino: tesserino_text, 
            password: password_text
        }


        
        $.post("login_trainer", credentials)
            .done((result) =>{

                console.log(result);
                sessionStorage.setItem("_id", result);
                show_lessons();
            })
            .fail(err =>{
                if (err.status === 404) {
                    // Gestione di un errore 404 (Not Found)
                    Swal.fire({
                        title: 'Errore 404',
                        text: err.responseText,
                        icon: 'error'
                    });
                }
                else if (err.status === 401){
                    // Gestione di un errore 401 (Unauthorized)
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



function show_lessons(){


    $(".form-area").remove();
    
    var $div_lessons = $("<div>");
    $div_lessons.addClass("lessons");

    var $h2 = $("<h2>").text("LEZIONI PROGRAMMATE");

    $div_lessons.append($h2); 

    $("main .container").append($div_lessons);

    console.log("id:" + sessionStorage.getItem("_id"));

    $.getJSON("lessons_trainer.json", {"_id": sessionStorage.getItem("_id")})
        .done(lessons =>{

            console.log(lessons);

            lessons.forEach(element => {

                var lesson = {
                    data: element.data,
                    orario: element.orario, 
                    luogo: element.luogo, 
                    classe: element.classeCani
                } 

                add_lesson(lesson);
            });
        })
        .fail(err =>{
            if (err.status === 500) {
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