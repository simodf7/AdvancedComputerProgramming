const bodyParser = require('body-parser'),
    express = require('express'),
    http = require('http'), 
    mongoose = require('mongoose'); 
    PORT = 3500;
url_db = "mongodb://127.0.0.1:27017/doghero";


var app = express(); 
app.use(express.static(__dirname+'/client')); 
// app.use(express.urlencoded());

app.use(bodyParser.urlencoded({ limit: "50mb", extended: true, parameterLimit: 50000 }))


// Connessione al Database

mongoose.connect(url_db)
.then(result => console.log("Connection established"))
.catch(err => console.log('ERROR: Database not connected'));


var padrone_Schema = new mongoose.Schema({
    nome: String,
    cognome: String,
    email: String,
    numeroTelefono: String,
    password: String,
    cani: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Cane' }]
},  {collection: 'padroni'});


var cane_Schema = new mongoose.Schema({
    codiceChip: String,
    nome: String,
    razza: String,
    eta: String,
    classe: {
      type: String,
      enum: ['L1', 'L2', 'L3'],
      default: 'L1',
    },
    padrone: { type: mongoose.Schema.Types.ObjectId, ref: 'Padrone' },
    lezioni: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Lezione' }]
}, {collection: 'cani'});


var addestratore_Schema = new mongoose.Schema({
    nome: String,
    cognome: String,
    numeroTelefono: String,
    numeroTesserino: String,
    password: String, 
    lezioni: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Lezione' }]
}, {collection: 'addestratori'});


var lezione_Schema = new mongoose.Schema({
    data: String,
    orario: String,
    luogo: String,
    addestratore: { type: mongoose.Schema.Types.ObjectId, ref: 'Addestratore' },
    classeCani: {
        type: String,
        enum: ['L1', 'L2', 'L3'],
        default: 'L1',
    },
    presenze: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Cane' }],
    votiCani: [{ cane: { type: mongoose.Schema.Types.ObjectId, ref: 'Cane' }, voto: Number }]
}, {collection: 'lezioni'});


// middleware

cane_Schema.pre('save', function (next) {
    const cane = this;

    // Trova il padrone associato al cane
    Padrone.findOneAndUpdate({ _id: cane.padrone }, {"$push": {cani: cane._id}})
        .then(() =>{
            next();
        }) 
        .catch(err =>{
            console.log(err);
            next(err);
        })
        
});




lezione_Schema.pre('save', function(next) {

    const lezione = this;

    // Trova l'addestratore
    Addestratore.findOneAndUpdate({ _id: lezione.addestratore }, {"$push": {lezioni: lezione._id}})
        .then( () =>{
            next();
        })
        .catch(err =>{
            console.log(err);
            next(err);
        })


});




// definiamo il modello 
var Padrone = mongoose.model("Padrone", padrone_Schema); 
var Cane = mongoose.model("Cane", cane_Schema);
var Addestratore = mongoose.model("Addestratore", addestratore_Schema);
var Lezione = mongoose.model("Lezione", lezione_Schema);


http.createServer(app).listen(PORT, () => console.log("Server listening on port %d",PORT));


app.post("/insertTrainer", function(req,res) {

    console.log(req.body);


    Addestratore.find({"numeroTesserino": req.body.tesserino})
        .then(result =>{
            if(result.length !== 0){
                console.log("TRAINER ALREADY ADDED");
                res.status(404).send("Tesserino già presente");
            }
            else{

                var newAddestratore = new Addestratore({ 
                    "nome": req.body.nome,
                    "cognome": req.body.cognome, 
                    "numeroTelefono": req.body.telefono,
                    "numeroTesserino": req.body.tesserino, 
                    "password": req.body.password,
                    "lezioni": [], 
                });

                newAddestratore.save()
                    .then(() => {
                        res.status(201).send("Addestratore correttamente inserito");
                    })
                    .catch(err => {
                        console.error("Errore durante il salvataggio del nuovo addestratore:", err);
                        res.status(500).send("Server Error: " + err.message);
                    });

            }
        })
        .catch(err => {
            console.error("Errore durante la ricerca del numero di tesserino:", err);
            res.status(500).send("Server Error: " + err.message);
        });


}); 




app.post("/insertLesson", function(req,res) {

    console.log(req.body);

    const name_array = (req.body.addestratore).split(" "); 

    const nome = name_array[0];
    let cognome = "";  
    const size = name_array.length; 

    if(size > 2){
        for(let i = 1; i<size; i++){
            if(i === size -1 ){
                cognome += name_array[i]; 
                
            }
            else{
                cognome += (name_array[i] + " "); 
            }
            
        }
    }
    else{
        cognome = name_array[1]; 
    }
    
    
    

    // console.log(nome);
    // console.log(cognome);

    const date_array = (req.body.data).split("T");
    const date = date_array[0]; 
    const time = date_array[1]; 


    
    Addestratore.find({"nome": nome, "cognome": cognome})
        .then(result =>{
            if(result.length === 0){
                console.log("TRAINER NOT FOUND");
                res.status(404).send("L'addestratore indicato non esiste");
            }
            else{

                Lezione.findOne({'data': date, "orario": time, 'addestratore': result[0]._id})
                    .then( lezione  => {

                        if(lezione){
                            res.status(404).send("L'addestratore ha già una lezione in quella fascia oraria");
                        }else{

                            var newLezione = new Lezione({ 
                                "data": date,
                                "orario": time, 
                                "luogo": req.body.luogo,
                                "addestratore": result[0]._id, 
                                "classe": req.body.classe,
                                "presenze": [], 
                                "votiCani":  []
                            });
            
                            newLezione.save()
                                .then(() =>{
                                    res.status(201).send("Lezione Correttamente Programmata");
                                })
                                .catch(err => {
                                    console.error("Errore durante il salvataggio della Lezione:", err);
                                    res.status(500).send("Server Error: " + err.message);
                                });


                        }
                        
                    })  
                    .catch(err => {
                        console.error("Errore durante il controllo orario della lezione:", err);
                        res.status(500).send("Server Error: " + err.message);
                    });


            }
        })
        .catch(err => {
            console.error("Errore durante la ricerca dell'addestratore:", err);
            res.status(500).send("Server Error: " + err.message);
        });



}); 



