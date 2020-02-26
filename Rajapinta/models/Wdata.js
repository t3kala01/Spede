
const mongoose = require('mongoose');
const Schema = mongoose.Schema;


let Wdata = new Schema({
  nimi: {
    type: String
  },
  pisteet: {
    type: Number
  },
  },{
    collection: 'Data', versionKey :false
  }
  );

module.exports = mongoose.model('Wdata', Wdata);