

  /**
   * Sample JavaScript code for fcm.projects.messages.send
   * See instructions for running APIs Explorer code samples locally:
   * https://developers.google.com/explorer-help/guides/code_samples#javascript
   */

  function authenticate() {
    return gapi.auth2.getAuthInstance()
        .signIn({scope: "https://www.googleapis.com/auth/cloud-platform https://www.googleapis.com/auth/firebase.messaging"})
        .then(function() { console.log("Sign-in successful"); },
              function(err) { console.error("Error signing in", err); });
  }
  function loadClient() {
    gapi.client.setApiKey("AIzaSyCNuQD68Jw_abElCM3B0XK-n4fNY-H7oJ0");	//YOUR_API_KEY
    return gapi.client.load("https://content.googleapis.com/discovery/v1/apis/fcm/v1/rest")
        .then(function() { console.log("GAPI client loaded for API"); },
              function(err) { console.error("Error loading GAPI client for API", err); });
  }
  // Make sure the client is loaded and sign-in is complete before calling this method.
  function execute() {
    return gapi.client.fcm.projects.messages.send({
      "parent": "projects/final-332911",
      "resource": {
        "message": {
          "webpush": {
            "data": {
              "name": "hmd",
              "age": "13"
            },
            "notification": {
              "title": "알림을 확인해주세요.",
              "body": "&lt;a href='https://www.naver.com'&gt;네이버로 이동&lt;/a&gt;",
              "icon": "https://cdn.ksilbo.co.kr/news/photo/202106/902252_501806_3356.png"
            }
          },
          "token": "d_5janCHZ2NDPNTu078wfy:APA91bEzwq9gxsZDVHvV4PR4ngwqCdtn2parjGiMUgMqerCHaGoOyY0AA-2yK8_jbhUicWmo9Mpiwq7ogzhrLccC4fSehhcz9FbWl5v9VM29Et_MWN2wLXTSeb_m7_ToruIgAXnGLZqj"
        }
      }
    })
        .then(function(response) {
                // Handle the results here (response.result has the parsed body).
                console.log("Response", response);
              },
              function(err) { console.error("Execute error", err); });
  };
  gapi.load("client:auth2", function() {
    gapi.auth2.init({client_id: "197983811902-pjbm4hbcnb5lt33ese87h4g2vbl35qtt.apps.googleusercontent.com"});
  });

