const bodyParser = require('body-parser'),
    express = require('express'),
    http = require('http'), 
    mongoose = require('mongoose'); 
    PORT = 3500;
url_db = "mongodb://127.0.0.1:27017/all2drive";


var app = express(); 
app.use(express.static(__dirname+'/client')); 
// app.use(express.urlencoded());

app.use(bodyParser.urlencoded({ limit: "50mb", extended: true, parameterLimit: 50000 }))


// Connessione al Database

mongoose.connect(url_db)
    .then(result => console.log("Connection established"))
    .catch(err => console.log('ERROR: Database not connected'));

var user_Schema = mongoose.Schema({
    username: String,
    password: String,
    email: String, 
    phone: String,
    ads: [{
        id: Number,
        insert_date: {type: Date, default: Date.now},
        city: String, 
        category: {
            type: String,
            enum: ['Auto', 'Moto']
        }, 
        type: {
            type: String,
            enum: ['Nuovo', 'Usato']
        }, 
        km: String, 
        description: String, 
        price: String,
        photos: {
            type: [String], 
            validate: [arrayLimit, '{PATH}, exceeds the limit of 8']
        }
    }]
})

// funzione che effettua il controllo del numero di foto
function arrayLimit(val) { return val.length <= 8 && val.length >=1}


// definiamo il modello 
var User = mongoose.model("User", user_Schema); 


http.createServer(app).listen(PORT, () => console.log("Server listening on port %d",PORT));


// route per ricavare gli utenti 
app.get("/users.json", function(req,res){
    User.find({})
        .then((users) => {
            res.json(users); 
            res.status(200).send();
        })
        .catch(err => {
            console.log(err);
            res.status(404).send("QUERY ERROR IN FINDING ALL USERS");
        });
}) 

app.post("/login", function(req,res) {

    var user_credential = {
        "username": req.body.username, 
        "password": req.body.password
    };

    User.find(user_credential).then(result => {

        if(result.length !== 0){
            // Utente trovato, credenziali giuste
            console.log("USER FOUND");
            res.status(200).send();
        }else{
            // Query andata a buon fine ma nessuno utente trovato o credenziali errate
           console.log("NOT FOUND");
            res.status(404).send("UTENTE NON TROVATO O CREDENZIALI ERRATE");
        }

    })
    .catch(err => {

        console.log("QUERY ERROR", err);
        res.status(404).send("QUERY ERROR");

    }); 



});




// route per la registrazione nuovo utente
app.post("/signup", function (req, res) {

	console.log(req.body);


    User.find({"username": req.body.username})
        .then(result =>{
            if(result.length !== 0){
                console.log("USERNAME ALREADY USED");
                res.status(404).send("USERNAME ALREADY USED");
            }
            else{

                var newUser = new User({ 
                    "username": req.body.username,
                    "password": req.body.password, 
                    "email": req.body.email, 
                    "phone": req.body.phone, 
                    "ads": [], 
                });

                newUser.save().then( () =>{
                    res.status(200).send("UTENTE CORRETTAMENTE REGISTRATO");
                })

            }
        })
        .catch(err =>{
            console.log("POST ERROR",err);
            res.status(404).send("QUERY ERROR");
        })
});


// route per inserire un'offerta

app.post("/insertAd", (req,res) =>{

    // console.log(req.body)

    User.find({"username": req.body.username})
        .then(user =>{  
            console.log(user);
            user[0]['ads'].push(req.body.ads[0]); 
            user[0].save().then(() =>{
                    res.status(200).send("AD INSERTED CORRECTLY");
                })
                .catch(err => {
                    console.log(err);
                    res.status(404).send("SAVE ERROR IN INSERTING AD");
                });
        })
        .catch(err =>{
            console.log("ERROR POST", err); 
            res.status(404).send("QUERY ERROR IN INSERTING AD");
        }); 


});