app.post("/signup", function(req,res){

    console.log(req.body);



    
    Padrone.find({"email": req.body.email})
        .then(result =>{
            if(result.length !== 0){
                console.log("MAIL ALREADY USED");
                res.status(404).send("L'email indicata non è disponibile");
            }
            else{


                var newPadrone = new Padrone({ 
                    "nome": req.body.nome,
                    "cognome": req.body.cognome, 
                    "email": req.body.email, 
                    "telefono": req.body.telefono, 
                    "password": req.body.password, 
                    "cani": []
                });

                newPadrone.save().then( () =>{
                    res.status(201).send("Padrone correttamente registrato");
                })
                .catch(err => {
                    console.error("Errore durante il salvataggio del padrone:", err);
                    res.status(500).send("Server Error: " + err.message);
                });

            }
        })
        .catch(err => {
            console.error("Errore durante il controllo email del padrone:", err);
            res.status(500).send("Server Error: " + err.message);
        });






});


app.post("/login_owner", function(req,res){

    console.log(req.body);



    
    Padrone.find({"email": req.body.email})
        .then(result =>{
            if(result.length === 0){
                console.log("UTENTE NON REGISTRATO");
                res.status(404).send("Padrone non registrato");
            }
            else{

                if(result[0].password !== req.body.password){
                    console.log("CREDENZIALI ERRATE");  // 401 == Unauthorized
                    res.status(401).send('Credenziali Errate');
                }
                else{
                    console.log("OK");
                    res.status(200).send(result[0]._id);
                }
                
            }
        })
        .catch(err => {  // 500 == Internal Server error
            console.error("Errore durante il controllo email del proprietario:", err);
            res.status(500).send("Server Error: " + err.message);
        });





});



app.post("/insertDog", function(req,res){

    console.log(req.body);



    
    Cane.find({"codiceChip": req.body.codice})
        .then(result =>{
            if(result.length !== 0){
                console.log("CHIP ALREADY USED");
                res.status(404).send("Esiste già un cane con il chip indicato");
            }
            else{


                var newCane = new Cane({ 
                    "codiceChip": req.body.codice,
                    "nome": req.body.nome, 
                    "razza": req.body.razza, 
                    "eta": req.body.eta,
                    "padrone": req.body.padrone,
                    "lezioni": []
                });

                newCane.save().then( () =>{
                    res.status(201).send("Cane Inserito Correttamente");
                })
                .catch(err => {
                    console.error("Errore durante l'inserimento del cane:", err);
                    res.status(500).send("Server Error: " + err.message);
                });

            }
        })
        .catch(err => {
            console.error("Errore durante il contrllo chip del cane:", err);
            res.status(500).send("Server Error: " + err.message);
        });






});




app.get("/lessons_owner.json", function(req,res){

    _id = req.query; 
    // console.log(_id);
    var classes = []; 

    Cane.find({"padrone": _id}, 'classe')
        .then( result =>{

            if(result.length === 0){
                res.status(404).send("Il padrone non ha nessun cane");
            }
            else{

                result.forEach( element =>{
                    classes.push(element["classe"]);
                });
                
                Lezione.find({"classeCani": { $in: classes }})
                    .populate('addestratore', 'nome cognome')
                    .then(lessons => {
                        res.status(200).json(lessons); 
                        
                    })
                    .catch(err => {
                        console.error("Errore durante la rierca delle lezioni:", err);
                        res.status(500).send("Server Error: " + err.message);
                    });
     
            }

            

        })
        .catch(err => {
            console.error("Errore durante la ricerca delle classi dei cani:", err);
            res.status(500).send("Server Error: " + err.message);
        });



});


app.post("/login_trainer", function(req,res){

    console.log(req.body);



    
    Addestratore.find({"numeroTesserino": req.body.tesserino})
        .then(result =>{
            if(result.length === 0){
                console.log("ADDESTRATORE NON REGISTRATO");
                res.status(404).send("Il tesserino inserito non corrisponde a nessun addestratore registrato");
            }
            else{

                if(result[0].password !== req.body.password){
                    console.log("CREDENZIALI ERRATE");
                    res.status(401).send('Credenziali Errate');
                }
                else{
                    console.log("OK");
                    res.status(200).send(result[0]._id);
                }
                
            }
        })
        .catch(err => {
            console.error("Errore durante la ricerca tesserino dell'addestratore:", err);
            res.status(500).send("Server Error: " + err.message);
        });






});



app.get("/lessons_trainer.json", function(req,res){

    _id = req.query; 
    console.log(_id);

 
    Lezione.find({"addestratore": _id})
        .then(lessons => {
            res.status(200).json(lessons);
            
        })
        .catch(err => {
            console.error("Errore durante la ricerca delle lezioni:", err);
            res.status(500).send("Server Error: " + err.message);
        });




});