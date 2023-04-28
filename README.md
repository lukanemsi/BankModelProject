# BankModelProject
java web-service using rest-api, connecting postgresql and using jdbc more details given:

This is Rest API project which is working with PostgreSQL database (username=”postgres”, password=”password”).
Web service is using provided tomcat server. 
Folder have generator directory with .jar and .cmd File, run.cmd is generates appropriate database from scripts.sql
For web service to work. Web service have 2 endpoint path: ‘/operations’ and ‘/registration’.
For operations we have:
 ‘/fillBalance’ 
Method has request {cardnumber:int, amount:int} parameter, it fills the following card numbers balance with passed amount.
‘/transaction/{cardNumber}’
With method same method request, it takes amount from one card and transacts it to another.
‘/checkBalance/{personId}’
Returns the map of all {cardnumber : amount }which is registered by person with personId, and also returns total sum.
‘/history/{personId}’
Returns information about realized transactions from all cards owned by person.
As for registration:
‘/cardHolder’ and ‘/card’
Both of them takes following model class instances, which is then checked for validation and if it is valid then written to according database tables
REQUEST EXAMPLES:
PUT
http://localhost:8080/RestApi_war/operations/fillBalance
Request Headers
Accept application/json
Content-Type application/json
Accept-Encoding application/json
Bodyraw (json)
{
  "cardNumber": 2345234523452,
  "amount": 10000
}

PUT
http://localhost:8080/RestApi_war/operations/transaction/1234123412341
Request Headers 
Accept application/json
Content-Type application/json
Accept-Encoding application/json
Bodyraw (json)
{
  "cardNumber": 2345234523452,
  "amount": 10000
}

GET
http://localhost:8080/RestApi_war/operations/checkBalance/3
Request Headers
Accept
application/json
Content-Type
application/json
Accept-Encoding
application/json

GET
http://localhost:8080/RestApi_war/operations/history/2
Request Headers
Accept
application/json
Content-Type
application/json
Accept-Encoding
application/json

POST
http://localhost:8080/RestApi_war/registration/card
Request Headers
Accept application/json
Content-Type application/json
Accept-Encoding application/json
Bodyraw (json)
{
  "cardNumber": 1634127891234567,
  "CCV": 972,
  "expDate": "2029-04-07",
  "balance": 5000,
  "cardHolderId": 4
}

POST
http://localhost:8080/RestApi_war/registration/cardholder
Request Headers
Accept application/json
Content-Type application/json
Accept-Encoding application/json
Bodyraw (json)
{
  "id": 10,
  "fistName": "Luka",
  "lastName": "Nemsitsveridze",
  "email": "Luka.nemsi@gmail.com"
}
