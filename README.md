![AddrManager](http://img.verto.me/addrmanager-logo.png)

A sample project using spring boot with angularjs and semantic-ui

Demo: [http://addrmanager-spring-boot.herokuapp.com](http://addrmanager-spring-boot.herokuapp.com)

## Libraries

- `Maven`
- `Spring boot`
- `Spring Data Jpa`
- `HSQLDB`
- `Spring MVC`
- `AngularJs`
- `Semantic-UI`


## Running 

```shell
mvn spring-boot:run
```

Access the project via: http://localhost:8080/


## Using API

### ZipCode

- `GET /zipcodes/search?zipCode={zipCode}` - search for adress info by a zipcode

### Address

- `GET /addresses` - get all address
- `POST /addreses` - create a new address
- `GET /addresses/{id}` - get a specific address
- `PUT /addresses/{id}` - update an address
- `DELETE /addresses/{id}` - delete an address
