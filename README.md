# Spring Boot Application

This is a Spring Boot application developed with **Java 21**. The application provides a service that is hosted on the following URL:

## Hosted URL

[http://47.129.232.0:8080/suggestions?q=](http://47.129.232.0:8080/suggestions?q=)

---

## Local Deployment

For local deployment, you will need the `secrets.properties` file.

To obtain the file, please send an email to:  
**[laisobert2@gmail.com](mailto:laisobert2@gmail.com)**

You can access the Swagger UI for API documentation at:
http://localhost:8080/swagger-ui/index.html#

## Notes

- The application uses **Supabase** as the database to make the project more scalable, instead of reading data from a static TSV file.
- The database uses the `LIKE` operator for fetching data, but the program can also fall back on selecting all data and applying fuzzy matching, rather than relying on the built-in search functionality of the `LIKE` operator.
- The project is hosted on **AWS EC2**.

## Result
`/suggestions?q=SomeRandomCityInTheMiddleOfNowhere`
![image](https://github.com/user-attachments/assets/b3671902-44a0-4c1c-a49d-d6fbe8aafa8e)

`/suggestions?q=SomeRandomCityInTheMiddleOfNowhere`
![image](https://github.com/user-attachments/assets/f7979698-82ea-488c-b976-852871540c02)


---
