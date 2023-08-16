FROM tomcat:10-jdk21-openjdk

COPY build/libs/mailService-1.0.0.war /usr/local/tomcat/webapps/mail.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
