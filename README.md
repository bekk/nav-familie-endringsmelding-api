# nav-familie-endringsmelding-api
API-repo for sommerstudenter som skal sitte med endringsmeldinger for Team Familie i NAV.

## Kjør på localhost:
bruk Launcher : ApplicationLocalLauncher 


## Hvordan logge inn på localhost:
Eksempel:
http://localhost:8099/local/cookie?issuerId=selvbetjening&audience=aud-localhost

Med redirect:
http://localhost:8099/local/cookie?redirect=http://localhost:3000/familie/endringsmelding&issuerId=selvbetjening&audience=aud-localhost

For å logge inn med et gyldig fødselsnummer:
http://localhost:8099/local/cookie?subject=[gyldigFødselsnummer]

### Swagger api kall med autentisering:
http://localhost:8099/swagger-ui.html
