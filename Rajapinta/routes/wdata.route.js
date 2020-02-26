const express = require('express');
const app = express();
const wdataRoutes = express.Router();


let Wdata = require('../models/Wdata');


wdataRoutes.route('/add').post(function (req, res) {
  let wdata = new Wdata(req.body);
  wdata.save()
    .then(wdata => {
      res.status(200).json({'Wdata': 'Wdata has been added successfully'});
    })
    .catch(err => {
    res.status(400).send("unable to save to database");
    });
});


wdataRoutes.route('/').get(function (req, res) {
    Wdata.find(function (err, wdata){
      if(err){
        console.log(err);
      }
      else {
        res.json(wdata);
      }
    });
  });

  wdataRoutes.route('/day/:daten').get(function (req, res) {
    

    //let q = Wdata.find().sort({ _id: -1 }).limit(20);
    let datenow = req.params.daten;
    var q = Wdata.find({date: {$eq :datenow}});

    q.exec((err, wdata) =>{



      if(err){
        console.log(err);
      }
      else {
        res.json(wdata);
      }
    });
  });




wdataRoutes.route('/edit/:id').get(function (req, res) {
    let id = req.params.id;
    Wdata.findById(id, function (err, wdata){
        res.json(wdata);
    });
});

wdataRoutes.route('/update/:id').post(function (req, res) {
    Wdata.findById(req.params.id, function(err, wdata) {
      if (!wdata)
        res.status(404).send("Record not found");
      else {
        wdata._id = req.body._id;
        wdata.temperature = req.body.temperature;
        wdata.humidity = req.body.humidity;
        wdata.time = req.body.time;
  
        wdata.save().then(wdata => {
            res.json('Update complete');
        })
        .catch(err => {
              res.status(400).send("unable to update the database");
        });
      }
    });
  });

  wdataRoutes.route('/delete/:id').get(function (req, res) {
    Wdata.findByIdAndRemove({_id: req.params.id}, function(err, wdata){
        if(err) res.json(err);
        else res.json('Successfully removed');
    });
});
  
module.exports = wdataRoutes;