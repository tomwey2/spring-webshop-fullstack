// locate your element and add the Click Event Listener
document.getElementById("parent-list").addEventListener("click",function(e) {
    // e.target is our targetted element.
    // try doing console.log(e.target.nodeName), it will result LI
    if(e.target && e.target.nodeName == "LI") {
        console.log(e.target.id + " was clicked");
        alert(e.target.id + " was clicked");
    }
});

// Listener
document.getElementById("loginSmall").addEventListener("click", login);
document.getElementById("loginLarge").addEventListener("click", login);

function dologin() {
    console.log("test");
    alert("Hello World!");
}
