var path = require('path');
var cmd  = require('node-command-line'),Promise = require('bluebird');

var video_qualities = []



var encodeVideo = (video_name,callback) => {    
    const in_path = path.join(__dirname,'/../../uploads/',video_name)
    const out_path = path.join(__dirname,'/../../videos/',video_name)
    video_qualities.push(`ffmpeg -i ${in_path} -s 160x90 -c:v libx264 -b:v 250k -g 90 -an ${out_path}_160x90_250k.mp4`)
    video_qualities.push(`ffmpeg -i ${in_path} -s 320x180 -c:v libx264 -b:v 500k -g 90 -an ${out_path}_320x180_500k.mp4`)
    video_qualities.push(`ffmpeg -i ${in_path} -s 640x360 -c:v libx264 -b:v 750k -g 90 -an ${out_path}_640x360_750k.mp4`)
    video_qualities.push(`ffmpeg -i ${in_path} -s 640x360 -c:v libx264 -b:v 1000k -g 90 -an ${out_path}_640x360_1000k.mp4`)
    video_qualities.push(`ffmpeg -i ${in_path} -s 1280x720 -c:v libx264 -b:v 1500k -g 90 -an ${out_path}_1280x720_1500k.mp4`)
    video_qualities.push(`ffmpeg -i ${in_path} -c:a aac -b:a 128k -vn ${out_path}_audio_128k.mp4`)
    video_qualities.push(`mp4box -dash 5000 -rap -profile dashavc264:onDemand -mpd-title BBB -out ./videos/manifest.mpd -frag 2000 ${out_path}_audio_128k.mp4 ${out_path}_160x90_250k.mp4 ${out_path}_320x180_500k.mp4 ${out_path}_640x360_750k.mp4 ${out_path}_640x360_1000k.mp4 ${out_path}_1280x720_1500k.mp4`)
    Promise.coroutine(function *() {
      for(var i=0; i < video_qualities.length; i++) {
        yield cmd.run(video_qualities[i]);
        console.log(`Finished ${i}`);
      } 
      callback();
    })();
  }
module.exports = {encodeVideo};