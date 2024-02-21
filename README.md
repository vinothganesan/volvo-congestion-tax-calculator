# congestion-tax-calculator

Springboot REST Api service to calculate the congestion tax calculator.

As part of calculation, this application is not charged on weekends (Saturdays and Sundays), public holidays, days before a public holiday and during the month of July.

Limited the scope to the year 2013.

REST Endpoint:

**API URL:** http://localhost:8080/api/congestion/calculate

**API method:** POST

**API Content Type:** application/json

**Request:** 

    {
		"vehicle_type": "Car",
		"dates": [
			"2013-02-08 06:20:27","2013-02-08 06:27:00",
			"2013-02-08 14:35:00","2013-02-08 15:29:00",
			"2013-02-08 15:47:00","2013-02-08 16:01:00",
			"2013-02-08 16:48:00","2013-02-08 17:49:00",
			"2013-02-08 18:29:00","2013-02-08 18:35:00"
		],
		"city": "Gothenburg"
	}
	
**Response:**  

    {
		"message": null,
		"tax_amount": 60,
		"taxed_day": "2013-02-08"
	}


### Prerequsit

Java 8+

Maven
