FROM lolhens/baseimage-openjre
ADD target/product-search-1.0-SNAPSHOT.jar product-search-1.0-SNAPSHOT.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "product-search-1.0-SNAPSHOT.jar"]
