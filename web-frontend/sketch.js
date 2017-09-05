/* stuff for materializecss */
$(document).ready(function() {
    $('select').material_select();
});

/* coming soon add robot functionality */
$('#addrobot').on('click',function(){
    alert();
})       

/* global fields */
var images = {};
var robotsJSON;
var robots = [];
var selectedRobot;
var grid;
var gridJSON;
var tileSize;
var tiles = [];
var pallets = [];
var palletsJSON;
var selectedPallet;

/* loading assets before any other magic happens */
function preload() {
    images["robot"] = loadImage("images/robot.png");
    images["track"] = loadImage("images/tiles/track.png");
    images["noTrack"] = loadImage("images/tiles/no-track.png");
    images["pileOfDirtyBlocks"] = loadImage("images/blocks/pile-of-dirty-blocks.png");
}

function setup() {        
    var url = "http://localhost:4567/grids/smartrobots";
    gridJSON = loadJSON(url, setupGrid);
    setInterval(fetchUpdates, 50);
}

function setupGrid() {   
    var winwidth = document.getElementById('sketch-holder-wrapper').offsetWidth;
    tileSize = winwidth / gridJSON.tiles.length;
    var canvas = createCanvas(gridJSON.tiles.length * tileSize, gridJSON.tiles[0].length * tileSize);
    canvas.parent('sketch-holder');
    
    for (var y = 0; y < gridJSON.tiles[0].length; y++) {
        for (var x = 0; x < gridJSON.tiles.length; x++) {
            tiles.push(new Tile(gridJSON.tiles[x][y], x, y));
        }
    }
}

function setupRobotSelection() {
    
}

function fetchUpdates() {    
    var url = "http://localhost:4567/robots";
    robotsJSON = loadJSON(url, updateRobots);
    
    url = "http://localhost:4567/pallets";
    pallets = loadJSON(url, updatePallets);
}

function updateRobots() {
    for (var key in robotsJSON) {
        if (!robots[key]) {
            robots.push(new Robot(robotsJSON[key].x, robotsJSON[key].y, robotsJSON[key].hasBlock,
                                 robotsJSON[key].block)); 
        } else {
            robots[key].update(robotsJSON[key].x, robotsJSON[key].y, robotsJSON[key].hasBlock,
                              robotsJSON[key].block);
        }
    }
}

function updatePallets() {
    for (var key in palletsJSON) {
        if (!pallets[key]) {
            pallets.push(new Pallet(palletsJSON[key].x, palletsJSON[key].y, palletsJSON[key].blocks))
        } else {
            pallets[key].update(palletsJSON[key].x, palletsJSON[key].y, palletsJSON[key]);
        }
    }
}

function draw() {
    clear();
    renderTiles();
    renderRobots();
    renderPallets();
    //renderSelection();
}

function renderTiles() {
    for (var i = 0; i < tiles.length; i++) {
        tiles[i].display();
    }
}

function renderPallets() {
    for (var i = 0; i < pallets.length; i++) {
        pallets[i].display();
    }
}

function renderRobots() {
    for (var i = 0; i < robots.length; i++) {
        robots[i].display();
    }
}

function mousePressed() {
    for (var i = 0; i < robots.length; i++) {
        robots[i].clicked();
    }
}

function renderSelection() {        
    if (selectedRobot && !selectedPallet) {
        renderSelectedRobot();
    }
    
    if (selectedPallet && !selectedRobot) {
        renderSelectedPallet();
    } 
    
    if (!selectedPallet && !selectedRobot){
        $("#selection-info").html('');
    }
}

function renderSelectedRobot() {
    $("#selection-info").html('');
    var content = "<table id=\"selection-table\">"
    content += '<thead><tr><th>X</th><th>Y</th><th>hasBlock</th><th>block clean</th><th>block painted</th></tr></thead>'
    content += '<tbody><tr><td>' + selectedRobot.x.toFixed(2) +'</td>';
    content += '<td>' + selectedRobot.y.toFixed(2) +'</td>';
    
    if (selectedRobot.hasBlock) {
        content += '<td>' + selectedRobot.hasBlock +'</td>';
        content += '<td>' + selectedRobot.block.cleaned +'</td>';
        content += '<td>' + selectedRobot.block.painted +'</td>';
    }
        
    content += '</tr></tbody>';
    content += "</table>"
    $('#selection-info').append(content);
}

