import sys
import re
def states(lines):
	with open("states.txt") as f:
		stateList = f.read().splitlines();
	cityList = [];
	for state in stateList:
		cities = set()
		for line in lines:
		
			info = line.split('\t')
			
			if info[4] == state:
				cities.add(info[3])
		cityList.append(cities);

	ret = sorted(cityList[0] & cityList[1])
	s = "";
	for l in ret:	
		l = re.sub(',','',l)
		l = re.sub('\'','',l)
		s+=l+'\n'
	f = open('CommonCityNames.txt', 'w')
	f.write(s)
	f.close()


def zips(lines):
	with open("zips.txt") as f:
		zipList = f.read().splitlines();
	s = "";
	zipset = set();
	for zipcode in zipList:
		for line in lines:
			info = line.split('\t')
			if info[1] == zipcode and  not zipcode in zipset:
				s += info[6] + " " + info[7] + '\n'
				zipset.add(zipcode)
				
	f = open('LatLon.txt', 'w')
	f.write(s)
	f.close()

def cities(zips):
	stateList = [];
	with open("cities.txt") as f:
		cityList = f.read().splitlines();
	for city in cityList:
	
		cityByState = set();

		for line in zips:
			info = line.split('\t')
			if info[3].lower() == city.lower():
				cityByState.add(info[4])
		#cityByState.sort()
		stateList.append(cityByState)
		cityByState = []

	s = ""
	for l in stateList:
		l = sorted(l)
		s += str(l).strip('[]')+ '\n'
	s = re.sub(',','',s)
	s = re.sub('\'', '', s)
	f = open('CityStates.txt', 'w')
	f.write(s)
	f.close()

if __name__ == "__main__":
	with open("zipcodes.txt") as f:
		lines = f.read().splitlines()
	
	states(lines)
	zips(lines)
	cities(lines)
