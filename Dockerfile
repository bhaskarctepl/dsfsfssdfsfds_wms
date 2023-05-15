FROM maven:3-openjdk-8 AS build
ARG SPRING_PROFILE
WORKDIR /app
COPY . .
ENV SPRING_PROFILE $SPRING_PROFILE
RUN mvn clean package -DskipTests
RUN ls -al target/
EXPOSE 8080
CMD java -Dspring.profiles.active="$SPRING_PROFILE" -jar /app/target/wearables_mobile_services.jar