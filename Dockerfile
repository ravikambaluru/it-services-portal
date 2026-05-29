FROM tomcat:10.1.55
COPY target/it-services-portal.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
