TestUser:

#1:
	Username: 	maxmuster@oth.de
	Passwort:	1234

#2:
	Username:	mariamuster@othr.de
	Passwort: 	1234


PartnerProjekte:
- Port 8917: 	Andrea Lange
	--> wird aufgerufen beim Buchen eines Events
		--> GET-Requests zum Abrufen von Venues
		--> POST-Request zum Buchen eines Events
	--> wird aufgerufen beim Anschauen eines Events
		--> GET-Request zum Abrufen der Venue-Details

	--> ruft meine Schnittstelle auf beim Anzeigen eines Events auf ihrer Seite
		--> GET-Request zum Abrufen von Artist-Details

- Port 8928:	Michael Kneifel
	--> ruft meine Schnittstelle auf beim Anzeigen eines Events auf ihrer Seite
		--> GET-Request zum Abrufen von Artist-Details



Beispiel Event buchen:
- Ort: München, Kapazität: 500-1000
- Ort: Ingolstadt, Kapazität: 500-1000
- Ort: Regensburg, Kapazität: 1000-5000 / über 5000