# nav-familie-endringsmelding-api
API-repo for sommerstudenter som skal sitte med endringsmeldinger for Team Familie i NAV.

## Kjør på localhost:
bruk Launcher : ApplicationLocalLauncher 


## Hvordan logge inn på localhost:
Med redirect:
http://localhost:8099/local/cookie?redirect=http://localhost:3000&issuerId=tokenx&audience=familie-endringsmelding

For å logge inn med et eget fødselsnummer må du sette subject:
http://localhost:8099/local/cookie?subject=[gyldigFødselsnummer]&redirect=http://localhost:3000&issuerId=tokenx&audience=familie-endringsmelding

### Swagger api kall med autentisering:
http://localhost:8099/swagger-ui.html
