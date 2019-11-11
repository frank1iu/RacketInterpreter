// All of the Node.js APIs are available in the preload process.
// It has the same sandbox as a Chrome extension.
const request = require("request-promise-native");
function racketEval(program) {
    return new Promise((resolve, reject) => {
        request("http://127.0.0.1:27999/exec?program=" + encodeURIComponent(program)).then(body => {
            resolve(body);
        }).catch(err => reject(err));
    });
}

window.racketEval = racketEval;
window.process_exit = () => {
    const w = require('electron').remote.getCurrentWindow();
    w.close();
};