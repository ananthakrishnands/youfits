var socket = io();
var name;
socket.on('connect',function () {
  console.log('Connected to server');
  var params = jQuery.deparam(window.location.search);
  name = params.name
  socket.emit('join',params,function(error){
    if(error){
      alert(err);
      window.location.href = '/';
    }
    else{
      console.log("No errors");
    }
  })
})

function scrollToBottom (){
  //Selectors
  var messages = jQuery('#messages');
  var newMessage = messages.children('li:last-child')
  //Heights
  var clientHeight = messages.prop('clientHeight');
  var scrollTop = messages.prop("scrollTop");
  var scrollHeight = messages.prop('scrollHeight');
  var newMessageHeight = newMessage.innerHeight();
  var lastMessageHeight = newMessage.prev().innerHeight();

  if(clientHeight + scrollTop + newMessageHeight + lastMessageHeight >= scrollHeight){
    messages.scrollTop(scrollHeight);
  }
}

socket.on("newMessage",function (message) {
  var formattedTime = moment(message.createdAt).format('h:mm a');
  var template = jQuery('#message-template').html();
  if(message.from === name){  
  template = `<li class="message" style="float:right;clear:both;">  
  <div id="message_div">
    <div class="message__title" >
      <h4>{{from}}</h4>
      <span>{{createdAt}}</span>
    </div>
    <div class="message-body">
      <p>{{text}}</p>
    </div>
  </div>
  </li>`
  }
  else{
    template = `<li class="message" style="float:left;clear:both;">  
    <div id="message_div">
      <div class="message__title" >
        <h4>{{from}}</h4>
        <span>{{createdAt}}</span>
      </div>
      <div class="message-body">
        <p>{{text}}</p>
      </div>
    </div>
    </li>`
  }
  var html = Mustache.render(template,{
    text:message.text,
    from:message.from,  
    createdAt:formattedTime
  });
  jQuery('#messages').append(html);
  console.log("Send message");
  scrollToBottom();
})

socket.on('newPdfMessage',function (message) {
  console.log("Inside newPdfMessage")  
  console.log(message.from)
  if(message.from === name){
    var li = jQuery(`<li style="float:right;clear:both;"><div><a href="http://localhost:3000/${message}">health report uploaded</a></div></li>`)    
  }
  else{
    var li = jQuery(`<li style="float:left;clear:both;"><div><a href="http://localhost:3000/${message}">download health report sent</a></div></li>`)    
  }
  jQuery('#messages').append(li);
  scrollToBottom();
})

socket.on('disconnect',function () {
  console.log("Disconnected from server");
})

socket.on('updateUserList',function(users){
  var ol = jQuery('<ol></ol>');

  users.forEach(function(user){
    ol.append(jQuery('<li></li>').text(user))
  })

  jQuery('#users').html(ol);
})

socket.on('newLocationMessage',function (message) {
  var li = jQuery('<li></li>')
  var a = jQuery('<a target="_blank">My current Location</a>');
  var formattedTime = moment(message.createdAt).format('h:mm a');
  var template = jQuery('#location-message-template').html();
  var html = Mustache.render(template,{
    url: message.url,
    from:message.from,
    createdAt:formattedTime
  });
  jQuery('#messages').append(html);
  scrollToBottom();
})

// socket.on('newVideoMessage',function (message) {
//   console.log("Inside newVideoMessage")  
//   var li = jQuery(`<li><div><video autoplay src="http://localhost:3000/${message}" controls></video></div></li>`)
//   jQuery('#messages').append(li);
//   scrollToBottom();
// })



jQuery('#message-form').on('submit',function (e) {
  e.preventDefault();

  var messageTextbox = jQuery('[name=message]')
  console.log("Message sent")

  socket.emit('createMessage',{
    text:messageTextbox.val()
  },function () {
    messageTextbox.val("")
  })
})

var locationButton = jQuery('#send-location');
var fileField = jQuery('#file-field');
var myForm = jQuery('#uploadForm')
var myFrame = jQuery('#myFrame')

$(document).ready(function () { 
  var options = { 
      beforeSubmit: showRequest,  // pre-submit callback 
      success: showResponse  // post-submit callback 
  }; 

  // bind to the form's submit event 
  $('#uploadForm').submit(function () { 
      $(this).ajaxSubmit(options); 
      // always return false to prevent standard browser submit and page navigation 
      return false; 
  }); 
}); 

// pre-submit callback 
function showRequest(formData, jqForm, options) { 
  alert('Uploading is starting.'); 
  return true; 
} 

// post-submit callback 
function showResponse(responseText, statusText, xhr, $form) {
  var file_name = document.getElementById('file-field').value.split(/(\\|\/)/g).pop();
  console.log(`Inside listener ${file_name}`)
  socket.emit('createPdfMessage',file_name);
} 