
document.addEventListener("click", function (event) {
    const tile = {x: Math.floor(event.layerX / tileSize), y: Math.floor(event.layerY / tileSize)};
    socket.emit("destination", JSON.stringify(tile));
});
