var main = function(){

    "use strict"; 

    $(".button-cerca button").on("click", function () {
        ricercaAuto();
    });
}



function ricercaAuto(){

        let category = $(".input-categoria select").val(); 
        let type = $(".input-tipologia select").val(); 
        let city = $(".input-citta input").val(); 

        if(city === ""){
            window.alert("Inserisci città");
        }

        let ads_found = 0;

        $.getJSON("users.json").then(users => {
            $(".box div").remove();
    
            users.forEach(user => {
                (user.ads).forEach(ad =>{
                    if(ad.category === category && ad.type === type && ad.city === city){

                        ads_found++;
                        var $div = $("<div>");
                        $div.addClass("box-ad");
    
                        var $div_img = $("<div>");
                        $div_img.addClass("img");
    
    
                        var $img = $("<img>");
                        $img.attr("src", ad.photos[0]); 
                        $div_img.append($img);
    
                        var $div_data = $("<div>");
                        $div_data.addClass("data"); 
                        $div_data.append($("<h2>").text(ad.description)); 
    
                        const date = new Date(ad.insert_date);
                        const day = date.getDay() +1; 
                        const month = date.getMonth() +1; 
                        const year = date.getFullYear();  
    
                        $div_data.append($("<h4>").text(ad.city + " - " + day + "/" + month + "/" + year)); 
                        $div_data.append($("<h3>").text(ad.price + " €")); 
    
                        var $div_additional = $("<div>");
                        $div_additional.addClass("additional");
    
                        var $div_vehicle =  $("<div>");
                        $div_vehicle.addClass("vehicle");
                        $div_vehicle.append($("<h4>").text(ad.type));
                        $div_vehicle.append($("<h4>").text(ad.category));
                        $div_vehicle.append($("<h4>").text(ad.km+" Km"));
    
    
                        var $div_seller =  $("<div>");
                        $div_seller.addClass("seller");
                        var $span_seller = $("<span>").text("RIVENDITORE"); 
                        $div_seller.append($("<h3>").append($span_seller)) ; 
                        $div_seller.append($("<h4>").text(user.phone));
                        $div_seller.append($("<h4>").text(user.email));
    
    
                        $div_additional.append($div_vehicle)
                        $div_additional.append($div_seller);
                        $div_data.append($div_additional);
                        $div.append($div_img);
                        $div.append($div_data);
                        $(".box").append($div);
    
                    }
                })
            });

            console.log(ads_found);

            if(ads_found === 0){
                    $(".box *").remove(); 
                    var $span_fail = $("<span>").addClass("fail").text("La ricerca non ha prodotto risultati"); 
                    $(".box").append($("<h4>").append($span_fail));
            }   
        });
        
        
        


}



$(document).ready(main);

