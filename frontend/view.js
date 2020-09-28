socket.on('gameState', function (event) {
    const gameState = JSON.parse(event);
    console.log(gameState);
    context.clearRect(0, 0, windowWidth, windowHeight);
    drawGameBoard(gameState['tiles']);
    drawPlayers(gameState['locations']);
});


function drawGameBoard(tiles) {
    for (let i = 0; i < tiles.length; i++) {
        for (let j = 0; j < tiles[i].length; j++) {
            const tile = tiles[i][j];
            let color = '#eeffee';
            if (tile.type === "mountain") {
                color = '#aa8833';
            } else if (tile.type === "forest") {
                color = '#228822';
            } else if (tile.type === "water") {
                color = '#aaccff';
            }
            placeTile(j, i, color);
        }
    }
}


function drawPlayers(locations) {
    for (const location of locations) {
        placePlayer(location.x, location.y);
    }
}


function placeTile(x, y, color) {
    context.fillStyle = color;
    context.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
    context.strokeStyle = 'black';
    context.strokeRect(x * tileSize, y * tileSize, tileSize, tileSize);
}

function placePlayer(x, y) {
    const scaledX = x * tileSize;
    const scaledY = y * tileSize;
    context.fillStyle = "#0000ff";
    context.beginPath();
    context.arc(scaledX, scaledY, tileSize / 2.0, 0, 2 * Math.PI);
    context.fill();
    context.strokeStyle = 'black';
    context.stroke();
}
