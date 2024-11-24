# Spring Boot Application

This is a Spring Boot application developed with **Java 21**. The application provides a service that is hosted on the following URL:

## Hosted URL

[http://54.169.146.58:8080/suggestions?q=](http://54.169.146.58:8080/suggestions?q=)

---

## Local Deployment

For local deployment, you will need the `secrets.properties` file.

To obtain the file, please send an email to:  
**[laisobert2@gmail.com](mailto:laisobert2@gmail.com)**

## Notes

- The application uses **Supabase** as the database to make the project more scalable, instead of reading data from a static TSV file.
- The database uses the `LIKE` operator for fetching data, but the program can also fall back on selecting all data and applying fuzzy matching, rather than relying on the built-in search functionality of the `LIKE` operator.
- The project is hosted on **AWS EC2**.

---
