const start = { latitude: 41.486398, longitude: 1.487870 }; //41.486398, 1.487870
const latConst = 111320;

const radians = (degrees) => {
    return degrees * (Math.PI / 180);
};

const longitude = (latitude) => {
    return latConst * Math.cos(radians(latitude));
};

const makeGrid = (start, w, h, step) => {
    const grid = [];
    const latitudeStep = step / 111320;
    for (let y = 0; y < h; y++) {
        const localLatitude = start.latitude + latitudeStep * y;
        const localLongitude = step / longitude(localLatitude);
        grid.push([]);
        for (let x = 0; x < w; x++) {
            const point = [localLatitude, start.longitude + localLongitude * x];
            grid[y].push(point);
        }
    }
    return grid;
};

const makeAreas = (start, w, h, step) => {
    const grid = makeGrid(start, w + 1, h + 1, step);
    const areas = [];
    for (let i = 0; i < h; i++) {
        for (let j = 0; j < w; j++) {
            const area = {
                id : i * w + j,
                owner : -1,
                name : "Area " + (i * w + j),
                points : [
                    grid[i + 1][j],
                    grid[i + 1][j + 1],
                    grid[i][j + 1],
                    grid[i][j],
                ],
            };
            areas.push(area);
        }
    }
    return areas;
};

//console.log(makeAreas(start, 690, 690, 300));

await Deno.writeTextFile(
    "areasOutput.json",
    JSON.stringify(makeAreas(start, 80, 80, 387))
);
