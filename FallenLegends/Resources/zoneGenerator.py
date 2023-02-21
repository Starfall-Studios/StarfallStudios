import json

# zone format {"owner":-1,"id":1,"name":"Test Zone 1","points":[{"latitude":41.57660025593672,"longitude":1.6017485255249397},{"latitude":41.57321351450865,"longitude":1.6013365167652305},{"latitude":41.57445008068013,"longitude":1.6055154627566814},{"latitude":41.577139622357116,"longitude":1.6053977459682045}]}

# 1 degree of latitude = 111.32 km
#387.3 meters = 0.00346 degrees of latitude

# 1 degree of longitude = 111.32 km * cos(latitude)