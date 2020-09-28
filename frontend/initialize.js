const socket = io.connect("http://localhost:8080", {transports: ['websocket']});

const canvas = document.getElementById("canvas");
const context = canvas.getContext("2d");

const tileSize = 30;

const screenHeight = 20;
const screenWidth = 30;

const windowHeight = tileSize * screenHeight;
const windowWidth = tileSize * screenWidth;

canvas.setAttribute("height", windowHeight + "px");
canvas.setAttribute("width", windowWidth + "px");
