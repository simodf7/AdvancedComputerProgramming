var main = function(){

    // listener per l'upload 
    $(".input-foto input").on("change", () =>{
        insert_photo();
       
    });

    $(".button-ad button").on("click" , function(){
        insert_Ad();

    });
    

}




function insert_photo(){

    console.log("entrato");
    var input = $(".input-foto input")["0"]; 
    

    var fReader = new FileReader();
    fReader.readAsDataURL(input.files["0"]);
    fReader.onloadend = function(event){
        sessionStorage.setItem("url", event.target.result); 
        
    }
}

function check_ad_values(description, city, km, price){
    if(description === "" || city === "" || km === "" || price ===""){
        return true; 
    }
}

function insert_Ad(){
    console.log("PREMUTO")
    let description_input = $(".input-descrizione input").val(); 
    let category_input = $(".input-categoria select").val(); 
    let type_input = $(".input-tipologia select").val(); 
    let city_input = $(".input-citta input").val(); 
    let km_input = $(".input-chilometri input").val(); 
    let price_input = $(".input-prezzo input").val(); 
   // let photo = $(".input-foto input").val();
   let photo = sessionStorage.getItem("url");

    

    if(check_ad_values(description_input, city_input, km_input, price_input)){
        window.alert("INSERISCI TUTTI");
    }
    else{
        console.log("Entrato");

        let max_id = 0; 

        $.getJSON("users.json").then(users => {
            users.forEach((user) =>{
                user.ads.forEach((ad) =>{
                    if(max_id < ad.id) {max_id = ad.id}; 
                })
            });
        })
        .fail(err => {
            console.log(err);
        });
        
        console.log("CLIENT", max_id);


        let User = {
            username: sessionStorage.getItem('username'),
            ads:[{
                id: max_id+1,
                city: city_input, 
                category: category_input,
                type: type_input,
                km: km_input, 
                description: description_input, 
                price: price_input, 
                photos: [photo]
            }]
        }

        $.post("insertAd", User)
            .then((result) => {
                window.location = "index.html";
            })
            .fail( err => console.log(err));
    }

}


$(document).ready(main);