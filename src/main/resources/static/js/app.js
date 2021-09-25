let sortDirection = false;
//pi: let serverUrl = "http://192.168.188.24:8080/webshop";
let serverUrl = "http://localhost:8080";


// Open and close sidebar
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}
    
function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}
