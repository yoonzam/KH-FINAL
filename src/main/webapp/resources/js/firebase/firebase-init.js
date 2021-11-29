if('serviceWorker' in navigator) {
    navigator.serviceWorker
        .register('/resources/firebase-messaging-sw.js')
        .then( () => {
            console.log('firebase-messaging-sw registered!');
        })
        .catch( err => {
            console.log(err);
        });
}