function renderSelectedPallet() {
    $("#selection-info").html('');
    var content = "<table id=\"selection-table\">"
    content += '<thead><tr><th>X</th><th>Y</th><th>is goal</th><th>block count</th></tr></thead>'
    content += '<tbody><tr><td>' + pallets[selectedPallet].x.toFixed(2) +'</td>';
    content += '<td>' + pallets[selectedPallet].y.toFixed(2) +'</td>';
    content += '<td>' + pallets[selectedPallet].isGoal +'</td>';
    content += '<td>' + pallets[selectedPallet].blocks.length +'</td>';
    content += '</tr></tbody>';
    content += "</table>"
    $('#selection-info').append(content);
}

function mouseClicked() {
    for (var i = 0; i < robots.length; i++) {
        if (robots[i].clicked()) {
            console.log(robots[i]);
            break;
        }
    }
}


/* 
 * Robot class
 */
function Robot(x, y, hasBlock, block) {
    this.x = x;
    this.y = y;
    this.hasBlock = hasBlock;
    this.block = block;
    
    this.display = function() {
        var coords = toIso(this.x, this.y);
        image(images["robot"], coords[0], coords[1], tileSize, tileSize);
    }
    
    this.update = function(x, y, hasBlock, block) {
        this.x = x;
        this.y = y;
        this.hasBlock = hasBlock;
        this.block = block;
    }
    
    this.clicked = function() {
        var coords = toIso(this.x, this.y);
        if (mouseX >= (coords[0] * tileSize) 
            && mouseX <= (coords[0] * tileSize + tileSize)
            && mouseY >= (coords[1] * tileSize) 
            && mouseY <= (coords[1] * tileSize + tileSize)){
            // ^ condition
            selectedRobot = this;
            selectedPallet = undefined;
            return true;
        }
        selectedRobot = undefined;
    }
}

/*
 * Pallet class
 */
function Pallet(x, y, blocks) {
    this.x = x;
    this.y = y;
    this.blocks = blocks;
    
    this.update = function(x, y, blocks) {
        this.x = x;
        this.y = y;
        this.blocks = blocks;
    }
    
    this.display = function() {
        console.log(blocks.length);
    }
}


/*
 * Grid class
 */
function Grid(name, tiles) {
    this.name = name;
    this.tiles = tiles;
    
    this.display = function() {
        for (var tile in tiles) {
            tile.display();
        }
    }
}

/*
 * Tile class
 */
function Tile(type, x, y) {
    this.type = type;
    this.x = x;
    this.y = y;
    
    this.display = function() {
        var coords = toIso(this.x, this.y);
        //noStroke();
        if (this.type === "NO_TRACK") {
            fill(255);            
            return;
        } else if (this.type === "TRACK") {
            fill(150);
        } else if (this.type === "SOURCE_PALLET") {
            fill(255, 0, 0);
            image(images["pileOfDirtyBlocks"], coords[0] - tileSize, coords[1] + tileSize, tileSize, tileSize);
            
        } else if (this.type === "GOAL_PALLET") {
            fill(0, 255, 0);
        } else if (this.type === "PAINTING_STATION") {
            fill(0, 255, 255);
        } else if (this.type === "CLEANING_STATION") {
            fill(0, 100, 255);
        }
        drawIsoRect(coords[0], coords[1]);
        // rect(this.x * tileSize, this.y * tileSize, tileSize, tileSize);
        // fill(100);
        // textAlign(CENTER, CENTER);
        // text(this.x + "|" + this.y, this.x * tileSize, this.y * tileSize, tileSize, tileSize);
    }
}

function drawIsoRect(x, y) {
    beginShape();
    vertex(x, y +  3 * tileSize / 4);
    vertex(x + tileSize / 2, y + tileSize/2);
    vertex(x + tileSize, y + 3 * tileSize / 4);
    vertex(x + tileSize / 2, y + tileSize);
    endShape(CLOSE);
}
function toIso(x, y) {
    var isoX = (x * tileSize / 2) + (y * tileSize / 2) + tileSize * 1.5 ;
    var isoY = (y * tileSize / 4) - (x * tileSize / 4) + tileSize * 5;
    return [isoX, isoY];
}

/*function twoDToIso(pt:Point):Point{
  var tempPt:Point = new Point(0,0);
  tempPt.x = pt.x - pt.y;
  tempPt.y = (pt.x + pt.y) / 2;
  return(tempPt);
}*/