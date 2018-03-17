var moment = require('moment')

var generateMessage = (from,text)=>{
  return {
    from,
    text,
    createdAt:new Date().getTime()
  };
};

var generatePdfMessage = (from,name)=>{
  return {
    from,
    name
  };
};

var generateLocationMessage = (from,latitude,longitude)=>{
  return{
    from,
    url:`https://www.google.com/maps?q=${latitude},${longitude}`,
    createdAt:moment().valueOf()
  };
};


module.exports = {generateMessage,generateLocationMessage,generatePdfMessage}


