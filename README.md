
# to Java 21 and SpringBoot

This project contains the migration of the application to Java 21 and SpringBoot

After checking out the project go into the `kitchensinkh2` directory and run the steps below




## How to start the application

To run this project, you will need to run the following steps

    1. mvn clean install

    2. mvn spring-boot:run -X


## API Reference

#### Get all Members

```http
  GET /members/allMembers
```


#### Register a member

```http
  POST /members/register
```

Sample JSON iput 
```
{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "3333330000"
}
```


#### Run the test

```
 mvn -Dtest=RemoteMemberRegistrationIT.java test
```

#### install mondo DB

https://www.mongodb.com/docs/manual/installation/

if you want a UI tool please download Compass from Mongo DB website

#### start mongo DB instance
```
brew services start mongodb-community@8.0
```

#### stop mongo DB instance
```
brew services start mongodb-community@8.0
```
