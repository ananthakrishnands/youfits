const path = require('path')
const http = require('http')
const publicPath = path.join(__dirname,"../public")
var express = require('express')
const socketIO = require('socket.io')
var multer = require('multer')

const {encodeVideo} = require('./utils/encode')
const {generateMessage} = require('./utils/message')
const {generatePdfMessage} = require('./utils/message')
const {isRealString} = require('./utils/validation')
const {Users} = require('./utils/users');

var port = process.env.PORT || 3000;
var file_path = path.join(__dirname,'../uploads/')

console.log(__dirname + '/../public');
console.log(publicPath);

var app = express();
var server = http.createServer(app);
var io = socketIO(server);
var users = new Users();

app.use(express.static(publicPath));

   // multer code 

   var storage =   multer.diskStorage({
    destination: function (req, file, callback) {
      callback(null, './uploads');
    },
    filename: function (req, file, callback) {
      file.fieldname = file.originalname + '.pdf';
      console.log(file.fieldname); 
      callback(null, file.fieldname);
    }
  });
  
  var upload = multer({ storage : storage}).single('userVideo');
  
  app.post('/api/video',function(req,res){
      upload(req,res,function(err) {
          if(err) {
              return res.end("Error uploading file.");
          }
        res.writeHead(200)
        res.end("Response is recived")
        console.log("response sent")
      });       
  });
  
  //multer code end
  
  app.get('/manifest',(req,res)=>{
    console.log(file_path + 'manifest.mpd')
    res.sendFile(file_path + 'manifest.mpd')
    console.log("Send the manifest file")
  })
  
  app.get('/:id',(req,res)=>{
    console.log(req.params.id);
    res.sendFile(file_path + `${req.params.id}`)  
  })
  
  
  //Dash code end


io.on('connection',(socket)=>{
  console.log('New user connected');
  
  socket.on('join',(params,callback)=>{
    if(!isRealString(params.name) || !isRealString(params.room)) {
        callback("Name and room name is required")
    }
    socket.join(params.room);
    users.removeUser(socket.id);
    users.addUser(socket.id,params.name,params.room);

    io.emit(params.room).emit('updateUserList',users.getUserList(params.room));
    socket.emit("newMessage",generateMessage('Doctor','Welcome'));
    socket.broadcast.to(params.room).emit('newMessage',generateMessage('Admin',`${params.name} has joined this room`));

    callback();
  })

  socket.on('createVideoMessage',(name)=>{
    var user = users.getUser(socket.id);        
    console.log(`a video is uploaded by user ${user.name}`); 
    // encodeVideo(file.fieldname,()=>{
  

    // });    
    io.to(user.room).emit("newMessage",generateMessage(user.name,''));    
    io.to(user.room).emit('newVideoMessage',name +'.mkv');
    console.log("New video message emitted") 
  })

  socket.on('createPdfMessage',(name)=>{
    var user = users.getUser(socket.id);
    io.to(user.room).emit('newMessage',generateMessage(user.name,''));
    io.to(user.room).emit('newPdfMessage',generatePdfMessage(user.name,name+'.pdf'));
    console.log("New pdf message emitted")
  })

  socket.on('createMessage',(message,callback)=>{
    var user= users.getUser(socket.id);

    console.log(user.name)

    if(user && isRealString(message.text)){
      io.to(user.room).emit("newMessage",generateMessage(user.name,message.text));
      // io.emit('newMessage',generateMessage('User',message.txt))
      console.log("Sent the message")
    }
    // io.to(user.room).emit("newMessage",generateMessage(user.name,message.text));
    
    callback('');
  })

  //   socket.broadcast.emit('newMessage',{
  //     from:message.from,
  //     text:message.text,
  //     createdAt:new Date().getTime()
  //   })
  // })

  socket.on('createLocationMessage',(coords)=>{
    var user = users.getUser(socket.id);

    console.log(user.name + "is about to send a location message")
    // io.emit('newLocationMessage',generateLocationMessage('Admin:',coords.latitude,coords.longitude))
    io.to(user.room).emit("newLocationMessage",generateLocationMessage(user.name,coords.latitude,coords.longitude))
  })


  socket.on('disconnect',()=>{
    console.log("Disconnected from client");
    var user = users.removeUser(socket.id);

    if(user){
      io.to(user.room).emit('updateUserList',users.getUserList(user.room));
      io.to(user.room).emit('newMessage',generateMessage('Admin', `${user.name} has left`));
    }
  })

})

server.listen(port,()=>{
  console.log(`Started on port ${port}`);
})
